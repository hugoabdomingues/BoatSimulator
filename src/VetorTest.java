import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VetorTest {

    /**
     * teste do Construtor da classe vetor
     */
    @Test
    public void testeConstrutorVetor() {
        assertEquals("[1.00,3.00]", new Vetor(new Ponto(1, 3)).toString());
    }

    /**
     * teste do modulo
     */
    @Test
    public void testModulo() {
        assertEquals(4, new Vetor(new Ponto(4, 0)).modulo());
    }

    /**
     * teste do produto interno
     */
    @Test
    public void testProdutoInterno () {

        assertEquals(10, new Vetor(new Ponto(2,2)).produtoInterno(new Vetor(new Ponto(2,3))));

    }
    /**
     * teste do cossine similarity
     */
    @Test
    public void testCossineSimilarity () {
        Vetor v1 = new Vetor(new Ponto(1,3));
        Vetor v2 = new Vetor(new Ponto(4, 5.5));
        assertEquals(0.95, v1.cossineSimilarity(v2), 0.2); // delta é a tolerância de casas décimais (neste caso temos tolerância para 1e-2.
    }
    /**
     * Teste getX
     */
    @Test
    public void testGetX() {
        assertEquals(1, new Vetor(new Ponto(1,3)).getX());
    }

    /**
     * Teste do getY
     */
    @Test
    public void testGetY() {
        assertEquals(3, new Vetor(new Ponto(1,3)).getY());
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
        //testar o null:
        Ponto pontoA2 = new Ponto(4,4);
        Ponto pontoB2 = new Ponto(6,6);
        Vetor v2 = new Vetor(new Ponto(2,2));
        assertNull(v2.intersect(new SegmentoReta(pontoA2,pontoB2)));
        //testar a mensagem "SegmentoReta:iv":


    }

    /**
     * teste exceção "Vetor:iv" da função intersect
     */
    @Test
    public void testExcecaoVetor() {
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Vetor(new Ponto(0,0)));
        assertEquals("Vetor:iv", erro.getMessage());
    }

    /**
     * Teste SegmentoReta:iv
     */
    @Test
    public void testExcecaoSegmento() {
        Ponto pontoA = new Ponto(2,2);
        Ponto pontoB = new Ponto(2,2);
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Vetor(new Ponto(1,4)).intersect(new SegmentoReta(pontoA,pontoB)));
        assertEquals("SegmentoReta:iv", erro.getMessage());
    }





    @Test
    public void testAdd (){
        Vetor vetor1 = new Vetor(new Ponto(2, -2));
        Vetor vetor2 = new Vetor(new Ponto(1, 3));

        assertEquals("[3.00,1.00]", vetor1.add(vetor2).toString());

    }




}