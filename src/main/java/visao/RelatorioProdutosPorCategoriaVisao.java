package visao;

/**
 * Tela de relatório de produtos por categoria.
 * Exibe uma tabela com a quantidade de produtos cadastrados em cada categoria.
 * Os dados são carregados do banco de dados através do ProdutoDAO.
 * 
 * @author Marcos Antonio Gasperin
 */

import dao.ProdutoDAO;
import modelo.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioProdutosPorCategoriaVisao {

    private JPanel panel;
    private JTable tabela;
    private DefaultTableModel modelo;

    /**
     * Construtor da tela de relatório de produtos por categoria.
     * Inicializa os componentes gráficos e carrega os dados da tabela.
     */
    public RelatorioProdutosPorCategoriaVisao() {
        panel = new JPanel();
        panel.setLayout(null);

        modelo = new DefaultTableModel();
        modelo.addColumn("Categoria");
        modelo.addColumn("Qtd. de Produtos");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 10, 500, 300);
        panel.add(scroll);

        carregarDados();
    }

    /**
     * Carrega os dados dos produtos do banco de dados e preenche a tabela com a contagem por categoria.
     */
    private void carregarDados() {
        modelo.setRowCount(0);
        List<Produto> lista = new ProdutoDAO().listarTodos();

        Map<String, Integer> contagem = new HashMap<>();
        for (Produto p : lista) {
            String categoria = p.getCategoria().getNome();
            contagem.put(categoria, contagem.getOrDefault(categoria, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
            modelo.addRow(new Object[]{
                entry.getKey(),
                entry.getValue()
            });
        }
    }

    /**
     * Retorna o painel principal da tela de relatório de produtos por categoria.
     * 
     * @return JPanel com o conteúdo da tela.
     */
    public JPanel getContentPanel() {
        return panel;
    }
}
