package controller.rest;

import dao.ProdutoDAO;
import modelo.Categoria;
import modelo.Produto;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

class RelatorioRestControllerTest {

    private List<Produto> criarProdutosExemplo() {
        Categoria alimentos = new Categoria(1, "Alimentos", "1kg", "Pacote");
        return Arrays.asList(
                new Produto(1, "Arroz", 10.0, "KG", 3, 5, 20, alimentos),
                new Produto(2, "Feijao", 8.0, "KG", 15, 5, 30, alimentos),
                new Produto(3, "Acucar", 4.0, "KG", 25, 10, 20, alimentos)
        );
    }

    @Test
    void produtosAbaixoMinimo_deveRetornarApenasItensAbaixoDoMinimo() {
        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.listarTodos()).thenReturn(criarProdutosExemplo()))) {

            Response response = new RelatorioRestController().produtosAbaixoMinimo();
            Map<?, ?> resultado = (Map<?, ?>) response.getEntity();

            assertEquals(200, response.getStatus());
            assertEquals(1, resultado.get("total"));
        }
    }

    @Test
    void produtosAcimaMaximo_deveRetornarApenasItensAcimaDoMaximo() {
        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.listarTodos()).thenReturn(criarProdutosExemplo()))) {

            Response response = new RelatorioRestController().produtosAcimaMaximo();
            Map<?, ?> resultado = (Map<?, ?>) response.getEntity();

            assertEquals(200, response.getStatus());
            assertEquals(1, resultado.get("total"));
        }
    }

    @Test
    void listaPrecos_deveCalcularValorTotalDoEstoque() {
        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.listarTodos()).thenReturn(criarProdutosExemplo()))) {

            Response response = new RelatorioRestController().listaPrecos();
            Map<?, ?> resultado = (Map<?, ?>) response.getEntity();

            assertEquals(200, response.getStatus());
            assertEquals(250.0, resultado.get("valorTotalEstoque"));
        }
    }

    @Test
    void dashboard_deveRetornarResumoDoSistema() {
        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.listarTodos()).thenReturn(criarProdutosExemplo()))) {

            Response response = new RelatorioRestController().dashboard();
            Map<?, ?> dashboard = (Map<?, ?>) response.getEntity();

            assertEquals(200, response.getStatus());
            assertEquals(3, dashboard.get("totalProdutos"));
            assertEquals(1L, dashboard.get("produtosAbaixoMinimo"));
            assertEquals(1L, dashboard.get("produtosAcimaMaximo"));
        }
    }

    @Test
    void produtosPorCategoria_deveFiltrarPorCategoria() {
        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.listarTodos()).thenReturn(criarProdutosExemplo()))) {

            Response response = new RelatorioRestController().produtosPorCategoria(1);
            Map<?, ?> resultado = (Map<?, ?>) response.getEntity();

            assertEquals(200, response.getStatus());
            assertEquals(3, resultado.get("total"));
        }
    }

    @Test
    void balancoFinanceiro_deveRetornarTotaisDoEstoque() {
        try (MockedConstruction<ProdutoDAO> ignored = mockConstruction(ProdutoDAO.class, (mock, ctx) ->
                when(mock.listarTodos()).thenReturn(criarProdutosExemplo()))) {

            Response response = new RelatorioRestController().balancoFinanceiro();
            Map<?, ?> balanco = (Map<?, ?>) response.getEntity();

            assertEquals(200, response.getStatus());
            assertEquals(250.0, balanco.get("valorTotalEstoque"));
            assertEquals(3, balanco.get("totalProdutosCadastrados"));
            assertEquals(43, balanco.get("totalItensEstoque"));
        }
    }

    @Test
    void errorResponse_deveRetornarMensagemEStatus() {
        RelatorioRestController.ErrorResponse error = new RelatorioRestController.ErrorResponse("falha");
        assertEquals("falha", error.getMessage());
        assertEquals("error", error.getStatus());
    }
}
