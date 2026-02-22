/**
 * Classe que representa um vetor no plano.
 * @author Hugo_Domingues
 * @version 21-02-2026
 * @inv modulo() > 0
 */
public class Vetor {
    private Ponto p;

    /**
     * Construtor que cria um vetor a partir de um ponto.
     * @param a Ponto a partir do qual vamos criar um vetor
     */
    public Vetor (Ponto a) {
        if (a.getX() == 0 && a.getY() == 0) { //invariante
            System.out.println("Vetor:iv");
            System.exit(0); // o 0 significa que o programa terminou normalmente. É uma maneira de forçar a paragem do programa
        }

        this.p = a;
    }

    /**
     * Cálcula o módulo do vetor
     * @return O módulo
     */
    public double modulo () {return Math.sqrt(Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2));}

    /**
     * Cálcula o produto interno do vetor com outro vetor (pedido no input)
     * No Construtor (this.x, this.y) definimos as características que são fixas do objeto. O vetor vai ter sempre estas coordenadas X e Y dele.
     * Já nos Parênteses da função definimos os valores que o vetor precisa apenas neste momento para fazer uma conta.
     * @param v2 Segundo vetor inserido.
     * @return resultado do produto interno
     */
    public double produtoInterno (Vetor v2) {
        return this.p.getX() * v2.getX() + this.getY() * v2.getY(); //multiplicamos o ponto do construtor (primeiro scan) com o segundo ponto que vem do scan diretamente para o argumento da função
    }

    /**
     * Cálcula o Cossine similarity entre o vetor atual da classe com outro vetor
     * @param v2 segundo vetor pedido no input
     * @return O resultado da fórmula
     */
    public double cossineSimilarity (Vetor v2) {
        return produtoInterno(v2) / (this.modulo() * v2.modulo());
    }

    /**
     * Assessor/getter da coordenada x do vetor
     * @return O x do vetor
     */
    public double getX() {return this.p.getX();}

    /**
     * Assessor/getter da coordenada y do vetor
     * @return O y do vetor
     */
    public double getY() {return this.p.getY();}

    /**
     * Assessor/getter do ponto que forma o vetor
     * @return O ponto
     */
    public Ponto getPonto()
    {
        return this.p;
    }
}
