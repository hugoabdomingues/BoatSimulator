/**
 * Classe que representa o autopiloto
 * @author Hugo_Domingues
 * @version 21-02-2026
 * @inv pontoA não pode ser igual ao pontoB
 */
public class AutoPilot {

    private Ponto pontoA;
    private Ponto pontoB;
    private SegmentoReta sr;

    /**
     * Construtor da classe AutoPilot
     * @param a ponto de partida do avião
     * @param b ponto de chegada do avião
     */
    public AutoPilot (Ponto a, Ponto b) {
        this.pontoA = a;
        this.pontoB = b;
        this.sr = new SegmentoReta(a,b);
    }

    /**
     * Cálcula o tempo necessário para um avião se deslocar do ponto A ao ponto B
     * @param windSpeed velocidade do vento
     * @param linearSpeed velocidade do avião
     * @return o tempo que demora a viagem do ponto A ao ponto B
     */
    public double time(Vetor windSpeed, double linearSpeed) {

        //deslocamento de A a B = B-A
        Vetor deslocamento = new Vetor(pontoB).sub(new Vetor(pontoA));



    }

    public Vetor speed (Vetor windSpeed, double time) {

    }



}
