package controller.rest;

import dao.CategoriaDAO;
import modelo.Categoria;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoriaRestControllerTest {

    @Test
    void listarTodas_deveRetornarOkComLista() {
        List<Categoria> categorias = Arrays.asList(
                new Categoria(1, "Bebidas", "500ml", "Garrafa")
        );

        try (MockedConstruction<CategoriaDAO> ignored = mockConstruction(CategoriaDAO.class, (mock, ctx) ->
                when(mock.listarTodas()).thenReturn(categorias))) {

            Response response = new CategoriaRestController().listarTodas();
            assertEquals(200, response.getStatus());
            assertEquals(categorias, response.getEntity());
        }
    }

    @Test
    void buscarPorId_deveRetornarNotFoundQuandoNaoExiste() {
        try (MockedConstruction<CategoriaDAO> ignored = mockConstruction(CategoriaDAO.class, (mock, ctx) ->
                when(mock.buscarPorId(99)).thenReturn(null))) {

            Response response = new CategoriaRestController().buscarPorId(99);
            assertEquals(404, response.getStatus());
        }
    }

    @Test
    void criar_deveRetornarCreated() {
        CategoriaRestController.CategoriaDTO dto = new CategoriaRestController.CategoriaDTO();
        dto.setNome("Laticinios");
        dto.setTamanho("1L");
        dto.setEmbalagem("Caixa");

        try (MockedConstruction<CategoriaDAO> mocked = mockConstruction(CategoriaDAO.class)) {
            Response response = new CategoriaRestController().criar(dto);
            assertEquals(201, response.getStatus());
            verify(mocked.constructed().get(0)).inserir(org.mockito.ArgumentMatchers.any(Categoria.class));
        }
    }

    @Test
    void dto_deveManterGettersESetters() {
        CategoriaRestController.CategoriaDTO dto = new CategoriaRestController.CategoriaDTO();
        dto.setNome("Higiene");
        dto.setTamanho("200ml");
        dto.setEmbalagem("Frasco");

        assertEquals("Higiene", dto.getNome());
        assertEquals("200ml", dto.getTamanho());
        assertEquals("Frasco", dto.getEmbalagem());

        CategoriaRestController.ErrorResponse error = new CategoriaRestController.ErrorResponse("erro");
        assertEquals("erro", error.getMessage());
        assertEquals("error", error.getStatus());

        CategoriaRestController.SuccessResponse success = new CategoriaRestController.SuccessResponse("ok");
        assertEquals("ok", success.getMessage());
        assertNotNull(success.getStatus());
    }
}
