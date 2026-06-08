package controller;

import dao.CategoriaDAO;
import modelo.Categoria;
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

class CategoriaServletTest {

    @Test
    void doGetSemAction_deveListarCategorias() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("action")).thenReturn(null);
        when(request.getRequestDispatcher("/categorias.jsp")).thenReturn(dispatcher);

        try (MockedConstruction<CategoriaDAO> ignored = mockConstruction(CategoriaDAO.class, (mock, ctx) ->
                when(mock.listarTodas()).thenReturn(Collections.emptyList()))) {
            new CategoriaServlet().doGet(request, response);
        }

        verify(request).setAttribute("listaCategorias", Collections.emptyList());
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doGetComActionExcluir_deveRedirecionar() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("action")).thenReturn("excluir");
        when(request.getParameter("id")).thenReturn("1");

        try (MockedConstruction<CategoriaDAO> ignored = mockConstruction(CategoriaDAO.class)) {
            new CategoriaServlet().doGet(request, response);
        }

        verify(response).sendRedirect("categorias");
    }

    @Test
    void doPostComActionSalvar_deveInserirERedirecionar() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("action")).thenReturn("salvar");
        when(request.getParameter("nome")).thenReturn("Bebidas");
        when(request.getParameter("tamanho")).thenReturn("500ml");
        when(request.getParameter("embalagem")).thenReturn("Garrafa");

        try (MockedConstruction<CategoriaDAO> mocked = mockConstruction(CategoriaDAO.class)) {
            new CategoriaServlet().doPost(request, response);
            verify(mocked.constructed().get(0)).inserir(org.mockito.ArgumentMatchers.any(Categoria.class));
        }

        verify(response).sendRedirect("categorias");
    }
}
