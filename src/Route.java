import java.util.ArrayList;

/**
 * Classe que representa a rota
 * @author Hugo_Domingues
 * @version 19-03-2026
 * @inv uma rota tem pelo menos 2 pontos
 */
public class Route {
    private Ponto[] pontosRota;

    /**
     * Construtor da Rota
     * @param pontos array com os pontos que formam a rota
     */
    public Route (Ponto[] pontos) {

        if (pontos.length < 2) {
            throw new IllegalArgumentException("Rota:iv");
        }

        this.pontosRota = pontos;
    }

    /**
     * cálcula o comprimento da rota na sua totalidade. Ou seja, a distância entre todos os pontos que cosntituem a rota.
     * @return o comprimento da rota
     */
    public double comprimentoTotal () {
        double comprimento = 0;
        for (int i = 0; i < this.pontosRota.length-1; i++) {
            comprimento = comprimento + this.pontosRota[i].distanciaPontos(this.pontosRota[i + 1]);
        }
        return comprimento;
    }

    /**
     * Encontrar pontos de interseção entre a rota e o segmento de reta parede
     * @param parede segmento de reta que pode intersetar a nossa rota
     * @return arraylist com os pontos que intersetam o segmento de reta OU um arraylist vazio.
     */
    public ArrayList<Ponto> intersecao (SegmentoReta parede) {
        //array para armazenar os pontos de interseção entre os dois segmentos de reta testados (no caso em que estes se cruzam)
        ArrayList<Ponto> pontosintersecao = new ArrayList<>();
        for (int i = 0; i < this.pontosRota.length-1; i++) {
            //criamos o segmento de reta que constitui parte da rota (dois a distância entre dois pontos de cada vez) e que vamos verificar se este se cruza com o segmento de reta "parede" mais à frente..
                SegmentoReta parteDaRota = new SegmentoReta(this.pontosRota[i], this.pontosRota[i+1]);
                Ponto pontoTestado = parteDaRota.intersectRotas(parede);
                if (pontoTestado != null) {
                    pontosintersecao.add(pontoTestado);
                }
        }
         return pontosintersecao;
    }

    /**
     * getter/assessor dos pontos que constituem a rota
     * @return array com todos os pontos da rota
     */
    public Ponto[] getPontos(){
        return this.pontosRota;
    }

    /**
     * cálcular tempo que leva a percorrer a totalidade da rota
     * @param vl velocidade linear dada pelo input
     * @return tempo que leva a percorrer a rota
     */
    public double tempoTotalRota(double vl) {
        // t = distancia / velocidade
        return comprimentoTotal()/vl;
    }

    /**
     *
     * Dado um tempo t, temos de cálcular onde vai estar o avião quando no tempo t
     * @param vl velocidade produzida pela força dos motores
     * @param t tempo dado
     * @return ponto em que o avião se encontra no momento t.
     */
    public Ponto pontoDaRotaNoTempoT(double vl, double t) {
        // distancia = v * t
        double distanciaRestante = vl * t;
        for (int i = 0; i < this.pontosRota.length-1; i++) {

            double distanciaSegmento = this.pontosRota[i].distanciaPontos(this.pontosRota[i+1]);
            if (Math.abs(distanciaRestante - distanciaSegmento) < 1e-9) { //se forem iguais encontrámos o ponto
                return this.pontosRota[i+1];
            } else if (distanciaRestante < distanciaSegmento) {
                //percentagem do segmento que temos de percorrer para encontrar o ponto
                double percentagemDistanciaPorPercorrer = distanciaRestante / distanciaSegmento;

                //vetor AB = B - A -->d istancia do segmento atual
                Vetor AB = new Vetor(this.pontosRota[i+1]).sub(new Vetor(this.pontosRota[i]));

                //multiplicamos pela percentagem da distância que falta percorrer e temos a distância de A até o Ponto pontoDaRotaNoTempoT
                Vetor AatePontoT = AB.mult(percentagemDistanciaPorPercorrer);

                //finalmente usamos a função move() para irmos do ponto A até ao ponto pontoDaRotaNoTempoT
                return this.pontosRota[i].move(AatePontoT);

            } else {
                //caso a distância restante seja maior que a distância do segmento, subtraímos a distância do segmento à restante e o loop continua
                distanciaRestante = distanciaRestante - distanciaSegmento;
            }
        }
        return null;
    }


    public String vetoresVelocidade (Vetor w, double vl) {

        String vetoresV = "";
        for (int i = 0; i < this.pontosRota.length-1; i++) {
            AutoPilot autoPilot = new AutoPilot(this.pontosRota[i],this.pontosRota[i+1]);
            double tempoDeAB = autoPilot.time(vl);

            Vetor vetorVelocidade = autoPilot.speed(w,tempoDeAB);

            vetoresV = vetoresV + vetorVelocidade.toString();

        }
        return vetoresV;
    }



}

