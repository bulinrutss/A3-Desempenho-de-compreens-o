package visao;

/**
 * Tela de relatório de balanço financeiro do estoque.
 * Exibe uma tabela com todos os produtos, mostrando quantidade em estoque, valor unitário e valor total por produto.
 * Também exibe o valor total geral do estoque.
 * 
 * Os dados são carregados do banco de dados através do ProdutoDAO.
 * 
 * @author Marcos Antonio Gasperin
 */

import dao.ProdutoDAO;
import modelo.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class RelatorioBalancoFinanceiroVisao {

    private JPanel panel;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JLabel lblTotalGeral;

    /**
     * Construtor da tela de relatório de balanço financeiro.
     * Inicializa os componentes gráficos e carrega os dados da tabela.
     */
    public RelatorioBalancoFinanceiroVisao() {
        panel = new JPanel();
        panel.setLayout(null);

        modelo = new DefaultTableModel();
        modelo.addColumn("Produto");
        modelo.addColumn("Qtd. Estoque");
        modelo.addColumn("Valor Unit.");
        modelo.addColumn("Valor Total");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 10, 640, 300);
        panel.add(scroll);

        lblTotalGeral = new JLabel("Total Geral: R$ 0.00");
        lblTotalGeral.setBounds(10, 320, 300, 25);
        panel.add(lblTotalGeral);

        carregarDados();
    }

    /**
     * Carrega os dados dos produtos do banco de dados e preenche a tabela.
     * Calcula o valor total de cada produto e o total geral do estoque.
     */
    private void carregarDados() {
        modelo.setRowCount(0);
        double totalGeral = 0;
        List<Produto> lista = new ProdutoDAO().listarTodos();
        lista.sort((a, b) -> a.getNome().compareToIgnoreCase(b.getNome()));

        for (Produto p : lista) {
            double total = p.getPrecoUnitario() * p.getQuantidadeEstoque();
            modelo.addRow(new Object[]{
                p.getNome(),
                p.getQuantidadeEstoque(),
                String.format("R$ %.2f", p.getPrecoUnitario()),
                String.format("R$ %.2f", total)
            });
            totalGeral += total;
        }

        lblTotalGeral.setText(String.format("Total Geral: R$ %.2f", totalGeral));
    }

    /**
     * Retorna o painel principal da tela de relatório de balanço financeiro.
     * 
     * @return JPanel com o conteúdo da tela.
     */
    public JPanel getContentPanel() {
        return panel;
    }
}
