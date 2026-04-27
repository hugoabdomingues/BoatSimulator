import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PortoTest {

    @Test
    public void testConstrutor() {

        char nome = 'A';
        Ponto localizacao = new Ponto(0, 0);
        Porto p = new Porto(nome, localizacao);

        assertEquals('A', p.getNome());
        assertEquals(localizacao, p.getLocalizacao());
        assertNotNull(p.getStringBarcosFila());
        assertTrue(p.getStringBarcosFila().isEmpty());
    }

    @Test

    public void testVerificarFila() {

        Porto p = new Porto('A', new Ponto(0,0));

        p.agendarBarco("T=10, B, 2.0");
        p.agendarBarco("T=15, C, 3.0");

        Barco resultado = p.verificarFila(10.0);

        assertNotNull(resultado, "O barco deve ser criado");
        assertTrue(p.getStringBarcosFila().isEmpty(), "O barco deve desaparecer da lista após partir");
    }

    @Test

    public void testGetNome() {

        Porto p = new Porto('B', new Ponto(1,1));

        assertEquals('B', p.getNome());
    }

    @Test
    public void testGetLocalizacao(){

        Ponto ponto = new Ponto(2,3);
        Porto p = new Porto('C', ponto);

        assertEquals(ponto, p.getLocalizacao());
    }

    @Test

    public void testAgendarBarco(){

        Porto p = new Porto('D', new Ponto(0,0));

        String barco = "T=12, J, 2";
        p.agendarBarco(barco);

        assertEquals(1, p.getStringBarcosFila().size());
        assertTrue(p.getStringBarcosFila().contains(barco));
    }

    @Test

    public void testAgendarBarcos(){

        Porto p = new Porto('E', new Ponto(0,0));

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

        Porto p = new Porto('H', new Ponto(1,1));

        p.agendarBarco("T=1, J, 2");
        List<String> fila = p.getStringBarcosFila();

        assertNotNull(fila);
        assertEquals(1, fila.size());
    }

}