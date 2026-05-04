package test;

import geometria.Ponto;
import geometria.Quadrado;
import entity.Route;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QuadradoTest {

    /**
     * Construtor teste 1
     * quadrado válido
     */
    @Test
    public void test1ConstrutorQuadrado() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(1,0), new Ponto(1,1), new Ponto(0,1)};
        assertNotNull(new Quadrado(vertices));
    }
    /**
     * Construtor teste 2
     *retângulo não quadrado (lados diferentes)
     */
    @Test
    public void test2ConstrutorQuadrado() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(4,0), new Ponto(4,2), new Ponto(0,2)};
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Quadrado(vertices));
        assertEquals("geometria.Quadrado:iv", erro.getMessage());
    }
    /**
     * Construtor teste 3
     * vértices que não formam sequer um retângulo
     */
    @Test
    public void test3ConstrutorQuadrado() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(1,0), new Ponto(2,1), new Ponto(0,1)};
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Quadrado(vertices));
        assertEquals("geometria.Quadrado:iv", erro.getMessage());
    }

    /**
     * Testes 1 do intersect vindo da classe poligono:
     * rota atravessa o quadrado: 2 interseções
     */
    @Test
    public void test1IntersectQuadrado() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(2,0), new Ponto(2,2), new Ponto(0,2)};
        Quadrado q = new Quadrado(vertices);
        Ponto[] pontosRota = {new Ponto(-1,1), new Ponto(3,1)};
        Route rota = new Route(pontosRota);
        assertEquals(2, q.intersect(rota).size());
    }
    /**
     * Teste 2 do intersect vindo da classe poligono:
     * rota fora do quadrado: lista vazia
     */
    @Test
    public void test2IntersectQuadrado() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(2,0), new Ponto(2,2), new Ponto(0,2)};
        Quadrado q = new Quadrado(vertices);
        Ponto[] pontosRota = {new Ponto(5,0), new Ponto(5,2)};
        Route rota = new Route(pontosRota);
        assertTrue(q.intersect(rota).isEmpty());
    }
    /**
     * Teste 3 do intersect vindo da classe poligono:
     * rota toca apenas numa aresta: 1 interseção
     */
    @Test
    public void test3IntersectQuadrado() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(2,0), new Ponto(2,2), new Ponto(0,2)};
        Quadrado q = new Quadrado(vertices);
        Ponto[] pontosRota = {new Ponto(1,-1), new Ponto(1,0)};
        Route rota = new Route(pontosRota);
        ArrayList<Ponto> resultado = q.intersect(rota);
        assertEquals(1, resultado.size());
        assertEquals(1.0, resultado.get(0).getX(), 1e-9);
        assertEquals(0.0, resultado.get(0).getY(), 1e-9);
    }

}