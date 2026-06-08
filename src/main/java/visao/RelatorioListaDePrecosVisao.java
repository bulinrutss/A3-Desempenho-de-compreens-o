package visao;

/**
 * Tela de relatório de lista de preços dos produtos.
 * Exibe uma tabela com todos os produtos cadastrados, mostrando nome, preço unitário, unidade e categoria.
 * Os dados são carregados do banco de dados através do ProdutoDAO.
 * 
 * @author Marcos Antonio Gasperin
 */

import dao.ProdutoDAO;
import modelo.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class RelatorioListaDePrecosVisao {

    private JPanel panel;
    private JTable tabela;
    private DefaultTableModel modelo;

    /**
     * Construtor da tela de relatório de lista de preços.
     * Inicializa os componentes gráficos e carrega os dados da tabela.
     */
    public RelatorioListaDePrecosVisao() {
        panel = new JPanel();
        panel.setLayout(null);

        modelo = new DefaultTableModel();
        modelo.addColumn("Produto");
        modelo.addColumn("Preço Unitário");
        modelo.addColumn("Unidade");
        modelo.addColumn("Categoria");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 10, 560, 330);
        panel.add(scroll);

        carregarDados();
    }

    /**
     * Carrega os dados dos produtos do banco de dados e preenche a tabela.
     */
    private void carregarDados() {
        modelo.setRowCount(0);
        List<Produto> lista = new ProdutoDAO().listarTodos();
        lista.sort((a, b) -> a.getNome().compareToIgnoreCase(b.getNome()));

        for (Produto p : lista) {
            modelo.addRow(new Object[]{
                p.getNome(),
                String.format("R$ %.2f", p.getPrecoUnitario()),
                p.getUnidade(),
                p.getCategoria().getNome()
            });
        }
    }

    /**
     * Retorna o painel principal da tela de relatório de lista de preços.
     * 
     * @return JPanel com o conteúdo da tela.
     */
    public JPanel getContentPanel() {
        return panel;
    }
}
