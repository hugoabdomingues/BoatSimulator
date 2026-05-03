import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;



public class BarcoTest {
    @Test
    public void TestConstrutor(){
        Ponto[] p = new Ponto[2];
        p[0] = new Ponto(0,1);
        p[1] = new Ponto(1,2);
        Navegador n = new Navegador(new Route(p));
        Ponto localizacao = new Ponto(0, 1);

        Barco p1 = new Barco("A", 2,"J", 12, localizacao,n);
        assertNotNull(p1, "O objeto deveria ter sido instanciado");
        //Neste teste vai fazer com que os outros testes parem por causa do System.exit();
        Barco p2 = new Barco("A", -1,"J", 0, localizacao,n);
        assertNull(p2, "O objeto não deve ser instanciado");
    }

    @Test
    public void TestGetCodigo(){
        Ponto[] p = new Ponto[2];
        p[0] = new Ponto(0,1);
        p[1] = new Ponto(1,2);
        Navegador n = new Navegador(new Route(p));
        Ponto localizacao = new Ponto(0, 1);
        Barco p1 = new Barco("A", 2,"J", 12, localizacao,n);

        assertEquals("A12", p1.getCodigo());

    }

    @Test
    public void TestGetVl(){
        Ponto[] p = new Ponto[2];
        p[0] = new Ponto(0,1);
        p[1] = new Ponto(1,2);
        Navegador n = new Navegador(new Route(p));
        Ponto localizacao = new Ponto(0, 1);
        Barco p1 = new Barco("A", 2,"J", 12, localizacao,n);
        assertEquals(2,p1.getVl());
    }

    @Test
    public void TestPosicaoAtual(){

        Ponto localizacao = new Ponto(0, 0);
        Barco p = new Barco("A12",2,'C',12,new Navegador(0,0),false,localizacao);
        assertEquals(localizacao, p.getPosicao_atual());

        Ponto[] p = new Ponto[2];
        p[0] = new Ponto(0,1);
        p[1] = new Ponto(1,2);
        Navegador n = new Navegador(new Route(p));
        Ponto localizacao = new Ponto(0, 1);
        Barco p1 = new Barco("A", 2,"J", 12, localizacao,n);
        assertEquals(localizacao, p1.getPosicao_atual());

    }



    @Test
    public void estIntersetarBarco(){
        Ponto[] p = new Ponto[2];
        p[0] = new Ponto(0,1);
        p[1] = new Ponto(1,2);
        Navegador n = new Navegador(new Route(p));
        Ponto localizacao = new Ponto(0, 1);
        Barco p1 = new Barco("A", 2,"J", 12, localizacao,n);

        Ponto[] p2 = new Ponto[2];
        p[0] = new Ponto(1,0);
        p[1] = new Ponto(1,2);
        Navegador n1 = new Navegador(new Route(p2));
        Ponto localizacao1 = new Ponto(1, 0);
        Barco p3 = new Barco("A", 2,"J", 11, localizacao1,n1);

        //Caso 1: Se dois barcos intersetarem-se
        //O barco p1 é maior do que o barco p3
        assertTrue(p1.intersetarBarco(p3));
        assertFalse(p1.getEstado());
        assertTrue(p3.getEstado());

        //Caso 2: Se dois barcos não se intersetarem-se
        Ponto[] p4 = new Ponto[2];
        p[0] = new Ponto(3,0);
        p[1] = new Ponto(1,5);
        Ponto localizacao2 = new Ponto(3,0);
        Navegador n2 = new Navegador(new Route(p4));
        Barco p5 = new Barco("A", 2,"J", 10, localizacao2,n2);
        assertFalse(p3.intersetarBarco(p5));
        assertFalse(p5.intersetarBarco(p1));

        //Caso 3: O barco parado já não interseta com o barco maior
        //O barco parado retoma a viagem (usando o metodo retomar())

    }


}
