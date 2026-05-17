package entity;

import geometria.Ponto;
import geometria.Vetor;

/**
 * Classe que representa o navegador
 * Responsável pela navegação pela rota em tempo real
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 05-04-2026
 */
public class Navegador {
    private Route rota;

    /**
     * Construtor do Navegador
     * @param rota Route
     * @inv a rota tem de ser != de null
     */
    public Navegador (Route rota) {
        if (rota == null){
            throw new IllegalArgumentException("entity.Navegador:iv");
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
     * Dado um tempo t, calcula onde o barco se encontra, compensando a corrente para manter a velocidade linear constante.
     * @param vl double, velocidade linear que deve ser mantida na rota
     * @param t double, tempo dado
     * @param corrente Vetor, velocidade da corrente exercida no barco
     * @return ponto em que o barco se encontra no momento t
     */
    public Ponto pontoDaRotaNoTempoT(double vl, double t, Vetor corrente) {

        Ponto[] pontosRota = this.rota.getPontos();
        double distanciaRestante = vl * t;

        for (int i = 0; i < pontosRota.length-1; i++) {
            Ponto pontoA = pontosRota[i];
            Ponto pontoB = pontosRota[i+1];


            if (distanciaRestante < 1e-9) {
                return pontoA;
            }

            double distanciaSegmento = pontoA.distanciaPontos(pontoB);
            if (Math.abs(distanciaRestante - distanciaSegmento) < 1e-9) {
                return pontoB;
            } else if (distanciaRestante < distanciaSegmento) {
                double percentagemDistanciaPorPercorrer = distanciaRestante / distanciaSegmento;
                return pontoSegmentoCorrente(pontoA, pontoB, percentagemDistanciaPorPercorrer, vl, corrente);
            } else {
                distanciaRestante = distanciaRestante - distanciaSegmento;
            }
        }
        return null;
    }
    /**
     * Calcula a posição no segmento usando uma velocidade vetorial que compensa a corrente.
     * @param pontoA Ponto
     * @param pontoB Ponto
     * @param percentDistPercorrer double
     * @param vl double
     * @param corrente Vetor
     * @return Ponto
     */
    private Ponto pontoSegmentoCorrente(Ponto pontoA, Ponto pontoB, double percentDistPercorrer, double vl, Vetor corrente) {
        Vetor deslocamentoSegmento = new Vetor(pontoB).sub(new Vetor(pontoA));

        if (corrente == null) {
            Vetor AtePontoT = deslocamentoSegmento.mult(percentDistPercorrer);
            return pontoA.move(AtePontoT);
        }

        AutoPilot piloto = new AutoPilot(pontoA, pontoB);
        double tempoDeAB = piloto.time(vl);
        Vetor velocidadeReal = piloto.speed(corrente, tempoDeAB).add(corrente);
        Vetor AtePontoT = velocidadeReal.mult(tempoDeAB * percentDistPercorrer);

        return pontoA.move(AtePontoT);
    }


    /**
     * lista de vetores velocidade do avião v, no formato [x,y]
     * @param corrente velocidade da corrente exercida no barco
     * @param vl velocidade linear (velocidade gerada pelos motores do veículo)
     * @return vetores velocidade
     */
    public String vetoresVelocidade (Vetor corrente, double vl) {
        Ponto[] pontosRota = this.rota.getPontos();

        String vetoresV = "";
        for (int i = 0; i < pontosRota.length-1; i++) {
            AutoPilot autoPilot = new AutoPilot(pontosRota[i], pontosRota[i+1]);
            double tempoDeAB = autoPilot.time(vl);

            Vetor vetorVelocidade = autoPilot.speed(corrente,tempoDeAB);

            vetoresV = vetoresV + vetorVelocidade.toString() + " ";

        }
        //retiramos o espaço a mais no fim
        return vetoresV.trim();
    }

}
