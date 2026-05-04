package entity;

import geometria.NodeDijkstra;
import geometria.Ponto;
import geometria.SegmentoReta;
import geometria.Vetor;

import java.util.*;

/**
 * Classe que representa o mapa
 * @author
 * @version 26-04-2026
 * @inv a instância do mapa deve ser única
 */
public class Mapa {

    private static Mapa instanciaMapa;

    private List<Barco> naviosAtivos;
    private List<Porto> portos;
    private List<Route> rotas;
    private List<Obstaculo> obstaculos;
    private Vetor corrente;
    private boolean mostrarHitbox;
    private double tempoAtual;

    //construtor privado
    private Mapa() {
        this.naviosAtivos = new ArrayList<>();
        this.portos = new ArrayList<>();
        this.rotas = new ArrayList<>();
        this.obstaculos = new ArrayList<>();
        this.corrente = null;
        this.mostrarHitbox = false;
        this.tempoAtual = 0.0;
    }

    /**
     * Devolve a única instância do entity.Mapa.
     * @return a instância do entity.Mapa
     */
    public static Mapa getInstancia() {
        if (instanciaMapa == null) {
            instanciaMapa = new Mapa();
        }
        return instanciaMapa;
    }


    /**
     * Avança o tempo da simulação, move barcos, etc
     * @param deltaTempo o tempo passado desde a última atualização
     * @see "https://medium.com/@AlexanderObregon/building-shortest-path-logic-with-dijkstras-algorithm-in-java-e2566d64412f"
     */
    public void tick(double deltaTempo) {
        //Incrementamos o tempo atual da simulação
        this.tempoAtual += deltaTempo;

        //verificamos as partidas
        for (Porto porto : this.portos) {
            Barco barcoNaFila = porto.verificarFila(this.tempoAtual);

            if (barcoNaFila != null) {
                //Descobrir onde estamos e para onde vamos
                Ponto partida = porto.getLocalizacao();
                Ponto destino = obterLocalizacaoPorto(barcoNaFila.getDestino());

                if (destino != null) {
                    //Chamamos o Dijkstra para calcular o caminho
                    Route rotaCalculada = calcularRotaDijkstra(partida, destino);

                    if (rotaCalculada != null) {
                        //Criamos o Navegador com a rota e entregamo-lo ao barco
                        Navegador nav = new Navegador(rotaCalculada);
                        barcoNaFila.setNavegador(nav);

                        //só aqui é q adicionamos o barco ao mapa, pois já sabe para onde vai
                        this.naviosAtivos.add(barcoNaFila);
                        System.out.println("Partida do barco: " + barcoNaFila.getCodigo() + " do porto " + porto.getNome());
                    } else {
                        System.out.println("ERRO: Nao foi possivel encontrar rota segura para " + barcoNaFila.getCodigo());
                    }
                }
            }
        }

        //movemos os barcos e verificamos as chegadas e criamos uma lista temporária para guardar os barcos que chegam, para dps os eliminarmos da lista de naviosAtivos
        List<Barco> barcosQueChegaram = new ArrayList<>();

        for (Barco barco : naviosAtivos) {
            // Só movemos o barco se ele não estiver parado à espera (por causa de colisões)
            if (!barco.getEstado()) {
                //determinamos o tempo que passou desde que ele partiu para determinarmos o ponto onde ele vai estar agora
                double tempoAViajar = this.tempoAtual - barco.getHora();

                //Usamos o navegador para determinar o ponto onde o barco se encontra passado tempoAViajar
                Ponto novaPosicao = barco.getNavegador().pontoDaRotaNoTempoT(barco.getVl(), tempoAViajar);

                if (novaPosicao != null) {
                    //se n for null, quer dizer que o barco ainda está a viajar
                    barco.setPosicaoAtual(novaPosicao);
                } else {
                    //se for null, o barco chegou ao destino e temos de o guardar na lista temp para o eliminarmos da lista de naviosAtivos
                    barcosQueChegaram.add(barco);
                }
            } else {
                //se o barco estiver parado (estado == true), temos de atrasar a sua hora de partida somando o deltaTempo. Assim, o tempoAViajar dele congela e evitamos que ele se teletransporte ao retomar viagem
                barco.setHora(barco.getHora() + deltaTempo);
            }
        }

        //Retiramos os barcos que chegaram da lista de naviosAtivos --> apagamos os barcos do mapa
        for (Barco barco : barcosQueChegaram) {
            this.naviosAtivos.remove(barco);
            System.out.println("O barco " + barco.getCodigo() + " chegou ao destino.");
        }
        // Depois de todos se mexerem, verificamos se algum chocou
        verificarConflitos();
    }

