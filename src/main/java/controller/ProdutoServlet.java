package controller;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import modelo.Categoria;
import modelo.Produto;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet responsável pelo gerenciamento de Produtos na interface web.
 * Processa requisições HTTP para operações CRUD de produtos e renderiza páginas JSP.
 * 
 * Mapeamento: /produtos
 * 
 * Ações suportadas (via parâmetro action):
 * - listar  - Lista todos os produtos (padrão)
 * - novo    - Exibe formulário de criação
 * - editar  - Exibe formulário de edição
 * - excluir - Remove um produto
 * - salvar  - Salva novo produto (POST)
 * - atualizar - Atualiza produto existente (POST)
 * 
 * @author
 */
@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {

    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    /**
     * Processa requisições GET para exibir listagens e formulários de produtos.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "listar";
        }

        switch (action) {
            case "novo":
                mostrarFormularioNovo(request, response);
                break;
            case "editar":
                mostrarFormularioEditar(request, response);
                break;
            case "excluir":
                excluirProduto(request, response);
                break;
            default:
                listarProdutos(request, response);
                break;
        }
    }

    /**
     * Processa requisições POST para salvar ou atualizar produtos.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("salvar".equals(action)) {
            salvarProduto(request, response);
        } else if ("atualizar".equals(action)) {
            atualizarProduto(request, response);
        }
    }

    /**
     * Lista todos os produtos cadastrados e encaminha para a página de listagem.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void listarProdutos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Produto> listaProdutos = produtoDAO.listarTodos();
        request.setAttribute("listaProdutos", listaProdutos);
        request.getRequestDispatcher("/produtos.jsp").forward(request, response);
    }

    /**
     * Exibe o formulário para criação de um novo produto com lista de categorias disponíveis.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Categoria> listaCategorias = categoriaDAO.listarTodas();
        request.setAttribute("listaCategorias", listaCategorias);
        request.getRequestDispatcher("/produto-form.jsp").forward(request, response);
    }

    /**
     * Exibe o formulário de edição preenchido com os dados do produto.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Produto produto = produtoDAO.buscarPorId(id);
        List<Categoria> listaCategorias = categoriaDAO.listarTodas();
        request.setAttribute("produto", produto);
        request.setAttribute("listaCategorias", listaCategorias);
        request.getRequestDispatcher("/produto-form.jsp").forward(request, response);
    }

    /**
     * Salva um novo produto no banco de dados.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void salvarProduto(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String nome = request.getParameter("nome");
        double precoUnitario = Double.parseDouble(request.getParameter("precoUnitario"));
        String unidade = request.getParameter("unidade");
        int quantidade = Integer.parseInt(request.getParameter("quantidade"));
        int quantidadeMinima = Integer.parseInt(request.getParameter("quantidadeMinima"));
        int quantidadeMaxima = Integer.parseInt(request.getParameter("quantidadeMaxima"));
        int categoriaId = Integer.parseInt(request.getParameter("categoriaId"));

        Categoria categoria = categoriaDAO.buscarPorId(categoriaId);
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setPrecoUnitario(precoUnitario);
        produto.setUnidade(unidade);
        produto.setQuantidade(quantidade);
        produto.setQuantidadeMinima(quantidadeMinima);
        produto.setQuantidadeMaxima(quantidadeMaxima);
        produto.setCategoria(categoria);

        produtoDAO.inserir(produto);
        response.sendRedirect("produtos");
    }

    /**
     * Atualiza os dados de um produto existente no banco de dados.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void atualizarProduto(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        double precoUnitario = Double.parseDouble(request.getParameter("precoUnitario"));
        String unidade = request.getParameter("unidade");
        int quantidade = Integer.parseInt(request.getParameter("quantidade"));
        int quantidadeMinima = Integer.parseInt(request.getParameter("quantidadeMinima"));
        int quantidadeMaxima = Integer.parseInt(request.getParameter("quantidadeMaxima"));
        int categoriaId = Integer.parseInt(request.getParameter("categoriaId"));

        Categoria categoria = categoriaDAO.buscarPorId(categoriaId);
        Produto produto = new Produto(id, nome, precoUnitario, unidade, quantidade, quantidadeMinima, quantidadeMaxima, categoria);
        produtoDAO.atualizar(produto);
        response.sendRedirect("produtos");
    }

    /**
     * Exclui um produto do banco de dados pelo seu ID.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void excluirProduto(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        produtoDAO.excluir(id);
        response.sendRedirect("produtos");
    }
}
