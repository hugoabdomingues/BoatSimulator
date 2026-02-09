//@author - Hugo Domingues, 86128
public class Vetor {
    private double x, y;

    public Vetor (double x, double y) {
        this.x = x;
        this.y = y;
        if (x == 0 && y == 0) {
            System.out.println("iv");
            System.exit(0); // o 0 significa que o programa terminou normalmente. É uma maneira de forçar a paragem do programa
        }
    }

    public double modulo (double x, double y) {return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));}

    /**
     * No Construtor (this.x, this.y) definimos as características que são fixas do objeto. O vetor vai ter sempre estas coordenadas X e Y dele.
     * Já nos Parênteses da função definimos os valores que o vetor precisa apenas neste momento para fazer uma conta.
     * @param v2 Segundo vetor inserido.
     * @return resultado do produto interno
     */
    public double produtoInterno (Vetor v2) {
        return this.x * v2.x + this.y * v2.y; //multiplicamos o ponto do construtor (primeiro scan) com o segundo ponto que vem do scan diretamente para o argumento da função
    }

    public double cossineSimilarity (Vetor v2) {
        return produtoInterno(v2) / (modulo(this.x, this.y) * modulo(v2.x, v2.y));
    }

}
