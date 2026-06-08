package modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoriaTest {

    @Test
    void deveCriarCategoriaComConstrutorParametrizado() {
        Categoria categoria = new Categoria(1, "Bebidas", "500ml", "Garrafa");

        assertEquals(1, categoria.getId());
        assertEquals("Bebidas", categoria.getNome());
        assertEquals("500ml", categoria.getTamanho());
        assertEquals("Garrafa", categoria.getEmbalagem());
        assertEquals("Bebidas", categoria.toString());
    }

    @Test
    void deveAtualizarAtributosComSetters() {
        Categoria categoria = new Categoria();

        categoria.setId(2);
        categoria.setNome("Alimentos");
        categoria.setTamanho("1kg");
        categoria.setEmbalagem("Caixa");

        assertEquals(2, categoria.getId());
        assertEquals("Alimentos", categoria.getNome());
        assertEquals("1kg", categoria.getTamanho());
        assertEquals("Caixa", categoria.getEmbalagem());
    }
}
