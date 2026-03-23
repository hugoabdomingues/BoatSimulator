import java.util.ArrayList;

/**
 * Classe que representa um polígono
 * @author Hugo_Domingues
 * @version 21-03-2026
 * @inv Um polígono tem pelo menos 3 vértices.
 */
public class Poligono {
    private Ponto[] vertices;
    /**
     * Construtor do Polígono.
     * @param vertices Array com os vértices do polígono.
     * @throws IllegalArgumentException se a invariante for violada.
     */
    public Poligono(Ponto[] vertices) {
        if (vertices == null || vertices.length < 3) {
            throw new IllegalArgumentException("Poligono:iv");
        }
        this.vertices = vertices;
    }

    /**
     * Getter/assessor dos vertices do poligono
     * @return vertices
     */
    public Ponto[] getVertices() {
        return this.vertices;
    }

    /**
     * função que tenciona verificar se a rota bate num poligono
     * @param rota rota constituída por 1 ou mais segmentos de reta
     * @return um Arraylist com os pontos de interseção (ou um Arraylist vazio, caso n haja pontos em que a rota intersete com o poligono)
     */
    public ArrayList<Ponto> intersectObstacle(Route rota) {
        ArrayList<Ponto> colisoes = new ArrayList<Ponto>();
        for (int i = 0; i < vertices.length; i++) {
            // quando i é o último índice (por exemplo i = 3 num quadrado com 4 vértices), (3 + 1) % 4 = 0, o que liga o último vértice ao primeiro, fechando o polígono
            SegmentoReta aresta = new SegmentoReta(vertices[i], vertices[(i + 1) % vertices.length]);
            ArrayList<Ponto> intersectPonto = rota.intersecao(aresta);
            colisoes.addAll(intersectPonto);
        }
        return colisoes;
    }



}
