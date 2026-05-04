package entity;

import geometria.Ponto;
import geometria.SegmentoReta;

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
        ArrayList<Ponto> pontosIntersecao = new ArrayList<>();
        for (int i = 0; i < this.pontosRota.length-1; i++) {
            //criamos o segmento de reta que constitui parte da rota (dois a distância entre dois pontos de cada vez) e que vamos verificar se este se cruza com o segmento de reta "parede" mais à frente..
                SegmentoReta parteDaRota = new SegmentoReta(this.pontosRota[i], this.pontosRota[i+1]);
                Ponto pontoTestado = parteDaRota.intersectRotas(parede);
                if (pontoTestado != null) {
                    pontosIntersecao.add(pontoTestado);
                }
        }
         return pontosIntersecao;
    }

    /**
     * getter/assessor dos pontos que constituem a rota
     * @return array com todos os pontos da rota
     */
    public Ponto[] getPontos(){
        return this.pontosRota;
    }

}

