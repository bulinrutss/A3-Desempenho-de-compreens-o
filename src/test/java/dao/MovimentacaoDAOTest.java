package dao;

import modelo.Movimentacao;
import modelo.Produto;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovimentacaoDAOTest {

    @Test
    void registrarMovimento_deveInserirEAtualizarEstoqueEmEntrada() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement insertStmt = mock(PreparedStatement.class);
        PreparedStatement updateStmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(insertStmt, updateStmt);
        when(insertStmt.executeUpdate()).thenReturn(1);
        when(updateStmt.executeUpdate()).thenReturn(1);

        Produto produto = new Produto(1, "Cafe", 8.0, "UN", 10, 2, 100, null);
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setTipo("ENTRADA");
        movimentacao.setQuantidade(5);
        movimentacao.setProduto(produto);

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            new MovimentacaoDAO().registrarMovimento(movimentacao);
            verify(conn, times(2)).prepareStatement(anyString());
            verify(updateStmt).setInt(1, 15);
        }
    }

    @Test
    void buscarPorId_deveRetornarMovimentacaoQuandoEncontrada() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDateTime data = LocalDateTime.of(2026, 3, 10, 12, 0);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("m_id")).thenReturn(1);
        when(rs.getString("tipo")).thenReturn("ENTRADA");
        when(rs.getInt("quantidade")).thenReturn(5);
        when(rs.getTimestamp("data_movimento")).thenReturn(Timestamp.valueOf(data));
        when(rs.getInt("p_id")).thenReturn(2);
        when(rs.getString("nome")).thenReturn("Cafe");
        when(rs.getDouble("preco_unitario")).thenReturn(8.0);
        when(rs.getString("unidade")).thenReturn("UN");
        when(rs.getInt("quantidade")).thenReturn(10);
        when(rs.getInt("quantidade_minima")).thenReturn(2);
        when(rs.getInt("quantidade_maxima")).thenReturn(100);

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            Movimentacao movimentacao = new MovimentacaoDAO().buscarPorId(1);
            assertNotNull(movimentacao);
            assertEquals("ENTRADA", movimentacao.getTipo());
            assertEquals("Cafe", movimentacao.getProduto().getNome());
        }
    }

    @Test
    void buscarPorId_deveRetornarNullQuandoNaoEncontrada() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            assertNull(new MovimentacaoDAO().buscarPorId(99));
        }
    }

    @Test
    void registrarMovimento_deveAtualizarEstoqueEmSaida() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement insertStmt = mock(PreparedStatement.class);
        PreparedStatement updateStmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(insertStmt, updateStmt);
        when(insertStmt.executeUpdate()).thenReturn(1);
        when(updateStmt.executeUpdate()).thenReturn(1);

        Produto produto = new Produto(1, "Cafe", 8.0, "UN", 10, 2, 100, null);
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setTipo("SAIDA");
        movimentacao.setQuantidade(3);
        movimentacao.setProduto(produto);

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            new MovimentacaoDAO().registrarMovimento(movimentacao);
            verify(updateStmt).setInt(1, 7);
        }
    }

    @Test
    void listarPorProduto_deveRetornarMovimentacoes() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDateTime data = LocalDateTime.of(2026, 3, 10, 12, 0);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("m_id")).thenReturn(1);
        when(rs.getString("tipo")).thenReturn("ENTRADA");
        when(rs.getInt("quantidade")).thenReturn(5);
        when(rs.getTimestamp("data_movimento")).thenReturn(Timestamp.valueOf(data));
        when(rs.getInt("p_id")).thenReturn(1);
        when(rs.getString("nome")).thenReturn("Cafe");
        when(rs.getDouble("preco_unitario")).thenReturn(8.0);
        when(rs.getString("unidade")).thenReturn("UN");
        when(rs.getInt("quantidade_minima")).thenReturn(2);
        when(rs.getInt("quantidade_maxima")).thenReturn(100);

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            List<Movimentacao> movimentacoes = new MovimentacaoDAO().listarPorProduto(1);
            assertEquals(1, movimentacoes.size());
            assertEquals("ENTRADA", movimentacoes.get(0).getTipo());
        }
    }

    @Test
    void listarTodas_deveRetornarMovimentacoesQuandoExistemRegistros() throws Exception {
        Connection conn = mock(Connection.class);
        java.sql.Statement stmt = mock(java.sql.Statement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDateTime data = LocalDateTime.of(2026, 3, 10, 12, 0);

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("m_id")).thenReturn(1);
        when(rs.getString("tipo")).thenReturn("SAIDA");
        when(rs.getInt("quantidade")).thenReturn(2);
        when(rs.getTimestamp("data_movimento")).thenReturn(Timestamp.valueOf(data));
        when(rs.getInt("p_id")).thenReturn(1);
        when(rs.getString("nome")).thenReturn("Cafe");
        when(rs.getDouble("preco_unitario")).thenReturn(8.0);
        when(rs.getString("unidade")).thenReturn("UN");
        when(rs.getInt("quantidade_minima")).thenReturn(2);
        when(rs.getInt("quantidade_maxima")).thenReturn(100);

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            List<Movimentacao> movimentacoes = new MovimentacaoDAO().listarTodas();
            assertEquals(1, movimentacoes.size());
            assertEquals("SAIDA", movimentacoes.get(0).getTipo());
        }
    }

    @Test
    void listarTodas_deveRetornarListaVazia() throws Exception {
        Connection conn = mock(Connection.class);
        java.sql.Statement stmt = mock(java.sql.Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            List<Movimentacao> movimentacoes = new MovimentacaoDAO().listarTodas();
            assertEquals(0, movimentacoes.size());
        }
    }

    private MockedConstruction<Conexao> mockarConexao(Connection conn) {
        return mockConstruction(Conexao.class, (mock, context) -> {
            when(mock.getConexao()).thenReturn(conn);
            doNothing().when(mock).fecharConexao();
        });
    }
}
