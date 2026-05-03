package tests;

import entities.Ponto;
import entities.Route;
import entities.Tempestade;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TempestadeTest {
    @Test
    public void testVerificaColisao() {
        //Criamos uma tempestade em (0,0) com raio 5
        Ponto centro = new Ponto(0, 0);
        Tempestade tempestade = new Tempestade("entity.Tempestade A", centro, 5.0);

        // Criamos uma rota que passa exatamente pelo meio do círculo (de x=-10 a x=10)
        Route rotaQueAtravessa = new Route(new Ponto[]{new Ponto(-10,0), new Ponto(10,0)});

        // Executamos a verificação
        ArrayList<Ponto> colisoes = tempestade.verificaColisao(rotaQueAtravessa);

        // Verificamos se detetou colisões (a lista não pode estar vazia)
        assertNotNull(colisoes, "A lista de colisões não deve ser nula.");
    }
}