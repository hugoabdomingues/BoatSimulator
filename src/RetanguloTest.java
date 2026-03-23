import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RetanguloTest {

    /**
     * teste1 construtor -->retângulo válido
     */
    @Test
    public void test1ConstrutorRetangulo() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(4,0), new Ponto(4,2), new Ponto(0,2)};
        assertNotNull(new Retangulo(vertices));
    }

    /**
     * teste2 Construtor: menos de 4 vértices--> invariante violada
     */
    @Test
    public void test2ConstrutorRetangulo() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(1,0), new Ponto(0,1)};
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Retangulo(vertices));
        assertEquals("Retangulo:iv", erro.getMessage());
    }

    /**
     * teste 3 construtor --> losango: lados iguais mas diagonais diferentes, logo n é um retangulo
     */
    @Test
    public void test3ConstrutorRetangulo() {
        Ponto[] vertices = {new Ponto(0,1), new Ponto(1,0), new Ponto(0,-1), new Ponto(-1,0)};
        Exception erro = assertThrows(IllegalArgumentException.class, () -> new Retangulo(vertices));
        assertEquals("Retangulo:iv", erro.getMessage());
    }

    /**
     * Teste 1 AreEqual valores iguais --> true
     */
    @Test
    public void test1AreEqual() {
        Retangulo r = new Retangulo(new Ponto[]{new Ponto(0,0), new Ponto(2,0), new Ponto(2,1), new Ponto(0,1)});
        assertTrue(r.areEqual(3.0, 3.0));
    }

    /**
     * teste 2 are equals
     * diferença menor que 1e-9 --> considera iguais
     */
    @Test
    public void test2AreEqual() {
        Retangulo r = new Retangulo(new Ponto[]{new Ponto(0,0), new Ponto(2,0), new Ponto(2,1), new Ponto(0,1)});
        assertTrue(r.areEqual(1.0, 1.0 + 1e-10));
    }

    /**
     * teste 3 areequals: valores  diferentes--> false
     */
    @Test
    public void test3AreEqual() {
        Retangulo r = new Retangulo(new Ponto[]{new Ponto(0,0), new Ponto(2,0), new Ponto(2,1), new Ponto(0,1)});
        assertFalse(r.areEqual(3.0, 4.0));
    }

    /**
     * teste1 intersect
     * rota atravessa o retângulo horizontalmente: 2 interseções
     */
    @Test
    public void test1IntersectRetangulo() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(4,0), new Ponto(4,2), new Ponto(0,2)};
        Retangulo r = new Retangulo(vertices);
        Ponto[] pontosRota = {new Ponto(-1,1), new Ponto(5,1)};
        Route rota = new Route(pontosRota);
        assertEquals(2, r.intersect(rota).size());
    }

    /**
     * teste 2 intersect
     * rota fora do retângulo --> lista vazia
     */
    @Test
    public void test2IntersectRetangulo() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(4,0), new Ponto(4,2), new Ponto(0,2)};
        Retangulo r = new Retangulo(vertices);
        Ponto[] pontosRota = {new Ponto(5,0), new Ponto(5,2)};
        Route rota = new Route(pontosRota);
        assertTrue(r.intersect(rota).isEmpty());
    }

    /**
     * teste3 intersect
     * rota toca apenas um canto do retângulo --> 1 interseção
     */
    @Test
    public void test3IntersectRetangulo() {
        Ponto[] vertices = {new Ponto(0,0), new Ponto(4,0), new Ponto(4,2), new Ponto(0,2)};
        Retangulo r = new Retangulo(vertices);
        Ponto[] pontosRota = {new Ponto(-1,0), new Ponto(1,0)};
        Route rota = new Route(pontosRota);
        assertEquals(1, r.intersect(rota).size());
        assertEquals("(0.00,0.00)", r.intersect(rota).get(0).toString());
    }

}