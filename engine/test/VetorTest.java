package test;

import geometria.Ponto;
import geometria.SegmentoReta;
import geometria.Vetor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe teste da classe vetor.
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 07-03-2026
 * @inv modulo() > 0
 */
public class VetorTest {

    /**
     * teste do Construtor da classe vetor
     */
    @Test
    public void testeConstrutor1Vetor() {
        assertEquals("[1.00,3.00]", new Vetor(new Ponto(1, 3)).toString());
        assertEquals("[-1.00,-3.00]", new Vetor(new Ponto(-1, -3)).toString());

    }
    /**
     * teste do Construtoar da classe vetor: erro
     */
    @Test
    public void testeConstrutor1VetorInvariante() {

        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Vetor(new Ponto(0,0)));
        assertEquals("geometria.Vetor:iv", erro.getMessage());

    }

    /**
     * Teste do construtor 2 da classe vetor
     */
    @Test
    public void testConstrutor2Vetor () {
        assertEquals("[1.00,3.00]", new Vetor(1,3).toString());
    }


    /**
     * teste do modulo
     */
    @Test
    public void testModulo() {
        assertEquals(4, new Vetor(new Ponto(4, 0)).modulo());
        //9 + 16 = 25 --> 5
        assertEquals(5, new Vetor(new Ponto(-3, 4)).modulo());
        //4 + 9 = 15 --> 3.6
        assertEquals(10, new Vetor(new Ponto(8, 6)).modulo());
    }

    /**
     * teste do produto interno
     */
    @Test
    public void testProdutoInterno () {

        assertEquals(10, new Vetor(new Ponto(2,2)).produtoInterno(new Vetor(new Ponto(2,3))));
        assertEquals(2, new Vetor(new Ponto(-2,2)).produtoInterno(new Vetor(new Ponto(2,3))));
        assertEquals(-4, new Vetor(new Ponto(2,0)).produtoInterno(new Vetor(new Ponto(-2,3))));

    }
    /**
     * teste do cossine similarity
     */
    @Test
    public void test1CossineSimilarity () {
        Vetor v1 = new Vetor(new Ponto(1,3));
        Vetor v2 = new Vetor(new Ponto(4, 5.5));
        assertEquals(0.95, v1.cossineSimilarity(v2), 0.2); // delta é a tolerância de casas décimais (neste caso temos tolerância para 1e-2.
    }
    /**
     * teste 2 do cossine similarity
     */
    @Test
    public void test2CossineSimilarity () {
        Vetor v3 = new Vetor(new Ponto(1,0));
        Vetor v4 = new Vetor(new Ponto(0,1));

        assertEquals(0.0, v3.cossineSimilarity(v4), 0.1); //0.1 --> tolerância 1e-1

    }
    /**
     * teste 3 do cossine similarity
     */
    @Test
    public void test3CossineSimilarity () {
        Vetor v1 = new Vetor(new Ponto(2,2));
        Vetor v2 = new Vetor(new Ponto(2,2));
        assertEquals(1.0, v1.cossineSimilarity(v2), 0.1); //0.1 --> tolerância 1e-1
    }

    /**
     * Teste getX
     */
    @Test
    public void testGetX() {
        assertEquals(1, new Vetor(new Ponto(1,3)).getX());
        assertEquals(-2, new Vetor(new Ponto(-2,3)).getX());
        assertEquals(0, new Vetor(new Ponto(0,5)).getX());
    }

    /**
     * Teste do getY
     */
    @Test
    public void testGetY() {
        assertEquals(3, new Vetor(new Ponto(1,3)).getY());
        assertEquals(-2, new Vetor(new Ponto(1,-2)).getY());
        assertEquals(0, new Vetor(new Ponto(4,0)).getY());
    }
    /**
     * Teste do getPonto
     */
    @Test
    public void testGetPonto() {
        assertEquals("(1.00,3.00)", new Vetor(new Ponto(1, 3)).getPonto().toString());
    }
    /**
     * Teste do intersect
     */
    @Test
    public void testIntersect() {
        Ponto pontoA = new Ponto(0,1);
        Ponto pontoB = new Ponto(4,1);
        Vetor v1 = new Vetor(new Ponto(2,2));
        assertEquals("(1.00,1.00)", v1.intersect(new SegmentoReta(pontoA,pontoB)).toString());
    }
    /**
     * Teste do intersect: null
     */
    @Test
    public void testIntersectNull() {
        Ponto pontoA2 = new Ponto(4,4);
        Ponto pontoB2 = new Ponto(6,6);
        Vetor v2 = new Vetor(new Ponto(2,2));
        assertNull(v2.intersect(new SegmentoReta(pontoA2,pontoB2)));
    }

    /**
     * teste do intersect: exceção "geometria.Vetor:iv" da função intersect
     */
    @Test
    public void testIntersectExcecaoVetor() {
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Vetor(new Ponto(0,0)));
        assertEquals("geometria.Vetor:iv", erro.getMessage());
    }

    /**
     * Teste do intersect: geometria.SegmentoReta:iv
     */
    @Test
    public void testIntersectExcecaoSegmento() {
        Ponto pontoA = new Ponto(2,2);
        Ponto pontoB = new Ponto(2,2);
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Vetor(new Ponto(1,4)).intersect(new SegmentoReta(pontoA,pontoB)));
        assertEquals("geometria.SegmentoReta:iv", erro.getMessage());
    }

    /**
     * Teste do mult (multiplicação do X e Y por um escalar d)
     */
    @Test
    public void testMult() {
        assertEquals("[2.00,4.00]", new Vetor(new Ponto(1,2)).mult(2).toString());

    }
    /**
     * Teste do mult multiplicação por um escalar neg.
     */
    @Test
    public void testMult2() {
        assertEquals("[-1.00,-2.00]", new Vetor(new Ponto(1,2)).mult(-1).toString());

    }
    /**
     * Teste do mult (multiplicação por um escalar decimal)
     */
    @Test
    public void testMult3() {
        assertEquals("[1.00,2.00]", new Vetor(new Ponto(2,4)).mult(0.5).toString());
    }


    /**
     * Teste do add
     */
    @Test
    public void testAdd (){
        Vetor vetor1 = new Vetor(new Ponto(2, -2));
        Vetor vetor2 = new Vetor(new Ponto(1, 3));
        assertEquals("[3.00,1.00]", vetor1.add(vetor2).toString());
    }
    /**
     * Teste do add
     */
    @Test
    public void testAdd2 (){
        //resultado neg.
        Vetor vetor1 = new Vetor(new Ponto(-1, -1));
        Vetor vetor2 = new Vetor(new Ponto(-2, -2));
        assertEquals("[-3.00,-3.00]", vetor1.add(vetor2).toString());
    }
    /**
     * Teste do add
     */
    @Test
    public void testAdd3 (){
        //resultado 0
        Vetor vetor1 = new Vetor(new Ponto(1, 1));
        Vetor vetor2 = new Vetor(new Ponto(-1, -1));
        Exception erro = assertThrows(IllegalArgumentException.class, () -> vetor1.add(vetor2));
        assertEquals("geometria.Vetor:iv", erro.getMessage());
    }

    /**
     * Teste do sub
     */
    @Test
    public void testAsub (){
        Vetor vetor1 = new Vetor(new Ponto(2, -2));
        Vetor vetor2 = new Vetor(new Ponto(1, 3));
        assertEquals("[1.00,-5.00]", vetor1.sub(vetor2).toString());
    }    /**
     * Teste do sub 2
     */
    @Test
    public void testAsub2 (){
        Vetor vetor1 = new Vetor(new Ponto(2, 0));
        Vetor vetor2 = new Vetor(new Ponto(-2, 3));
        assertEquals("[4.00,-3.00]", vetor1.sub(vetor2).toString());
    }    /**
     * Teste do sub 3
     */
    @Test
    public void testsub3 (){
        Vetor vetor1 = new Vetor(new Ponto(2, 3));
        Vetor vetor2 = new Vetor(new Ponto(-2, -3));
        assertEquals("[4.00,6.00]", vetor1.sub(vetor2).toString());
    }

}