package entity;

import geometria.Poligono;
import geometria.Ponto;

import java.util.ArrayList;

/**
 * Classe que representa um obstáculo fixo (entity.Ilha), que é um poligono
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 27-04-2026
 */
public class Ilha extends Obstaculo {

    private Poligono ilha;

    /**
     * Construtor da classe entity.Ilha
     * @param id Nome ou identificador da ilha
     * @param pontos Array de pontos que definem os vértices do polígono da ilha
     * @inv O array de pontos tem que ser maior do que 3 e nao pode ser nulo
     */
    public Ilha(String id, Ponto[] pontos) {
        super(id); //Chamamos o construtor da superclasse entity.Obstaculo

        if (pontos == null || pontos.length < 3) {
            throw new IllegalArgumentException("entity.Ilha:iv");
        }

        this.ilha = new Poligono(pontos);
    }


    /**
     * Getter do polígono que define a forma da ilha.
     * @return Poligono
     */
    public Poligono getIlha() {
        return this.ilha;
    }

    /**
     * Verifica se uma rota colide com a área poligonal desta ilha.
     * @param rota A rota cuja trajetória vai ser testada contra os limites da ilha
     * @return Lista de pontos de interseção entre a rota e os limites da ilha
     */
    @Override
    public ArrayList<Ponto> verificaColisao(Route rota) {
        return this.ilha.intersect(rota);
    }
}