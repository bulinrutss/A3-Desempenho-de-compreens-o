package visao;

/**
 * Tela principal do sistema de Controle de Estoque.
 * Exibe o menu lateral com as opções de cadastro, movimentação, histórico e relatórios.
 * Ao clicar em cada botão, o painel de conteúdo é atualizado com a respectiva tela.
 * 
 * Também fornece método utilitário para verificar se uma categoria possui produtos vinculados.
 * 
 * @author Marcos Antonio Gasperin
 */

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.Conexao;

public class MenuPrincipal extends JFrame {

    private JPanel painelConteudo;

    /**
     * Construtor da tela principal.
     * Inicializa os componentes gráficos, menu lateral e listeners dos botões.
     */
    public MenuPrincipal() {
        setTitle("Sistema de Controle de Estoque");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new GridLayout(0, 1, 5, 5));
        painelMenu.setPreferredSize(new Dimension(200, 0));

        JButton btnCategoria = new JButton("Categorias");
        JButton btnProduto = new JButton("Produtos");
        JButton btnMovimentacao = new JButton("Movimentar Estoque");
        JButton btnHistorico = new JButton("Histórico de Movimentações");
        JButton btnRel1 = new JButton("Relatório: Lista de Preços");
        JButton btnRel2 = new JButton("Relatório: Balanço Financeiro");
        JButton btnRel3 = new JButton("Relatório: Abaixo do Mínimo");
        JButton btnRel4 = new JButton("Relatório: Acima do Máximo");
        JButton btnRel5 = new JButton("Relatório: Por Categoria");

        painelMenu.add(btnCategoria);
        painelMenu.add(btnProduto);
        painelMenu.add(btnMovimentacao);
        painelMenu.add(btnHistorico);
        painelMenu.add(btnRel1);
        painelMenu.add(btnRel2);
        painelMenu.add(btnRel3);
        painelMenu.add(btnRel4);
        painelMenu.add(btnRel5);

        painelConteudo = new JPanel();
        painelConteudo.setLayout(new BorderLayout());

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelMenu, painelConteudo);
        split.setDividerLocation(200);
        split.setEnabled(false);

        add(split);

        btnCategoria.addActionListener(e -> mostrarPainel(new CategoriaVisao().getContentPanel()));
        btnProduto.addActionListener(e -> mostrarPainel(new ProdutoVisao().getContentPanel()));
        btnMovimentacao.addActionListener(e -> mostrarPainel(new MovimentacaoVisao().getContentPanel()));
        btnHistorico.addActionListener(e -> mostrarPainel(new HistoricoMovimentacaoVisao().getContentPanel()));
        btnRel1.addActionListener(e -> mostrarPainel(new RelatorioListaDePrecosVisao().getContentPanel()));
        btnRel2.addActionListener(e -> mostrarPainel(new RelatorioBalancoFinanceiroVisao().getContentPanel()));
        btnRel3.addActionListener(e -> mostrarPainel(new RelatorioProdutosAbaixoMinimoVisao().getContentPanel()));
        btnRel4.addActionListener(e -> mostrarPainel(new RelatorioProdutosAcimaMaximoVisao().getContentPanel()));
        btnRel5.addActionListener(e -> mostrarPainel(new RelatorioProdutosPorCategoriaVisao().getContentPanel()));
    }

    /**
     * Troca o painel de conteúdo exibido na área principal da tela.
     * 
     * @param painel Novo painel a ser exibido.
     */
    private void mostrarPainel(JPanel painel) {
        painelConteudo.removeAll();
        painelConteudo.add(painel, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }

    /**
     * Verifica se uma categoria possui produtos vinculados.
     * 
     * @param categoriaId ID da categoria a ser verificada.
     * @return true se houver produtos vinculados, false caso contrário.
     */
    public static boolean categoriaTemProdutos(int categoriaId) {
        String sql = "SELECT COUNT(*) FROM produto WHERE categoria_id = ?";
        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, categoriaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                boolean temProdutos = rs.getInt(1) > 0;
                rs.close();
                stmt.close();
                return temProdutos;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.fecharConexao();
        }
        return false;
    }

    /**
     * Método principal para iniciar a aplicação.
     * Cria e exibe a tela principal do sistema.
     * 
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
