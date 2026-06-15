package controller.rest;

import dao.MovimentacaoDAO;
import dao.ProdutoDAO;
import modelo.Movimentacao;
import modelo.Produto;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

class MovimentacaoRestControllerTest {

    @Test
    void buscarPorId_deveRetornarOkQuandoEncontrada() {
        Movimentacao movimentacao = new Movimentacao(1, "ENTRADA", 5,
                LocalDateTime.of(2026, 3, 10, 12, 0), new Produto(1, "Cafe", 8.0, "UN", 10, 2, 100, null));

        try (MockedConstruction<MovimentacaoDAO> ignored = mockConstruction(MovimentacaoDAO.class, (mock, ctx) ->
                when(mock.buscarPorId(1)).thenReturn(movimentacao))) {

            Response response = new MovimentacaoRestController().buscarPorId(1);
            assertEquals(200, response.getStatus());
            assertEquals(movimentacao, response.getEntity());
        }
    }

    @Test
    void buscarPorId_deveRetornarNotFoundQuandoNaoEncontrada() {
        try (MockedConstruction<MovimentacaoDAO> ignored = mockConstruction(MovimentacaoDAO.class, (mock, ctx) ->
                when(mock.buscarPorId(99)).thenReturn(null))) {

            Response response = new MovimentacaoRestController().buscarPorId(99);
            assertEquals(404, response.getStatus());
        }
    }

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
    void listarPorProduto_deveRetornarOkQuandoProdutoExiste() {
        Produto produto = new Produto(3, "Cafe", 8.0, "UN", 10, 2, 100, null);
        List<Movimentacao> movimentacoes = Collections.singletonList(
                new Movimentacao(1, "ENTRADA", 5, LocalDateTime.of(2026, 3, 10, 12, 0), produto)
        );

        try (MockedConstruction<MovimentacaoDAO> movMock = mockConstruction(MovimentacaoDAO.class, (mock, ctx) ->
                     when(mock.listarPorProduto(3)).thenReturn(movimentacoes));
             MockedConstruction<ProdutoDAO> prodMock = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                     when(mock.buscarPorId(3)).thenReturn(produto))) {

            Response response = new MovimentacaoRestController().listarPorProduto(3);
            assertEquals(200, response.getStatus());
            assertEquals(movimentacoes, response.getEntity());
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

    @Test
    void dto_deveManterGettersESetters() {
        MovimentacaoRestController.MovimentacaoDTO dto = new MovimentacaoRestController.MovimentacaoDTO();
        dto.setTipo("SAIDA");
        dto.setQuantidade(3);
        dto.setProdutoId(1);

        assertEquals("SAIDA", dto.getTipo());
        assertEquals(3, dto.getQuantidade());
        assertEquals(1, dto.getProdutoId());

        MovimentacaoRestController.ErrorResponse error = new MovimentacaoRestController.ErrorResponse("erro");
        assertEquals("erro", error.getMessage());
        assertEquals("error", error.getStatus());

        MovimentacaoRestController.SuccessResponse success = new MovimentacaoRestController.SuccessResponse("ok");
        assertEquals("ok", success.getMessage());
        assertNotNull(success.getStatus());
    }
}
