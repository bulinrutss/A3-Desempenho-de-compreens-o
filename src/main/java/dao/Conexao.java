package dao;

/**
 * Classe responsável por fornecer a conexão com o banco de dados MySQL.
 * A conexão é criada ao instanciar a classe.
 * 
 * É necessário instanciar essa classe para obter uma conexão.
 * 
 * @author Marcos Antonio Gasperin
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private final String URL = "jdbc:mysql://localhost:3306/controle_estoque";
    private final String USUARIO = "root";
    private final String SENHA = "";

    private Connection conexao;

    /**
     * Construtor que estabelece a conexão com o banco de dados.
     */
    public Conexao() {
        try {
            // Carrega o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Driver MySQL não encontrado.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados.");
        }
    }

    /**
     * Retorna a conexão ativa.
     * 
     * @return Connection Conexão ativa com o banco de dados.
     */
    public Connection getConexao() {
        return conexao;
    }

    /**
     * Fecha a conexão com o banco de dados.
     */
    public void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
