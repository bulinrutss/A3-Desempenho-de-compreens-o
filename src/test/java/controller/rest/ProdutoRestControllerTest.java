package controller.rest;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import modelo.Categoria;
import modelo.Produto;
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

class ProdutoRestControllerTest {

    @Test
    void listarTodos_deveRetornarOkComLista() {
        List<Produto> produtos = Arrays.asList(
                new Produto(1, "Arroz", 10.0, "KG", 5, 2, 20, null)
        );

        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.listarTodos()).thenReturn(produtos))) {

            Response response = new ProdutoRestController().listarTodos();
            assertEquals(200, response.getStatus());
            assertEquals(produtos, response.getEntity());
        }
    }

    @Test
    void buscarPorId_deveRetornarOkQuandoEncontrado() {
        Produto produto = new Produto(1, "Arroz", 10.0, "KG", 5, 2, 20, null);

        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.buscarPorId(1)).thenReturn(produto))) {

            Response response = new ProdutoRestController().buscarPorId(1);
            assertEquals(200, response.getStatus());
            assertEquals(produto, response.getEntity());
        }
    }

    @Test
    void buscarPorId_deveRetornarNotFoundQuandoNaoEncontrado() {
        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.buscarPorId(99)).thenReturn(null))) {

            Response response = new ProdutoRestController().buscarPorId(99);
            assertEquals(404, response.getStatus());
        }
    }

    @Test
    void criar_deveRetornarBadRequestQuandoCategoriaNaoExiste() {
        ProdutoRestController.ProdutoDTO dto = new ProdutoRestController.ProdutoDTO();
        dto.setNome("Feijao");
        dto.setPrecoUnitario(8.0);
        dto.setUnidade("KG");
        dto.setQuantidade(10);
        dto.setQuantidadeMinima(2);
        dto.setQuantidadeMaxima(50);
        dto.setCategoriaId(99);

        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class);
             MockedConstruction<CategoriaDAO> ignored2 = mockConstruction(CategoriaDAO.class, (mock, ctx) ->
                     when(mock.buscarPorId(99)).thenReturn(null))) {

            Response response = new ProdutoRestController().criar(dto);
            assertEquals(400, response.getStatus());
        }
    }

    @Test
    void criar_deveRetornarCreatedQuandoDadosValidos() {
        Categoria categoria = new Categoria(1, "Alimentos", "1kg", "Pacote");
        ProdutoRestController.ProdutoDTO dto = new ProdutoRestController.ProdutoDTO();
        dto.setNome("Feijao");
        dto.setPrecoUnitario(8.0);
        dto.setUnidade("KG");
        dto.setQuantidade(10);
        dto.setQuantidadeMinima(2);
        dto.setQuantidadeMaxima(50);
        dto.setCategoriaId(1);

        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class);
             MockedConstruction<CategoriaDAO> ignored2 = mockConstruction(CategoriaDAO.class, (mock, ctx) ->
                     when(mock.buscarPorId(1)).thenReturn(categoria))) {

            Response response = new ProdutoRestController().criar(dto);
            assertEquals(201, response.getStatus());
        }
    }

    @Test
    void atualizar_deveRetornarOkQuandoDadosValidos() {
        Categoria categoria = new Categoria(1, "Alimentos", "1kg", "Pacote");
        Produto existente = new Produto(1, "Arroz", 10.0, "KG", 5, 2, 20, categoria);
        ProdutoRestController.ProdutoDTO dto = new ProdutoRestController.ProdutoDTO();
        dto.setNome("Arroz Integral");
        dto.setPrecoUnitario(12.0);
        dto.setUnidade("KG");
        dto.setQuantidade(8);
        dto.setQuantidadeMinima(2);
        dto.setQuantidadeMaxima(30);
        dto.setCategoriaId(1);

        try (MockedConstruction<ProdutoDAO> produtoMock = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                     when(mock.buscarPorId(1)).thenReturn(existente));
             MockedConstruction<CategoriaDAO> categoriaMock = mockConstruction(CategoriaDAO.class, (mock, ctx) ->
                     when(mock.buscarPorId(1)).thenReturn(categoria))) {

            Response response = new ProdutoRestController().atualizar(1, dto);
            assertEquals(200, response.getStatus());
            verify(produtoMock.constructed().get(0)).atualizar(org.mockito.ArgumentMatchers.any(Produto.class));
        }
    }

    @Test
    void atualizar_deveRetornarNotFoundQuandoProdutoNaoExiste() {
        ProdutoRestController.ProdutoDTO dto = new ProdutoRestController.ProdutoDTO();
        dto.setCategoriaId(1);

        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.buscarPorId(99)).thenReturn(null))) {

            Response response = new ProdutoRestController().atualizar(99, dto);
            assertEquals(404, response.getStatus());
        }
    }

    @Test
    void atualizar_deveRetornarBadRequestQuandoCategoriaNaoExiste() {
        Produto existente = new Produto(1, "Arroz", 10.0, "KG", 5, 2, 20, null);
        ProdutoRestController.ProdutoDTO dto = new ProdutoRestController.ProdutoDTO();
        dto.setCategoriaId(99);

        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                     when(mock.buscarPorId(1)).thenReturn(existente));
             MockedConstruction<CategoriaDAO> ignored2 = mockConstruction(CategoriaDAO.class, (mock, ctx) ->
                     when(mock.buscarPorId(99)).thenReturn(null))) {

            Response response = new ProdutoRestController().atualizar(1, dto);
            assertEquals(400, response.getStatus());
        }
    }

    @Test
    void excluir_deveRetornarOkQuandoProdutoExiste() {
        Produto produto = new Produto(7, "Feijao", 8.0, "KG", 10, 2, 50, null);

        try (MockedConstruction<ProdutoDAO> mocked = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.buscarPorId(7)).thenReturn(produto))) {

            Response response = new ProdutoRestController().excluir(7);
            assertEquals(200, response.getStatus());
            verify(mocked.constructed().get(0)).excluir(7);
        }
    }

    @Test
    void excluir_deveRetornarNotFoundQuandoProdutoNaoExiste() {
        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.buscarPorId(7)).thenReturn(null))) {

            Response response = new ProdutoRestController().excluir(7);
            assertEquals(404, response.getStatus());
        }
    }

    @Test
    void dto_deveManterGettersESetters() {
        ProdutoRestController.ProdutoDTO dto = new ProdutoRestController.ProdutoDTO();
        dto.setNome("Arroz");
        dto.setPrecoUnitario(10.0);
        dto.setUnidade("KG");
        dto.setQuantidade(5);
        dto.setQuantidadeMinima(2);
        dto.setQuantidadeMaxima(20);
        dto.setCategoriaId(1);

        assertEquals("Arroz", dto.getNome());
        assertEquals(10.0, dto.getPrecoUnitario());
        assertEquals("KG", dto.getUnidade());
        assertEquals(5, dto.getQuantidade());
        assertEquals(2, dto.getQuantidadeMinima());
        assertEquals(20, dto.getQuantidadeMaxima());
        assertEquals(1, dto.getCategoriaId());

        ProdutoRestController.ErrorResponse error = new ProdutoRestController.ErrorResponse("erro");
        assertEquals("erro", error.getMessage());
        assertEquals("error", error.getStatus());

        ProdutoRestController.SuccessResponse success = new ProdutoRestController.SuccessResponse("ok");
        assertEquals("ok", success.getMessage());
        assertNotNull(success.getStatus());
    }
}
