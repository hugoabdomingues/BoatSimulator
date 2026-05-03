package tests;

import entities.Ponto;
import entities.SegmentoReta;
import entities.Vetor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe teste da classe vetor.
 * @author Hugo_Domingues
 * @version 07-03-2026
 * @inv modulo() > 0
 */
public class VetorTest {

    /**
     * teste do Construtor da classe vetor
     */
    @Test
    public void testeConstrutor1Vetor() {
        Assertions.assertEquals("[1.00,3.00]", new Vetor(new Ponto(1, 3)).toString());
        Assertions.assertEquals("[-1.00,-3.00]", new Vetor(new Ponto(-1, -3)).toString());

    }
    /**
     * teste do Construtoar da classe vetor: erro
     */
    @Test
    public void testeConstrutor1VetorInvariante() {

        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Vetor(new Ponto(0,0)));
        assertEquals("entities.Vetor:iv", erro.getMessage());

    }

    /**
     * Teste do construtor 2 da classe vetor
     */
    @Test
    public void testConstrutor2Vetor () {
        Assertions.assertEquals("[1.00,3.00]", new Vetor(1,3).toString());
    }


    /**
     * teste do modulo
     */
    @Test
    public void testModulo() {
        Assertions.assertEquals(4, new Vetor(new Ponto(4, 0)).modulo());
        //9 + 16 = 25 --> 5
        Assertions.assertEquals(5, new Vetor(new Ponto(-3, 4)).modulo());
        //4 + 9 = 15 --> 3.6
        Assertions.assertEquals(10, new Vetor(new Ponto(8, 6)).modulo());
    }

    /**
     * teste do produto interno
     */
    @Test
    public void testProdutoInterno () {

        Assertions.assertEquals(10, new Vetor(new Ponto(2,2)).produtoInterno(new Vetor(new Ponto(2,3))));
        Assertions.assertEquals(2, new Vetor(new Ponto(-2,2)).produtoInterno(new Vetor(new Ponto(2,3))));
        Assertions.assertEquals(-4, new Vetor(new Ponto(2,0)).produtoInterno(new Vetor(new Ponto(-2,3))));

    }
    /**
     * teste do cossine similarity
     */
    @Test
    public void test1CossineSimilarity () {
        Vetor v1 = new Vetor(new Ponto(1,3));
        Vetor v2 = new Vetor(new Ponto(4, 5.5));
        Assertions.assertEquals(0.95, v1.cossineSimilarity(v2), 0.2); // delta é a tolerância de casas décimais (neste caso temos tolerância para 1e-2.
    }
    /**
     * teste 2 do cossine similarity
     */
    @Test
    public void test2CossineSimilarity () {
        Vetor v3 = new Vetor(new Ponto(1,0));
        Vetor v4 = new Vetor(new Ponto(0,1));

        Assertions.assertEquals(0.0, v3.cossineSimilarity(v4), 0.1); //0.1 --> tolerância 1e-1

    }
    /**
     * teste 3 do cossine similarity
     */
    @Test
    public void test3CossineSimilarity () {
        Vetor v1 = new Vetor(new Ponto(2,2));
        Vetor v2 = new Vetor(new Ponto(2,2));
        Assertions.assertEquals(1.0, v1.cossineSimilarity(v2), 0.1); //0.1 --> tolerância 1e-1
    }

    /**
     * Teste getX
     */
    @Test
    public void testGetX() {
        Assertions.assertEquals(1, new Vetor(new Ponto(1,3)).getX());
        Assertions.assertEquals(-2, new Vetor(new Ponto(-2,3)).getX());
        Assertions.assertEquals(0, new Vetor(new Ponto(0,5)).getX());
    }

    /**
     * Teste do getY
     */
    @Test
    public void testGetY() {
        Assertions.assertEquals(3, new Vetor(new Ponto(1,3)).getY());
        Assertions.assertEquals(-2, new Vetor(new Ponto(1,-2)).getY());
        Assertions.assertEquals(0, new Vetor(new Ponto(4,0)).getY());
    }
    /**
     * Teste do getPonto
     */
    @Test
    public void testGetPonto() {
        Assertions.assertEquals("(1.00,3.00)", new Vetor(new Ponto(1, 3)).getPonto().toString());
    }
    /**
     * Teste do intersect
     */
    @Test
    public void testIntersect() {
        Ponto pontoA = new Ponto(0,1);
        Ponto pontoB = new Ponto(4,1);
        Vetor v1 = new Vetor(new Ponto(2,2));
        Assertions.assertEquals("(1.00,1.00)", v1.intersect(new SegmentoReta(pontoA,pontoB)).toString());
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
     * teste do intersect: exceção "entities.Vetor:iv" da função intersect
     */
    @Test
    public void testIntersectExcecaoVetor() {
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Vetor(new Ponto(0,0)));
        assertEquals("entities.Vetor:iv", erro.getMessage());
    }

    /**
     * Teste do intersect: entities.SegmentoReta:iv
     */
    @Test
    public void testIntersectExcecaoSegmento() {
        Ponto pontoA = new Ponto(2,2);
        Ponto pontoB = new Ponto(2,2);
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Vetor(new Ponto(1,4)).intersect(new SegmentoReta(pontoA,pontoB)));
        assertEquals("entities.SegmentoReta:iv", erro.getMessage());
    }

    /**
     * Teste do mult (multiplicação do X e Y por um escalar d)
     */
    @Test
    public void testMult() {
        Assertions.assertEquals("[2.00,4.00]", new Vetor(new Ponto(1,2)).mult(2).toString());

    }
    /**
     * Teste do mult multiplicação por um escalar neg.
     */
    @Test
    public void testMult2() {
        Assertions.assertEquals("[-1.00,-2.00]", new Vetor(new Ponto(1,2)).mult(-1).toString());

    }
    /**
     * Teste do mult (multiplicação por um escalar decimal)
     */
    @Test
    public void testMult3() {
        Assertions.assertEquals("[1.00,2.00]", new Vetor(new Ponto(2,4)).mult(0.5).toString());
    }


    /**
     * Teste do add
     */
    @Test
    public void testAdd (){
        Vetor vetor1 = new Vetor(new Ponto(2, -2));
        Vetor vetor2 = new Vetor(new Ponto(1, 3));
        Assertions.assertEquals("[3.00,1.00]", vetor1.add(vetor2).toString());
    }
    /**
     * Teste do add
     */
    @Test
    public void testAdd2 (){
        //resultado neg.
        Vetor vetor1 = new Vetor(new Ponto(-1, -1));
        Vetor vetor2 = new Vetor(new Ponto(-2, -2));
        Assertions.assertEquals("[-3.00,-3.00]", vetor1.add(vetor2).toString());
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
        assertEquals("entities.Vetor:iv", erro.getMessage());
    }

    /**
     * Teste do sub
     */
    @Test
    public void testAsub (){
        Vetor vetor1 = new Vetor(new Ponto(2, -2));
        Vetor vetor2 = new Vetor(new Ponto(1, 3));
        Assertions.assertEquals("[1.00,-5.00]", vetor1.sub(vetor2).toString());
    }    /**
     * Teste do sub 2
     */
    @Test
    public void testAsub2 (){
        Vetor vetor1 = new Vetor(new Ponto(2, 0));
        Vetor vetor2 = new Vetor(new Ponto(-2, 3));
        Assertions.assertEquals("[4.00,-3.00]", vetor1.sub(vetor2).toString());
    }    /**
     * Teste do sub 3
     */
    @Test
    public void testsub3 (){
        Vetor vetor1 = new Vetor(new Ponto(2, 3));
        Vetor vetor2 = new Vetor(new Ponto(-2, -3));
        Assertions.assertEquals("[4.00,6.00]", vetor1.sub(vetor2).toString());
    }

}