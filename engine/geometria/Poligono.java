package geometria;

import entity.Route;

import java.util.ArrayList;

/**
 * Classe que representa um polígono
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 21-03-2026
 * @inv Um polígono tem pelo menos 3 vértices.
 */
public class Poligono{
    private Ponto[] vertices;
    /**
     * Construtor do Polígono.
     * @param vertices Array com os vértices do polígono.
     * @throws IllegalArgumentException se a invariante for violada.
     */
    public Poligono(Ponto[] vertices) {
        if (vertices == null || vertices.length < 3) {
            throw new IllegalArgumentException("geometria.Poligono:iv");
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
     * iteramos primeiro pelos segmentos da rota (loop externo) e depois pelas arestas do polígono (loop interno).
     * Isso garante a ordem cronológica que o enunciado pede, porque processamos o percurso do carro segmento a segmento, e só depois verificamos todas as paredes para cada segmento.
     * @param rota rota constituída por 1 ou mais segmentos de reta
     * @return um Arraylist com os pontos de interseção (ou um Arraylist vazio, caso n haja pontos em que a rota intersete com o poligono)
     */
    public ArrayList<Ponto> intersect(Route rota) {
        ArrayList<Ponto> colisoes = new ArrayList<>();
        Ponto[] pontosRota = rota.getPontos();

        //O loop de fora é para vermos segmento a segmento se este bate nalgum poligono. O carro faz o Segmento 1, depois o Segmento 2, depois o Segmento 3.
        for (int i = 0; i < pontosRota.length - 1; i++) {
            SegmentoReta segmentoCarro = new SegmentoReta(pontosRota[i], pontosRota[i + 1]);

            //Em cada segmento testamos se batemos nas "paredes" do polígono
            //é importanto fazermos assim pela questão cronológica solicitada no enunciado.--> o output deve ser de forma cronologica. Ou seja, primeiro o carro bateu na parede 3 e depois na parede 1 (por exemplo) isto é importante ter em conta
            for (int j = 0; j < vertices.length; j++) {
                SegmentoReta arestaPoligono = new SegmentoReta(vertices[j], vertices[(j + 1) % vertices.length]);

                Ponto choque = segmentoCarro.intersectRotas(arestaPoligono);
                if (choque != null) {
                    //Evitamos adicionar o mesmo ponto se, por exemplo, o carro bater numa esquina do polígono
                    if (colisoes.isEmpty() || !colisoes.get(colisoes.size() - 1).equals(choque)) {
                        colisoes.add(choque);
                    }
                }
            }
        }
        return colisoes;
    }

    /**
     * calcular se dois comprimentos são iguais
     * PROTECTED: só a própria classe e subclasses podem usar este metodo
     * @param a comprimento 1
     * @param b comprimento 2
     * @return true se forem iguais, false se não forem iguais
     */
    public boolean areEqual (double a, double b) {
        return Math.abs(a - b) < 1e-9;
    }

}


