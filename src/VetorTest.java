import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VetorTest {


    @Test
    public void testAdd (){

        Vetor vetor1 = new Vetor(new Ponto(2, -2));
        Vetor vetor2 = new Vetor(new Ponto(1, 3));

        assertEquals("[3.00,1.00]", vetor1.add(vetor2).toString());

    }



}