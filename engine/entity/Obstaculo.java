package entity;

import geometria.Ponto;

import java.util.ArrayList;

/**
 * Classe abstrata que representa um obstáculo genérico no mapa.
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 27-04-2026
 */
public abstract class Obstaculo {

    private String id;

    /**
     * Construtor da classe entity.Obstaculo
     * @param id String,  ID único/ nome do obstáculo
     * @inv id != null && não vazio
     */
    public Obstaculo(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("entity.Obstaculo:iv");
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