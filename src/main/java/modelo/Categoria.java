package modelo;

/**
 * Classe que representa uma categoria de produto.
 * Contém informações como id, nome, tamanho e embalagem.
 * 
 * Fornece métodos de acesso (get/set) para os atributos.
 * 
 * @author Douglas Pierri Beccari
 */
public class Categoria {
    private int id;
    private String nome;
    private String tamanho;
    private String embalagem;

    /**
     * Construtor padrão.
     */
    public Categoria() {
    }

    /**
     * Construtor com parâmetros.
     * 
     * @param id        Identificador da categoria.
     * @param nome      Nome da categoria.
     * @param tamanho   Tamanho associado à categoria.
     * @param embalagem Tipo de embalagem da categoria.
     */
    public Categoria(int id, String nome, String tamanho, String embalagem) {
        this.id = id;
        this.nome = nome;
        this.tamanho = tamanho;
        this.embalagem = embalagem;
    }

    /**
     * Retorna o id da categoria.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Define o id da categoria.
     * @param id Identificador da categoria.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome da categoria.
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da categoria.
     * @param nome Nome da categoria.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o tamanho da categoria.
     * @return tamanho
     */
    public String getTamanho() {
        return tamanho;
    }

    /**
     * Define o tamanho da categoria.
     * @param tamanho Tamanho da categoria.
     */
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    /**
     * Retorna o tipo de embalagem da categoria.
     * @return embalagem
     */
    public String getEmbalagem() {
        return embalagem;
    }

    /**
     * Define o tipo de embalagem da categoria.
     * @param embalagem Tipo de embalagem.
     */
    public void setEmbalagem(String embalagem) {
        this.embalagem = embalagem;
    }
    
    /**
     * Retorna o nome da categoria como representação textual.
     * @return nome da categoria
     */
    public String toString() {
        return nome;
    }
}

