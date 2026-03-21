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
            throw new IllegalArgumentException("SegmentoReta:iv");
        }
    }

    /**
     * Construtor que cria um segmento de reta entre o pontoA e o pontoB,
     * @param pontoA Ponto de origem do segmento de reta
     * @param pontoB Ponto final do segmento de reta
     */
    public SegmentoReta(Ponto pontoA, Ponto pontoB) {
        if (pontoA.getX() == pontoB.getX() && pontoA.getY() == pontoB.getY()) { //invariante da classe
            throw new IllegalArgumentException("SegmentoReta:iv");
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
     * Cáculo do ponto de interseção entre o vetor e o segmento de reta
     * Usamos para este fim o método intersecao da classe vetor
     * @param v vetor v
     * @return retorna o ponto de interseção ou null, caso não haja interseção
     */
    public Ponto intersect(Vetor v) {return v.intersect(this); }


    /**
     * Cálcular o ponto de interseção entre dois segmentos de reta (um deles é o da classe)
     * @param parede segmento de reta que representa onde a rota pode "bater"
     * @return ponto de interseção OU null.
     */
    public Ponto intersectRotas(SegmentoReta parede) {
        // Formato geral: Ax + By = C
        /* A = yB - yA
         *  B = xA - xB
         *  C = A * xA + B * yA
         * */
        // Reta do semento de reta que faz parte da rota:
        double Arota = this.pontoB.getY() - this.pontoA.getY();
        double Brota = this.pontoA.getX() - this.pontoB.getX();
        double Crota = (Arota * this.pontoA.getX()) + (Brota * this.pontoA.getY());

        // Reta do Segmento de Reta:
        double Aparede = parede.getPontoB().getY() - parede.getPontoA().getY();
        double Bparede = parede.getPontoA().getX() - parede.getPontoB().getX();
        double Cparede = (Aparede * parede.getPontoA().getX()) + (Bparede * parede.getPontoA().getY());

        //Cálculo do Determinante... se este for igual a 0 então as retas são paralelas (não se intersetam)
        //se for diferente de 0 então elas intersetam-se  -->> resta saber se intersetam no intervalo compreeendido pelos pontos do vetor e do segmento de reta
        // D = (Arota * Bsegmento) - (Brota * Aparede)
        double determinante = (Arota * Bparede) - (Brota * Aparede);

        if (determinante == 0) {
            return null;
        } else {
            //Cálculamos o X e o Y do ponto em que intersetam
            // X = Dx / D
            // Y = Dy / D

            //Dx = (Crota * Bsegmento) - (Cparede * Brota)
            double Dx = (Crota * Bparede) - (Cparede * Brota);

            //Dy = (Arota * Cparede) - (Crota * Aparede)
            double Dy = (Arota * Cparede) - (Crota * Aparede);

            double coordenadaX = Dx/determinante;
            double coordenadaY = Dy/determinante;

            //verificamos agora se este ponto está compreendido no intervalo.
            Ponto pontoIntersecao = new Ponto(coordenadaX, coordenadaY);

            //verificamos se o coordenadaX e o coordenadaY fazem parte do segmento rota e se fazem parte do segmento de reta parede. Se fizer parte de ambos, então o ponto é um ponto de interseção válido
            //usamos caixas, tal como nos jogos, começando por verificar se o coordenadaX e o coordenadaY cabem na caixa feita pelo segmento de reta ROTA:
            double minXRota = Math.min(this.pontoA.getX(), this.pontoB.getX());
            double maxXRota = Math.max(this.pontoA.getX(), this.pontoB.getX());
            double minYRota = Math.min(this.pontoA.getY(), this.pontoB.getY());
            double maxYRota = Math.max(this.pontoA.getY(), this.pontoB.getY());

            boolean dentroDaCaixaRota;
            if (coordenadaX >= minXRota && coordenadaX <= maxXRota && coordenadaY >= minYRota && coordenadaY <= maxYRota){
                dentroDaCaixaRota = true;
            } else dentroDaCaixaRota = false;

            //agora verificamos se a coordenadaX e a coordenadaY estão dentro da "caixa" criada pelo segmento de reta parede
            double minXParede = Math.min(parede.getPontoA().getX(), parede.getPontoB().getX());
            double maxXParede = Math.max(parede.getPontoA().getX(), parede.getPontoB().getX());
            double minYParede = Math.min(parede.getPontoA().getY(), parede.getPontoB().getY());
            double maxYParede = Math.max(parede.getPontoA().getY(), parede.getPontoB().getY());

            boolean dentroDaCaixaParede;
            if (coordenadaX >= minXParede && coordenadaX <= maxXParede && coordenadaY >= minYParede && coordenadaY <= maxYParede) {
                dentroDaCaixaParede = true;
            } else dentroDaCaixaParede = false;

            if (dentroDaCaixaRota && dentroDaCaixaParede) {
                return pontoIntersecao;
            } else return null;
        }
    }
}


