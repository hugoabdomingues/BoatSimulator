import java.util.ArrayList;

/**
 * Classe abstrata que representa um obstáculo genérico no mapa.
 * @author Hugo Domingues, Ana Sofia, Renato Chumbinho
 * @version 27-04-2026
 * @inv id != null && não vazio
 */
public abstract class Obstaculo {

    private String id;

    /**
     * Construtor da classe Obstaculo
     * @param id ID único/ nome do obstáculo
     */
    public Obstaculo(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Obstaculo:iv");
        }
        this.id = id;
    }

    /**
     * Getter: Devolve o ID do obstáculo
     * @return String com o id do obstáculo
     */
    public String getId() {
        return this.id;
    }

    /**
     * Verifica se uma dada rota colide com este obstáculo.
     * @param rota A rota cuja trajetória vai ser testada
     * @return Lista de Pontos onde ocorre a interseção ouu lista vazia se não houver colisão
     */
    public abstract ArrayList<Ponto> verificaColisao(Route rota);
}