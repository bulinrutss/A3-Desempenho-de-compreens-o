package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro CORS (Cross-Origin Resource Sharing) para permitir requisições de origens diferentes.
 * Este filtro é essencial para permitir que aplicações front-end hospedadas em outros domínios
 * possam consumir a API REST do sistema.
 * 
 * Configurações CORS:
 * - Access-Control-Allow-Origin: Define quais origens podem acessar a API
 * - Access-Control-Allow-Methods: Métodos HTTP permitidos (GET, POST, PUT, DELETE, OPTIONS)
 * - Access-Control-Allow-Headers: Headers HTTP permitidos nas requisições
 * - Access-Control-Allow-Credentials: Permite envio de credenciais (cookies, autenticação)
 * 
 * Mapeamento: /* (aplica-se a todas as requisições)
 * 
 * @author
 */
@WebFilter("/*")
public class CorsFilter implements Filter {

    /**
     * Método de inicialização do filtro.
     * Chamado uma vez quando o filtro é instanciado pelo container.
     * 
     * @param filterConfig Objeto de configuração do filtro.
     * @throws ServletException Se ocorrer erro na inicialização.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialização do filtro
    }

    /**
     * Método principal do filtro que processa cada requisição HTTP.
     * Adiciona os headers CORS necessários para permitir requisições cross-origin.
     * 
     * @param servletRequest Requisição do cliente.
     * @param servletResponse Resposta para o cliente.
     * @param chain Cadeia de filtros para continuar o processamento.
     * @throws IOException Se ocorrer erro de entrada/saída.
     * @throws ServletException Se ocorrer erro no processamento.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Permite requisições de qualquer origem (em produção, especifique o domínio do front-end)
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        // Métodos HTTP permitidos
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        
        // Headers permitidos
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
        
        // Permite credenciais
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        // Tempo de cache do preflight
        response.setHeader("Access-Control-Max-Age", "3600");

        // Se for requisição OPTIONS (preflight), retorna OK
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * Método de limpeza do filtro.
     * Chamado quando o filtro é destruído pelo container.
     */
    @Override
    public void destroy() {
        // Limpeza do filtro
    }
}
