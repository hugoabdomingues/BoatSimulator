import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IlhaTest {

        @Test
        public void testVerificaColisao() {
            //criamos uma ilha quadrada de 4x4
            Ponto[] pontosIlha = {new Ponto(0,0), new Ponto(0,4), new Ponto(4,4), new Ponto(4,0)};
            Ilha ilha = new Ilha("Ilha A", pontosIlha);

            // Criamos uma rota que atravessa a ilha horizontalmente (de x=-2 até x=6, no y=2)
            Route rotaQueAtravessa = new Route(new Ponto[]{new Ponto(-2,2), new Ponto(6,2)});

            ArrayList<Ponto> colisoes = ilha.verificaColisao(rotaQueAtravessa);

            // Verificamos se detetou colisões (a lista não pode estar vazia)
            assertNotNull(colisoes, "A lista de colisões não deve ser nula.");
        }
    }