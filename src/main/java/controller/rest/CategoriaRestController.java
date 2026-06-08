package controller.rest;

import dao.CategoriaDAO;
import modelo.Categoria;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controlador REST para gerenciamento de Categorias.
 * Fornece endpoints da API REST para operações CRUD (Create, Read, Update, Delete) de categorias.
 * 
 * Endpoints disponíveis:
 * - GET    /api/categorias      - Lista todas as categorias
 * - GET    /api/categorias/{id} - Busca categoria por ID
 * - POST   /api/categorias      - Cria nova categoria
 * - PUT    /api/categorias/{id} - Atualiza categoria existente
 * - DELETE /api/categorias/{id} - Exclui categoria
 * 
 * @author
 */
@Path("/api/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaRestController {

    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    /**
     * Lista todas as categorias cadastradas.
     * Endpoint: GET /api/categorias
     * 
     * @return Response com lista de categorias em formato JSON ou mensagem de erro.
     */
    @GET
    public Response listarTodas() {
        try {
            List<Categoria> categorias = categoriaDAO.listarTodas();
            return Response.ok(categorias).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao listar categorias: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Busca uma categoria específica pelo seu ID.
     * Endpoint: GET /api/categorias/{id}
     * 
     * @param id Identificador da categoria.
     * @return Response com categoria encontrada ou mensagem de erro (404 se não encontrada).
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            Categoria categoria = categoriaDAO.buscarPorId(id);
            if (categoria != null) {
                return Response.ok(categoria).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Categoria não encontrada"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao buscar categoria: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Cria uma nova categoria no sistema.
     * Endpoint: POST /api/categorias
     * 
     * @param categoriaDTO Objeto DTO contendo os dados da categoria a ser criada.
     * @return Response com mensagem de sucesso (201 Created) ou erro.
     */
    @POST
    public Response criar(CategoriaDTO categoriaDTO) {
        try {
            Categoria categoria = new Categoria();
            categoria.setNome(categoriaDTO.getNome());
            categoria.setTamanho(categoriaDTO.getTamanho());
            categoria.setEmbalagem(categoriaDTO.getEmbalagem());

            categoriaDAO.inserir(categoria);
            
            return Response.status(Response.Status.CREATED)
                    .entity(new SuccessResponse("Categoria criada com sucesso"))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao criar categoria: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Atualiza os dados de uma categoria existente.
     * Endpoint: PUT /api/categorias/{id}
     * 
     * @param id Identificador da categoria a ser atualizada.
     * @param categoriaDTO Objeto DTO contendo os novos dados da categoria.
     * @return Response com mensagem de sucesso ou erro (404 se categoria não encontrada).
     */
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, CategoriaDTO categoriaDTO) {
        try {
            Categoria categoriaExistente = categoriaDAO.buscarPorId(id);
            if (categoriaExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Categoria não encontrada"))
                        .build();
            }

            Categoria categoria = new Categoria(
                id,
                categoriaDTO.getNome(),
                categoriaDTO.getTamanho(),
                categoriaDTO.getEmbalagem()
            );

            categoriaDAO.atualizar(categoria);
            
            return Response.ok(new SuccessResponse("Categoria atualizada com sucesso")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao atualizar categoria: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Exclui uma categoria do sistema pelo seu ID.
     * Endpoint: DELETE /api/categorias/{id}
     * 
     * @param id Identificador da categoria a ser excluída.
     * @return Response com mensagem de sucesso ou erro (404 se categoria não encontrada).
     */
    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") int id) {
        try {
            Categoria categoria = categoriaDAO.buscarPorId(id);
            if (categoria == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Categoria não encontrada"))
                        .build();
            }

            categoriaDAO.excluir(id);
            
            return Response.ok(new SuccessResponse("Categoria excluída com sucesso")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao excluir categoria: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * DTO (Data Transfer Object) para receber dados de categoria do cliente.
     * Utilizado nas requisições POST e PUT para criar e atualizar categorias.
     */
    public static class CategoriaDTO {
        private String nome;
        private String tamanho;
        private String embalagem;

        /**
         * Obtém o nome da categoria.
         * @return Nome da categoria.
         */
        public String getNome() { return nome; }
        
        /**
         * Define o nome da categoria.
         * @param nome Nome da categoria.
         */
        public void setNome(String nome) { this.nome = nome; }

        /**
         * Obtém o tamanho da categoria.
         * @return Tamanho da categoria.
         */
        public String getTamanho() { return tamanho; }
        
        /**
         * Define o tamanho da categoria.
         * @param tamanho Tamanho da categoria.
         */
        public void setTamanho(String tamanho) { this.tamanho = tamanho; }

        /**
         * Obtém o tipo de embalagem da categoria.
         * @return Embalagem da categoria.
         */
        public String getEmbalagem() { return embalagem; }
        
        /**
         * Define o tipo de embalagem da categoria.
         * @param embalagem Embalagem da categoria.
         */
        public void setEmbalagem(String embalagem) { this.embalagem = embalagem; }
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
