package test;

import entity.Obstaculo;
import geometria.Ponto;
import entity.Route;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 */
class ObstaculoTest {
    @Test
    public void testGetId() {
        Obstaculo obstaculo = new Obstaculo("entity.Tempestade A") {
            @Override
            public ArrayList<Ponto> verificaColisao(Route rota) {
                return new ArrayList<>();
            }
        };

        // Verifica se o ID guardado e devolvido é exatamente o mesmo que passámos
        assertEquals("entity.Tempestade A", obstaculo.getId());
    }
}