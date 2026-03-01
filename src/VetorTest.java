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

    }
    /**
     * Teste do add da classe Vetor
     */
    @Test
    public void testAdd (){
        Vetor vetor1 = new Vetor(new Ponto(2, -2));
        Vetor vetor2 = new Vetor(new Ponto(1, 3));

        assertEquals("[3.00,1.00]", vetor1.add(vetor2).toString());

    }




}