package visao;

/**
 * Tela de cadastro e manutenção de produtos.
 * Permite inserir, atualizar, excluir e listar produtos do estoque.
 * Os campos incluem nome, preço, unidade, quantidades, categoria e uma tabela para exibição dos produtos cadastrados.
 * 
 * Os botões permitem salvar um novo produto, atualizar um existente ou excluir.
 * A seleção de uma linha na tabela preenche os campos para edição.
 * 
 * @author João Vitor Cardoso de Jesus
 */

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import modelo.Categoria;
import modelo.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ProdutoVisao {

    private JPanel panel;
    private JTextField txtNome, txtPreco, txtUnidade, txtQtd, txtMin, txtMax;
    private JComboBox<Categoria> comboCategoria;
    private JTable tabela;
    private DefaultTableModel modelo;

    /**
     * Construtor da tela de produtos.
     * Inicializa os componentes gráficos, listeners e carrega os dados da tabela e categorias.
     */
    public ProdutoVisao() {
        panel = new JPanel();
        panel.setLayout(null);

        int y = 10;
        panel.add(label("Nome:", 10, y)); txtNome = field(100, y); panel.add(txtNome); y += 30;
        panel.add(label("Preço:", 10, y)); txtPreco = field(100, y); panel.add(txtPreco); y += 30;
        panel.add(label("Unidade:", 10, y)); txtUnidade = field(100, y); panel.add(txtUnidade); y += 30;
        panel.add(label("Qtd Estoque:", 10, y)); txtQtd = field(100, y); panel.add(txtQtd); y += 30;
        panel.add(label("Qtd Mínima:", 10, y)); txtMin = field(100, y); panel.add(txtMin); y += 30;
        panel.add(label("Qtd Máxima:", 10, y)); txtMax = field(100, y); panel.add(txtMax); y += 30;

        panel.add(label("Categoria:", 10, y));
        comboCategoria = new JComboBox<>();
        comboCategoria.setBounds(100, y, 200, 25);
        panel.add(comboCategoria); y += 35;

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(320, 10, 100, 25);
        panel.add(btnSalvar);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(320, 45, 100, 25);
        panel.add(btnAtualizar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(320, 80, 100, 25);
        panel.add(btnExcluir);

        modelo = new DefaultTableModel(new String[]{"ID", "Nome", "Preço", "Unidade", "Estoque", "Min", "Max", "Categoria"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, y, 660, 250);
        panel.add(scroll);

        carregarCategorias();
        carregarTabela();

        btnSalvar.addActionListener(e -> {
            Produto p = new Produto();
            p.setNome(txtNome.getText());
            p.setPrecoUnitario(Double.parseDouble(txtPreco.getText()));
            p.setUnidade(txtUnidade.getText());
            p.setQuantidadeEstoque(Integer.parseInt(txtQtd.getText()));
            p.setQuantidadeMinima(Integer.parseInt(txtMin.getText()));
            p.setQuantidadeMaxima(Integer.parseInt(txtMax.getText()));
            p.setCategoria((Categoria) comboCategoria.getSelectedItem());

            new ProdutoDAO().inserir(p);
            limparCampos();
            carregarTabela();
        });

        btnAtualizar.addActionListener(e -> {
            int selected = tabela.getSelectedRow();
            if (selected != -1) {
                Produto p = new Produto();
                p.setId((int) modelo.getValueAt(selected, 0));
                p.setNome(txtNome.getText());
                p.setPrecoUnitario(Double.parseDouble(txtPreco.getText()));
                p.setUnidade(txtUnidade.getText());
                p.setQuantidadeEstoque(Integer.parseInt(txtQtd.getText()));
                p.setQuantidadeMinima(Integer.parseInt(txtMin.getText()));
                p.setQuantidadeMaxima(Integer.parseInt(txtMax.getText()));
                p.setCategoria((Categoria) comboCategoria.getSelectedItem());

                new ProdutoDAO().atualizar(p);
                limparCampos();
                carregarTabela();
            }
        });

        btnExcluir.addActionListener(e -> {
            int selected = tabela.getSelectedRow();
            if (selected != -1) {
                int id = (int) modelo.getValueAt(selected, 0);
                new ProdutoDAO().excluir(id);
                limparCampos();
                carregarTabela();
            }
        });

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = tabela.getSelectedRow();
                txtNome.setText(modelo.getValueAt(i, 1).toString());
                txtPreco.setText(modelo.getValueAt(i, 2).toString());
                txtUnidade.setText(modelo.getValueAt(i, 3).toString());
                txtQtd.setText(modelo.getValueAt(i, 4).toString());
                txtMin.setText(modelo.getValueAt(i, 5).toString());
                txtMax.setText(modelo.getValueAt(i, 6).toString());
                String cat = modelo.getValueAt(i, 7).toString();
                for (int j = 0; j < comboCategoria.getItemCount(); j++) {
                    if (comboCategoria.getItemAt(j).getNome().equals(cat)) {
                        comboCategoria.setSelectedIndex(j);
                        break;
                    }
                }
            }
        });
    }

    /**
     * Cria e retorna um JLabel posicionado.
     * @param texto Texto do label.
     * @param x Posição X.
     * @param y Posição Y.
     * @return JLabel criado.
     */
    private JLabel label(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(x, y, 90, 20);
        return lbl;
    }

    /**
     * Cria e retorna um JTextField posicionado.
     * @param x Posição X.
     * @param y Posição Y.
     * @return JTextField criado.
     */
    private JTextField field(int x, int y) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, 200, 25);
        return txt;
    }

    /**
     * Carrega todas as categorias cadastradas no banco e preenche o combo de seleção.
     */
    private void carregarCategorias() {
        List<Categoria> lista = new CategoriaDAO().listarTodas();
        comboCategoria.removeAllItems();
        for (Categoria c : lista) {
            comboCategoria.addItem(c);
        }
    }

    /**
     * Carrega todos os produtos cadastrados no banco e preenche a tabela.
     */
    private void carregarTabela() {
        modelo.setRowCount(0);
        for (Produto p : new ProdutoDAO().listarTodos()) {
            modelo.addRow(new Object[]{
                    p.getId(), p.getNome(), p.getPrecoUnitario(), p.getUnidade(),
                    p.getQuantidadeEstoque(), p.getQuantidadeMinima(), p.getQuantidadeMaxima(),
                    p.getCategoria().getNome()
            });
        }
    }

    /**
     * Limpa os campos de texto e a seleção da tabela.
     * Usado após salvar, atualizar ou excluir um produto.
     */
    private void limparCampos() {
        txtNome.setText("");
        txtPreco.setText("");
        txtUnidade.setText("");
        txtQtd.setText("");
        txtMin.setText("");
        txtMax.setText("");
        comboCategoria.setSelectedIndex(-1);
    }

    /**
     * Retorna o painel principal da tela de produtos.
     * Usado pelo MenuPrincipal para exibir esta tela.
     * 
     * @return JPanel com o conteúdo da tela de produtos.
     */
    public JPanel getContentPanel() {
        return panel;
    }
}
