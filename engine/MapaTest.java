import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapaTest {

    @BeforeEach
    public void setUp() {
        //limpamos a instancia a cada teste que fazemos..
        Mapa.resetInstancia();
    }

    @Test
    public void testGetInstancia_Singleton() {
        Mapa mapa1 = Mapa.getInstancia();
        Mapa mapa2 = Mapa.getInstancia();

        // Testa se a variável mapa1 e mapa2 são o mesmo, para termos a certeza que não ha mais do que uma instancia de mapa
        assertSame(mapa1, mapa2, "As instâncias devem ser a mesma.");
    }

    @Test
    public void testGetNavegUpdate() {
        Mapa mapa = Mapa.getInstancia();
        NavegacaoUpdate update = mapa.getNavegUpdate();

        assertNotNull(update);
    }

    @Test
    public void testTempo() {
        Mapa mapa = Mapa.getInstancia();
        mapa.tick(0.5);
    }


}