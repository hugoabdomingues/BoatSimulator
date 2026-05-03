/**
 * Classe que representa o autopiloto
 * @author Hugo_Domingues
 * @version 25-03-2026
 * @inv pontoA não pode ser igual ao pontoB
 */
public class AutoPilot {

    private Ponto pontoA;
    private Ponto pontoB;
    private Vetor deslocamento;

    /**
     * Construtor da classe AutoPilot
     * @param a ponto de partida do avião
     * @param b ponto de chegada do avião
     */
    public AutoPilot (Ponto a, Ponto b) {
        if(a.equals(b)) {
            throw new IllegalArgumentException("AutoPilot:iv");
        }
        this.pontoA = a;
        this.pontoB = b;
        //deslocamento de A a B = B-A
        this.deslocamento = new Vetor(pontoB).sub(new Vetor(pontoA));
    }

    /**
     * Cálcula o tempo necessário para um avião se deslocar do ponto A ao ponto B
     * @param vl velocidade linear, calculada através da função speed
     * @return o tempo que demora a viagem do ponto A ao ponto B
     * @see "apontamentos de Física I"
     */
    public double time(double vl) {
        // t = r / v
        return this.deslocamento.modulo()/vl;
    }

    /**
     * cálcula o vetor velocidade
     * @param corrente deslocamento do barco executado pela corrente
     * @param t tempo calculado no metodo time.
     * @return vetor velocidade do avião
     */
    public Vetor speed (Vetor corrente, double t) {
        // v = (r / t) - corrente              (fórmula retirada do enunciado)
        return this.deslocamento.mult(1/t).sub(corrente);
    }



}
