/**
 * Classe que representa um Quadrado
 * @author Hugo_Domingues
 * @version 21-03-2026
 * @inv Um Quadrado tem exatamente 4 vértices. Os quadrados têm todos os lados iguais E as diagonais com o mesmo comprimento.
 */
public class Quadrado extends Retangulo {
    /**
     * Construtor da classe Quadrado
     * @param vertices vertices que constituem o quadrado
     * Como o retangulo já verifica se lados opostos são iguais e se as diagonais sao iguais, resta-nos apenas verificar se lados nao opostos sao iguais.
     */
    public Quadrado (Ponto[] vertices) {
        super(vertices);
        //lado 1
        double comprimentoLado1 = vertices[0].distanciaPontos(vertices[1]);
        //lado adjacente
        double comprimentoLado2 = vertices[1].distanciaPontos(vertices[2]);
        //chamamos a função areEqual da classe Retangulo para verificar se os lados sao iguais.
        if (!areEqual(comprimentoLado1, comprimentoLado2)) {
            throw new IllegalArgumentException("Quadrado:iv");
        }
    }
}
