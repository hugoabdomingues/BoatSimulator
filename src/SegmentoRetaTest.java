import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe teste do segmento de reta
 * @author Hugo_Domingues
 * @version 07-03-2026
 * @inv O Segmento não pode ter os dois extremos no mesmo ponto.
 */
public class SegmentoRetaTest {

    /**
     * Teste do primeiro Construtor da clase segmento de reta
     */
    @Test
    public void test1Construtor1Segmento () {
        assertEquals("sr((1.00,1.00); (3.00,3.00))", new SegmentoReta(new Ponto(1,1), new Vetor(new Ponto(2,2))).toString());
    }
    /**
     * Teste do primeiro Construtor da clase segmento de reta
     */
    @Test
    public void test2Construtor1Segmento () {
        assertEquals("sr((1.00,1.00); (-4.00,-4.00))", new SegmentoReta(new Ponto(1,1), new Vetor(new Ponto(-5,-5))).toString());
    }
    /**
     * Teste do primeiro Construtor da clase segmento de reta
     */
    @Test
    public void testInvarianteConstrutor1Segmento () {
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new SegmentoReta(new Ponto(2,2),new Vetor(new Ponto(0,0))));
        assertEquals("Vetor:iv", erro.getMessage());
    }



    /**
     * Teste do segundo construtor da classe segmento de reta
     */
    @Test
    public void test1Construtor2Segmento(){
        assertEquals("sr((1.00,1.00); (2.00,2.00))", new SegmentoReta(new Ponto(1,1), (new Ponto(2,2))).toString());
    }
    /**
     * Teste do segundo construtor da classe segmento de reta
     */
    @Test
    public void test2Construtor2Segmento(){
        assertEquals("sr((1.00,1.00); (-4.00,0.00))", new SegmentoReta(new Ponto(1,1), (new Ponto(-4,0))).toString());
    }
    /**
     * Teste do segundo construtor da classe segmento de reta
     */
    @Test
    public void testInvarianteConstrutor2Segmento(){
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new SegmentoReta(new Ponto(3,3), new Ponto(3,3)));
        assertEquals("SegmentoReta:iv", erro.getMessage());
    }










    /**
     * Teste 1 do getPontoA: coordenadas positivas
     */
    @Test
    public void test1GetPontoA() {
        SegmentoReta sr = new SegmentoReta(new Ponto(1, 2), new Ponto(4, 5));
        assertEquals("(1.00,2.00)", sr.getPontoA().toString());
    }

    /**
     * Teste 2 do getPontoA: coordenadas negativas
     */
    @Test
    public void test2GetPontoA() {
        SegmentoReta sr = new SegmentoReta(new Ponto(-1, -2), new Ponto(4, 5));
        assertEquals("(-1.00,-2.00)", sr.getPontoA().toString());
    }

    /**
     * Teste 3 do getPontoA: Origem
     */
    @Test
    public void test3GetPontoA() {
        SegmentoReta sr = new SegmentoReta(new Ponto(0, 0), new Vetor(new Ponto(1, 1)));
        assertEquals("(0.00,0.00)", sr.getPontoA().toString());
    }

    /**
     * Teste 1 do getPontoB: Construtor 1 (Ponto, Vetor)
     */
    @Test
    public void test2GetPontoB() {
        SegmentoReta sr = new SegmentoReta(new Ponto(2, 2), new Vetor(new Ponto(3, 1)));
        assertEquals("(5.00,3.00)", sr.getPontoB().toString());
    }

    /**
     * Teste 2 do getPontoB: Construtor 2 (Ponto, Ponto)
     */
    @Test
    public void test1GetPontoB() {
        SegmentoReta sr = new SegmentoReta(new Ponto(1, 1), new Ponto(5, 5));
        assertEquals("(5.00,5.00)", sr.getPontoB().toString());
    }

    /**
     * Teste 3 do getPontoB: Valores negativos e decimais
     */
    @Test
    public void test3GetPontoB() {
        SegmentoReta sr = new SegmentoReta(new Ponto(0, 0), new Ponto(-4.5, -2.5));
        assertEquals("(-4.50,-2.50)", sr.getPontoB().toString());
    }

    /**
     * Teste 1 do toString: o Ponto A tem menor módulo que o Ponto B
     * imprime o A primeiro
     */
    @Test
    public void test1ToString() {
        SegmentoReta sr = new SegmentoReta(new Ponto(1, 1), new Ponto(4, 4));
        assertEquals("sr((1.00,1.00); (4.00,4.00))", sr.toString());
    }

    /**
     * Teste 2 do toString: o Ponto B tem menor módulo que o Ponto A
     * imprime o B primeiro.
     */
    @Test
    public void test2ToString() {
        SegmentoReta sr = new SegmentoReta(new Ponto(5, 5), new Ponto(0, 1));
        assertEquals("sr((0.00,1.00); (5.00,5.00))", sr.toString());
    }

    /**
     * Teste 3 do toString: Pontos com módulos iguais (mesma distância à origem)
     * imprime o ponto A primeiro
     */
    @Test
    public void test3ToString() {
        SegmentoReta sr = new SegmentoReta(new Ponto(0, 2), new Ponto(2, 0));
        assertEquals("sr((0.00,2.00); (2.00,0.00))", sr.toString());
    }

    /**
     * Teste 1 do intersect: Interseção válida no meio do segmento
     */
    @Test
    public void test1Intersect() {
        SegmentoReta sr = new SegmentoReta(new Ponto(0, 1), new Ponto(4, 1));
        Vetor v = new Vetor(new Ponto(2, 2));
        assertEquals("(1.00,1.00)", sr.intersect(v).toString());
    }

    /**
     * Teste 2 do intersect: sem interseção ---> retorna null
     */
    @Test
    public void test3IntersectNull() {
        SegmentoReta sr = new SegmentoReta(new Ponto(4, 4), new Ponto(6, 6));
        Vetor v = new Vetor(new Ponto(2, 2)); // O vetor aponta para cima no eixo Y, nunca toca no segmento
        assertNull(sr.intersect(v));
    }
}