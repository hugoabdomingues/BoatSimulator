package tests;

import entities.Ponto;
import entities.Route;
import entities.Triangulo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TrianguloTest {


    /**
     * Teste 1 Construtor
     * triângulo válido
     */
    @Test
    public void test1ConstrutorTriangulo() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(1,0), new Ponto(0,1)};
        assertNotNull(new Triangulo(vertices));
    }

    /**
     * Teste 2 Construtor
     * vértices colineares
     */
    @Test
    public void test2ConstrutorTriangulo() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(1,1), new Ponto(2,2)};
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Triangulo(vertices));
        assertEquals("entity.Triangulo:iv", erro.getMessage());
    }
    /**
     * Teste 3 Construtor
     * 4 vértices
     */
    @Test
    public void test3ConstrutorTriangulo() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(1,0), new Ponto(1,1), new Ponto(0,1)};
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Triangulo(vertices));
        assertEquals("entity.Triangulo:iv", erro.getMessage());
    }

    /**
     * Teste 1 Intersect
     * rota atravessa o triângulo: 2 interseções
     */
    @Test
    public void test1IntersectTriangulo() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(4,0), new Ponto(2,4)};
        Triangulo t = new Triangulo(vertices);
        Ponto[] pontosRota = {new Ponto(-1,2), new Ponto(5,2)};
        Route rota = new Route(pontosRota);
        assertEquals(2, t.intersect(rota).size());
    }
    /**
     * Teste 2 Intersect
     * rota fora do triângulo: lista vazia
     */
    @Test
    public void test2IntersectTriangulo() {
        // rota fora do triângulo → lista vazia
        Ponto[] vertices = {new Ponto(0,0), new Ponto(4,0), new Ponto(2,4)};
        Triangulo t = new Triangulo(vertices);
        Ponto[] pontosRota = {new Ponto(5,0), new Ponto(5,4)};
        Route rota = new Route(pontosRota);
        assertTrue(t.intersect(rota).isEmpty());
    }
    /**
     * Teste 3 Intersect
     * rota toca apenas numa aresta: 1 interseção
     */
    @Test
    public void test3IntersectTriangulo() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(4,0), new Ponto(2,4)};
        Triangulo t = new Triangulo(vertices);
        Ponto[] pontosRota = {new Ponto(-1,0), new Ponto(2,0)};
        Route rota = new Route(pontosRota);
        ArrayList<Ponto> resultado = t.intersect(rota);
        assertEquals(1, resultado.size());
        Assertions.assertEquals(0.0, resultado.get(0).getX(), 1e-9);
        Assertions.assertEquals(0.0, resultado.get(0).getY(), 1e-9);
    }

}