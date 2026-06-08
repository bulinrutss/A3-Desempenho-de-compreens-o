package controller;

import dao.ProdutoDAO;
import modelo.Produto;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet responsável pela geração de Relatórios Gerenciais na interface web.
 * Processa requisições HTTP para gerar diversos relatórios de estoque e renderiza páginas JSP.
 * 
 * Mapeamento: /relatorios
 * 
 * Ações suportadas (via parâmetro action):
 * - menu           - Exibe menu de relatórios (padrão)
 * - lista-precos   - Gera lista de preços de todos os produtos
 * - abaixo-minimo  - Gera relatório de produtos abaixo do estoque mínimo
 * - acima-maximo   - Gera relatório de produtos acima do estoque máximo
 * 
 * @author
 */
@WebServlet("/relatorios")
public class RelatorioServlet extends HttpServlet {

    private ProdutoDAO produtoDAO = new ProdutoDAO();

    /**
     * Processa requisições GET para exibir menu e gerar relatórios.
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
            action = "menu";
        }

        switch (action) {
            case "lista-precos":
                gerarListaPrecos(request, response);
                break;
            case "abaixo-minimo":
                gerarProdutosAbaixoMinimo(request, response);
                break;
            case "acima-maximo":
                gerarProdutosAcimaMaximo(request, response);
                break;
            default:
                mostrarMenu(request, response);
                break;
        }
    }

    /**
     * Exibe o menu principal de relatórios disponíveis.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void mostrarMenu(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/relatorios.jsp").forward(request, response);
    }

    /**
     * Gera relatório de lista de preços com todos os produtos cadastrados.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void gerarListaPrecos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Produto> listaProdutos = produtoDAO.listarTodos();
        request.setAttribute("listaProdutos", listaProdutos);
        request.setAttribute("tipoRelatorio", "Lista de Preços");
        request.getRequestDispatcher("/relatorio-lista-precos.jsp").forward(request, response);
    }

    /**
     * Gera relatório de produtos com estoque abaixo do mínimo.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void gerarProdutosAbaixoMinimo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Produto> listaProdutos = produtoDAO.listarAbaixoMinimo();
        request.setAttribute("listaProdutos", listaProdutos);
        request.setAttribute("tipoRelatorio", "Produtos Abaixo do Mínimo");
        request.getRequestDispatcher("/relatorio-produtos.jsp").forward(request, response);
    }

    /**
     * Gera relatório de produtos com estoque acima do máximo.
     * 
     * @param request Objeto HttpServletRequest contendo a requisição do cliente.
     * @param response Objeto HttpServletResponse para enviar a resposta ao cliente.
     * @throws ServletException Se ocorrer erro no processamento do servlet.
     * @throws IOException Se ocorrer erro de entrada/saída.
     */
    private void gerarProdutosAcimaMaximo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Produto> listaProdutos = produtoDAO.listarAcimaMaximo();
        request.setAttribute("listaProdutos", listaProdutos);
        request.setAttribute("tipoRelatorio", "Produtos Acima do Máximo");
        request.getRequestDispatcher("/relatorio-produtos.jsp").forward(request, response);
    }
}
