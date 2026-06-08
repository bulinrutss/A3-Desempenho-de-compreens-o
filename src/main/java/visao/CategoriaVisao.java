package visao;

/**
 * Tela de cadastro e manutenção de categorias.
 * Permite inserir, atualizar, excluir e listar categorias de produtos.
 * Utiliza uma tabela para exibir as categorias cadastradas e campos para edição.
 * 
 * Os botões permitem salvar uma nova categoria, atualizar uma existente ou excluir.
 * A seleção de uma linha na tabela preenche os campos para edição.
 * 
 * @author Douglas Pierri Beccari
 */

import dao.CategoriaDAO;
import modelo.Categoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CategoriaVisao {

    private JPanel panel;
    private JTextField txtNome;
    private JTextField txtTamanho;
    private JTextField txtEmbalagem;
    private JTable tabela;
    private DefaultTableModel modelo;

    /**
     * Construtor da tela de categorias.
     * Inicializa os componentes gráficos, listeners e carrega os dados da tabela.
     */
    public CategoriaVisao() {
        panel = new JPanel();
        panel.setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(10, 10, 100, 20);
        panel.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(110, 10, 200, 25);
        panel.add(txtNome);

        JLabel lblTamanho = new JLabel("Tamanho:");
        lblTamanho.setBounds(10, 40, 100, 20);
        panel.add(lblTamanho);

        txtTamanho = new JTextField();
        txtTamanho.setBounds(110, 40, 200, 25);
        panel.add(txtTamanho);

        JLabel lblEmbalagem = new JLabel("Embalagem:");
        lblEmbalagem.setBounds(10, 70, 100, 20);
        panel.add(lblEmbalagem);

        txtEmbalagem = new JTextField();
        txtEmbalagem.setBounds(110, 70, 200, 25);
        panel.add(txtEmbalagem);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(330, 10, 100, 25);
        panel.add(btnSalvar);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(330, 40, 100, 25);
        panel.add(btnAtualizar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(330, 70, 100, 25);
        panel.add(btnExcluir);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Tamanho");
        modelo.addColumn("Embalagem");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 110, 560, 230);
        panel.add(scroll);

        carregarTabela();

        btnSalvar.addActionListener(e -> {
            Categoria c = new Categoria();
            c.setNome(txtNome.getText());
            c.setTamanho(txtTamanho.getText());
            c.setEmbalagem(txtEmbalagem.getText());

            new CategoriaDAO().inserir(c);
            limparCampos();
            carregarTabela();
        });

        btnAtualizar.addActionListener(e -> {
            int selected = tabela.getSelectedRow();
            if (selected != -1) {
                int id = (int) modelo.getValueAt(selected, 0);
                Categoria c = new Categoria();
                c.setId(id);
                c.setNome(txtNome.getText());
                c.setTamanho(txtTamanho.getText());
                c.setEmbalagem(txtEmbalagem.getText());

                new CategoriaDAO().atualizar(c);
                limparCampos();
                carregarTabela();
            }
        });

        btnExcluir.addActionListener(e -> {
            int selected = tabela.getSelectedRow();
            if (selected != -1) {
                int id = Integer.parseInt(modelo.getValueAt(selected, 0).toString());

                if (MenuPrincipal.categoriaTemProdutos(id)) {
                    JOptionPane.showMessageDialog(panel, "Não é possível excluir esta categoria: existem produtos vinculados.");
                    return;
                }

                new CategoriaDAO().excluir(id);
                limparCampos();
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(panel, "Selecione uma categoria para excluir.");
            }
        });

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selected = tabela.getSelectedRow();
                txtNome.setText(modelo.getValueAt(selected, 1).toString());
                txtTamanho.setText(modelo.getValueAt(selected, 2).toString());
                txtEmbalagem.setText(modelo.getValueAt(selected, 3).toString());
            }
        });
    }

    /**
     * Carrega os dados das categorias cadastradas na tabela.
     * Busca todas as categorias no banco e preenche o modelo da tabela.
     */
    private void carregarTabela() {
        modelo.setRowCount(0);
        List<Categoria> lista = new CategoriaDAO().listarTodas();
        for (Categoria c : lista) {
            modelo.addRow(new Object[]{c.getId(), c.getNome(), c.getTamanho(), c.getEmbalagem()});
        }
    }

    /**
     * Limpa os campos de texto e a seleção da tabela.
     * Usado após salvar, atualizar ou excluir uma categoria.
     */
    private void limparCampos() {
        txtNome.setText("");
        txtTamanho.setText("");
        txtEmbalagem.setText("");
        tabela.clearSelection();
    }

    /**
     * Retorna o painel principal da tela de categorias.
     * Usado pelo MenuPrincipal para exibir esta tela.
     * 
     * @return JPanel com o conteúdo da tela de categorias.
     */
    public JPanel getContentPanel() {
        return panel;
    }
}
