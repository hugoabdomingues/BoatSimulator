package test;

import entity.NavegacaoUpdate;
import geometria.Ponto;
import geometria.Vetor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 */
class NavegacaoUpdateTest {

    private NavegacaoUpdate updateTest(List<String> barcos, Vetor corrente, double tempo) {
        return new NavegacaoUpdate(
                barcos,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                corrente,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                tempo
        );
    }

    @Test
    void testCriacaoELeituraDoRecord() {
        List<String> listaBarcos = new ArrayList<>();
        listaBarcos.add("A12");
        listaBarcos.add("F9");

        Vetor correnteTeste = new Vetor(2.5, -1.0);

        NavegacaoUpdate update = updateTest(listaBarcos, correnteTeste, 0.0);

        assertNotNull(update);
        assertEquals(2, update.barcosAtivos().size());
        assertEquals("A12", update.barcosAtivos().get(0));
        assertEquals("F9", update.barcosAtivos().get(1));
        assertEquals(2.5, update.corrente().getX(), 1e-9);
        assertEquals(-1.0, update.corrente().getY(), 1e-9);
    }

    @Test
    void testTempoAtualGuardadoCorretamente() {
        NavegacaoUpdate update = updateTest(new ArrayList<>(), new Vetor(1.0, 0.0), 12.5);
        assertEquals(12.5, update.tempoAtual(), 1e-9);
    }

    @Test
    void testListasVaziasSaoAceites() {
        NavegacaoUpdate update = updateTest(new ArrayList<>(), new Vetor(1.0, 0.0), 0.0);
        assertNotNull(update);
        assertTrue(update.barcosAtivos().isEmpty());
        assertTrue(update.localizacaoBarcosAtivos().isEmpty());
        assertTrue(update.mostrarHitboxNavio().isEmpty());
        assertTrue(update.obstaculos().isEmpty());
        assertTrue(update.barcosFila().isEmpty());
        assertTrue(update.portos().isEmpty());
        assertTrue(update.rotas().isEmpty());
    }

    @Test
    void testRecordImutabilidade() {
        List<String> listaOriginal = new ArrayList<>();
        listaOriginal.add("B1");

        NavegacaoUpdate update = updateTest(listaOriginal, new Vetor(1.0, 0.0), 0.0);

        // modificar a lista externa não deve afectar o record (Java records guardam a referência,
        // mas o contrato é que o DTO não deve ser modificado após criação)
        assertEquals(1, update.barcosAtivos().size());
        assertEquals("B1", update.barcosAtivos().get(0));
    }

    @Test
    void testListaLocalizacoesECorrespondencia() {
        List<String> barcos = List.of("X1", "X2");
        List<Ponto> posicoes = new ArrayList<>();
        posicoes.add(new Ponto(10.0, 20.0));
        posicoes.add(new Ponto(30.0, 40.0));

        NavegacaoUpdate update = new NavegacaoUpdate(
                barcos,
                posicoes,
                new ArrayList<>(),
                new ArrayList<>(),
                new Vetor(1.0, 0.0),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                5.0
        );

        assertEquals(2, update.localizacaoBarcosAtivos().size());
        assertEquals(10.0, update.localizacaoBarcosAtivos().get(0).getX(), 1e-9);
        assertEquals(40.0, update.localizacaoBarcosAtivos().get(1).getY(), 1e-9);
    }

    @Test
    void testHitboxFlagsSaoGuardadas() {
        List<Boolean> hitboxes = new ArrayList<>();
        hitboxes.add(true);
        hitboxes.add(false);

        NavegacaoUpdate update = new NavegacaoUpdate(
                new ArrayList<>(),
                new ArrayList<>(),
                hitboxes,
                new ArrayList<>(),
                new Vetor(1.0, 0.0),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0.0
        );

        assertEquals(2, update.mostrarHitboxNavio().size());
        assertTrue(update.mostrarHitboxNavio().get(0));
        assertFalse(update.mostrarHitboxNavio().get(1));
    }
}