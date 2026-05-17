import controller.Controller;
import entity.CenarioLoader;
import entity.Mapa;
import gui.View;

import javax.swing.*;

/**
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Mapa mapa = Mapa.getInstancia();
            CenarioLoader.carregarCenario(mapa);

            View janela = new View();
            new Controller(janela, mapa);

            janela.setVisible(true);
        });
    }
}


