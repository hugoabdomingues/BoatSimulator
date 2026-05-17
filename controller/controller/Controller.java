package controller;

import entity.Mapa;
import entity.NavegacaoUpdate;
import geometria.Vetor;
import gui.View;

import javax.swing.*;

/**
 * Controller do padrão MVC. Liga a View ao Model (Mapa).
 * Lê os inputs do utilizador, arranca a simulação e entrega os NavegacaoUpdates ao MapaView a cada tick.
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 21-03-2026
 */
public class Controller {
    private View janela;
    private Mapa mapa;

    /**
     * Cria o Controller e associa-o à janela e ao mapa.
     * Chama imediatamente mostrarMenu() para exibir o ecrã inicial ao utilizador.
     * @param janela A janela principal da aplicação
     * @param mapa A instância única do Mapa (singleton)
     */
    public Controller(View janela, Mapa mapa) {
        this.mapa = mapa;
        this.janela = janela;
        mostrarMenu();
    }

    /**
     * Exibe o painel do menu inicial e liga o listener ao botão Start.
     * Quando o utilizador carrega Start, lê a corrente (X, Y), configura o mapa e avança para o mapa.
     */
    private void mostrarMenu() {
        this.janela.getMenuInicial().getStart().addActionListener(e -> {
            double x = this.janela.getMenuInicial().getInputX();
            double y = this.janela.getMenuInicial().getInputY();
            mapa.setCorrente(new Vetor(x, y)); //aceitamos todos os casos dados do utilizador

            mostrarMapa();
        });

        this.janela.getContentPane().removeAll();
        this.janela.add(this.janela.getMenuInicial());
        this.janela.revalidate();
        this.janela.repaint();
    }

    /**
     * Substitui o menu pelo painel do mapa e arranca o temporizador da simulação.
     */
    private void mostrarMapa() {
        this.janela.getContentPane().removeAll();
        this.janela.add(this.janela.getMostrarMapa());
        this.janela.revalidate();
        this.janela.repaint();
        iniciarTemporizador();
    }

    /**
     * Cria e arranca o temporizador que conduz o loop da simulação.
     * A cada 100ms: avança o mapa (tick), obtém o DTO de estado e entrega ao MapaView para redesenhar.
     */
    private void iniciarTemporizador() {
        Timer timer = new Timer(100, e -> {
            mapa.tick(0.1);
            NavegacaoUpdate update = mapa.getNavegUpdate();
            janela.getMostrarMapa().atualizar(update);
            this.janela.repaint();
        });
        timer.start();
    }
}
