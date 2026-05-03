package entities;

import java.util.List;
import java.util.ArrayList;

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
    private int tempoAtual;

    //construtor privado
    private Mapa() {
        this.naviosAtivos = new ArrayList<>();
        this.portos = new ArrayList<>();
        this.rotas = new ArrayList<>();
        this.obstaculos = new ArrayList<>();
        this.corrente = null;
        this.mostrarHitbox = false;
        this.tempoAtual = 0; // 1.0
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
            //verificamos as filas de espera de cada porto em busca de barcos cuja hora <= tempoAtual.
            // Depois adicionamos esses barcos à lista de naviosAtivos.
            Barco barcoNaFila = porto.verificarFila(this.tempoAtual);
            if (barcoNaFila != null) {
                this.naviosAtivos.add(barcoNaFila);
                System.out.println("Partida do barco: " + barcoNaFila.getCodigo() + " do porto " + porto.getNome());
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

        //Comparamos cada barco
        for (Barco barco1 : this.naviosAtivos) {
            //com todos os outros barcos
            for (Barco barco2 : this.naviosAtivos) {

                //ignoramos se for o próprio barco
                if (barco1 == barco2) {
                    continue;
                }

                // Calculamos a distância entre os barcos
                double distancia = barco1.getPosicao_atual().distanciaPontos(barco2.getPosicao_atual());

                //visto que cada barco tem um raio = 1, então se os dois chocam a distancia tem de ser <=2
                if (distancia < 2.0) {

                    //mandamos parar sempre o barco 1, porque eventualmente esta lista vai percorrer todos os barcos
                    //o navio com o código de viagem lexicograficamente menor espera que o outro passe
                    if (barco1.getCodigo().compareTo(barco2.getCodigo()) < 0) {
                        //se retornar um valor negativo quer dizer que o barco1 é lexicograficamente menor e, portanto, terá de esperar
                        barco1.setEstado(true); //fica à espera
                    }
                }
            }
        }
    }

    /**
     * Verifica se um segmento de reta choca contra algum obstáculo no mapa.
     * @param a entities.Ponto de início do segmento
     * @param b entities.Ponto de fim do segmento
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
     * @return O record entities.NavegacaoUpdate
     */
    public NavegacaoUpdate getNavegUpdate() {

        //preparamos as listas de dados que queremos enviar para o record para atualizarmos o mapa no GUI
        List<String> nomesBarcos = new ArrayList<>();
        List<Ponto> posicoesBarcos = new ArrayList<>();
        List<Boolean> hitboxes = new ArrayList<>();

        //preenchemos as listas com os dados dos barcos que estão no mar
        for (Barco barco : this.naviosAtivos) {
            nomesBarcos.add(barco.getCodigo());
            posicoesBarcos.add(barco.getPosicao_atual());
            hitboxes.add(this.mostrarHitbox);
        }

        //preenchemos a lista com as strings dos barcos à espera nos portos
        List<String> barcosEmEspera = new ArrayList<>();
        for (Porto porto : this.portos) {
            barcosEmEspera.addAll(porto.getStringBarcosFila());
        }

        //criamos o Record e enviamos para o GUI
        return new NavegacaoUpdate(nomesBarcos, posicoesBarcos,hitboxes, this.obstaculos, this.corrente, barcosEmEspera);
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