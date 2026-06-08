package visao;

/**
 * Tela de relatório de produtos acima do estoque máximo.
 * Exibe uma tabela com todos os produtos cujo estoque está acima da quantidade máxima definida.
 * Os dados são carregados do banco de dados através do ProdutoDAO.
 * 
 * @author Marcos Antonio Gasperin
 */

import dao.ProdutoDAO;
import modelo.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class RelatorioProdutosAcimaMaximoVisao {

    private JPanel panel;
    private JTable tabela;
    private DefaultTableModel modelo;

    /**
     * Construtor da tela de relatório de produtos acima do máximo.
     * Inicializa os componentes gráficos e carrega os dados da tabela.
     */
    public RelatorioProdutosAcimaMaximoVisao() {
        panel = new JPanel();
        panel.setLayout(null);

        modelo = new DefaultTableModel();
        modelo.addColumn("Produto");
        modelo.addColumn("Qtd. Estoque");
        modelo.addColumn("Qtd. Máxima");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 10, 500, 300);
        panel.add(scroll);

        carregarDados();
    }

    /**
     * Carrega os dados dos produtos do banco de dados e preenche a tabela apenas com os que estão acima do máximo.
     */
    private void carregarDados() {
        modelo.setRowCount(0);
        List<Produto> lista = new ProdutoDAO().listarTodos();

        for (Produto p : lista) {
            if (p.getQuantidadeEstoque() > p.getQuantidadeMaxima()) {
                modelo.addRow(new Object[]{
                    p.getNome(),
                    p.getQuantidadeEstoque(),
                    p.getQuantidadeMaxima()
                });
            }
        }
    }

    /**
     * Retorna o painel principal da tela de relatório de produtos acima do máximo.
     * 
     * @return JPanel com o conteúdo da tela.
     */
    public JPanel getContentPanel() {
        return panel;
    }
}