    /**
     * Verifica as colisões e gere as prioridades entre os barcos.
     */
    private void verificarConflitos() {

        //Soltamos os travões de todos os barcos para evitar que algum barco fique parado para sempre e reavaliamos //-/*
        for (Barco barco : this.naviosAtivos) {
            barco.setEstado(false);
        }

        //Comparamos cada barco com todos os outros
        for (Barco barco1 : this.naviosAtivos) {
            for (Barco barco2 : this.naviosAtivos) {
                if (barco1 == barco2) continue;
                barco1.intersetarBarco(barco2);
            }
        }
    }

    /**
     * Verifica se um segmento de reta choca contra algum obstáculo no mapa.
     * @param a geometria.Ponto de início do segmento
     * @param b geometria.Ponto de fim do segmento
     * @return true se o caminho estiver livre, false se bater em algo
     */
    private boolean isCaminhoLivre(Ponto a, Ponto b) {
        Ponto[] pontosSegmento = {a, b};
        Route rotaTemp = new Route(pontosSegmento);

        for (Obstaculo obstaculo : this.obstaculos) {
            ArrayList<Ponto> colisoes = obstaculo.verificaColisao(rotaTemp);
            if (colisoes != null && !colisoes.isEmpty()) {
                return false; //bateu em algo
            }
        }
        return true; //caminho livre de obstaculos
    }



    /**
     * Cria e devolve o  estado atual do motor para o GUI
     * @return O record entity.NavegacaoUpdate
     */
    public NavegacaoUpdate getNavegUpdate() {

        //preparamos as listas de dados que queremos enviar para o record para atualizarmos o mapa no GUI
        List<String> nomesBarcos = new ArrayList<>();
        List<Ponto> posicoesBarcos = new ArrayList<>();
        List<Boolean> hitboxes = new ArrayList<>();

        //preenchemos as listas com os dados dos barcos que estão no mar
        for (Barco barco : this.naviosAtivos) {
            nomesBarcos.add(barco.getCodigo());
            posicoesBarcos.add(barco.getPosicaoAtual());
            hitboxes.add(this.mostrarHitbox);
        }

        //preenchemos a lista com as strings dos barcos à espera nos portos
        List<String> barcosEmEspera = new ArrayList<>();
        for (Porto porto : this.portos) {
            barcosEmEspera.addAll(porto.getStringBarcosFila());
        }

        //criamos o Record e enviamos para o GUI
        return new NavegacaoUpdate(nomesBarcos, posicoesBarcos,hitboxes, this.obstaculos, this.corrente, barcosEmEspera, this.portos, this.rotas);
    }

    /**
     * Encontra a localização (Ponto) de um porto a partir do seu nome.
     * @param nomePorto O nome do porto que queremos procurar
     * @return A localização (Ponto) ou null se não encontrar
     */
    private Ponto obterLocalizacaoPorto(String nomePorto) {
        for (Porto porto : this.portos) {
            if (porto.getNome().equals(nomePorto)) {
                return porto.getLocalizacao();
            }
        }
        return null;
    }

