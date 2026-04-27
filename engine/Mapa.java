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
     * Devolve a única instância do Mapa.
     * @return a instância do Mapa
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
     */
    public void tempo(double deltaTempo) {
        // TODO
    }

    /**
     * Verifica as colisões e gere as prioridades entre os barcos.
     */
    private void verificarConflitos() {
        // TODO
    }

    /**
     * Alterna o estado de visibilidade das hitboxes.
     */
    public void mostrarHitboxes() {
        // TODO
    }

    /**
     * Cria e devolve o  estado atual do motor para o GUI
     * @return O record NavegacaoUpdate
     */
    public NavegacaoUpdate getNavegUpdate() {
        return new NavegacaoUpdate(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), this.obstaculos, this.corrente, new ArrayList<>());
    }

    public void addBarco(Barco barco) {
        // TODO
    }

    public void addPorto(Porto porto) {
        // TODO
    }

    public void addRota(Route rota) {
        // TODO
    }

    public void addObstaculo(Obstaculo obstaculo) {
        // TODO
    }

    public void setCorrente(Vetor novaCorrente) {
        // TODO
    }

    // para limpar os testes
    public static void resetInstancia() {
        instanciaMapa = null;
    }
}