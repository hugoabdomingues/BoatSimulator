package controller;

import entity.Mapa;
import entity.Porto;
import entity.Route;
import entity.NavegacaoUpdate;
import geometria.Ponto;
import geometria.Vetor;

public class SimuladorController {

    private Mapa mapa;


    public SimuladorController() {
        //instaciamos o mapa
        this.mapa = Mapa.getInstancia();

        //a corrente marítima
        this.mapa.setCorrente(new Vetor(new Ponto(1, 2)));

        inicializarCenario();

        // Iniciar a GUI (por fazer)
    }

    /**
     * O "Game Loop" que a GUI vai chamar várias vezes por segundo
     */
    public void atualizarSimulacao(double deltaTempo) {
        //Mandamos relogio avançar
        mapa.tick(deltaTempo);

        //Pedimos o estado atual do mapa
        NavegacaoUpdate estadoAtual = mapa.getNavegUpdate();

        //Mandamos esse estado para o GUI desenhar
    }

    /**
     * Onde criamos os portos, rotas e obstáculos exigidos pelo guião.
     */
    private void inicializarCenario() {

        //criamos 4 portos
        Porto portoA = new Porto("A", new Ponto(1, 1));
        portoA.agendarBarco("T=12, F, 2.0");
        portoA.agendarBarco("T=15, F, 3.0");
        mapa.addPorto(portoA);

        Porto portoE = new Porto("E", new Ponto(8, 8));
        mapa.addPorto(portoE);

        Porto portoF = new Porto("F", new Ponto(1, 8));
        portoF.agendarBarco("T=9, E, 2.0");
        mapa.addPorto(portoF);

        Porto portoJ = new Porto("J", new Ponto(8, 1));
        portoJ.agendarBarco("T=14, F, 1.0");
        mapa.addPorto(portoJ);

        // criamos as 6 rotas
        Ponto cruzamentoCentral = new Ponto(4.5, 4.5);

        mapa.addRota(new Route(new Ponto[]{portoA.getLocalizacao(), new Ponto(2, 2), new Ponto(3, 3), cruzamentoCentral}));
        mapa.addRota(new Route(new Ponto[]{cruzamentoCentral, new Ponto(6, 6), new Ponto(7, 7), portoE.getLocalizacao()}));
        mapa.addRota(new Route(new Ponto[]{portoF.getLocalizacao(), new Ponto(2, 7), new Ponto(3, 6), cruzamentoCentral}));
        mapa.addRota(new Route(new Ponto[]{cruzamentoCentral, new Ponto(6, 3), new Ponto(7, 2), portoJ.getLocalizacao()}));
        mapa.addRota(new Route(new Ponto[]{portoA.getLocalizacao(), new Ponto(3, 1), new Ponto(6, 1), portoJ.getLocalizacao()}));
        mapa.addRota(new Route(new Ponto[]{portoF.getLocalizacao(), new Ponto(3, 8), new Ponto(6, 8), portoE.getLocalizacao()}));

        //criar obstaculos por fazer
    }
}