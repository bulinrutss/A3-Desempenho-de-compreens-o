package visao;

import dao.ProdutoDAO;
import modelo.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Tela de relatório de produtos abaixo do estoque mínimo.
 * Exibe uma tabela com todos os produtos cujo estoque está abaixo da quantidade mínima definida.
 * Os dados são carregados do banco de dados através do ProdutoDAO.
 * 
 * @author Marcos Antonio Gasperin
 */
public class RelatorioProdutosAbaixoMinimoVisao  {

    private JPanel panel;
    private JTable tabela;
    private DefaultTableModel modelo;

    /**
     * Construtor da tela de relatório de produtos abaixo do mínimo.
     * Inicializa os componentes gráficos e carrega os dados da tabela.
     */
    public RelatorioProdutosAbaixoMinimoVisao () {
        panel = new JPanel();
        panel.setLayout(null);

        modelo = new DefaultTableModel();
        modelo.addColumn("Produto");
        modelo.addColumn("Qtd. Estoque");
        modelo.addColumn("Qtd. Mínima");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 10, 500, 300);
        panel.add(scroll);

        carregarDados();
    }

    /**
     * Carrega os dados dos produtos do banco de dados e preenche a tabela apenas com os que estão abaixo do mínimo.
     */
    private void carregarDados() {
        modelo.setRowCount(0);
        List<Produto> lista = new ProdutoDAO().listarTodos();

        for (Produto p : lista) {
            if (p.getQuantidadeEstoque() < p.getQuantidadeMinima()) {
                modelo.addRow(new Object[]{
                    p.getNome(),
                    p.getQuantidadeEstoque(),
                    p.getQuantidadeMinima()
                });
            }
        }
    }

    /**
     * Retorna o painel principal da tela de relatório de produtos abaixo do mínimo.
     * 
     * @return JPanel com o conteúdo da tela.
     */
    public JPanel getContentPanel() {
        return panel;
    }
}
