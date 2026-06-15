package dao;

import modelo.Categoria;
import modelo.Produto;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProdutoDAOTest {

    @Test
    void buscarPorId_deveRetornarProdutoQuandoEncontrado() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("nome")).thenReturn("Arroz");
        when(rs.getDouble("preco_unitario")).thenReturn(10.0);
        when(rs.getString("unidade")).thenReturn("KG");
        when(rs.getInt("quantidade")).thenReturn(5);
        when(rs.getInt("quantidade_minima")).thenReturn(2);
        when(rs.getInt("quantidade_maxima")).thenReturn(20);
        when(rs.getInt("cid")).thenReturn(1);
        when(rs.getString("cnome")).thenReturn("Alimentos");
        when(rs.getString("tamanho")).thenReturn("1kg");
        when(rs.getString("embalagem")).thenReturn("Pacote");

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            Produto produto = new ProdutoDAO().buscarPorId(1);
            assertNotNull(produto);
            assertEquals("Arroz", produto.getNome());
            assertEquals("Alimentos", produto.getCategoria().getNome());
        }
    }

    @Test
    void buscarPorId_deveRetornarNullQuandoNaoEncontrado() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            assertNull(new ProdutoDAO().buscarPorId(99));
        }
    }

    @Test
    void listarTodos_deveRetornarListaVazia() throws Exception {
        Connection conn = mock(Connection.class);
        java.sql.Statement stmt = mock(java.sql.Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            List<Produto> produtos = new ProdutoDAO().listarTodos();
            assertEquals(0, produtos.size());
        }
    }

    @Test
    void listarTodos_deveRetornarProdutosQuandoExistemRegistros() throws Exception {
        Connection conn = mock(Connection.class);
        java.sql.Statement stmt = mock(java.sql.Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("nome")).thenReturn("Arroz");
        when(rs.getDouble("preco_unitario")).thenReturn(10.0);
        when(rs.getString("unidade")).thenReturn("KG");
        when(rs.getInt("quantidade")).thenReturn(5);
        when(rs.getInt("quantidade_minima")).thenReturn(2);
        when(rs.getInt("quantidade_maxima")).thenReturn(20);
        when(rs.getInt("cid")).thenReturn(1);
        when(rs.getString("cnome")).thenReturn("Alimentos");
        when(rs.getString("tamanho")).thenReturn("1kg");
        when(rs.getString("embalagem")).thenReturn("Pacote");

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            List<Produto> produtos = new ProdutoDAO().listarTodos();
            assertEquals(1, produtos.size());
            assertEquals("Arroz", produtos.get(0).getNome());
        }
    }

    @Test
    void atualizar_deveExecutarUpdate() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);

        Produto produto = new Produto(1, "Arroz", 10.0, "KG", 5, 2, 20,
                new Categoria(1, "Alimentos", "1kg", "Pacote"));

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            new ProdutoDAO().atualizar(produto);
            verify(stmt).executeUpdate();
        }
    }

    @Test
    void excluir_deveExecutarDelete() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            new ProdutoDAO().excluir(1);
            verify(stmt).executeUpdate();
        }
    }

    @Test
    void listarAbaixoMinimo_deveRetornarProdutos() throws Exception {
        Connection conn = mock(Connection.class);
        java.sql.Statement stmt = mock(java.sql.Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        configurarResultSetProduto(rs);

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            List<Produto> produtos = new ProdutoDAO().listarAbaixoMinimo();
            assertEquals(1, produtos.size());
        }
    }

    @Test
    void listarAcimaMaximo_deveRetornarProdutos() throws Exception {
        Connection conn = mock(Connection.class);
        java.sql.Statement stmt = mock(java.sql.Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        configurarResultSetProduto(rs);

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            List<Produto> produtos = new ProdutoDAO().listarAcimaMaximo();
            assertEquals(1, produtos.size());
        }
    }

    @Test
    void inserir_deveExecutarInsert() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);

        Produto produto = new Produto();
        produto.setNome("Feijao");
        produto.setPrecoUnitario(8.0);
        produto.setUnidade("KG");
        produto.setQuantidade(10);
        produto.setQuantidadeMinima(2);
        produto.setQuantidadeMaxima(50);
        produto.setCategoria(new Categoria(1, "Alimentos", "1kg", "Pacote"));

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            new ProdutoDAO().inserir(produto);
            verify(stmt).executeUpdate();
        }
    }

    private void configurarResultSetProduto(ResultSet rs) throws Exception {
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("nome")).thenReturn("Arroz");
        when(rs.getDouble("preco_unitario")).thenReturn(10.0);
        when(rs.getString("unidade")).thenReturn("KG");
        when(rs.getInt("quantidade")).thenReturn(1);
        when(rs.getInt("quantidade_minima")).thenReturn(5);
        when(rs.getInt("quantidade_maxima")).thenReturn(20);
        when(rs.getInt("cid")).thenReturn(1);
        when(rs.getString("cnome")).thenReturn("Alimentos");
        when(rs.getString("tamanho")).thenReturn("1kg");
        when(rs.getString("embalagem")).thenReturn("Pacote");
    }

    private MockedConstruction<Conexao> mockarConexao(Connection conn) {
        return mockConstruction(Conexao.class, (mock, context) -> {
            when(mock.getConexao()).thenReturn(conn);
            doNothing().when(mock).fecharConexao();
        });
    }
}
