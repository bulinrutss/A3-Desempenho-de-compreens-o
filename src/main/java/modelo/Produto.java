package modelo;

/**
 * Classe que representa um produto do estoque.
 * Contém informações como id, nome, preço unitário, unidade, quantidade em estoque,
 * quantidade mínima, quantidade máxima e a categoria do produto.
 * Fornece métodos de acesso (get/set) para todos os atributos.
 * 
 * @author João Vitor Cardoso de Jesus
 */

public class Produto {
    private int id;
    private String nome;
    private double precoUnitario;
    private String unidade;
    private int quantidadeEstoque;
    private int quantidadeMinima;
    private int quantidadeMaxima;
    private Categoria categoria;

    /**
     * Construtor padrão.
     */
    public Produto() {
    }

    /**
     * Construtor com parâmetros.
     * 
     * @param id                Identificador do produto.
     * @param nome              Nome do produto.
     * @param precoUnitario     Preço unitário do produto.
     * @param unidade           Unidade de medida do produto.
     * @param quantidadeEstoque Quantidade atual em estoque.
     * @param quantidadeMinima  Quantidade mínima permitida em estoque.
     * @param quantidadeMaxima  Quantidade máxima permitida em estoque.
     * @param categoria         Categoria do produto.
     */
    public Produto(int id, String nome, double precoUnitario, String unidade, int quantidadeEstoque,
                   int quantidadeMinima, int quantidadeMaxima, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.unidade = unidade;
        this.quantidadeEstoque = quantidadeEstoque;
        this.quantidadeMinima = quantidadeMinima;
        this.quantidadeMaxima = quantidadeMaxima;
        this.categoria = categoria;
    }

    /**
     * Retorna o id do produto.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Define o id do produto.
     * @param id Identificador do produto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome do produto.
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     * @param nome Nome do produto.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o preço unitário do produto.
     * @return precoUnitario
     */
    public double getPrecoUnitario() {
        return precoUnitario;
    }

    /**
     * Define o preço unitário do produto.
     * @param precoUnitario Preço unitário.
     */
    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    /**
     * Retorna a unidade de medida do produto.
     * @return unidade
     */
    public String getUnidade() {
        return unidade;
    }

    /**
     * Define a unidade de medida do produto.
     * @param unidade Unidade de medida.
     */
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    /**
     * Retorna a quantidade atual em estoque.
     * @return quantidadeEstoque
     */
    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    /**
     * Define a quantidade atual em estoque.
     * @param quantidadeEstoque Quantidade em estoque.
     */
    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    /**
     * Alias para setQuantidadeEstoque (compatibilidade com Servlets).
     * @param quantidade Quantidade em estoque.
     */
    public void setQuantidade(int quantidade) {
        this.quantidadeEstoque = quantidade;
    }

    /**
     * Alias para getQuantidadeEstoque (compatibilidade com Servlets).
     * @return quantidade em estoque
     */
    public int getQuantidade() {
        return this.quantidadeEstoque;
    }

    /**
     * Retorna a quantidade mínima permitida em estoque.
     * @return quantidadeMinima
     */
    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    /**
     * Define a quantidade mínima permitida em estoque.
     * @param quantidadeMinima Quantidade mínima.
     */
    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    /**
     * Retorna a quantidade máxima permitida em estoque.
     * @return quantidadeMaxima
     */
    public int getQuantidadeMaxima() {
        return quantidadeMaxima;
    }

    /**
     * Define a quantidade máxima permitida em estoque.
     * @param quantidadeMaxima Quantidade máxima.
     */
    public void setQuantidadeMaxima(int quantidadeMaxima) {
        this.quantidadeMaxima = quantidadeMaxima;
    }

    /**
     * Retorna a categoria do produto.
     * @return categoria
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria do produto.
     * @param categoria Categoria do produto.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Retorna o nome do produto como representação textual.
     * @return nome do produto
     */
    public String toString() {
        return nome;
    }
}

