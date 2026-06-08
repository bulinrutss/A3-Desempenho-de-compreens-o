package controller.rest;

import dao.ProdutoDAO;
import modelo.Produto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador REST para geração de Relatórios Gerenciais.
 * Fornece endpoints da API REST para consulta de diversos relatórios do sistema de estoque.
 * 
 * Endpoints disponíveis:
 * - GET /api/relatorios/produtos-abaixo-minimo            - Produtos com estoque abaixo do mínimo
 * - GET /api/relatorios/produtos-acima-maximo             - Produtos com estoque acima do máximo
 * - GET /api/relatorios/lista-precos                      - Lista de preços de todos os produtos
 * - GET /api/relatorios/produtos-por-categoria/{categoriaId} - Produtos por categoria
 * - GET /api/relatorios/balanco-financeiro                - Balanço financeiro do estoque
 * - GET /api/relatorios/dashboard                         - Dados resumidos do sistema
 * 
 * @author
 */
@Path("/api/relatorios")
@Produces(MediaType.APPLICATION_JSON)
public class RelatorioRestController {

    private ProdutoDAO produtoDAO = new ProdutoDAO();

    /**
     * Gera relatório de produtos com estoque abaixo do mínimo.
     * Retorna lista de produtos e total de itens abaixo do estoque mínimo.
     * Endpoint: GET /api/relatorios/produtos-abaixo-minimo
     * 
     * @return Response com relatório contendo total e lista de produtos ou mensagem de erro.
     */
    @GET
    @Path("/produtos-abaixo-minimo")
    public Response produtosAbaixoMinimo() {
        try {
            List<Produto> todosProdutos = produtoDAO.listarTodos();
            List<Produto> produtosAbaixo = todosProdutos.stream()
                    .filter(p -> p.getQuantidade() < p.getQuantidadeMinima())
                    .collect(Collectors.toList());

            Map<String, Object> resultado = new HashMap<>();
            resultado.put("total", produtosAbaixo.size());
            resultado.put("produtos", produtosAbaixo);

            return Response.ok(resultado).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao gerar relatório: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Gera relatório de produtos com estoque acima do máximo.
     * Retorna lista de produtos e total de itens acima do estoque máximo.
     * Endpoint: GET /api/relatorios/produtos-acima-maximo
     * 
     * @return Response com relatório contendo total e lista de produtos ou mensagem de erro.
     */
    @GET
    @Path("/produtos-acima-maximo")
    public Response produtosAcimaMaximo() {
        try {
            List<Produto> todosProdutos = produtoDAO.listarTodos();
            List<Produto> produtosAcima = todosProdutos.stream()
                    .filter(p -> p.getQuantidade() > p.getQuantidadeMaxima())
                    .collect(Collectors.toList());

            Map<String, Object> resultado = new HashMap<>();
            resultado.put("total", produtosAcima.size());
            resultado.put("produtos", produtosAcima);

            return Response.ok(resultado).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao gerar relatório: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Gera relatório de lista de preços de todos os produtos.
     * Retorna lista de produtos com seus preços e valor total do estoque.
     * Endpoint: GET /api/relatorios/lista-precos
     * 
     * @return Response com relatório contendo produtos e valor total do estoque ou mensagem de erro.
     */
    @GET
    @Path("/lista-precos")
    public Response listaPrecos() {
        try {
            List<Produto> produtos = produtoDAO.listarTodos();

            double valorTotal = produtos.stream()
                    .mapToDouble(p -> p.getPrecoUnitario() * p.getQuantidade())
                    .sum();

            Map<String, Object> resultado = new HashMap<>();
            resultado.put("produtos", produtos);
            resultado.put("valorTotalEstoque", valorTotal);

            return Response.ok(resultado).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao gerar relatório: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Gera relatório de produtos de uma categoria específica.
     * Filtra e retorna apenas produtos da categoria solicitada.
     * Endpoint: GET /api/relatorios/produtos-por-categoria/{categoriaId}
     * 
     * @param categoriaId Identificador da categoria para filtrar os produtos.
     * @return Response com relatório contendo total e lista de produtos da categoria ou mensagem de erro.
     */
    @GET
    @Path("/produtos-por-categoria/{categoriaId}")
    public Response produtosPorCategoria(@PathParam("categoriaId") int categoriaId) {
        try {
            List<Produto> todosProdutos = produtoDAO.listarTodos();
            List<Produto> produtosCategoria = todosProdutos.stream()
                    .filter(p -> p.getCategoria() != null && p.getCategoria().getId() == categoriaId)
                    .collect(Collectors.toList());

            Map<String, Object> resultado = new HashMap<>();
            resultado.put("total", produtosCategoria.size());
            resultado.put("produtos", produtosCategoria);

            return Response.ok(resultado).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao gerar relatório: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Gera relatório de balanço financeiro do estoque.
     * Calcula e retorna valor total, quantidade de produtos e itens em estoque.
     * Endpoint: GET /api/relatorios/balanco-financeiro
     * 
     * @return Response com relatório financeiro completo contendo totais e lista de produtos ou mensagem de erro.
     */
    @GET
    @Path("/balanco-financeiro")
    public Response balancoFinanceiro() {
        try {
            List<Produto> produtos = produtoDAO.listarTodos();

            double valorTotal = produtos.stream()
                    .mapToDouble(p -> p.getPrecoUnitario() * p.getQuantidade())
                    .sum();

            int totalProdutos = produtos.size();
            int totalItens = produtos.stream()
                    .mapToInt(Produto::getQuantidade)
                    .sum();

            Map<String, Object> balanco = new HashMap<>();
            balanco.put("valorTotalEstoque", valorTotal);
            balanco.put("totalProdutosCadastrados", totalProdutos);
            balanco.put("totalItensEstoque", totalItens);
            balanco.put("produtos", produtos);

            return Response.ok(balanco).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao gerar relatório: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Gera dados consolidados para dashboard (resumo geral do sistema).
     * Retorna estatísticas gerais: total de produtos, produtos abaixo do mínimo,
     * produtos acima do máximo e valor total do estoque.
     * Endpoint: GET /api/relatorios/dashboard
     * 
     * @return Response com dados resumidos do sistema ou mensagem de erro.
     */
    @GET
    @Path("/dashboard")
    public Response dashboard() {
        try {
            List<Produto> produtos = produtoDAO.listarTodos();

            long abaixoMinimo = produtos.stream()
                    .filter(p -> p.getQuantidade() < p.getQuantidadeMinima())
                    .count();

            long acimaMaximo = produtos.stream()
                    .filter(p -> p.getQuantidade() > p.getQuantidadeMaxima())
                    .count();

            double valorTotal = produtos.stream()
                    .mapToDouble(p -> p.getPrecoUnitario() * p.getQuantidade())
                    .sum();

            Map<String, Object> dashboard = new HashMap<>();
            dashboard.put("totalProdutos", produtos.size());
            dashboard.put("produtosAbaixoMinimo", abaixoMinimo);
            dashboard.put("produtosAcimaMaximo", acimaMaximo);
            dashboard.put("valorTotalEstoque", valorTotal);

            return Response.ok(dashboard).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao gerar dashboard: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Classe para respostas de erro da API.
     * Retorna mensagens de erro padronizadas ao cliente.
     */
    public static class ErrorResponse {
        private String message;
        private String status = "error";

        /**
         * Construtor que cria uma resposta de erro.
         * @param message Mensagem de erro a ser retornada.
         */
        public ErrorResponse(String message) {
            this.message = message;
        }

        /**
         * Obtém a mensagem de erro.
         * @return Mensagem de erro.
         */
        public String getMessage() { return message; }
        
        /**
         * Obtém o status da resposta.
         * @return Status "error".
         */
        public String getStatus() { return status; }
    }
}
