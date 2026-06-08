package controller.rest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RestApplicationTest {

    @Test
    void deveRegistrarTodosOsControladoresRest() {
        RestApplication application = new RestApplication();

        assertTrue(application.getClasses().contains(ProdutoRestController.class));
        assertTrue(application.getClasses().contains(CategoriaRestController.class));
        assertTrue(application.getClasses().contains(MovimentacaoRestController.class));
        assertTrue(application.getClasses().contains(RelatorioRestController.class));
    }
}
