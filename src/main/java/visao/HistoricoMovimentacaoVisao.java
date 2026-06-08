package visao;

/**
 * Tela de histórico de movimentações de produtos.
 * Exibe uma tabela com todas as movimentações cadastradas, mostrando tipo, produto, quantidade e data/hora.
 * Os dados são carregados do banco de dados através do MovimentacaoDAO.
 * 
 * @author Kaike Augusto Dias dos Santos
 */

import dao.MovimentacaoDAO;
import modelo.Movimentacao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoricoMovimentacaoVisao {

    private JPanel panel;
    private JTable tabela;
    private DefaultTableModel modelo;

    /**
     * Construtor da tela de histórico de movimentações.
     * Inicializa os componentes gráficos e carrega os dados da tabela.
     */
    public HistoricoMovimentacaoVisao() {
        panel = new JPanel();
        panel.setLayout(null);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Tipo");
        modelo.addColumn("Produto");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Data/Hora");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 10, 660, 330);
        panel.add(scroll);

        carregarHistorico();
    }

    /**
     * Carrega o histórico de movimentações do banco de dados e preenche a tabela.
     */
    private void carregarHistorico() {
        modelo.setRowCount(0);
        List<Movimentacao> lista = new MovimentacaoDAO().listarTodas();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Movimentacao mov : lista) {
            modelo.addRow(new Object[]{
                mov.getId(),
                mov.getTipo(),
                mov.getProduto().getNome(),
                mov.getQuantidade(),
                dtf.format(mov.getDataMovimento())
            });
        }
    }

    /**
     * Retorna o painel principal da tela de histórico de movimentações.
     * 
     * @return JPanel com o conteúdo da tela.
     */
    public JPanel getContentPanel() {
        return panel;
    }
}
