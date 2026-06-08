package visao;

/**
 * Tela de movimentação de estoque.
 * Permite registrar entradas e saídas de produtos, selecionando o produto e a quantidade.
 * Exibe alertas caso o estoque fique acima do máximo ou abaixo do mínimo após a movimentação.
 * 
 * Os dados são atualizados no banco de dados e a lista de produtos é recarregada após cada operação.
 * 
 * @author Kaike Augusto Dias dos Santos
 */

import dao.MovimentacaoDAO;
import dao.ProdutoDAO;
import modelo.Movimentacao;
import modelo.Produto;

import javax.swing.*;
import java.util.List;

public class MovimentacaoVisao {

    private JPanel panel;
    private JComboBox<Produto> comboProduto;
    private JTextField txtQuantidade;

    /**
     * Construtor da tela de movimentação.
     * Inicializa os componentes gráficos, listeners e carrega a lista de produtos.
     */
    public MovimentacaoVisao() {
        panel = new JPanel();
        panel.setLayout(null);

        JLabel lblProduto = new JLabel("Produto:");
        lblProduto.setBounds(20, 20, 100, 25);
        panel.add(lblProduto);

        comboProduto = new JComboBox<>();
        comboProduto.setBounds(120, 20, 230, 25);
        panel.add(comboProduto);

        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setBounds(20, 60, 100, 25);
        panel.add(lblQuantidade);

        txtQuantidade = new JTextField();
        txtQuantidade.setBounds(120, 60, 230, 25);
        panel.add(txtQuantidade);

        JButton btnEntrada = new JButton("Entrada");
        btnEntrada.setBounds(60, 110, 100, 30);
        panel.add(btnEntrada);

        JButton btnSaida = new JButton("Saída");
        btnSaida.setBounds(180, 110, 100, 30);
        panel.add(btnSaida);

        carregarProdutos();

        btnEntrada.addActionListener(e -> registrarMovimento("ENTRADA"));
        btnSaida.addActionListener(e -> registrarMovimento("SAIDA"));
    }

    /**
     * Carrega todos os produtos cadastrados no banco e preenche o combo de seleção.
     */
    private void carregarProdutos() {
        List<Produto> lista = new ProdutoDAO().listarTodos();
        comboProduto.removeAllItems();
        for (Produto p : lista) {
            comboProduto.addItem(p);
        }
    }

    /**
     * Registra uma movimentação de entrada ou saída para o produto selecionado.
     * Atualiza o estoque e exibe alertas caso necessário.
     * 
     * @param tipo Tipo da movimentação ("ENTRADA" ou "SAIDA").
     */
    private void registrarMovimento(String tipo) {
        Produto produto = (Produto) comboProduto.getSelectedItem();
        if (produto == null) {
            JOptionPane.showMessageDialog(panel, "Selecione um produto.");
            return;
        }

        int qtd;
        try {
            qtd = Integer.parseInt(txtQuantidade.getText());
            if (qtd <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Informe uma quantidade válida.");
            return;
        }

        Movimentacao mov = new Movimentacao();
        mov.setTipo(tipo);
        mov.setQuantidade(qtd);
        mov.setProduto(produto);

        new MovimentacaoDAO().registrarMovimento(mov);

        int novaQtd = tipo.equals("ENTRADA") ?
            produto.getQuantidadeEstoque() + qtd :
            produto.getQuantidadeEstoque() - qtd;

        String alerta = null;
        if (tipo.equals("ENTRADA") && novaQtd > produto.getQuantidadeMaxima()) {
            alerta = "⚠ Estoque acima do máximo!";
        } else if (tipo.equals("SAIDA") && novaQtd < produto.getQuantidadeMinima()) {
            alerta = "⚠ Estoque abaixo do mínimo!";
        }

        if (alerta != null) {
            JOptionPane.showMessageDialog(panel, alerta);
        } else {
            JOptionPane.showMessageDialog(panel, "Movimentação registrada com sucesso.");
        }

        txtQuantidade.setText("");
        carregarProdutos();
    }

    /**
     * Retorna o painel principal da tela de movimentação.
     * 
     * @return JPanel com o conteúdo da tela.
     */
    public JPanel getContentPanel() {
        return panel;
    }
}
