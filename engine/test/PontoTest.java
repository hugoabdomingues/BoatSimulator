package test;

import geometria.Ponto;
import geometria.Vetor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe teste da classe ponto
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 01-03-2026
 * @inv O ponto é definido por um x e um y.
 */
public class PontoTest {

    /**
     * Teste 1 do Construtor do geometria.Ponto
     */
    @Test
    public void test1ConstrutorPonto () {
        assertEquals("(1.00,3.00)", new Ponto(1, 3).toString());
    }

    /**
     * Teste 2 do Construtor do geometria.Ponto
     */
    @Test
    public void test2ConstrutorPonto () {
        // números negativos
        assertEquals("(-2.50,-4.00)", new Ponto(-2.5, -4).toString());
    }

    /**
     * Teste 3 do Construtor do geometria.Ponto: zero e arredondamentos
     */
    @Test
    public void test3ConstrutorPonto () {
        // zero e arredondamento
        assertEquals("(0.00,5.13)", new Ponto(0, 5.129).toString());
    }

    /**
     * teste do getX da classe geometria.Ponto
     */
    @Test
    public void testPontoGetX() {
        assertEquals(1, new Ponto(1, 3).getX());
        assertEquals(-2, new Ponto(-2,3).getX());
        assertEquals(0, new Ponto(0,5).getX());
    }

    /**
     * teste do getY da classe geometria.Ponto
     */
    @Test
    public void testPontoGetY() {
        assertEquals(3, new Ponto(1, 3).getY());
        assertEquals(-2, new Ponto(1,-2).getY());
        assertEquals(0, new Ponto(0,0).getY());
    }
    /**
     * Teste do move (soma do ponto A com o vetor B para chegar ao ponto B)
     */
    @Test
    public void testMove() {
        assertEquals("(3.00,3.00)", new Ponto(1,1).move(new Vetor(new Ponto(2,2))).toString());
        //coordenadas negativas (mover para trás)
        assertEquals("(-1.00,-1.00)", new Ponto(1,1).move(new Vetor(new Ponto(-2,-2))).toString());
        assertEquals("(3.00,6.00)", new Ponto(3,1).move(new Vetor(new Ponto(0,5))).toString());
    }

    /**
     * Teste do toString
     */
    @Test
    public void testToString () {
        assertEquals("(1.00,3.00)", new Ponto(1, 3).toString());
        assertEquals("(-1.00,-3.00)", new Ponto(-1, -3).toString());
        assertEquals("(0.00,0.00)", new Ponto(0, 0).toString());

    }

    /**
     * Teste 1 do equals
     * pontos iguais
     */
    @Test
    public void test1EqualsPonto() {
        assertEquals(new Ponto(1,1), new Ponto(1,1));
    }

    /**
     * Teste 2 do equals
     * pontos diferentes
     */
    @Test
    public void test2EqualsPonto() {
        assertNotEquals(new Ponto(2,2), new Ponto(1,1));
    }
    /**
     * Teste 3 do equals
     * objecto não é um ponto
     */
    @Test
    public void test3EqualsPonto() {
        assertNotEquals(new Ponto(2, 2), new Vetor(2, 2));
    }

    /**
     * Teste 1 da DistanciaPonmtos
     * distância horizontal simples
     */
    @Test
    public void test1DistanciaPontos() {
        // distância horizontal simples
        assertEquals(4.0, new Ponto(0,0).distanciaPontos(new Ponto(4,0)), 1e-9);
    }

    /**
     * distancia entre 2 pontos (diagonal)
     */
    @Test
    public void test2DistanciaPontos() {
        assertEquals(5.0, new Ponto(0,0).distanciaPontos(new Ponto(3,4)), 1e-9);
    }

    /**
     * teste 3 distancia entre 2 pontos
     * mesmo ponto, logo, distancia 0
     */
    @Test
    public void test3DistanciaPontos() {
        // mesmo ponto → distância 0
        assertEquals(0.0, new Ponto(2,3).distanciaPontos(new Ponto(2,3)), 1e-9);
    }
}