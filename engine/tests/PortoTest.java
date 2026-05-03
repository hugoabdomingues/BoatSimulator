package tests;

import entities.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PortoTest {

    @Test
    public void testConstrutor() {

        String nome = "A";
        Ponto localizacao = new Ponto(0, 0);
        Porto p = new Porto(nome, localizacao);

        assertEquals("A", p.getNome());
        assertEquals(localizacao, p.getLocalizacao());
        assertNotNull(p.getStringBarcosFila());
        assertTrue(p.getStringBarcosFila().isEmpty());
    }

    @Test
    public void testVerificarFila() {

        Ponto p1 = new Ponto(0, 0);
        Ponto p2 = new Ponto(10, 10);
        Route rota = new Route(new Ponto[]{p1, p2});
        Navegador nav = new Navegador(rota);

        Porto p = new Porto("A", p1);

        p.agendarBarco("T=10, B, 1");

        Barco resultado = p.verificarFila(10.0, nav);

        assertNotNull(resultado, "O barco deve ser criado");
        assertEquals("B1", resultado.getCodigo(), "O ID do barco deve ser 'B1'");
        assertTrue(p.getStringBarcosFila().isEmpty(), "A fila deve estar vazia após a partida do barco");
    }


    @Test

    public void testGetNome() {

        Porto p = new Porto("B", new Ponto(1,1));

        assertEquals("B", p.getNome());
    }

    @Test
    public void testGetLocalizacao(){

        Ponto ponto = new Ponto(2,3);
        Porto p = new Porto("C", ponto);

        assertEquals(ponto, p.getLocalizacao());
    }

    @Test

    public void testAgendarBarco(){

        Porto p = new Porto("D", new Ponto(0,0));

        String barco = "T=12, J, 2";
        p.agendarBarco(barco);

        assertEquals(1, p.getStringBarcosFila().size());
        assertTrue(p.getStringBarcosFila().contains(barco));
    }

    @Test

    public void testAgendarBarcos(){

        Porto p = new Porto("E", new Ponto(0,0));

        String barco1 = "T=20, J, 2";
        String barco2 = "T=10, F, 3";
        String barco3 = "T=15, F, 1";

        p.agendarBarco(barco1);
        p.agendarBarco(barco2);
        p.agendarBarco(barco3);

        assertEquals(3, p.getStringBarcosFila().size());
        assertTrue(p.getStringBarcosFila().contains(barco1));
        assertTrue(p.getStringBarcosFila().contains(barco2));
        assertTrue(p.getStringBarcosFila().contains(barco3));
    }

    @Test

    public void testGetStringBarcosFila(){

        Porto p = new Porto("H", new Ponto(1,1));

        p.agendarBarco("T=1, J, 2");
        List<String> fila = p.getStringBarcosFila();

        assertNotNull(fila);
        assertEquals(1, fila.size());
    }

}