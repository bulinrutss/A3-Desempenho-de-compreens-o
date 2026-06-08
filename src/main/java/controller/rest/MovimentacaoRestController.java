package controller.rest;

import dao.MovimentacaoDAO;
import dao.ProdutoDAO;
import modelo.Movimentacao;
import modelo.Produto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controlador REST para gerenciamento de Movimentações de Estoque.
 * Fornece endpoints da API REST para registro e consulta de movimentações (entradas e saídas).
 * 
 * Endpoints disponíveis:
 * - GET  /api/movimentacoes                  - Lista todas as movimentações
 * - GET  /api/movimentacoes/{id}             - Busca movimentação por ID
 * - POST /api/movimentacoes                  - Registra nova movimentação
 * - GET  /api/movimentacoes/produto/{produtoId} - Lista movimentações de um produto
 * 
 * @author
 */
@Path("/api/movimentacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovimentacaoRestController {

    private MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    /**
     * Lista todas as movimentações de estoque cadastradas.
     * Endpoint: GET /api/movimentacoes
     * 
     * @return Response com lista de movimentações em formato JSON ou mensagem de erro.
     */
    @GET
    public Response listarTodas() {
        try {
            List<Movimentacao> movimentacoes = movimentacaoDAO.listarTodas();
            return Response.ok(movimentacoes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao listar movimentações: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Busca uma movimentação específica pelo seu ID.
     * Endpoint: GET /api/movimentacoes/{id}
     * 
     * @param id Identificador da movimentação.
     * @return Response com movimentação encontrada ou mensagem de erro (404 se não encontrada).
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            Movimentacao movimentacao = movimentacaoDAO.buscarPorId(id);
            if (movimentacao != null) {
                return Response.ok(movimentacao).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Movimentação não encontrada"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao buscar movimentação: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Registra uma nova movimentação de estoque (entrada ou saída).
     * Valida se o produto existe e se há estoque suficiente para saídas.
     * Endpoint: POST /api/movimentacoes
     * 
     * @param movimentacaoDTO Objeto DTO contendo os dados da movimentação (tipo, quantidade, produtoId).
     * @return Response com mensagem de sucesso (201 Created) ou erro.
     */
    @POST
    public Response registrar(MovimentacaoDTO movimentacaoDTO) {
        try {
            Produto produto = produtoDAO.buscarPorId(movimentacaoDTO.getProdutoId());
            if (produto == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Produto não encontrado"))
                        .build();
            }

            // Validação: verificar se tem estoque suficiente para saída
            if ("SAIDA".equals(movimentacaoDTO.getTipo()) && 
                produto.getQuantidade() < movimentacaoDTO.getQuantidade()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Estoque insuficiente. Disponível: " + produto.getQuantidade()))
                        .build();
            }

            Movimentacao movimentacao = new Movimentacao();
            movimentacao.setTipo(movimentacaoDTO.getTipo());
            movimentacao.setQuantidade(movimentacaoDTO.getQuantidade());
            movimentacao.setProduto(produto);

            movimentacaoDAO.registrarMovimento(movimentacao);
            
            return Response.status(Response.Status.CREATED)
                    .entity(new SuccessResponse("Movimentação registrada com sucesso"))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao registrar movimentação: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Lista todas as movimentações de um produto específico.
     * Endpoint: GET /api/movimentacoes/produto/{produtoId}
     * 
     * @param produtoId Identificador do produto.
     * @return Response com lista de movimentações do produto ou erro (404 se produto não encontrado).
     */
    @GET
    @Path("/produto/{produtoId}")
    public Response listarPorProduto(@PathParam("produtoId") int produtoId) {
        try {
            Produto produto = produtoDAO.buscarPorId(produtoId);
            if (produto == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Produto não encontrado"))
                        .build();
            }

            List<Movimentacao> movimentacoes = movimentacaoDAO.listarPorProduto(produtoId);
            return Response.ok(movimentacoes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao listar movimentações do produto: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * DTO (Data Transfer Object) para receber dados de movimentação do cliente.
     * Utilizado nas requisições POST para registrar movimentações de estoque.
     */
    public static class MovimentacaoDTO {
        private String tipo;  // "ENTRADA" ou "SAIDA"
        private int quantidade;
        private int produtoId;

        /**
         * Obtém o tipo da movimentação.
         * @return Tipo da movimentação ("ENTRADA" ou "SAIDA").
         */
        public String getTipo() { return tipo; }
        
        /**
         * Define o tipo da movimentação.
         * @param tipo Tipo da movimentação ("ENTRADA" ou "SAIDA").
         */
        public void setTipo(String tipo) { this.tipo = tipo; }

        /**
         * Obtém a quantidade da movimentação.
         * @return Quantidade movimentada.
         */
        public int getQuantidade() { return quantidade; }
        
        /**
         * Define a quantidade da movimentação.
         * @param quantidade Quantidade a ser movimentada.
         */
        public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

        /**
         * Obtém o ID do produto relacionado à movimentação.
         * @return ID do produto.
         */
        public int getProdutoId() { return produtoId; }
        
        /**
         * Define o ID do produto relacionado à movimentação.
         * @param produtoId ID do produto.
         */
        public void setProdutoId(int produtoId) { this.produtoId = produtoId; }
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

    /**
     * Classe para respostas de sucesso da API.
     * Retorna mensagens de sucesso padronizadas ao cliente.
     */
    public static class SuccessResponse {
        private String message;
        private String status = "success";

        /**
         * Construtor que cria uma resposta de sucesso.
         * @param message Mensagem de sucesso a ser retornada.
         */
        public SuccessResponse(String message) {
            this.message = message;
        }

        /**
         * Obtém a mensagem de sucesso.
         * @return Mensagem de sucesso.
         */
        public String getMessage() { return message; }
        
        /**
         * Obtém o status da resposta.
         * @return Status "success".
         */
        public String getStatus() { return status; }
    }
}
