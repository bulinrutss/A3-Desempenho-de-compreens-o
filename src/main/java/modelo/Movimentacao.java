package modelo;

/**
 * Classe que representa uma movimentação de entrada ou saída de produto.
 * Contém informações como id, tipo (ENTRADA/SAÍDA), quantidade, data da movimentação e o produto relacionado.
 * Fornece métodos de acesso (get/set) para os atributos.
 * 
 * @author Kaike Augusto Dias dos Santos
 */

import java.time.LocalDateTime;

public class Movimentacao {
    private int id;
    private String tipo;
    private int quantidade;
    private LocalDateTime dataMovimento;
    private Produto produto;

    /**
     * Construtor padrão.
     */
    public Movimentacao() {}

    /**
     * Construtor com parâmetros.
     * 
     * @param id             Identificador da movimentação.
     * @param tipo           Tipo da movimentação (ENTRADA ou SAÍDA).
     * @param quantidade     Quantidade movimentada.
     * @param dataMovimento  Data e hora da movimentação.
     * @param produto        Produto relacionado à movimentação.
     */
    public Movimentacao(int id, String tipo, int quantidade, java.time.LocalDateTime dataMovimento, Produto produto) {
        this.id = id;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.dataMovimento = dataMovimento;
        this.produto = produto;
    }

    /**
     * Retorna o id da movimentação.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Define o id da movimentação.
     * @param id Identificador da movimentação.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o tipo da movimentação (ENTRADA ou SAÍDA).
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo da movimentação.
     * @param tipo Tipo da movimentação (ENTRADA ou SAÍDA).
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna a quantidade movimentada.
     * @return quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade movimentada.
     * @param quantidade Quantidade movimentada.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Retorna a data e hora da movimentação.
     * @return dataMovimento
     */
    public LocalDateTime getDataMovimento() {
        return dataMovimento;
    }

    /**
     * Define a data e hora da movimentação.
     * @param dataMovimento Data e hora da movimentação.
     */
    public void setDataMovimento(LocalDateTime dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    /**
     * Retorna o produto relacionado à movimentação.
     * @return produto
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Define o produto relacionado à movimentação.
     * @param produto Produto relacionado.
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * Retorna a data formatada como String (dd/MM/yyyy HH:mm:ss).
     * @return data formatada
     */
    public String getDataFormatada() {
        if (dataMovimento == null) {
            return "";
        }
        java.time.format.DateTimeFormatter formatter = 
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dataMovimento.format(formatter);
    }
}

