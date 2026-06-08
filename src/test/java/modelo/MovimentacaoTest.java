package modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class MovimentacaoTest {

    @Test
    void deveCriarMovimentacaoComConstrutorParametrizado() {
        Produto produto = new Produto(1, "Cafe", 8.0, "UN", 20, 5, 100, null);
        LocalDateTime data = LocalDateTime.of(2026, 3, 15, 10, 30, 0);
        Movimentacao movimentacao = new Movimentacao(1, "ENTRADA", 10, data, produto);

        assertEquals(1, movimentacao.getId());
        assertEquals("ENTRADA", movimentacao.getTipo());
        assertEquals(10, movimentacao.getQuantidade());
        assertEquals(data, movimentacao.getDataMovimento());
        assertSame(produto, movimentacao.getProduto());
    }

    @Test
    void deveAtualizarAtributosComSetters() {
        Movimentacao movimentacao = new Movimentacao();
        Produto produto = new Produto();
        LocalDateTime data = LocalDateTime.of(2026, 6, 1, 14, 0, 0);

        movimentacao.setId(5);
        movimentacao.setTipo("SAIDA");
        movimentacao.setQuantidade(3);
        movimentacao.setDataMovimento(data);
        movimentacao.setProduto(produto);

        assertEquals(5, movimentacao.getId());
        assertEquals("SAIDA", movimentacao.getTipo());
        assertEquals(3, movimentacao.getQuantidade());
        assertEquals(data, movimentacao.getDataMovimento());
        assertEquals(produto, movimentacao.getProduto());
    }

    @Test
    void deveRetornarDataFormatadaOuVazioQuandoNula() {
        Movimentacao movimentacao = new Movimentacao();
        assertEquals("", movimentacao.getDataFormatada());

        movimentacao.setDataMovimento(LocalDateTime.of(2026, 1, 2, 8, 15, 30));
        assertEquals("02/01/2026 08:15:30", movimentacao.getDataFormatada());
    }
}
