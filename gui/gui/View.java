package gui;

import javax.swing.*;
/**
 * A classe View herda de JFrame. Um JFrame é a janela do Windows/Mac.---> É o frame com os botões de fechar e minimizar no canto.
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 14-05-2026
 */
public class View extends JFrame {
    private MapaView mapaView; //criamos uma tela para o mapa
    private MenuView menuView; // e uma tela para o menu

    /**
     * Construtor da View
     * Cria a janela principal, inicializa os painéis do menu e do mapa e configura a janela.
     */
    public View() {
        this.mapaView = new MapaView();
        this.menuView = new MenuView();
        configurarJanela();
    }

    /**
     * Define as propriedades base da janela: título, tamanho, comportamento de fecho e posição no ecrã.
     */
    private void configurarJanela() {
        setTitle("Simulador de Navegação");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Getter do painel da janela inicial para o Controller ligar o botão Start.
     * @return O painel MenuView com os campos da corrente e o botão Start
     */
    public MenuView getMenuInicial() {
        return this.menuView;
    }

    /**
     * Getter d o painel do mapa para o Controller o mostrar e enviar updates.
     * @return O painel MapaView que desenha o estado da simulação
     */
    public MapaView getMostrarMapa() {
        return this.mapaView;
    }
}
