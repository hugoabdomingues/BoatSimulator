import java.util.ArrayList;

/**
 * Classe que representa um obstáculo móvel (Tempestade) com área circular.
 * Interseta rotas, mas permanece fixa após a simulação iniciar. Muda de posição apenas de simulação em simulação
 * @author
 * @version 27-04-2026
 * @inv forma != null
 */
public class Tempestade extends Obstaculo {

    private Circulo forma;

    /**
     * Construtor da classe Tempestade
     * @param id Nome ou identificador da tempestade
     * @param centro Ponto central de origem da tempestade
     * @param raio Raio de alcance da tempestade
     */
    public Tempestade(String id, Ponto centro, double raio) {
        super(id); // Chama o construtor da superclasse Obstaculo

        if (centro == null || raio <= 0) {
            throw new IllegalArgumentException("Tempestade: centro ou raio inválidos.");
        }

     //cria o seu próprio círculo de área
        this.forma = new Circulo(centro, raio);
    }

    /**
     * Verifica a colisão da rota com a área circular da tempestade
     * @param rota Rota a verificar
     * @return Lista de pontos de interseção entre a rota e o círculo
     */
    @Override
    public ArrayList<Ponto> verificaColisao(Route rota) {
        return new ArrayList<>(); //return vazio
    }
}