    /**
     * Pega em todas as rotas e devolve uma lista simples só com os segmentos livres de perigo.
     */
    private List<SegmentoReta> obterEstradasSeguras() {
        List<SegmentoReta> estradas = new ArrayList<>();

        for (Route rota : this.rotas) {
            Ponto[] pontos = rota.getPontos();
            for (int i = 0; i < pontos.length - 1; i++) {
                // Se a linha não bater em nada, guardamos!
                if (isCaminhoLivre(pontos[i], pontos[i+1])) {
                    estradas.add(new SegmentoReta(pontos[i], pontos[i+1]));
                }
            }
        }
        return estradas;
    }

    /**
     * O Algoritmo de Dijkstra usando Maps (Dicionários)
     */
    private Route calcularRotaDijkstra(Ponto partida, Ponto destino) {

        List<SegmentoReta> todasAsEstradas = obterEstradasSeguras();
        PriorityQueue<NodeDijkstra> fila = new PriorityQueue<>();

        //os nossos dois "cadernos" (Maps):
        //O 1º caderno anota a distância mais curta conhecida até um Ponto
        Map<Ponto, Double> distancias = new HashMap<>();
        //O 2º caderno anota de onde viemos para depois reconstruir a rota
        Map<Ponto, Ponto> deOndeVim = new HashMap<>();

        //Configuração inicial: o início está à distância 0
        distancias.put(partida, 0.0);
        fila.add(new NodeDijkstra(partida, 0.0));

        while (!fila.isEmpty()) {

            NodeDijkstra atual = fila.poll();
            Ponto pontoOndeEstou = atual.getPontoAtual();

            //verificamos se chegámos ao destino
            if (pontoOndeEstou.equals(destino)) {

                //Vamos ler o 2º caderno onde fomos apontando o caminho até aqui, para criar então a Rota
                List<Ponto> caminhoFinal = new ArrayList<>();
                Ponto passo = destino;

                while (passo != null) {
                    //Adicionamos no início da lista para ficar na ordem correta
                    caminhoFinal.add(0, passo);
                    passo = deOndeVim.get(passo); //descobrimos o ponto anterior de onde viemos
                }

                return new Route(caminhoFinal.toArray(new Ponto[0]));
            }

            // Se entretanto já encontrámos um caminho mais curto para este ponto, ignoramos
            if (atual.getDistanciaPercorrida() > distancias.getOrDefault(pontoOndeEstou, Double.MAX_VALUE)) {
                continue;
            }

            //olhamos para os caminhos à nossa volta para encontrarmos as ligações diretas até aqui
            for (SegmentoReta estrada : todasAsEstradas) {
                Ponto vizinho = null;

                if (estrada.getPontoA().equals(pontoOndeEstou)) vizinho = estrada.getPontoB();
                else if (estrada.getPontoB().equals(pontoOndeEstou)) vizinho = estrada.getPontoA();

                //se houver um ponto neste caminho
                if (vizinho != null) {
                    double distanciaDaEstrada = pontoOndeEstou.distanciaPontos(vizinho);
                    double distanciaTotal = distancias.get(pontoOndeEstou) + distanciaDaEstrada;

                    //só vamos por este caminho se for mais curto do que qualquer outro caminho que já tenhamos descoberto para este ponto
                    if (distanciaTotal < distancias.getOrDefault(vizinho, Double.MAX_VALUE)) {

                        distancias.put(vizinho, distanciaTotal); //anotamos a nova melhor distância
                        deOndeVim.put(vizinho, pontoOndeEstou);  //anotamos a o caminho até aqui
                        fila.add(new NodeDijkstra(vizinho, distanciaTotal)); //mandamos explorar
                    }
                }
            }
        }

        return null; //Não encontrou caminho nenhum
    }

    public void addBarco(Barco barco) {
        this.naviosAtivos.add(barco);
    }

    public void addPorto(Porto porto) {
        this.portos.add(porto);
    }

    public void addRota(Route rota) {
        this.rotas.add(rota);
    }

    public void addObstaculo(Obstaculo obstaculo) {
        this.obstaculos.add(obstaculo);
    }

    public void setCorrente(Vetor novaCorrente) {
        this.corrente = novaCorrente;
    }

    // para limpar os testes
    public static void resetInstancia() {
        instanciaMapa = null;
    }
}