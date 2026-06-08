package controller;

import dao.ProdutoDAO;
import modelo.Categoria;
import modelo.Produto;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RelatorioServletTest {

    @Test
    void doGetSemAction_deveMostrarMenu() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("action")).thenReturn(null);
        when(request.getRequestDispatcher("/relatorios.jsp")).thenReturn(dispatcher);

        new RelatorioServlet().doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void doGetListaPrecos_deveGerarRelatorio() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        List<Produto> produtos = Collections.singletonList(
                new Produto(1, "Arroz", 10.0, "KG", 5, 2, 20, new Categoria(1, "Alimentos", "1kg", "Pacote"))
        );

        when(request.getParameter("action")).thenReturn("lista-precos");
        when(request.getRequestDispatcher("/relatorio-lista-precos.jsp")).thenReturn(dispatcher);

        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.listarTodos()).thenReturn(produtos))) {
            new RelatorioServlet().doGet(request, response);
        }

        verify(request).setAttribute("listaProdutos", produtos);
        verify(request).setAttribute("tipoRelatorio", "Lista de Preços");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doGetAbaixoMinimo_deveGerarRelatorio() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        Produto abaixo = new Produto(2, "Feijao", 7.0, "KG", 1, 5, 30, null);

        when(request.getParameter("action")).thenReturn("abaixo-minimo");
        when(request.getRequestDispatcher("/relatorio-produtos.jsp")).thenReturn(dispatcher);

        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.listarAbaixoMinimo()).thenReturn(Arrays.asList(abaixo)))) {
            new RelatorioServlet().doGet(request, response);
        }

        verify(request).setAttribute("tipoRelatorio", "Produtos Abaixo do Mínimo");
        verify(dispatcher).forward(request, response);
    }
}
