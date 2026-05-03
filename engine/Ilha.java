import java.util.ArrayList;

/**
 * Classe que representa um obstáculo fixo (Ilha), que é um poligono
 * @author
 * @version 27-04-2026
 * @inv forma != null
 */
public class Ilha extends Obstaculo {

    private Poligono forma;

    /**
     * Construtor da classe Ilha
     * @param id Nome ou identificador da ilha
     * @param pontos Array de pontos que definem os vértices do polígono da ilha
     */
    public Ilha(String id, Ponto[] pontos) {
        super(id); //Chamamos o construtor da superclasse Obstaculo

        if (pontos == null || pontos.length < 3) {
            throw new IllegalArgumentException("Ilha:iv");
        }

        this.forma = new Poligono(pontos);
    }


    @Override
    public ArrayList<Ponto> verificaColisao(Route rota) {
        return this.forma.intersect(rota);
    }
}