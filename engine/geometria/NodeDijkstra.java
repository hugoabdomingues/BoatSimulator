package geometria;

/**
 * Classe que representa NodeDijkstra
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 14-05-2026
 */
public class NodeDijkstra implements Comparable<NodeDijkstra> {

    private Ponto pontoAtual;
    private double distanciaPercorrida;

    /**
     * Construtor da class NodeDijkstra
     * @param pontoAtual
     * @param distanciaPercorrida
     */
    public NodeDijkstra(Ponto pontoAtual, double distanciaPercorrida) {
        this.pontoAtual = pontoAtual;
        this.distanciaPercorrida = distanciaPercorrida;
    }

    /**
     * Getter do Ponto atual do nó
     * @return Ponto
     */
    public Ponto getPontoAtual() { return pontoAtual; }

    /**
     * Getter da distancia percorrida
     * @return double
     */
    public double getDistanciaPercorrida() { return distanciaPercorrida; }

    /**
     * Compara um NodeDijkstra com outro NodeDijkstra
     * @param outro NodeDijkstra
     * @return int
     */
    @Override
    public int compareTo(NodeDijkstra outro) {
        return Double.compare(this.distanciaPercorrida, outro.distanciaPercorrida);
    }
}