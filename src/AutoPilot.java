/**
 * Classe que representa o autopiloto
 * @author Hugo_Domingues
 * @version 08-03-2026
 * @inv pontoA não pode ser igual ao pontoB
 */
public class AutoPilot {

    private Ponto pontoA;
    private Ponto pontoB;

    /**
     * Construtor da classe AutoPilot
     * @param a ponto de partida do avião
     * @param b ponto de chegada do avião
     */
    public AutoPilot (Ponto a, Ponto b) {
        if(a.equals(b)) {
            throw new IllegalArgumentException("SegmentoReta:iv");
        }
        this.pontoA = a;
        this.pontoB = b;
    }

    /**
     * Cálcula o tempo necessário para um avião se deslocar do ponto A ao ponto B
     * @param windSpeed deslocamento do avião executado pelo vento
     * @param linearSpeed velocidade do avião passada pelo main
     * @return o tempo que demora a viagem do ponto A ao ponto B
     * @see "apontamentos de Física I"
     */
    public double time(Vetor windSpeed, double linearSpeed) {
        //deslocamento de A a B = B-A
        Vetor deslocamento = new Vetor(pontoB).sub(new Vetor(pontoA));

        // vetor que representa o deslocamento do avião usando os motores (o resto do deslocamento é o vetor windspeed que faz)
        //para mais informação verificar gráficos feitos no caderno
        Vetor vt = deslocamento.sub(windSpeed);
        //t = distância / velocidade
        // distância = módulo de vt
        return vt.modulo()/linearSpeed;

    }

    /**
     * cálcula o vetor velocidade
     * @param windSpeed deslocamento do avião executado pelo vento
     * @param time tempo calculado no metodo time.
     * @return vetor velocidade do avião
     */
    public Vetor speed (Vetor windSpeed, double time) {

        //deslocamento de A a B = B-A
        Vetor deslocamento = new Vetor(pontoB).sub(new Vetor(pontoA));
        // v = (r - w)/t
        return (deslocamento.sub(windSpeed)).mult(1/time);
    }



}
