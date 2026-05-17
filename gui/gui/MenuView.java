package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Classe que representa o painel da janela inicial
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 14-05-2026
 */
public class MenuView extends JPanel {
    private JTextField InputCorrenteX = new JTextField(10);
    private JTextField InputCorrenteY = new JTextField(10);
    private JButton botaoStart = new JButton("Start");

    /**
     * Construtor do Menu View
     * Cria o painel de configuração da corrente com dois campos de texto (X e Y) e o botão Start.
     */
    public MenuView(){
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                "Configuração da Corrente",
                TitledBorder.CENTER,
                TitledBorder.TOP));

        add(new JLabel("X:"));
        add(this.InputCorrenteX);
        add(new JLabel("Y:"));
        add(this.InputCorrenteY);
        this.add(new JLabel(""));
        add(this.botaoStart);
    }

    /**
     * Getter do valor da componente X da corrente introduzido pelo utilizador
     * @return Componente X da corrente como double
     */
    public double getInputX(){
        return Double.valueOf(InputCorrenteX.getText());
    }

    /**
     * Getter do valor da componente Y da corrente introduzido pelo utilizador.
     * @return Componente Y da corrente como double
     */
    public double getInputY(){
        return Double.valueOf(InputCorrenteY.getText());
    }

    /**
     * Getter do botão Start para que o Controller possa ligar o seu listener.
     * @return O JButton Start
     */
    public JButton getStart(){
        return this.botaoStart;
    }
}
