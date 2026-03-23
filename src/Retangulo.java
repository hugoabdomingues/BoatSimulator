/**
 * Classe que representa um Retângulo
 * @author Hugo_Domingues
 * @version 21-03-2026
 * @inv Um Retângulo tem exatamente 4 vértices. Os retângulos têm os lados opostos iguais e as diagonais com o mesmo comprimento.
 */
public class Retangulo extends Poligono{

    /**
     * Construtor da classe retângulo
     * @param vertices vertices que cosntituem o retângulo
     */
    public Retangulo(Ponto[] vertices) {
        super(vertices);
        if (vertices.length != 4) {
            throw new IllegalArgumentException("Retangulo:iv");
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
        if (!areEqual(comprimentoLado1, comprimentoLadoOposto1) || !areEqual(comprimentoLado2, comprimentoLadoOposto2) || !areEqual(diagonal1,diagonal2)) { //se lado-lado >= 0 --> não são iguais
            throw new IllegalArgumentException("Retangulo:iv");
        }

    }

    /**
     * calcular se dois comprimentos são inguais
     * @param a comprimento 1
     * @param b comprimento 2
     * @return true se forem iguais, false se não forem iguais
     */
    public boolean areEqual (double a, double b) {
        return Math.abs(a - b) < 1e-9;
    }

}
