package test;

import entity.Route;
import geometria.Ponto;
import geometria.SegmentoReta;
import org.junit.jupiter.api.Test;
import entity.Mapa;
import entity.Porto;

import static org.junit.Assert.assertEquals;

/**
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 */
public class CaminhoCurtoTest {
    @Test
    public void TestCaminhoCurto() {
        //Criacao do mapa para testar
        Mapa mapa = Mapa.getInstancia();
        Porto start = new Porto("A", new Ponto(1, 1));
        Porto destino = new Porto("B", new Ponto(11, 1));
        Porto obstaculo = new Porto("C", new Ponto(1,9));
        Porto obstaculo1 = new Porto("C", new Ponto(11,9));

        Ponto[] pontos1 = new Ponto[6];
        Ponto[] pontos2 = new Ponto[4];
        Ponto[] pontos3 = new Ponto[4];
        Ponto[] pontos4 = new Ponto[3];

        pontos1[0] = new Ponto(1,1);
        pontos1[1] = new Ponto(3,4);
        pontos1[2] = new Ponto(1,5);
        pontos1[3] = new Ponto(3,6);
        pontos1[4] = new Ponto(1,7);
        pontos1[5] = new Ponto(1,9);

        pontos2[0] = new Ponto(4,2);
        pontos2[1] = new Ponto(1,3);
        pontos2[2] = new Ponto(2,4);
        pontos2[3] = new Ponto(1,7);

        pontos3[0] = new Ponto(1,1);
        pontos3[1] = new Ponto(4,1);
        pontos3[2] = new Ponto(4,3);
        pontos3[3] = new Ponto(11,1);

        pontos4[0] = new Ponto(11,1);
        pontos4[1] = new Ponto(11,4);
        pontos4[2] = new Ponto(1,1);

        Route rota = new Route(pontos1);
        Route rota1 = new Route(pontos2);
        Route rota2 = new Route(pontos3);
        Route rota3 = new Route(pontos4);

        mapa.addRota(rota);
        mapa.addRota(rota1);
        mapa.addRota(rota2);
        mapa.addRota(rota3);

        mapa.addPorto(start);
        mapa.addPorto(destino);
        mapa.addPorto(obstaculo);
        mapa.addPorto(obstaculo1);

        Route resultado = mapa.calcularRotaDijkstra(destino.getLocalizacao(),obstaculo.getLocalizacao());

        Ponto[] resultado_pontos = resultado.getPontos();
        SegmentoReta[] resultado_sr = new SegmentoReta[resultado_pontos.length];
        for(int i = 1; i < resultado_sr.length-1;i++){
            resultado_sr[i] = new SegmentoReta(resultado_pontos[i-1],resultado_pontos[i]);
            System.out.println(resultado_sr[i].toString()+"\n");

        }


    }
}
