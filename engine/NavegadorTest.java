import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NavegadorTest {

    /**
     * teste 1 construtor
     */
    @Test
    public void test1ConstrutorNavegador () {
        Ponto[] pontos = {new Ponto(1,1), new Ponto(2,2), new Ponto(5,0)};
        Navegador navegador = new Navegador(new Route(pontos));
        assertNotNull(navegador, "navegador criado com sucesso.");
    }
    /**
     * teste 2 construtor
     */
    @Test
    public void test2ConstrutorNavegador () {
        Exception rotaNull = assertThrows(IllegalArgumentException.class, () -> new Navegador(null));
        assertEquals("Navegador:iv", rotaNull.getMessage());
    }
    /**
     * teste 3 construtor
     */
    @Test
    public void test3ConstrutorNavegador () {
        Ponto[] pontos = {new Ponto(-1,-11), new Ponto(-2,-2), new Ponto(5,0)};
        Navegador navegador = new Navegador(new Route(pontos));
        assertNotNull(navegador, "navegador criado com sucesso.");
    }

    /**
     * teste 1 tempoTotalRota
     */
    @Test
    public void test1TempoTotalRota() {
        Ponto[] pontos = {new Ponto(0,0), new Ponto(3,4), new Ponto(6,8)};
        Navegador navegador = new Navegador(new Route(pontos));
        // Se a distância é 10 e a velolcidade linear é 2.0, demora 5 segundos
        assertEquals(5.00, navegador.tempoTotalRota(2.0), 1e-9);
    }

    /**
     * teste 2 tempoTotalRota
     */
    @Test
    public void test2TempoTotalRota(){
        Ponto[] pontos = {new Ponto(0,0), new Ponto(10,0)};
        Navegador navegador = new Navegador(new Route(pontos));
        assertEquals(5.00, navegador.tempoTotalRota(2), 1e-2);
    }

    /**
     * teste 3 tempoTotalRota
     */
    @Test
    public void test3TempoTotalRota() {
        Ponto[] pontos = {new Ponto(-10,0),new Ponto(0,0)};
        Navegador navegador = new Navegador(new Route(pontos));
        assertEquals(5.00, navegador.tempoTotalRota(2),1e-2);
    }

    /**
     * teste 1 pontoDaRotaNoTempoT
     */
    @Test
    public void test1PontoDaRotaNoTempoT() {
        Ponto[] pontos = {new Ponto(0,0), new Ponto(10,0)};
        Navegador navegador = new Navegador(new Route(pontos));
        assertEquals("(10.00,0.00)",navegador.pontoDaRotaNoTempoT(2,5).toString());
    }


    /**
     * teste3 pontoDaRotaNoTempoT
     */
    @Test
    public void test3pontoDaRotaNoTempoT() {
        Ponto[] pontos = {new Ponto(0,0), new Ponto(10,0)};
        Navegador navegador = new Navegador(new Route(pontos));
        assertEquals(new Ponto(10,0), navegador.pontoDaRotaNoTempoT(2, 5));
    }

    /**
     * teste1 vetoresVelocidade
     */
    @Test
    public void test1VetoresVelocidade() {
        Ponto[] pontos = {new Ponto(1,1), new Ponto(4,5)};
        Navegador nav = new Navegador(new Route(pontos));
        Vetor w = new Vetor(1,1); // Vento a empurrar (1,1)

        assertEquals("[2.00,3.00]", nav.vetoresVelocidade(w, 5.0));
    }
    /**
     * teste2 vetoresVelocidade
     */
    @Test
    public void test2VetoresVelocidade() {
        Ponto[] pontos = {new Ponto(5,1), new Ponto(5,5), new Ponto(7,5)}; // Distância = 5
        Navegador navegador = new Navegador(new Route(pontos));
        Vetor w = new Vetor(1,1); // Vento a empurrar (1,1)

        assertEquals("[-1.00,1.00] [1.00,-1.00]", navegador.vetoresVelocidade(w, 2));
    }
    /**
     * teste3 vetoresVelocidade
     * vento contra o veículo
     */
    @Test
    public void test3VetoresVelocidade() {
        //Começa em (1,1) e vai para (11,1). Deslocamento é (10,0).
        Ponto[] pontos = {new Ponto(1,1), new Ponto(11,1)};
        Navegador navegador = new Navegador(new Route(pontos));
        Vetor w = new Vetor(-2,0); // Vento a empurrar para trás

        assertEquals("[7.00,0.00]", navegador.vetoresVelocidade(w, 5.0));
    }

}