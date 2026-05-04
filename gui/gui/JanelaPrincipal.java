package gui;

import entity.NavegacaoUpdate;
import javax.swing.JFrame;

public class JanelaPrincipal extends JFrame {
    private PainelSimulador painel;

    public JanelaPrincipal() {
        //Configurações do tamanho da janela
        setTitle("Simulador de Navegação Náutica");
        setSize(800, 600); //Largura x altura inicial
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Quando fechamos a janela, o programa termina
        setLocationRelativeTo(null); //faz com que a janela apareça centrada no ecrã do utilizador

        //instanciamos e adicionamos o nosso painel
        painel = new PainelSimulador();
        add(painel); //Colamos o painel na janela
    }

    /**
     * O SimuladorController ou a classe Main vai chamar este método para passar os dados.
     * passamos então tudo para o painel de desenho.
     */
    public void enviarUpdate(NavegacaoUpdate update) {
        painel.atualizar(update);
    }
}
