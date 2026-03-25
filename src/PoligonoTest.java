import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PoligonoTest {
    /**
     * menos de 3 vértices --> invariante violada
     */
    @Test
    public void test1ConstrutorPoligono() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(1,1)};
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Poligono(vertices));
        assertEquals("Poligono:iv", erro.getMessage());
    }

    /**
     * exatamente 3 vértices --> válido
     */
    @Test
    public void test2ConstrutorPoligono() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(1,0), new Ponto(0,1)};
        assertNotNull(new Poligono(vertices));
    }

    /**
     * muitos vértices --> válido
     */
    @Test
    public void test3ConstrutorPoligono() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(2,0), new Ponto(2,2), new Ponto(1,3), new Ponto(0,2)};
        assertNotNull(new Poligono(vertices));
    }

    /**
     * verifica que devolve o número correto de vértices
     */
    @Test
    public void test1GetVertices() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(1,0), new Ponto(0,1)};
        Poligono p = new Poligono(vertices);
        assertEquals(3, p.getVertices().length);
    }

    /**
     * verifica que os vértices devolvidos têm as coordenadas corretas
     */
    @Test
    public void test2GetVertices() {
        Ponto[] vertices = {new Ponto(1,2), new Ponto(3,4), new Ponto(5,6)};
        Poligono p = new Poligono(vertices);
        assertEquals("(1.00,2.00)", p.getVertices()[0].toString());
        assertEquals("(3.00,4.00)", p.getVertices()[1].toString());
    }

    /**
     *verifica o último vértice
     */
    @Test
    public void test3GetVertices() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(4,0), new Ponto(4,4), new Ponto(0,4)};
        Poligono p = new Poligono(vertices);
        assertEquals("(0.00,4.00)", p.getVertices()[3].toString());
    }

    /**
     *rota não interseta o polígono--> lista vazia
     */
    @Test
    public void test1IntersectPoligono() {
        Ponto[] vertices = {new Ponto(5,5), new Ponto(6,5), new Ponto(6,6), new Ponto(5,6)};
        Poligono p = new Poligono(vertices);
        Ponto[] pontosRota = {new Ponto(0,0), new Ponto(1,0)};
        Route rota = new Route(pontosRota);
        assertTrue(p.intersect(rota).isEmpty());
    }

    /**
     * rota atravessa o quadrado deve encontrar 2 pontos de interseção
     */
    @Test
    public void test2IntersectPoligono() {
        // quadrado de (0,0) a (2,2), rota horizontal que atravessa a meio
        Ponto[] vertices = {new Ponto(0,0), new Ponto(2,0), new Ponto(2,2), new Ponto(0,2)};
        Poligono p = new Poligono(vertices);
        Ponto[] pontosRota = {new Ponto(-1,1), new Ponto(3,1)};
        Route rota = new Route(pontosRota);
        assertEquals(2, p.intersect(rota).size());
    }

    /**
     *
     */
    @Test
    public void test3IntersectPoligono() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(4,0), new Ponto(4,4), new Ponto(0,4)};
        Poligono p = new Poligono(vertices);
        Ponto[] pontosRota = {new Ponto(-1,0), new Ponto(2,0)};
        Route rota = new Route(pontosRota);
        ArrayList<Ponto> resultado = p.intersect(rota);
        assertEquals(1, resultado.size());
        assertEquals(0.0, resultado.get(0).getX(), 1e-9);
        assertEquals(0.0, resultado.get(0).getY(), 1e-9);
    }

    //  para aceder ao método PROTECTED areEqual do Poligono:
    private static class PoligonoTeste extends Poligono {
        public PoligonoTeste() {
            super(new Ponto[]{new Ponto(0,0), new Ponto(1,0), new Ponto(0,1)});
        }
        public boolean areEqualPublico(double a, double b) {
            return areEqual(a, b);
        }
    }

    /**
     * Teste 1 areEqual:valores iguais--> true
     */
    @Test
    public void test1AreEqual() {
        PoligonoTeste p = new PoligonoTeste();
        assertTrue(p.areEqualPublico(3.0, 3.0));
    }



    /**
     * Teste 3 areEqual: valores diferentes--> false
     */
    @Test
    public void test3AreEqual() {
        PoligonoTeste p = new PoligonoTeste();
        assertFalse(p.areEqualPublico(3.0, 4.0));
    }


}