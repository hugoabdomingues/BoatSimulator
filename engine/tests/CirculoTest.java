package tests;

import entities.Circulo;
import entities.Ponto;
import entities.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CirculoTest {
    /**
     * Teste 1 construtor
     * Círculo válido
     */
    @Test
    public void test1ConstrutorCirculo() {
        assertNotNull(new Circulo(new Ponto(0,0), 5));
    }

    /**
     * Teste 2 construtor
     * raio zero: inválido
     */
    @Test
    public void test2ConstrutorCirculo() {
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Circulo(new Ponto(0,0), 0));
        assertEquals("entity.Circulo:iv", erro.getMessage());
    }

    /**
     * Teste 3 Construtor
     * raio negativo: inválido
     */
    @Test
    public void test3ConstrutorCirculo() {
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Circulo(new Ponto(1,1), -3));
        assertEquals("entity.Circulo:iv", erro.getMessage());
    }

    /**
     * Teste 1 intersectCirculo
     * 2 interseções
     */
    @Test
    public void test1IntersectCirculo() {
        // círculo centrado em (2,0) com raio 1, rota horizontal que o atravessa
        Circulo c = new Circulo(new Ponto(2,0), 1);
        Ponto[] pontosRota = {new Ponto(0,0), new Ponto(4,0)};
        Route rota = new Route(pontosRota);
        ArrayList<Ponto> resultado = c.intersectCirculo(rota);
        assertEquals(2, resultado.size());
        // entra em (1,0) e sai em (3,0)
        Assertions.assertEquals(1.0, resultado.get(0).getX(), 1e-9);
        Assertions.assertEquals(0.0, resultado.get(0).getY(), 1e-9);
        Assertions.assertEquals(3.0, resultado.get(1).getX(), 1e-9);
        Assertions.assertEquals(0.0, resultado.get(1).getY(), 1e-9);
    }
    /**
     * Teste 1 intersectCirculo
     * rota n toca no circulo: lista vazia
     */
    @Test
    public void test2IntersectCirculo() {
        Circulo c = new Circulo(new Ponto(10,10), 1);
        Ponto[] pontosRota = {new Ponto(0,0), new Ponto(4,0)};
        Route rota = new Route(pontosRota);
        assertTrue(c.intersectCirculo(rota).isEmpty());
    }
    /**
     * Teste 1 intersectCirculo
     * rota tangente ao círculo. Toca num ponto
     */
    @Test
    public void test3IntersectCirculo() {
        // círculo centrado em (2,1) com raio 1, rota horizontal em y=0
        Circulo c = new Circulo(new Ponto(2,1), 1);
        Ponto[] pontosRota = {new Ponto(0,0), new Ponto(4,0)};
        Route rota = new Route(pontosRota);
        ArrayList<Ponto> resultado = c.intersectCirculo(rota);
        assertEquals(1, resultado.size());
        Assertions.assertEquals(2.0, resultado.get(0).getX(), 1e-9);
        Assertions.assertEquals(0.0, resultado.get(0).getY(), 1e-9);
    }

}