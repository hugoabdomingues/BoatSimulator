import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class ObstaculoTest {
    @Test
    public void testGetId() {
        Obstaculo obstaculo = new Obstaculo("Tempestade A") {
            @Override
            public ArrayList<Ponto> verificaColisao(Route rota) {
                return new ArrayList<>();
            }
        };

        // Verifica se o ID guardado e devolvido é exatamente o mesmo que passámos
        assertEquals("Tempestade A", obstaculo.getId());
    }
}