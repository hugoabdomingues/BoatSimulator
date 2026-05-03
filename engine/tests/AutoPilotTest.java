package tests;

import entities.AutoPilot;
import entities.Ponto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe que representa o autopiloto
 * @author Hugo_Domingues
 * @version 25-03-2026
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
        assertEquals("entity.AutoPilot:iv", erro.getMessage());
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
     * teste 1 do time
     */
    @Test
    public void test1Time () {
        AutoPilot autopilot = new AutoPilot(new Ponto(3,2),new Ponto(3,4));
        assertEquals(5.00, autopilot.time(0.4),1e-2);
    }
    /**
     * teste 2 do time
     */
    @Test
    public void test2Time () {
        AutoPilot autopilot = new AutoPilot(new Ponto(3,4),new Ponto(3,2));
        assertEquals(5.00, autopilot.time(0.4),1e-2);
    }
    /**
     * teste 3 do time
     */
    @Test
    public void test3Time () {
        AutoPilot autopilot = new AutoPilot(new Ponto(3,2),new Ponto(3,4));
        assertEquals(5.00, autopilot.time(0.4),1e-2);
    }

    /**
     * teste 1 do metodo speed
     */
    @Test
    public void test1Speed () {
        Vetor w = new Vetor(0.2,0.2);
        AutoPilot autoPilot = new AutoPilot(new Ponto(3,2), new Ponto(3,4));
        assertEquals("[-0.20,0.20]", autoPilot.speed(w, 5).toString());
    }
    /**
     * Teste 2
     * Sem vento (Vento = 0,0). O avião só precisa de ir em frente.
     * Rota de (0,0) a (10,0) em 2 segundos -> Velocidade solo = (5,0)
     */
    @Test
    public void test2Speed() {
        AutoPilot autoPilot = new AutoPilot(new Ponto(5, 0), new Ponto(10, 0));
        Vetor w = new Vetor(-2, 0); // Vento a empurrar para trás (temos de compensar com trabalho do motor para chegarmos ao ponto (10,0) num segundo
        double t = 1.0;

        Vetor velocidadeAviao = autoPilot.speed(w, t);

        Assertions.assertEquals(7.00, velocidadeAviao.getX(), 1e-2);
        Assertions.assertEquals(0.00, velocidadeAviao.getY(), 1e-2);
    }
    /**
     * teste 3 do metodo speed
     * vento a favor do avião
     */
    @Test
    public void test3Speed () {
        AutoPilot autoPilot = new AutoPilot(new Ponto(5, 5), new Ponto(10, 10));
        Vetor w = new Vetor(3, 3); // Vento a empurrar para trás (temos de compensar com trabalho do motor para chegarmos ao ponto (10,0) num segundo
        double t = 1.0;

        Vetor velocidadeAviao = autoPilot.speed(w, t);

        Assertions.assertEquals(2.00, velocidadeAviao.getX(), 1e-2);
        Assertions.assertEquals(2.00, velocidadeAviao.getY(), 1e-2);
    }


}