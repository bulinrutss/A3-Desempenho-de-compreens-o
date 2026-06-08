package dao;

import modelo.Categoria;
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

class CategoriaDAOTest {

    @Test
    void buscarPorId_deveRetornarCategoriaQuandoEncontrada() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("nome")).thenReturn("Bebidas");
        when(rs.getString("tamanho")).thenReturn("500ml");
        when(rs.getString("embalagem")).thenReturn("Garrafa");

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            Categoria categoria = new CategoriaDAO().buscarPorId(1);

            assertNotNull(categoria);
            assertEquals(1, categoria.getId());
            assertEquals("Bebidas", categoria.getNome());
            assertEquals("500ml", categoria.getTamanho());
            assertEquals("Garrafa", categoria.getEmbalagem());
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
            assertNull(new CategoriaDAO().buscarPorId(999));
        }
    }

    @Test
    void listarTodas_deveRetornarListaVaziaQuandoNaoHaRegistros() throws Exception {
        Connection conn = mock(Connection.class);
        java.sql.Statement stmt = mock(java.sql.Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            List<Categoria> categorias = new CategoriaDAO().listarTodas();
            assertEquals(0, categorias.size());
        }
    }

    @Test
    void inserir_deveExecutarInsert() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);

        Categoria categoria = new Categoria();
        categoria.setNome("Laticinios");
        categoria.setTamanho("1L");
        categoria.setEmbalagem("Caixa");

        try (MockedConstruction<Conexao> ignored = mockarConexao(conn)) {
            new CategoriaDAO().inserir(categoria);
            verify(stmt).executeUpdate();
        }
    }

    private MockedConstruction<Conexao> mockarConexao(Connection conn) {
        return mockConstruction(Conexao.class, (mock, context) -> {
            when(mock.getConexao()).thenReturn(conn);
            doNothing().when(mock).fecharConexao();
        });
    }
}
