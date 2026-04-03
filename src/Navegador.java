/**
 * Classe que representa o navegador
 * Responsável pela navegação pela rota em tempo real
 * @author Hugo_Domingues
 * @version 03-04-2026
 * @inv a rota tem de ser != de null
 */
public class Navegador {

    private Route rota;

    public Navegador (Route rota) {
        if (rota == null){
            throw new IllegalArgumentException("Navegador:iv");
        }
        this.rota = rota;
    }


    /**
     * cálcular tempo que leva a percorrer a totalidade da rota
     * @param vl velocidade linear dada pelo input
     * @return tempo que leva a percorrer a rota
     */
    public double tempoTotalRota(double vl) {
        // t = distancia / velocidade
        return rota.comprimentoTotal()/vl;
    }

    /**
     *
     * Dado um tempo t, temos de cálcular onde vai estar o avião quando no tempo t
     * @param vl velocidade produzida pela força dos motores
     * @param t tempo dado
     * @return ponto em que o avião se encontra no momento t.
     */
    public Ponto pontoDaRotaNoTempoT(double vl, double t) {
        Ponto[] pontosRota = this.rota.getPontos();
        // distancia = v * t
        double distanciaRestante = vl * t;
        for (int i = 0; i < pontosRota.length-1; i++) {

            double distanciaSegmento = pontosRota[i].distanciaPontos(pontosRota[i+1]);
            if (Math.abs(distanciaRestante - distanciaSegmento) < 1e-9) { //se forem iguais encontrámos o ponto
                return pontosRota[i+1];
            } else if (distanciaRestante < distanciaSegmento) {
                //percentagem do segmento que temos de percorrer para encontrar o ponto
                double percentagemDistanciaPorPercorrer = distanciaRestante / distanciaSegmento;

                //vetor AB = B - A -->distancia do segmento atual
                Vetor AB = new Vetor(pontosRota[i+1]).sub(new Vetor(pontosRota[i]));

                //multiplicamos pela percentagem da distância que falta percorrer e temos a distância de A até o Ponto pontoDaRotaNoTempoT
                Vetor AatePontoT = AB.mult(percentagemDistanciaPorPercorrer);

                //finalmente usamos a função move() para irmos do ponto A até ao ponto pontoDaRotaNoTempoT
                return pontosRota[i].move(AatePontoT);

            } else {
                //caso a distância restante seja maior que a distância do segmento, subtraímos a distância do segmento à restante e o loop continua
                distanciaRestante = distanciaRestante - distanciaSegmento;
            }
        }
        return null;
    }


    public String vetoresVelocidade (Vetor w, double vl) {
        Ponto[] pontosRota = this.rota.getPontos();

        String vetoresV = "";
        for (int i = 0; i < pontosRota.length-1; i++) {
            AutoPilot autoPilot = new AutoPilot(pontosRota[i], pontosRota[i+1]);
            double tempoDeAB = autoPilot.time(vl);

            Vetor vetorVelocidade = autoPilot.speed(w,tempoDeAB);

            vetoresV = vetoresV + vetorVelocidade.toString();

        }
        return vetoresV;
    }

}
