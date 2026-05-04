package geometria;

public class NodeDijkstra implements Comparable<NodeDijkstra> {

    private Ponto pontoAtual;
    private double distanciaPercorrida;

    public NodeDijkstra(Ponto pontoAtual, double distanciaPercorrida) {
        this.pontoAtual = pontoAtual;
        this.distanciaPercorrida = distanciaPercorrida;
    }

    public Ponto getPontoAtual() { return pontoAtual; }
    public double getDistanciaPercorrida() { return distanciaPercorrida; }

    @Override
    public int compareTo(NodeDijkstra outro) {
        return Double.compare(this.distanciaPercorrida, outro.distanciaPercorrida);
    }
}