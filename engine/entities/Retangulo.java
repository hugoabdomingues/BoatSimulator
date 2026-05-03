package entities;

/**
 * Classe que representa um Retângulo
 * @author Hugo_Domingues
 * @version 23-03-2026
 * @inv Um Retângulo tem exatamente 4 vértices. Os retângulos têm os lados opostos iguais e as diagonais com o mesmo comprimento.
 */
public class Retangulo extends Poligono {

    /**
     * Construtor público da classe retângulo (Usado pelo Cliente)
     * @param vertices vertices que cosntituem o retângulo
     */
    public Retangulo(Ponto[] vertices) {
        // Chama o construtor secreto abaixo, definindo o erro como "entity.Retangulo:iv"
        this(vertices, "entity.Retangulo:iv");
    }

    /**
     * Construtor protegido (Usado pela classe filha entity.Quadrado)
     *
     * @param vertices     vertices que constituem o retângulo/quadrado
     * @param mensagemErro mensagem de erro personalizada a lançar
     */
    protected Retangulo(Ponto[] vertices, String mensagemErro) {
        super(vertices);

        if (vertices.length != 4) {
            throw new IllegalArgumentException(mensagemErro);
        }

        //Os retângulos têm os lados opostos iguais e as diagonais com o mesmo comprimento.
        //verificar se lados opostos são iguais

        //lado 1 e 3
        double comprimentoLado1 = vertices[0].distanciaPontos(vertices[1]);
        double comprimentoLadoOposto1 = vertices[2].distanciaPontos(vertices[3]);
        //lado 2 e 4
        double comprimentoLado2 = vertices[1].distanciaPontos(vertices[2]);
        double comprimentoLadoOposto2 = vertices[3].distanciaPontos(vertices[0]);

        //verificar agora se o comprimento entre as diagonais é igual.
        double diagonal1 = vertices[0].distanciaPontos(vertices[2]);
        double diagonal2 = vertices[1].distanciaPontos(vertices[3]);

        //se o lados opostos têm comprimento diferente OU as diagonais têm comprimento diferente --> não é um retângulo
        if (!areEqual(comprimentoLado1, comprimentoLadoOposto1) || !areEqual(comprimentoLado2, comprimentoLadoOposto2) || !areEqual(diagonal1, diagonal2)) {
            // Lança o erro dinâmico (pode ser entity.Retangulo:iv ou entity.Quadrado:iv)
            throw new IllegalArgumentException(mensagemErro);
        }
    }
}


