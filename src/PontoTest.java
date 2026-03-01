import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PontoTest {

    //NOTA: perguntar ao professor se crio os pontos/vetores/etc em cada teste ou se crio variáveis globais para evitar repetição

    /**
     * Teste do Construtor do Ponto
     */
    @Test
    public void testConstrutorPonto () {
        assertEquals("(1.00,3.00)", new Ponto(1, 3).toString());
    }

    /**
     * teste do getX da classe Ponto
     */
    @Test
    public void testPontoGetX() {
        assertEquals(1, new Ponto(1, 3).getX());
    }

    /**
     * teste do getY da classe Ponto
     */
    @Test
    public void testPontoGetY() {
        assertEquals(3, new Ponto(1, 3).getY());
    }
    /**
     * Teste do move (soma do ponto A com o vetor B para chegar ao ponto B)
     */
    @Test
    public void testMove() {
        assertEquals("(3.00,3.00)", new Ponto(1,1).move(new Vetor(new Ponto(2,2))).toString());
    }

    /**
     * Teste do toString
     */
    @Test
    public void testToString () {
        assertEquals("(1.00,3.00)", new Ponto(1, 3).toString());
    }


}