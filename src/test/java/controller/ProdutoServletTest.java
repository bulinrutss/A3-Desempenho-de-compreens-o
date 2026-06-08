package controller;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import modelo.Categoria;
import modelo.Produto;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProdutoServletTest {

    @Test
    void doGetSemAction_deveListarProdutos() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("action")).thenReturn(null);
        when(request.getRequestDispatcher("/produtos.jsp")).thenReturn(dispatcher);

        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.listarTodos()).thenReturn(Collections.emptyList()))) {
            new ProdutoServlet().doGet(request, response);
        }

        verify(request).setAttribute("listaProdutos", Collections.emptyList());
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doGetComActionNovo_deveCarregarCategoriasNoFormulario() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        Categoria categoria = new Categoria(1, "Bebidas", "500ml", "Garrafa");

        when(request.getParameter("action")).thenReturn("novo");
        when(request.getRequestDispatcher("/produto-form.jsp")).thenReturn(dispatcher);

        try (MockedConstruction<CategoriaDAO> ignored = mockConstruction(CategoriaDAO.class, (mock, ctx) ->
                when(mock.listarTodas()).thenReturn(Collections.singletonList(categoria)))) {
            new ProdutoServlet().doGet(request, response);
        }

        verify(request).setAttribute("listaCategorias", Collections.singletonList(categoria));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPostComActionSalvar_deveInserirProduto() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Categoria categoria = new Categoria(1, "Bebidas", "500ml", "Garrafa");

        when(request.getParameter("action")).thenReturn("salvar");
        when(request.getParameter("nome")).thenReturn("Suco");
        when(request.getParameter("precoUnitario")).thenReturn("4.50");
        when(request.getParameter("unidade")).thenReturn("UN");
        when(request.getParameter("quantidade")).thenReturn("10");
        when(request.getParameter("quantidadeMinima")).thenReturn("2");
        when(request.getParameter("quantidadeMaxima")).thenReturn("50");
        when(request.getParameter("categoriaId")).thenReturn("1");

        try (MockedConstruction<ProdutoDAO> produtoMock = mockConstruction(ProdutoDAO.class);
             MockedConstruction<CategoriaDAO> categoriaMock = mockConstruction(CategoriaDAO.class, (mock, ctx) ->
                     when(mock.buscarPorId(1)).thenReturn(categoria))) {

            new ProdutoServlet().doPost(request, response);
            verify(produtoMock.constructed().get(0)).inserir(org.mockito.ArgumentMatchers.any(Produto.class));
        }

        verify(response).sendRedirect("produtos");
    }
}
