package modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ProdutoTest {

    @Test
    void deveCriarProdutoComConstrutorParametrizado() {
        Categoria categoria = new Categoria(1, "Bebidas", "500ml", "Garrafa");
        Produto produto = new Produto(10, "Refrigerante", 5.50, "UN", 100, 10, 500, categoria);

        assertEquals(10, produto.getId());
        assertEquals("Refrigerante", produto.getNome());
        assertEquals(5.50, produto.getPrecoUnitario());
        assertEquals("UN", produto.getUnidade());
        assertEquals(100, produto.getQuantidadeEstoque());
        assertEquals(100, produto.getQuantidade());
        assertEquals(10, produto.getQuantidadeMinima());
        assertEquals(500, produto.getQuantidadeMaxima());
        assertSame(categoria, produto.getCategoria());
        assertEquals("Refrigerante", produto.toString());
    }

    @Test
    void deveAtualizarAtributosComSettersEAliases() {
        Produto produto = new Produto();
        Categoria categoria = new Categoria(2, "Alimentos", "1kg", "Caixa");

        produto.setId(3);
        produto.setNome("Arroz");
        produto.setPrecoUnitario(12.90);
        produto.setUnidade("KG");
        produto.setQuantidade(50);
        produto.setQuantidadeMinima(5);
        produto.setQuantidadeMaxima(200);
        produto.setCategoria(categoria);

        assertEquals(3, produto.getId());
        assertEquals("Arroz", produto.getNome());
        assertEquals(12.90, produto.getPrecoUnitario());
        assertEquals("KG", produto.getUnidade());
        assertEquals(50, produto.getQuantidadeEstoque());
        assertEquals(5, produto.getQuantidadeMinima());
        assertEquals(200, produto.getQuantidadeMaxima());
        assertEquals(categoria, produto.getCategoria());
    }
}
