import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VetorTest {


    //NOTA: perguntar ao professor se crio os pontos/vetores/etc em cada teste ou se crio variáveis globais para evitar repetição

    /**
     * Testes da Classe Ponto
     */
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
     * Teste do
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