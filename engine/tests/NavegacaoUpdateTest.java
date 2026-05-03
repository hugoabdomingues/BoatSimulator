package tests;

import entities.NavegacaoUpdate;
import entities.Vetor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//Mudei coisas aqui pois tinha erro ass:Renato
class NavegacaoUpdateTest {
    @Test
    public void testCriacaoELeituraDoRecord() {
        List<String> listaBarcos = new ArrayList<>();
        listaBarcos.add("A12");
        listaBarcos.add("F9");

        Vetor correnteTeste = new Vetor(2.5, -1.0);

        NavegacaoUpdate update = new NavegacaoUpdate(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), correnteTeste, new ArrayList<>());

        assertNotNull(update);
        Assertions.assertEquals(2, update.barcosAtivos().size());
        Assertions.assertEquals("A12", update.barcosAtivos().get(0));
        Assertions.assertEquals(2.5, update.corrente().getX(), 1e-9);
        Assertions.assertEquals(-1.0, update.corrente().getY(), 1e-9);
    }

}