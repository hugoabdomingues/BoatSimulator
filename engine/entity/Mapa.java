package entity;

import geometria.NodeDijkstra;
import geometria.Ponto;
import geometria.SegmentoReta;
import geometria.Vetor;

import java.util.*;

/**
 * Classe que representa o mapa
 * @version 26-04-2026
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
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

    /**
     * Construtor do Mapa
     */
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
     * Devolve a única instância do entity.Mapa
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
        // Incrementamos o tempo atual da simulação
        this.tempoAtual += deltaTempo;

        // verificamos as partidas
        for (Porto porto : this.portos) {
            Barco barcoNaFila = porto.verificarFila(this.tempoAtual);

            if (barcoNaFila != null) {
                lancarBarcoSePossivel(porto, barcoNaFila);
            }
        }


        // movemos os barcos e verificamos as chegadas e criamos uma lista temporária
        List<Barco> barcosQueChegaram = processarNavios(deltaTempo);

        // Retiramos os barcos que chegaram da lista de naviosAtivos
        for (Barco barco : barcosQueChegaram) {
            this.naviosAtivos.remove(barco);
            System.out.println("O barco " + barco.getCodigo() + " chegou ao destino.");
        }

        // Depois de todos se mexerem, verificamos se algum chocou
        for (Barco barco : this.naviosAtivos) {
            barco.setEstado(false);

        }
        for (Barco barco1 : this.naviosAtivos) {
            for (Barco barco2 : this.naviosAtivos) {
                if (barco1 == barco2)
                    continue;
                barco1.intersetarBarco(barco2);
            }
        }
    }

    /**
     * Tenta calcular uma rota para o barco e adicioná-lo à lista de navios ativos.
     * @param porto Porto
     * @param barco Barco
     */
    private void lancarBarcoSePossivel(Porto porto, Barco barco) {
        // Descobrir onde estamos e para onde vamos
        Ponto partida = porto.getLocalizacao();
        Ponto destino = obterLocalizacaoPorto(barco.getDestino());

        if (destino == null) return;

        // Chamamos o Dijkstra para calcular o caminho
        Route rotaCalculada = calcularRotaDijkstra(partida, destino);

        if (rotaCalculada != null) {
            // Criamos o Navegador com a rota e entregamo-lo ao barco
            Navegador nav = new Navegador(rotaCalculada);
            barco.setNavegador(nav);

            // só aqui é q adicionamos o barco ao mapa, pois já sabe para onde vai
            this.naviosAtivos.add(barco);
            System.out.println("Partida do barco: " + barco.getCodigo() + " do porto " + porto.getNome());
        } else {
            System.out.println("ERRO: Nao foi possivel encontrar rota segura para " + barco.getCodigo());
        }
    }

    /**
     * Atualiza a posição de todos os barcos ativos no mapa com base no tempo decorrido
     * @param deltaTempo double
     * @return List Barco
     */
    private List<Barco> processarNavios(double deltaTempo) {
        List<Barco> barcosQueChegaram = new ArrayList<>();

        for (Barco barco : naviosAtivos) {
            // Só movemos o barco se ele não estiver parado à espera (por causa de colisões)
            if (!barco.getEstado()) {
                // determinamos o tempo que passou desde que ele partiu para determinarmos o ponto onde ele vai estar agora
                double tempoAViajar = this.tempoAtual - barco.getHora();

                // Usamos o navegador para determinar o ponto onde o barco se encontra passado tempoAViajar
                Ponto novaPosicao = barco.getNavegador().pontoDaRotaNoTempoT(barco.getVl(), tempoAViajar, this.corrente);

                if (novaPosicao != null) {
                    // se n for null, quer dizer que o barco ainda está a viajar
                    barco.setPosicaoAtual(novaPosicao);
                } else {
                    // se for null, o barco chegou ao destino e temos de o guardar na lista temp
                    barcosQueChegaram.add(barco);
                }
            } else {
                // se o barco estiver parado (estado == true), temos de atrasar a sua hora de partida
                barco.setHora(barco.getHora() + deltaTempo);
            }
        }
        return barcosQueChegaram;
    }

    /**
     * Verifica se um segmento de reta choca contra algum obstáculo no mapa.
     * 
     * @param a geometria.Ponto de início do segmento
     * @param b geometria.Ponto de fim do segmento
     * @return true se o caminho estiver livre, false se bater em algo
     */
    private boolean isCaminhoLivre(Ponto a, Ponto b) {
        Ponto[] pontosSegmento = { a, b };
        Route rotaTemp = new Route(pontosSegmento);

        for (Obstaculo obstaculo : this.obstaculos) {
            ArrayList<Ponto> colisoes = obstaculo.verificaColisao(rotaTemp);
            if (colisoes != null && !colisoes.isEmpty()) {
                return false; // bateu em algo
            }
        }
        return true; // caminho livre de obstaculos
    }

    /**
     * Cria e devolve o estado atual do motor para o GUI
     * @return O record entity.NavegacaoUpdate
     */
    public NavegacaoUpdate getNavegUpdate() {

        // preparamos as listas de dados que queremos enviar para o record para
        // atualizarmos o mapa no GUI
        List<String> nomesBarcos = new ArrayList<>();
        List<Ponto> posicoesBarcos = new ArrayList<>();
        List<Boolean> hitboxes = new ArrayList<>();

        // preenchemos as listas com os dados dos barcos que estão no mar
        for (Barco barco : this.naviosAtivos) {
            nomesBarcos.add(barco.getCodigo());
            posicoesBarcos.add(barco.getPosicaoAtual());

            // verificamos se este barco está dentro do raio de colisão de algum outro barco
            // se estiver, mostramos a hitbox em AMBOS (não só no que pára)
            boolean emZonaColisao = false;
            for (Barco outro : this.naviosAtivos) {
                if (outro != barco && barco.getPosicaoAtual().distanciaPontos(outro.getPosicaoAtual()) < 4.0) {
                    emZonaColisao = true;
                    break;
                }
            }
            hitboxes.add(emZonaColisao);
        }

        // preenchemos a lista com as strings dos barcos à espera nos portos
        List<String> barcosEmEspera = new ArrayList<>();
        for (Porto porto : this.portos) {
            barcosEmEspera.addAll(porto.getStringBarcosFila());
        }

        // criamos o Record e enviamos para o GUI
        return new NavegacaoUpdate(nomesBarcos, posicoesBarcos, hitboxes, this.obstaculos, this.corrente,
                barcosEmEspera, this.portos, this.rotas, this.tempoAtual);
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
     * Pega em todas as rotas e devolve uma lista simples só com os segmentos livres
     * de perigo.
     * @return List SegmentoReta
     */
    private List<SegmentoReta> getEstradasSeguras() {
        List<SegmentoReta> estradas = new ArrayList<>();

        for (Route rota : this.rotas) {
            Ponto[] pontos = rota.getPontos();
            for (int i = 0; i < pontos.length - 1; i++) {
                // Se a linha não bater em nada, guardamos
                if (isCaminhoLivre(pontos[i], pontos[i + 1])) {
                    estradas.add(new SegmentoReta(pontos[i], pontos[i + 1]));
                }
            }
        }
        return estradas;
    }

    /**
     * Algoritmo de Dijkstra
     * @param partida Ponto do Porto de partida
     * @param destino Ponto do Porto de destino
     * @return Rota do Barco
     */
    public Route calcularRotaDijkstra(Ponto partida, Ponto destino) {
        // Inicializacao dos dados
        List<SegmentoReta> todasAsEstradas = obterEstradasSegurasComIntersecoes(partida, destino);

        PriorityQueue<NodeDijkstra> fila = new PriorityQueue<>();
        Map<Ponto, Double> distancias = new HashMap<>();
        Map<Ponto, Ponto> deOndeVim = new HashMap<>();

        distancias.put(partida, 0.0);
        fila.add(new NodeDijkstra(partida, 0.0));

        while (!fila.isEmpty()) {
            NodeDijkstra atual = fila.poll(); // remove e retorna o elemento que está no topo da fila
            Ponto pontoOndeEstou = atual.getPontoAtual();

            // Cheguei ao destino
            if (pontoOndeEstou.equals(destino)) {
                return reconstruirRota(destino, deOndeVim);
            }

            // O nó que saiu da fila ainda representa o melhor caminho conhecido até esse
            // ponto
            if (atual.getDistanciaPercorrida() <= distancias.getOrDefault(pontoOndeEstou, Double.MAX_VALUE)) {
                for (SegmentoReta estrada : todasAsEstradas) {
                    Ponto vizinho = obterVizinho(estrada, pontoOndeEstou);

                    if (vizinho != null) {
                        double distanciaTotal = distancias.get(pontoOndeEstou)
                                + pontoOndeEstou.distanciaPontos(vizinho); // distancia percorrida + DistanciadaEstrada
                        // Encontrou um melhor caminho
                        if (distanciaTotal < distancias.getOrDefault(vizinho, Double.MAX_VALUE)) {
                            distancias.put(vizinho, distanciaTotal);
                            deOndeVim.put(vizinho, pontoOndeEstou);
                            fila.add(new NodeDijkstra(vizinho, distanciaTotal));
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Reconstrói a rota iterando desde o ponto de destino até à origem.
     * @param destino   O ponto final que o barco pretende alcançar
     * @param deOndeVim O mapa de precedências que indica o ponto ótimo anterior para chegar a um dado nó
     * @return Route que constitui a viagem
     */
    private Route reconstruirRota(Ponto destino, Map<Ponto, Ponto> deOndeVim) {
        List<Ponto> caminhoFinal = new ArrayList<>();
        Ponto passo = destino;

        while (passo != null) {
            caminhoFinal.add(0, passo);
            passo = deOndeVim.get(passo);
        }

        return new Route(caminhoFinal.toArray(new Ponto[0]));
    }
    /**
     * Devolve o outro extremo de uma estrada quando o ponto atual pertence a essa estrada.
     * @param estrada SegmentoReta
     * @param pontoOndeEstou Ponto
     * @return Ponto
     */
    private Ponto obterVizinho(SegmentoReta estrada, Ponto pontoOndeEstou) {
        if (estrada.getPontoA().equals(pontoOndeEstou)) {
            return estrada.getPontoB();
        }
        if (estrada.getPontoB().equals(pontoOndeEstou)) {
            return estrada.getPontoA();
        }
        return null;
    }

    /**
     * Gera uma lista de estradas seguras (segmentos) incorporando pontos de
     * interseção entre rotas como nós navegáveis.
     * Isto permite que o algoritmo do caminho mais curto mudem de rota nos pontos
     * onde se cruzam.
     * Adiciona também as rotas temporárias de partida e destino.
     * @param partida O ponto inicial da viagem
     * @param destino O ponto final da viagem
     * @return Uma lista de pequenos segmentos de reta que compõem a rede navegável completa
     */
    private List<SegmentoReta> obterEstradasSegurasComIntersecoes(Ponto partida, Ponto destino) {
        List<SegmentoReta> estradasOriginais = getEstradasSeguras();
        Map<SegmentoReta, List<Ponto>> pontosPorEstrada = new HashMap<>();

        // Inicializa cada estrada com os seus extremos, a partida e o destino quando
        // pertencem ao segmento.
        for (SegmentoReta estrada : estradasOriginais) {
            List<Ponto> pontosDaEstrada = new ArrayList<>();
            adicionarPontoSeValido(pontosDaEstrada, estrada, estrada.getPontoA(), estrada.getPontoB(), partida,
                    destino);
            pontosPorEstrada.put(estrada, pontosDaEstrada);
        }

        // Compara cada estrada com as seguintes para descobrir interseções sem repetir
        // pares.
        for (int i = 0; i < estradasOriginais.size(); i++) {
            SegmentoReta estradaA = estradasOriginais.get(i);

            // Percorre apenas as estradas seguintes à estrada atual.
            for (int j = i + 1; j < estradasOriginais.size(); j++) {
                SegmentoReta estradaB = estradasOriginais.get(j);
                Ponto intersecao = estradaA.intersectRotas(estradaB);

                if (intersecao != null) {
                    adicionarPontoSeValido(pontosPorEstrada.get(estradaA), estradaA, intersecao);
                    adicionarPontoSeValido(pontosPorEstrada.get(estradaB), estradaB, intersecao);
                }
            }
        }

        // Parte cada estrada em segmentos menores entre pontos consecutivos.
        return fragmentarTodasAsEstradas(estradasOriginais, pontosPorEstrada);
    }

    /**
     * Fragmenta todas as estradas fornecidas em segmentos menores, utilizando os
     * pontos de interseção de cada uma.
     * @param estradas List de SegmentoReta
     * @param pontosPorEstrada Map com os pontos de interseção associados a cada estrada
     * @return List de SegmentoReta contendo todos os fragmentos gerados
     */
    private List<SegmentoReta> fragmentarTodasAsEstradas(List<SegmentoReta> estradas,
            Map<SegmentoReta, List<Ponto>> pontosPorEstrada) {
        List<SegmentoReta> todasPartidas = new ArrayList<>();
        for (SegmentoReta estrada : estradas) {
            todasPartidas.addAll(gerarSegmentosFragmentados(estrada, pontosPorEstrada.get(estrada)));
        }
        return todasPartidas;
    }

    /**
     * Cria fragmentos menores de um SegmentoReta (estrada) original ligando os
     * pontos de interseção consecutivos.
     * @param estradaOriginal SegmentoReta original a ser fragmentado
     * @param pontos Lista de pontos de interseção e extremidades da estrada
     * @return List de SegmentoReta com os novos fragmentos gerados
     */
    private List<SegmentoReta> gerarSegmentosFragmentados(SegmentoReta estradaOriginal, List<Ponto> pontos) {
        List<SegmentoReta> fragmentos = new ArrayList<>();

        // Ordena os pontos para garantir que os segmentos seguem a linha da estrada
        pontos.sort(Comparator.comparingDouble(p -> distanciaAoLongoDoSegmento(estradaOriginal, p)));

        for (int i = 0; i < pontos.size() - 1; i++) {
            Ponto p1 = pontos.get(i);
            Ponto p2 = pontos.get(i + 1);

            if (!p1.equals(p2)) {
                fragmentos.add(new SegmentoReta(p1, p2));
            }
        }
        return fragmentos;
    }

    /**
     * Adiciona pontos à lista apenas se estiverem no segmento e ainda não existirem na lista.
     * @param pontos List Ponto
     * @param estrada SegmentoReta
     * @param pontosParaTestar Ponto
     */
    private void adicionarPontoSeValido(List<Ponto> pontos, SegmentoReta estrada, Ponto... pontosParaTestar) {
        for (Ponto pontoParaTestar : pontosParaTestar) {
            if (pontoEstaNoSegmento(pontoParaTestar, estrada)) {
                boolean jaExiste = false;
                for (Ponto ponto : pontos) {
                    if (ponto.equals(pontoParaTestar)) {
                        jaExiste = true;
                        break;
                    }
                }
                if (!jaExiste) {
                    pontos.add(pontoParaTestar);
                }
            }
        }
    }

    /**
     * Metodo que adiciona tempestades ao mapa
     * @param tempestadesRotas numero inteiro de tempestades na rota
     * @param tempestadesAleatorias numero inteiro de tempestades espalhadas pelo mapa
     */
    public void gerarTempestades(int tempestadesRotas, int tempestadesAleatorias) {
        Random rand = new Random();
        double raioMin = 1.0;
        double raioMax = 3.0;

        // Tempestades nas rotas
        if (!this.rotas.isEmpty()) {
            for (int i = 0; i < tempestadesRotas; i++) {
                Route rotaAleatoria = this.rotas.get(rand.nextInt(this.rotas.size()));
                Ponto[] pontos = rotaAleatoria.getPontos();

                // Escolhe um ponto da rota que não é um porto
                Ponto localizacao = pontos[rand.nextInt(pontos.length-2) + 1];

                // Calcula o raio
                double raio = raioMin + (raioMax - raioMin) * rand.nextDouble();
                this.addObstaculo(new Tempestade("TempestadeRota" + (i + 1), localizacao, raio));
            }
        }

        // Tempestades aleatórias
        for (int i = 0; i < tempestadesAleatorias; i++) {

            double x = 5 + (90 * rand.nextDouble());
            double y = 5 + (60 * rand.nextDouble());

            Ponto localizacaoAleatoria = new Ponto(x, y);
            double raio = raioMin + (raioMax - raioMin) * rand.nextDouble();

            this.addObstaculo(new Tempestade("TempestadeRandom" + (i + 1), localizacaoAleatoria, raio));
        }
    }

    /**
     * Verifica se o Ponto está naquele SegmentoReta
     * @param p Ponto
     * @param segmento SegmentoReta
     * @return boolean
     */
    private boolean pontoEstaNoSegmento(Ponto p, SegmentoReta segmento) {
        double dTotal = segmento.getPontoA().distanciaPontos(segmento.getPontoB());
        double d1 = p.distanciaPontos(segmento.getPontoA());
        double d2 = p.distanciaPontos(segmento.getPontoB());

        // Se a soma das distâncias às pontas for igual ao tamanho do segmento,o ponto
        // pertence ao segmento.
        return Math.abs((d1 + d2) - dTotal) < 1e-9;
    }

    /**
     * Calcula a distância desde o início do segmento até ao ponto, para ordenar os pontos no segmento.
     * @param segmento SegmentoReta
     * @param ponto Ponto
     * @return double
     */
    private double distanciaAoLongoDoSegmento(SegmentoReta segmento, Ponto ponto) {
        return segmento.getPontoA().distanciaPontos(ponto);
    }

    /**
     * Adiciona um barco à lista de navios ativos a navegar no mapa.
     * @param barco O barco a ser adicionado
     */
    public void addBarco(Barco barco) {
        this.naviosAtivos.add(barco);
    }

    /**
     * Adiciona um porto ao mapa.
     * @param porto O porto a ser adicionado
     */
    public void addPorto(Porto porto) {
        this.portos.add(porto);
    }

    /**
     * Devolve a lista de todos os barcos que estão atualmente a navegar no mapa.
     * @return List de Barco
     */
    public List<Barco> getNaviosAtivos() {
        return this.naviosAtivos;
    }

    /**
     *Getter da lista de todas as rotas predefinidas do mapa.
     * @return List Route
     */
    public List<Route> getRotas() {
        return this.rotas;
    }

    /**
     *Getter da lista de todos os obstáculos registados no mapa (ex: ilhas, tempestades).
     * @return List Obstaculo
     */
    public List<Obstaculo> getObstaculos() {
        return this.obstaculos;
    }

    /**
     * Adiciona uma nova rota ao mapa.
     * @param rota A rota a ser adicionada
     */
    public void addRota(Route rota) {
        this.rotas.add(rota);
    }

    /**
     * Adiciona um novo obstáculo ao mapa.
     * @param obstaculo O obstáculo a ser adicionado
     */
    public void addObstaculo(Obstaculo obstaculo) {
        this.obstaculos.add(obstaculo);
    }

    /**
     * Setter da corrente do mapa
     * @param novaCorrente O novo vetor da corrente
     */
    public void setCorrente(Vetor novaCorrente) {
        this.corrente = novaCorrente;
    }

    /**
     * Metodo usado para limpar os testes
     */
    public static void resetInstancia() {
        instanciaMapa = null;
    }
}
