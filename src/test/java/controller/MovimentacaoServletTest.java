package controller;

import dao.MovimentacaoDAO;
import dao.ProdutoDAO;
import modelo.Movimentacao;
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

class MovimentacaoServletTest {

    @Test
    void doGetSemAction_deveListarMovimentacoes() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("action")).thenReturn(null);
        when(request.getRequestDispatcher("/movimentacoes.jsp")).thenReturn(dispatcher);

        try (MockedConstruction<MovimentacaoDAO> ignored = mockConstruction(MovimentacaoDAO.class, (mock, ctx) ->
                when(mock.listarTodas()).thenReturn(Collections.emptyList()))) {
            new MovimentacaoServlet().doGet(request, response);
        }

        verify(request).setAttribute("listaMovimentacoes", Collections.emptyList());
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_deveRegistrarMovimentacaoERedirecionar() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Produto produto = new Produto(1, "Cafe", 8.0, "UN", 20, 5, 100, null);

        when(request.getParameter("tipo")).thenReturn("ENTRADA");
        when(request.getParameter("quantidade")).thenReturn("5");
        when(request.getParameter("produtoId")).thenReturn("1");

        try (MockedConstruction<MovimentacaoDAO> movMock = mockConstruction(MovimentacaoDAO.class);
             MockedConstruction<ProdutoDAO> prodMock = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                     when(mock.buscarPorId(1)).thenReturn(produto))) {

            new MovimentacaoServlet().doPost(request, response);
            verify(movMock.constructed().get(0)).registrarMovimento(org.mockito.ArgumentMatchers.any(Movimentacao.class));
        }

        verify(response).sendRedirect("movimentacoes");
    }
}
