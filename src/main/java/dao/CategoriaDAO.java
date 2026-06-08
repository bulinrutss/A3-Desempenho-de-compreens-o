package dao;

/**
 * Classe responsável pelas operações CRUD da tabela categoria no banco de dados.
 * 
 * @author 
 */
import modelo.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    /**
     * Insere uma nova categoria no banco de dados.
     *
     * @param categoria Objeto Categoria a ser inserido.
     */
    public void inserir(Categoria categoria) {
        String sql = "INSERT INTO categoria (nome, tamanho, embalagem) VALUES (?, ?, ?)";

        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getTamanho());
            stmt.setString(3, categoria.getEmbalagem());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.fecharConexao();
        }
    }

    /**
     * Atualiza os dados de uma categoria existente no banco de dados.
     *
     * @param categoria Objeto Categoria com os dados atualizados.
     */
    public void atualizar(Categoria categoria) {
        String sql = "UPDATE categoria SET nome=?, tamanho=?, embalagem=? WHERE id=?";

        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getTamanho());
            stmt.setString(3, categoria.getEmbalagem());
            stmt.setInt(4, categoria.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.fecharConexao();
        }
    }

    /**
     * Exclui uma categoria do banco de dados pelo seu ID.
     *
     * @param id Identificador da categoria a ser excluída.
     */
    public void excluir(int id) {
        String sql = "DELETE FROM categoria WHERE id=?";

        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.fecharConexao();
        }
    }

    /**
     * Busca uma categoria no banco de dados pelo seu ID.
     *
     * @param id Identificador da categoria.
     * @return Objeto Categoria encontrado ou null se não existir.
     */
    public Categoria buscarPorId(int id) {
        String sql = "SELECT * FROM categoria WHERE id=?";
        Categoria categoria = null;

        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNome(rs.getString("nome"));
                categoria.setTamanho(rs.getString("tamanho"));
                categoria.setEmbalagem(rs.getString("embalagem"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.fecharConexao();
        }

        return categoria;
    }

    /**
     * Lista todas as categorias cadastradas no banco de dados.
     *
     * @return Lista de objetos Categoria.
     */
    public List<Categoria> listarTodas() {
        String sql = "SELECT * FROM categoria ORDER BY nome";
        List<Categoria> lista = new ArrayList<>();

        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNome(rs.getString("nome"));
                categoria.setTamanho(rs.getString("tamanho"));
                categoria.setEmbalagem(rs.getString("embalagem"));
                lista.add(categoria);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.fecharConexao();
        }

        return lista;
    }
}
