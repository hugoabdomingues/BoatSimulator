import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe que representa o autopiloto
 * @author Hugo_Domingues
 * @version 08-03-2026
 * @inv pontoA não pode ser igual ao pontoB
 */
class AutoPilotTest {

    /**
     * teste do construtor
     */
    @Test
    public void teste1Construtor () {
        AutoPilot ap = new AutoPilot(new Ponto(5,5), new Ponto(2,2));
        assertNotNull(ap, "percurso criado com sucesso.");
    }
    /**
     * teste do construtor
     */
    @Test
    public void teste2Construtor () {
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new AutoPilot(new Ponto(2,2),new Ponto(2,2)));
        assertEquals("SegmentoReta:iv", erro.getMessage());
    }
    /**
     * teste do construtor
     */
    @Test
    public void teste3Construtor1 () {
        AutoPilot ap = new AutoPilot(new Ponto(-5,-5), new Ponto(-2,2));
        assertNotNull(ap, "percurso criado com sucesso.");
    }

    /**
     * Teste 1 do time
     */
    @Test
    public void test1Time () {
        Vetor windspeed = new Vetor(1,1);
        double linearSpeed = 0.283;
        assertEquals(5.00, new AutoPilot(new Ponto(2,2), new Ponto(2,4)).time(windspeed,linearSpeed), 0.01); //tolerância = delta = 0.01 = 1e-2
    }
    /**
     * Teste 2 do time
     */
    @Test
    public void test2Time () {
        Vetor windspeed = new Vetor(1,1);
        double linearSpeed = 0.632;
        assertEquals(5.00, new AutoPilot(new Ponto(2,4), new Ponto(2,2)).time(windspeed,linearSpeed), 0.01); //tolerância = delta = 0.01 = 1e-2
    }

    /**
     * Teste 3 do time
     */
    @Test
    public void test3Time () {
        //vento contra --> demora mto mais tempo
        Vetor windspeed = new Vetor(-5,0);
        double linearSpeed = 0.283;
        assertEquals(49.47, new AutoPilot(new Ponto(1,0), new Ponto(10,0)).time(windspeed,linearSpeed), 0.1);//tolerância = delta = 0.01 = 1e-2
    }

    /**
     * teste 1 do metodo speed
     */
    @Test
    public void test1Speed () {
        Vetor windspeed = new Vetor(1,1);
        AutoPilot autoPilot = new AutoPilot(new Ponto(2,2), new Ponto(2,4));
        assertEquals("[-0.20,0.20]", autoPilot.speed(windspeed, 5).toString());
    }
    /**
     * teste 1 do metodo speed
     */
    @Test
    public void test2Speed () {
        Vetor windspeed = new Vetor(1,1);
        AutoPilot autoPilot = new AutoPilot(new Ponto(2,4), new Ponto(2,2));
        assertEquals("[-0.20,-0.60]", autoPilot.speed(windspeed, 5).toString());
    }
    /**
     * teste 1 do metodo speed
     */
    @Test
    public void test3Speed () {
        //vento contra o avião
        Vetor windspeed = new Vetor(-5,0);
        AutoPilot autoPilot = new AutoPilot(new Ponto(1,0), new Ponto(10,0));
        assertEquals("[0.28,0.00]", autoPilot.speed(windspeed, 49.47).toString());
    }


}