package controller.rest;

import dao.MovimentacaoDAO;
import dao.ProdutoDAO;
import modelo.Produto;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import javax.ws.rs.core.Response;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

class MovimentacaoRestControllerTest {

    @Test
    void registrar_deveRetornarBadRequestQuandoProdutoNaoExiste() {
        MovimentacaoRestController.MovimentacaoDTO dto = new MovimentacaoRestController.MovimentacaoDTO();
        dto.setTipo("ENTRADA");
        dto.setQuantidade(5);
        dto.setProdutoId(99);

        try (MockedConstruction<MovimentacaoDAO> ignored = mockConstruction(MovimentacaoDAO.class);
             MockedConstruction<ProdutoDAO> ignored2 = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                     when(mock.buscarPorId(99)).thenReturn(null))) {

            Response response = new MovimentacaoRestController().registrar(dto);
            assertEquals(400, response.getStatus());
        }
    }

    @Test
    void registrar_deveRetornarBadRequestQuandoEstoqueInsuficiente() {
        Produto produto = new Produto(1, "Cafe", 8.0, "UN", 2, 1, 100, null);
        MovimentacaoRestController.MovimentacaoDTO dto = new MovimentacaoRestController.MovimentacaoDTO();
        dto.setTipo("SAIDA");
        dto.setQuantidade(5);
        dto.setProdutoId(1);

        try (MockedConstruction<MovimentacaoDAO> ignored = mockConstruction(MovimentacaoDAO.class);
             MockedConstruction<ProdutoDAO> ignored2 = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                     when(mock.buscarPorId(1)).thenReturn(produto))) {

            Response response = new MovimentacaoRestController().registrar(dto);
            assertEquals(400, response.getStatus());
        }
    }

    @Test
    void registrar_deveRetornarCreatedQuandoEntradaValida() {
        Produto produto = new Produto(1, "Cafe", 8.0, "UN", 10, 1, 100, null);
        MovimentacaoRestController.MovimentacaoDTO dto = new MovimentacaoRestController.MovimentacaoDTO();
        dto.setTipo("ENTRADA");
        dto.setQuantidade(5);
        dto.setProdutoId(1);

        try (MockedConstruction<MovimentacaoDAO> ignored = mockConstruction(MovimentacaoDAO.class);
             MockedConstruction<ProdutoDAO> ignored2 = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                     when(mock.buscarPorId(1)).thenReturn(produto))) {

            Response response = new MovimentacaoRestController().registrar(dto);
            assertEquals(201, response.getStatus());
        }
    }

    @Test
    void listarPorProduto_deveRetornarNotFoundQuandoProdutoNaoExiste() {
        try (MockedConstruction<MovimentacaoDAO> ignored = mockConstruction(MovimentacaoDAO.class);
             MockedConstruction<ProdutoDAO> ignored2 = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                     when(mock.buscarPorId(3)).thenReturn(null))) {

            Response response = new MovimentacaoRestController().listarPorProduto(3);
            assertEquals(404, response.getStatus());
        }
    }

    @Test
    void listarTodas_deveRetornarOk() {
        try (MockedConstruction<MovimentacaoDAO> ignored = mockConstruction(MovimentacaoDAO.class, (mock, ctx) ->
                when(mock.listarTodas()).thenReturn(Collections.emptyList()))) {

            Response response = new MovimentacaoRestController().listarTodas();
            assertEquals(200, response.getStatus());
        }
    }
}
