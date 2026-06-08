package controller.rest;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import modelo.Categoria;
import modelo.Produto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controlador REST para gerenciamento de Produtos.
 * Fornece endpoints da API REST para operações CRUD (Create, Read, Update, Delete) de produtos.
 * 
 * Endpoints disponíveis:
 * - GET    /api/produtos      - Lista todos os produtos
 * - GET    /api/produtos/{id} - Busca produto por ID
 * - POST   /api/produtos      - Cria novo produto
 * - PUT    /api/produtos/{id} - Atualiza produto existente
 * - DELETE /api/produtos/{id} - Exclui produto
 * 
 * @author
 */
@Path("/api/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoRestController {

    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    /**
     * Lista todos os produtos cadastrados no sistema.
     * Endpoint: GET /api/produtos
     * 
     * @return Response com lista de produtos em formato JSON ou mensagem de erro.
     */
    @GET
    public Response listarTodos() {
        try {
            List<Produto> produtos = produtoDAO.listarTodos();
            return Response.ok(produtos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao listar produtos: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Busca um produto específico pelo seu ID.
     * Endpoint: GET /api/produtos/{id}
     * 
     * @param id Identificador do produto.
     * @return Response com produto encontrado ou mensagem de erro (404 se não encontrado).
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            Produto produto = produtoDAO.buscarPorId(id);
            if (produto != null) {
                return Response.ok(produto).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Produto não encontrado"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao buscar produto: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Cria um novo produto no sistema.
     * Valida se a categoria informada existe antes de criar o produto.
     * Endpoint: POST /api/produtos
     * 
     * @param produtoDTO Objeto DTO contendo os dados do produto a ser criado.
     * @return Response com mensagem de sucesso (201 Created) ou erro.
     */
    @POST
    public Response criar(ProdutoDTO produtoDTO) {
        try {
            Categoria categoria = categoriaDAO.buscarPorId(produtoDTO.getCategoriaId());
            if (categoria == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Categoria não encontrada"))
                        .build();
            }

            Produto produto = new Produto();
            produto.setNome(produtoDTO.getNome());
            produto.setPrecoUnitario(produtoDTO.getPrecoUnitario());
            produto.setUnidade(produtoDTO.getUnidade());
            produto.setQuantidade(produtoDTO.getQuantidade());
            produto.setQuantidadeMinima(produtoDTO.getQuantidadeMinima());
            produto.setQuantidadeMaxima(produtoDTO.getQuantidadeMaxima());
            produto.setCategoria(categoria);

            produtoDAO.inserir(produto);
            
            return Response.status(Response.Status.CREATED)
                    .entity(new SuccessResponse("Produto criado com sucesso"))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao criar produto: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Atualiza os dados de um produto existente.
     * Valida se o produto e a categoria informados existem.
     * Endpoint: PUT /api/produtos/{id}
     * 
     * @param id Identificador do produto a ser atualizado.
     * @param produtoDTO Objeto DTO contendo os novos dados do produto.
     * @return Response com mensagem de sucesso ou erro (404 se produto/categoria não encontrados).
     */
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, ProdutoDTO produtoDTO) {
        try {
            Produto produtoExistente = produtoDAO.buscarPorId(id);
            if (produtoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Produto não encontrado"))
                        .build();
            }

            Categoria categoria = categoriaDAO.buscarPorId(produtoDTO.getCategoriaId());
            if (categoria == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Categoria não encontrada"))
                        .build();
            }

            Produto produto = new Produto(
                id,
                produtoDTO.getNome(),
                produtoDTO.getPrecoUnitario(),
                produtoDTO.getUnidade(),
                produtoDTO.getQuantidade(),
                produtoDTO.getQuantidadeMinima(),
                produtoDTO.getQuantidadeMaxima(),
                categoria
            );

            produtoDAO.atualizar(produto);
            
            return Response.ok(new SuccessResponse("Produto atualizado com sucesso")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao atualizar produto: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Exclui um produto do sistema pelo seu ID.
     * Endpoint: DELETE /api/produtos/{id}
     * 
     * @param id Identificador do produto a ser excluído.
     * @return Response com mensagem de sucesso ou erro (404 se produto não encontrado).
     */
    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") int id) {
        try {
            Produto produto = produtoDAO.buscarPorId(id);
            if (produto == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Produto não encontrado"))
                        .build();
            }

            produtoDAO.excluir(id);
            
            return Response.ok(new SuccessResponse("Produto excluído com sucesso")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao excluir produto: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * DTO (Data Transfer Object) para receber dados de produto do cliente.
     * Utilizado nas requisições POST e PUT para criar e atualizar produtos.
     */
    public static class ProdutoDTO {
        private String nome;
        private double precoUnitario;
        private String unidade;
        private int quantidade;
        private int quantidadeMinima;
        private int quantidadeMaxima;
        private int categoriaId;

        /**
         * Obtém o nome do produto.
         * @return Nome do produto.
         */
        public String getNome() { return nome; }
        
        /**
         * Define o nome do produto.
         * @param nome Nome do produto.
         */
        public void setNome(String nome) { this.nome = nome; }

        /**
         * Obtém o preço unitário do produto.
         * @return Preço unitário.
         */
        public double getPrecoUnitario() { return precoUnitario; }
        
        /**
         * Define o preço unitário do produto.
         * @param precoUnitario Preço unitário.
         */
        public void setPrecoUnitario(double precoUnitario) { this.precoUnitario = precoUnitario; }

        /**
         * Obtém a unidade de medida do produto.
         * @return Unidade de medida.
         */
        public String getUnidade() { return unidade; }
        
        /**
         * Define a unidade de medida do produto.
         * @param unidade Unidade de medida.
         */
        public void setUnidade(String unidade) { this.unidade = unidade; }

        /**
         * Obtém a quantidade em estoque.
         * @return Quantidade em estoque.
         */
        public int getQuantidade() { return quantidade; }
        
        /**
         * Define a quantidade em estoque.
         * @param quantidade Quantidade em estoque.
         */
        public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

        /**
         * Obtém a quantidade mínima de estoque.
         * @return Quantidade mínima.
         */
        public int getQuantidadeMinima() { return quantidadeMinima; }
        
        /**
         * Define a quantidade mínima de estoque.
         * @param quantidadeMinima Quantidade mínima.
         */
        public void setQuantidadeMinima(int quantidadeMinima) { this.quantidadeMinima = quantidadeMinima; }

        /**
         * Obtém a quantidade máxima de estoque.
         * @return Quantidade máxima.
         */
        public int getQuantidadeMaxima() { return quantidadeMaxima; }
        
        /**
         * Define a quantidade máxima de estoque.
         * @param quantidadeMaxima Quantidade máxima.
         */
        public void setQuantidadeMaxima(int quantidadeMaxima) { this.quantidadeMaxima = quantidadeMaxima; }

        /**
         * Obtém o ID da categoria do produto.
         * @return ID da categoria.
         */
        public int getCategoriaId() { return categoriaId; }
        
        /**
         * Define o ID da categoria do produto.
         * @param categoriaId ID da categoria.
         */
        public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }
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
