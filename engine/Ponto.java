/**
 * Classe que representa um ponto no plano xOy
 * @author Hugo_Domingues
 * @version 15-03-2026
 * @inv O ponto é definido por um x e um y.
 */
public class Ponto {
    private double x, y;

    /**
     * Construtor que cria um ponto
     * @param x coordenada x do ponto
     * @param y coordenada y do ponto
     */
    public Ponto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Assessor/getter da coordenada x do ponto.
     * @return o valor do x do ponto.
     */
    public double getX() {
        return this.x;
    }


    /**
     * Assessor/getter da coordenada y do ponto.
     * @return valor do y do ponto
     */
    public double getY() {
        return this.y;
    }

    /**
     * conversão do ponto para String, com o formato (x,y), respeitando as duas casas decimais
     * @return uma string com as coordenadas do ponto (x,y)
     */
    public String toString() {
        return String.format("(%.2f,%.2f)", this.x, this.y);
    }

    /**
     * Calcula o pontoB: ponto resultante da soma do pontoA (ponto pedido no input) com o vetorB. Somando o vetorB ao pontoA, obtemos o pontoB --> segundo ponto do segmento de reta
     * @param b o vetor que vamos somar ao pontoA
     * @return um novo ponto --> pontoB
     */
    public Ponto move(Vetor b) {
        return new Ponto(this.getX() + b.getX(), this.getY() + b.getY());
    }

    /**
     * método equals para verificar se dois pontos são iguais usando a noção de que dois pontos são iguais se as suas coordenadas double são iguais.
     * considera-se que dois double d e g são iguais se |d-g|<1e-9
     * @param o objeto (ponto) com o qual queremos comparar o ponto atual.
     * @return true se forem iguais e false se forem diferentes.
     */
    @Override //escrevemos em cima do equals da classe object.
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ponto)) return false; // se o objeto n for um ponto --> false
        Ponto p = (Ponto) o; //fazemos cast para ponto, caso seja um ponto
        // considera-se que dois double d e g são iguais se |d-g| < 1e-9
        return Math.abs(this.x - p.x) < 1e-9 && Math.abs(this.y - p.y) < 1e-9;}

    /**
     * cálcular a distância entre dois pontos
     * @param p pontoB
     * @return distancia entre dois pontos
     */
    public double distanciaPontos(Ponto p) {
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }
}
