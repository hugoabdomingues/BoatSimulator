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
        Vetor vetor1 = new Vetor(new Ponto(2,2));
        Vetor vetor2 = new Vetor(new Ponto(2,3));
        vetor1.produtoInterno(vetor2);

    }
    /**
     * teste do cossine similarity
     */

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