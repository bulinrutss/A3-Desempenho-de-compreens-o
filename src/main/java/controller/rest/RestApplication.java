package controller.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe de configuração da aplicação JAX-RS (Java API for RESTful Web Services).
 * Responsável por registrar todos os controladores REST e definir o path base da API.
 * 
 * O path base "/" significa que todos os endpoints REST começam a partir da raiz da aplicação.
 * Por exemplo: http://localhost:8080/api/produtos
 * 
 * @author
 */
@ApplicationPath("/")
public class RestApplication extends Application {

    /**
     * Registra todas as classes de controladores REST da aplicação.
     * Este método é chamado automaticamente pelo servidor de aplicação ao inicializar.
     * 
     * @return Set contendo todas as classes de controladores REST registradas.
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        
        // Registra todos os controladores REST disponíveis
        classes.add(ProdutoRestController.class);
        classes.add(CategoriaRestController.class);
        classes.add(MovimentacaoRestController.class);
        classes.add(RelatorioRestController.class);
        
        return classes;
    }
}
