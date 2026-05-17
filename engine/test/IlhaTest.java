package test;

import entity.Ilha;
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
class IlhaTest {

        @Test
        public void testVerificaColisao() {
            //criamos uma ilha quadrada de 4x4
            Ponto[] pontosIlha = {new Ponto(0,0), new Ponto(0,4), new Ponto(4,4), new Ponto(4,0)};
            Ilha ilha = new Ilha("entity.Ilha A", pontosIlha);

            // Criamos uma rota que atravessa a ilha horizontalmente (de x=-2 até x=6, no y=2)
            Route rotaQueAtravessa = new Route(new Ponto[]{new Ponto(-2,2), new Ponto(6,2)});

            ArrayList<Ponto> colisoes = ilha.verificaColisao(rotaQueAtravessa);

            assertNotNull(colisoes, "A lista de colisões não deve ser nula.");
        }
    }