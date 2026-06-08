import visao.MenuPrincipal;
import javax.swing.SwingUtilities;

/**
 * Classe principal do sistema Controle de Estoque.
 * Responsável por inicializar a aplicação e exibir a interface gráfica principal.
 * 
 * O método main utiliza SwingUtilities para garantir que a interface seja criada na thread de eventos do Swing.
 * 
 * @author Marcos Antonio Gasperin
 */
public class ControleEstoque {

    /**
     * Método principal que inicia a aplicação.
     * Cria e exibe a janela do MenuPrincipal.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
