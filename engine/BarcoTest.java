import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;



public class BarcoTest {
    @Test
    public void TestConstrutor(){
        //Caso 1: tudos os atributos estao certos
        Barco p = new Barco("A12",2,'C',12,new Navegador(0,0),false,new Ponto(0, 0));

        //Caso 2: O atributo código está mal formatado
        Barco p1 = new Barco("12A",2,'C',12,new Navegador(0,0),false,new Ponto(0, 0));
        //Neste caso o objeto barco nao e criado pois o seu codigo esta mal

        //Caso 3: O atributo vl não pode ser nulo ou negativo
        Barco p2 = new Barco("B12",-1,'C',12,new Navegador(0,0),false,new Ponto(0, 0));
        //Neste caso o objeto barco nao e criado pois o seu codigo esta mal
    }

    @Test
    public void TestGetCodigo(){
        String codigo = "A12";
        Ponto localizacao = new Ponto(0, 0);
        Barco p = new Barco(codigo,2,'C',12,new Navegador(0,0),false,localizacao);

        assertEquals("A12", p.getCodigo());

    }

    @Test
    public void TestGetVl(){
        Ponto localizacao = new Ponto(0, 0);
        Barco p = new Barco("A12",2,'C',12,new Navegador(0,0),false,localizacao);
        assertEquals(2,p.getVl());
    }

    @Test
    public void TestPosicaoAtual(){
        Ponto localizacao = new Ponto(0, 0);
        Barco p = new Barco('A12',2,'C',12,new Navegador(0,0),false,localizacao);
        assertEquals(localizacao, p.getPosicao_atual());
    }



    @Test
    public void estIntersetarBarco(){
        //Caso 1: Se dois barcos intersetarem-se
        //O barco com o codigo lexicograficamente maior avança e o Barco menor pára (usando o metodo parar())

        //Caso 2: Se dois barcos não se intersetarem-se
        //Nada acontece, ambos os barcos continuam a andar

        //Caso 3: O barco parado já não interseta com o barco maior
        //O barco parado retoma a viagem (usando o metodo retomar())

    }


}
