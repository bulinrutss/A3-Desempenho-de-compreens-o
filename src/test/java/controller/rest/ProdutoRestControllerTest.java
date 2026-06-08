package controller.rest;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import modelo.Categoria;
import modelo.Produto;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

class ProdutoRestControllerTest {

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
    void excluir_deveRetornarNotFoundQuandoProdutoNaoExiste() {
        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.buscarPorId(7)).thenReturn(null))) {

            Response response = new ProdutoRestController().excluir(7);
            assertEquals(404, response.getStatus());
        }
    }
}
