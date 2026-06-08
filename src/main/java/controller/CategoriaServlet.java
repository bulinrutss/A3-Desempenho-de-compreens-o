package controller;

import dao.CategoriaDAO;
import modelo.Categoria;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet responsável pelo gerenciamento de Categorias na interface web.
 * Processa requisições HTTP para operações CRUD de categorias e renderiza páginas JSP.
 * 
 * Mapeamento: /categorias
 * 
 * Ações suportadas (via parâmetro action):
 * - listar  - Lista todas as categorias (padrão)
 * - novo    - Exibe formulário de criação
 * - editar  - Exibe formulário de edição
 * - excluir - Remove uma categoria
 * - salvar  - Salva nova categoria (POST)
 * - atualizar - Atualiza categoria existente (POST)
 * 
 * @author
 */
@WebServlet("/categorias")
public class CategoriaServlet extends HttpServlet {

    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    /**
     * Processa requisições GET para exibir listagens e formulários de categorias.
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
                excluirCategoria(request, response);
                break;
            default:
                listarCategorias(request, response);
                break;
        }
    }

    /**
     * Processa requisições POST para salvar ou atualizar categorias.
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
            salvarCategoria(request, response);
        } else if ("atualizar".equals(action)) {
            atualizarCategoria(request, response);
        }
    }

    /**
     * Lista todas as categorias cadastradas e encaminha para a página de listagem.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void listarCategorias(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Categoria> listaCategorias = categoriaDAO.listarTodas();
        request.setAttribute("listaCategorias", listaCategorias);
        request.getRequestDispatcher("/categorias.jsp").forward(request, response);
    }

    /**
     * Exibe o formulário para criação de uma nova categoria.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/categoria-form.jsp").forward(request, response);
    }

    /**
     * Exibe o formulário de edição preenchido com os dados da categoria.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Categoria categoria = categoriaDAO.buscarPorId(id);
        request.setAttribute("categoria", categoria);
        request.getRequestDispatcher("/categoria-form.jsp").forward(request, response);
    }

    /**
     * Salva uma nova categoria no banco de dados.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void salvarCategoria(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String nome = request.getParameter("nome");
        String tamanho = request.getParameter("tamanho");
        String embalagem = request.getParameter("embalagem");

        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoria.setTamanho(tamanho);
        categoria.setEmbalagem(embalagem);

        categoriaDAO.inserir(categoria);
        response.sendRedirect("categorias");
    }

    /**
     * Atualiza os dados de uma categoria existente no banco de dados.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void atualizarCategoria(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String tamanho = request.getParameter("tamanho");
        String embalagem = request.getParameter("embalagem");

        Categoria categoria = new Categoria(id, nome, tamanho, embalagem);
        categoriaDAO.atualizar(categoria);
        response.sendRedirect("categorias");
    }

    /**
     * Exclui uma categoria do banco de dados pelo seu ID.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void excluirCategoria(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        categoriaDAO.excluir(id);
        response.sendRedirect("categorias");
    }
}
