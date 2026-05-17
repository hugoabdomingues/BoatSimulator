package geometria;

/**
 * Classe que representa um Triângulo
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 21-03-2026
 * @inv Um triângulo tem exatamente 3 vértices. Estes vértices não podem ser colineares.
 */
public class Triangulo extends Poligono{
    /**
     * Construtor do Triângulo, extended from poligono.
     * @param vertices Array com os vértices do polígono.
     * @throws IllegalArgumentException se vertices != 3
     */
    public Triangulo(Ponto[] vertices) {
        super(vertices); // chama o construtor do geometria.Poligono e valida que tem >= 3 vértices
        if (vertices.length != 3) {
            throw new IllegalArgumentException("geometria.Triangulo:iv");
        }
        //verificação de colinearidade
        // 3 pontos são colineares se não formarem um triângulo, ou seja, se a "área" entre eles for zero.
        double area = Math.abs((vertices[1].getX() - vertices[0].getX()) * (vertices[2].getY() - vertices[0].getY()) - (vertices[2].getX() - vertices[0].getX()) * (vertices[1].getY() - vertices[0].getY()));
        if (area < 1e-9) {
            throw new IllegalArgumentException("geometria.Triangulo:iv");
        }
    }




}
