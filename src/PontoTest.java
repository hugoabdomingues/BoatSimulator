import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe teste da classe ponto
 * @author Hugo_Domingues
 * @version 01-03-2026
 * @inv O ponto é definido por um x e um y.
 */
public class PontoTest {

    /**
     * Teste 1 do Construtor do Ponto
     */
    @Test
    public void test1ConstrutorPonto () {
        assertEquals("(1.00,3.00)", new Ponto(1, 3).toString());
    }

    /**
     * Teste 2 do Construtor do Ponto
     */
    @Test
    public void test2ConstrutorPonto () {
        // números negativos
        assertEquals("(-2.50,-4.00)", new Ponto(-2.5, -4).toString());
    }

    /**
     * Teste 3 do Construtor do Ponto: zero e arredondamentos
     */
    @Test
    public void test3ConstrutorPonto () {
        // zero e arredondamento
        assertEquals("(0.00,5.13)", new Ponto(0, 5.129).toString());
    }

    /**
     * teste do getX da classe Ponto
     */
    @Test
    public void testPontoGetX() {
        assertEquals(1, new Ponto(1, 3).getX());
        assertEquals(-2, new Ponto(-2,3).getX());
        assertEquals(0, new Ponto(0,5).getX());
    }

    /**
     * teste do getY da classe Ponto
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


}