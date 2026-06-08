package dao;

/**
 *
 * @author João Vitor Cardoso de Jesus
 */

import modelo.Categoria;
import modelo.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    /**
     * Insere um novo produto no banco de dados.
     *
     * @param produto Objeto Produto a ser inserido.
     */
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco_unitario, unidade, quantidade, quantidade_minima, quantidade_maxima, categoria_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoUnitario());
            stmt.setString(3, produto.getUnidade());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            stmt.setInt(5, produto.getQuantidadeMinima());
            stmt.setInt(6, produto.getQuantidadeMaxima());
            stmt.setInt(7, produto.getCategoria().getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.fecharConexao();
        }
    }

    /**
     * Atualiza os dados de um produto existente no banco de dados.
     *
     * @param produto Objeto Produto com os dados atualizados.
     */
    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome=?, preco_unitario=?, unidade=?, quantidade=?, quantidade_minima=?, quantidade_maxima=?, categoria_id=? WHERE id=?";

        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoUnitario());
            stmt.setString(3, produto.getUnidade());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            stmt.setInt(5, produto.getQuantidadeMinima());
            stmt.setInt(6, produto.getQuantidadeMaxima());
            stmt.setInt(7, produto.getCategoria().getId());
            stmt.setInt(8, produto.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.fecharConexao();
        }
    }

    /**
     * Exclui um produto do banco de dados pelo seu ID.
     *
     * @param id Identificador do produto a ser excluído.
     */
    public void excluir(int id) {
        String sql = "DELETE FROM produto WHERE id=?";

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
     * Lista todos os produtos cadastrados no banco de dados, incluindo informações da categoria relacionada.
     *
     * @return Lista de objetos Produto com os dados dos produtos e suas categorias.
     */
    public List<Produto> listarTodos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.id AS cid, c.nome AS cnome, c.tamanho, c.embalagem "
                + "FROM produto p "
                + "JOIN categoria c ON p.categoria_id = c.id "
                + "ORDER BY p.nome";

        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Categoria cat = new Categoria(
                        rs.getInt("cid"),
                        rs.getString("cnome"),
                        rs.getString("tamanho"),
                        rs.getString("embalagem")
                );

                Produto p = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco_unitario"),
                        rs.getString("unidade"),
                        rs.getInt("quantidade"),
                        rs.getInt("quantidade_minima"),
                        rs.getInt("quantidade_maxima"),
                        cat
                );
                lista.add(p);
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

    /**
     * Busca um produto específico pelo seu ID.
     *
     * @param id Identificador do produto.
     * @return Objeto Produto ou null se não encontrado.
     */
    public Produto buscarPorId(int id) {
        String sql = "SELECT p.*, c.id AS cid, c.nome AS cnome, c.tamanho, c.embalagem "
                + "FROM produto p "
                + "JOIN categoria c ON p.categoria_id = c.id "
                + "WHERE p.id = ?";

        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Categoria cat = new Categoria(
                        rs.getInt("cid"),
                        rs.getString("cnome"),
                        rs.getString("tamanho"),
                        rs.getString("embalagem")
                );

                Produto p = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco_unitario"),
                        rs.getString("unidade"),
                        rs.getInt("quantidade"),
                        rs.getInt("quantidade_minima"),
                        rs.getInt("quantidade_maxima"),
                        cat
                );
                rs.close();
                stmt.close();
                return p;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.fecharConexao();
        }

        return null;
    }

    /**
     * Lista produtos com quantidade abaixo do mínimo.
     *
     * @return Lista de produtos abaixo do mínimo.
     */
    public List<Produto> listarAbaixoMinimo() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.id AS cid, c.nome AS cnome, c.tamanho, c.embalagem "
                + "FROM produto p "
                + "JOIN categoria c ON p.categoria_id = c.id "
                + "WHERE p.quantidade < p.quantidade_minima "
                + "ORDER BY p.nome";

        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Categoria cat = new Categoria(
                        rs.getInt("cid"),
                        rs.getString("cnome"),
                        rs.getString("tamanho"),
                        rs.getString("embalagem")
                );

                Produto p = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco_unitario"),
                        rs.getString("unidade"),
                        rs.getInt("quantidade"),
                        rs.getInt("quantidade_minima"),
                        rs.getInt("quantidade_maxima"),
                        cat
                );
                lista.add(p);
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

    /**
     * Lista produtos com quantidade acima do máximo.
     *
     * @return Lista de produtos acima do máximo.
     */
    public List<Produto> listarAcimaMaximo() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.id AS cid, c.nome AS cnome, c.tamanho, c.embalagem "
                + "FROM produto p "
                + "JOIN categoria c ON p.categoria_id = c.id "
                + "WHERE p.quantidade > p.quantidade_maxima "
                + "ORDER BY p.nome";

        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Categoria cat = new Categoria(
                        rs.getInt("cid"),
                        rs.getString("cnome"),
                        rs.getString("tamanho"),
                        rs.getString("embalagem")
                );

                Produto p = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco_unitario"),
                        rs.getString("unidade"),
                        rs.getInt("quantidade"),
                        rs.getInt("quantidade_minima"),
                        rs.getInt("quantidade_maxima"),
                        cat
                );
                lista.add(p);
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
