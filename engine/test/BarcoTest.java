package test;

import entity.AutoPilot;
import entity.Barco;
import entity.Navegador;
import geometria.Ponto;
import entity.Route;
import geometria.Vetor;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



/**
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 */
public class BarcoTest {
    @Test
    public void TestConstrutor(){
        Ponto localizacao = new Ponto(0, 1);

        Barco p1 = new Barco("A", 2,"J", 12, localizacao);
        assertNotNull(p1, "O objeto deveria ter sido instanciado");

        //Falta caso que dá erro
    }

    @Test
    public void TestGetCodigo(){
        Ponto localizacao = new Ponto(0, 1);
        Barco p1 = new Barco("A", 2, "B",12,localizacao);

       assertEquals("A12", p1.getCodigo());
       assertNotEquals("A 12",p1.getCodigo());
        assertNotEquals("12A",p1.getCodigo());
    }

    @Test
    public void TestIntersetarBarco(){ //Completar dps
        Ponto[] p = new Ponto[2];
        p[0] = new Ponto(0,1);
        p[1] = new Ponto(1,2);
        Ponto localizacao = new Ponto(0, 1);
        Barco p1 = new Barco("A", 2, "B",12,localizacao);
        Ponto[] p3 = new Ponto[2];
        p3[0] = new Ponto(0,1);
        p3[1] = new Ponto(1,2);
        Ponto localizacao1 = new Ponto(1, 2);
        Barco p2 = new Barco("A", 2, "B",12,localizacao1);

    }

    @Test
    public void TestSet_GetPosicaoAtual(){
        Ponto localizacao = new Ponto(0, 1);
        Barco p1 = new Barco("A", 2, "B",12,localizacao);

        p1.setPosicaoAtual(new Ponto(2,3));

        assertEquals(new Ponto(2,3), p1.getPosicaoAtual());
        assertNotEquals(localizacao, p1.getPosicaoAtual());

        p1.setPosicaoAtual(new Ponto(4,5));
        assertEquals(new Ponto(4,5), p1.getPosicaoAtual());
        assertNotEquals(new Ponto(2,3), p1.getPosicaoAtual());
    }


    @Test
    public void TestSetNavegador(){
        Ponto[] p = new Ponto[2];
        p[0] = new Ponto(0,1);
        p[1] = new Ponto(1,2);
        Ponto[] j = new Ponto[2];
        j[0] = new Ponto(1,2);
        j[1] = new Ponto(4,5);
        Ponto localizacao = new Ponto(0, 1);
        Barco p1 = new Barco("A", 2, "B",12,localizacao);
        Navegador n = new Navegador(new Route(p));
        Navegador n1 = new Navegador(new Route(j));

        p1.setNavegador(n);
        assertEquals(n, p1.getNavegador());
        assertNotEquals(n1,p1.getNavegador());

        p1.setNavegador(n1);
        assertNotEquals(n, p1.getNavegador());
        assertEquals(n1,p1.getNavegador());



    }

    @Test
    public void TestSetHora(){
        Ponto localizacao = new Ponto(0, 1);
        Barco p1 = new Barco("A", 2, "B",12,localizacao);

        p1.setHora(25);
        assertNotEquals(25,p1.getHora());
        assertEquals(12,p1.getHora());

        p1.setHora(9);
        assertEquals(9, p1.getHora());
        assertNotEquals(12,p1.getHora());

    }

    @Test
    public void TestSetEstado(){
        Ponto localizacao = new Ponto(0, 1);
        Barco p1 = new Barco("A", 2, "B",12,localizacao);

        p1.setEstado(true);
        assertEquals( true, p1.getEstado());
        assertNotEquals( false, p1.getEstado());
        p1.setEstado(false);
        assertEquals( false, p1.getEstado());
    }
    @Test
    public void testCorrenteCompensadaPeloBarco() {
        Ponto partida = new Ponto(1, 1);
        Ponto destino = new Ponto(11, 1);
        Route rota = new Route(new Ponto[]{partida, destino});
        Navegador navegador = new Navegador(rota);
        Barco barco = new Barco("A", 5, "B", 1, partida);
        Vetor corrente = new Vetor(1, 1);

        barco.setNavegador(navegador);

        AutoPilot autoPilot = new AutoPilot(partida, destino);
        double tempoSegmento = autoPilot.time(barco.getVl());
        Vetor vetorDoBarco = autoPilot.speed(corrente, tempoSegmento);
        Ponto posicaoDepoisDeUmSegundo = barco.getNavegador().pontoDaRotaNoTempoT(barco.getVl(), 1, corrente);

        System.out.println("Vetor do barco para compensar a corrente: " + vetorDoBarco);

        assertEquals(4.0, vetorDoBarco.getX());
        assertEquals(-1.0, vetorDoBarco.getY());
        assertEquals(new Ponto(6, 1), posicaoDepoisDeUmSegundo);
    }


}
