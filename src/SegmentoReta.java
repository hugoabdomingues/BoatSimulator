import java.awt.*;

/**
 * Classe que representa um segmento de reta
 * @author Hugo_Domingues
 * @version 21-02-2026
 * @inv O Segmento não pode ter os dois extremos no mesmo ponto.
 */
public class SegmentoReta {

    private Ponto pontoA;
    private Ponto pontoB;

    /**
     * Construtor que cria um segmento de reta entre o pontoA e o pontoB,
     * sendo que o pontoB é cálculado através do método move da classe vetor
     * @param a Ponto de origem do segmento de reta
     * @param b Vetor que nos vai levar ao ponto B
     */
    public SegmentoReta(Ponto a, Vetor b) { //ponto B vai ser A + vetorB
        this.pontoA = a;
        this.pontoB = this.pontoA.move(b);
        if (pontoA.getX() == pontoB.getX() && pontoA.getY() == pontoB.getY()) { //invariante da classe
            System.out.println("SegmentoReta:iv");
            System.exit(0);
        }
    }

    /**
     * Construtor que cria um segmento de reta entre o pontoA e o pontoB,
     * @param pontoA Ponto de origem do segmento de reta
     * @param pontoB Ponto final do segmento de reta
     */
    public SegmentoReta(Ponto pontoA, Ponto pontoB) {
        if (pontoA.getX() == pontoB.getX() && pontoA.getY() == pontoB.getY()) { //invariante da classe
            System.out.println("SegmentoReta:iv");
            System.exit(0);
        }
        this.pontoA = pontoA;
        this.pontoB = pontoB;
    }

    /**
     * Serve para converter o segmento de reta para String no formato: sr((x1,y1); (x2,y2))
     * 1º Verifica qual o ponto mais próximo da origem através do cálculo do módulo, visto que este parte da origem.
     * Aquele que tiver o módulo menor, é o mais próximo da origem
     * Logo, é o primeiro ponto a ser "printed"
     * @return
     */
    public String toString() {

        // Assumindo que o A é sempre o que tem menor módulo

        Vetor vA = new Vetor(this.pontoA);
        Vetor vB = new Vetor(this.pontoB);

        if (vB.modulo() < vA.modulo())
        {
            Vetor temp = vA;
            vA = vB;
            vB = temp;
        }

        return String.format("sr(%s; %s)", vA.getPonto(), vB.getPonto());}

    /**
     * Assessor/getter do pontoA do segmento de reta
     * @return pontoA
     */
    public Ponto getPontoA() {return this.pontoA;}

    /**
     * Assessor/getter do pontoB do segmento de reta
     * @return PontoB
     */
    public Ponto getPontoB() {return this.pontoB;}

    /**
     * Cálculo
     * @param v
     * @return
     */
    public Ponto intersect(Vetor v) {return v.intersect(this); }
}


