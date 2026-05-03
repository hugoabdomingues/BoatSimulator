package tests;

import entities.Ponto;
import entities.Route;
import entities.SegmentoReta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Classe teste do entity.Route
 * @author Hugo_Domingues
 * @version 20-03-2026
 * @inv pontos consecutivos não podem ser iguais.
 */
class RouteTest {

    /**
     * Teste 1 do construtor
     * caso em que testamos a invariante de classe (uma rota tem pelo menos 2 pontos)
     */
    @Test
    public void test1ConstrutorRoute () {
        Ponto[] pontos = {new Ponto(2,2)};
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Route(pontos));
        assertEquals("Rota:iv", erro.getMessage());
    }
    /**
     * Teste 2 do Construtor
     * caso em que a rota é criada com sucesso
     */
    @Test
    public void test2ConstrutorRoute () {
        Ponto[] pontos = {new Ponto(4,4), new Ponto(6,6), new Ponto(7,7)};
        assertNotNull(new Route(pontos));
    }
    /**
     * Teste 3 do construtor
     */
    @Test
    public void test3ConstrutorRoute() {
        Ponto[] pontos = {new Ponto(0,0), new Ponto(1,1), new Ponto(2,2), new Ponto(3,0)};
        assertNotNull(new Route(pontos));
    }

    //comprimento

    /**
     * Teste 1 do comprimentoTotal
     * pontos iguais --> distancia 0
     */
    @Test
    public void test1ComprimentoTotal() {
        Ponto[] pontos = {new Ponto(2,2), new Ponto(2,2)};
        Route rota = new Route(pontos);
        assertEquals(0, rota.comprimentoTotal(), 1e-9);
    }
    /**
     * Teste 2 do comprimentoTotal
     * rota simples horizontal com distância 4
     */
    @Test
    public void test2ComprimentoTotal () {
        Ponto[] pontos = {new Ponto(0,0), new Ponto(4,0)};
        assertEquals(4.0, new Route(pontos).comprimentoTotal(), 1e-9);
    }

    /**
     * Teste 3 do comprimentoTotal
     */
    @Test
    public void test3ComprimentoTotal() {
        Ponto[] pontos = {new Ponto(0,0), new Ponto(3,0), new Ponto(3,4)};
        assertEquals(7.0, new Route(pontos).comprimentoTotal(), 1e-9);
    }

    /**
     * Teste 1 do intersectRota
     * Verificação da invariante (dois pontos consecutivos n podem ser iguais)
     */
    @Test
    public void test1IntersectRota () {
        Ponto[] pontos = {new Ponto(4,4),new Ponto(4,4), new Ponto(6,7)};
        SegmentoReta parede = new SegmentoReta(new Ponto(2,2), new Ponto(4,2));
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Route(pontos).intersecao(parede));
        assertEquals("entities.SegmentoReta:iv", erro.getMessage());
    }

    /**
     * Teste 2 do intersectRota
     * sem interseção: null
     */
    @Test
    public void test2IntersectRota () {
        Ponto[] pontos = {new Ponto(4,4), new Ponto(6,6), new Ponto(7,7)};
        Route rota = new Route(pontos);
        SegmentoReta parece = new SegmentoReta(new Ponto(2,2), new Ponto(4,2));
        assertTrue(rota.intersecao(parece).isEmpty());
    }
    /**
     * Teste 3 do intersectRota
     * interseção válida
     */
    @Test
    public void test3IntersectRota () {
        Ponto[] pontos = {new Ponto(0,0), new Ponto(4,0)};
        Route rota = new Route(pontos);
        SegmentoReta parede = new SegmentoReta(new Ponto(2,-1), new Ponto(2,1));
        ArrayList<Ponto> resultado = rota.intersecao(parede);
        Assertions.assertEquals("(2.00,0.00)", resultado.getFirst().toString());
    }

    @Test
    public void test1GetPonto() {
        //verifica que devolve o número correto de pontos
        Ponto[] pontos = {new Ponto(0,0), new Ponto(1,1), new Ponto(2,2)};
        Route rota = new Route(pontos);
        assertEquals(3, rota.getPontos().length);
    }

    @Test
    public void test2GetPonto() {
        //verifica que os pontos devolvidos têm as coordenadas corretas
        Ponto[] pontos = {new Ponto(1,2), new Ponto(3,4)};
        Route rota = new Route(pontos);
        assertEquals("(1.00,2.00)", rota.getPontos()[0].toString());
        assertEquals("(3.00,4.00)", rota.getPontos()[1].toString());
    }

    /**
     * getter
     */
    @Test
    public void test3GetPonto() {
        //verifica o último ponto da rota
        Ponto[] pontos = {new Ponto(0,0), new Ponto(2,2), new Ponto(5,3)};
        Route rota = new Route(pontos);
        assertEquals("(5.00,3.00)", rota.getPontos()[2].toString());
    }
}