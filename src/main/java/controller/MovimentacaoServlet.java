package controller;

import dao.MovimentacaoDAO;
import dao.ProdutoDAO;
import modelo.Movimentacao;
import modelo.Produto;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet responsável pelo gerenciamento de Movimentações de Estoque na interface web.
 * Processa requisições HTTP para registro de entradas e saídas de produtos e renderiza páginas JSP.
 * 
 * Mapeamento: /movimentacoes
 * 
 * Ações suportadas (via parâmetro action):
 * - listar - Lista todas as movimentações (padrão)
 * - novo   - Exibe formulário para registrar nova movimentação
 * - POST   - Registra uma nova movimentação de estoque
 * 
 * @author
 */
@WebServlet("/movimentacoes")
public class MovimentacaoServlet extends HttpServlet {

    private MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    /**
     * Processa requisições GET para exibir listagens e formulários de movimentações.
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
            default:
                listarMovimentacoes(request, response);
                break;
        }
    }

    /**
     * Processa requisições POST para registrar novas movimentações de estoque.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        registrarMovimento(request, response);
    }

    /**
     * Lista todas as movimentações cadastradas e encaminha para a página de listagem.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void listarMovimentacoes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Movimentacao> listaMovimentacoes = movimentacaoDAO.listarTodas();
        request.setAttribute("listaMovimentacoes", listaMovimentacoes);
        request.getRequestDispatcher("/movimentacoes.jsp").forward(request, response);
    }

    /**
     * Exibe o formulário para registro de nova movimentação com lista de produtos disponíveis.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Produto> listaProdutos = produtoDAO.listarTodos();
        request.setAttribute("listaProdutos", listaProdutos);
        request.getRequestDispatcher("/movimentacao-form.jsp").forward(request, response);
    }

    /**
     * Registra uma nova movimentação (entrada ou saída) de estoque.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void registrarMovimento(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String tipo = request.getParameter("tipo");
        int quantidade = Integer.parseInt(request.getParameter("quantidade"));
        int produtoId = Integer.parseInt(request.getParameter("produtoId"));

        Produto produto = produtoDAO.buscarPorId(produtoId);
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setTipo(tipo);
        movimentacao.setQuantidade(quantidade);
        movimentacao.setProduto(produto);

        movimentacaoDAO.registrarMovimento(movimentacao);
        response.sendRedirect("movimentacoes");
    }
}
