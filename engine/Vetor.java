import java.awt.*;
import java.util.Vector;

/**
 * Classe que representa um vetor no plano.
 * @author Hugo_Domingues
 * @version 20-03-2026
 * @inv modulo() > 0
 */
public class Vetor {
    private Ponto p;

    /**
     * Construtor que cria um vetor a partir de um ponto.
     * @param p Ponto a partir do qual vamos criar um vetor
     */
    public Vetor(Ponto p) {
        if (p.equals(new Ponto(0,0))) {//invariante
            throw new IllegalArgumentException("Vetor:iv");
        }

        this.p = p;
    }

    /**
     * 2º construtor da classe vetor
     * @param x coordenado x do ponto
     * @param y coordenada y do ponto
     */
    public Vetor(double x, double y) {
        this(new Ponto(x,y)); //fazemos um ponto das coordenadas e chamamos o primeiro construtor com "this". Enviamos o Ponto para o 1º construtor e o mesmo é processado.
    }

    /**
     * Cálcula o módulo do vetor
     * @return O módulo
     */
    public double modulo() {
        return Math.sqrt(Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2));
    }

    /**
     * Cálcula o produto interno do vetor com outro vetor (pedido no input)
     * No Construtor (this.x, this.y) definimos as características que são fixas do objeto. O vetor vai ter sempre estas coordenadas X e Y dele.
     * Já nos Parênteses da função definimos os valores que o vetor precisa apenas neste momento para fazer uma conta.
     * @param v2 Segundo vetor inserido.
     * @return resultado do produto interno
     */
    public double produtoInterno(Vetor v2) {
        return this.p.getX() * v2.getX() + this.getY() * v2.getY(); //multiplicamos o ponto do construtor (primeiro scan) com o segundo ponto que vem do scan diretamente para o argumento da função
    }

    /**
     * Cálcula o Cossine similarity entre o vetor atual da classe com outro vetor
     * @param v2 segundo vetor pedido no input
     * @return O resultado da fórmula
     */
    public double cossineSimilarity(Vetor v2) {
        return produtoInterno(v2) / (this.modulo() * v2.modulo());
    }

    /**
     * Assessor/getter da coordenada x do vetor
     * @return O x do vetor
     */
    public double getX() {
        return this.p.getX();
    }

    /**
     * Assessor/getter da coordenada y do vetor
     * @return O y do vetor
     */
    public double getY() {
        return this.p.getY();
    }

    /**
     * Assessor/getter do ponto que forma o vetor
     * @return O ponto
     */
    public Ponto getPonto() {
        return this.p;
    }

    /**
     * Verifica se o vetor interseta com o segmento de reta no plano
     * primeiro cálculamos o declive do vetor e o declive do segmento de reta que vão ser ambas retas --> y = mx
     * Em seguida, verificamos se o ponto em que intersetam está dentro do intervalo de pontos com que estamos a trabalhar
     *
     * @param v segmento de reta
     * @return o ponto de interseção ou null, caso não haja interseção
     * @see "https://brasilescola.uol.com.br/matematica/regra-cramer.htm"
     * @see "https://cp-algorithms.com/geometry/lines-intersection.html"
     * @see "https://www.baeldung.com/java-check-point-straight-line"
     */
    public Ponto intersect(SegmentoReta v) {

        Ponto pontoA = v.getPontoA();
        Ponto pontoB = v.getPontoB();
        // Formato geral: Ax + By = C
        /* A = yB - yA
         *  B = xA - xB
         *  C = A * xA + B * yA
         * */
        // Reta do Vetor:
        double Avetor = this.p.getY() - 0;
        double Bvetor = 0 - this.p.getX();
        double Cvetor = (Avetor * 0) + (Bvetor * 0);

        // Reta do Segmento de Reta:
        double Asegmento = pontoB.getY() - pontoA.getY();
        double Bsegmento = pontoA.getX() - pontoB.getX();
        double Csegmento = (Asegmento * pontoA.getX()) + (Bsegmento * pontoA.getY());

        //Cálculo do Determinante... se este for igual a 0 então as retas são paralelas (não se intersetam)
        //se for diferente de 0 então elas intersetam-se  -->> resta saber se intersetam no intervalo compreeendido pelos pontos do vetor e do segmento de reta
        // D = (Avetor * Bsegmento) - (Bvetor * Asegmento)
        double determinante = (Avetor * Bsegmento) - (Bvetor * Asegmento);

        // determinante == 0 ?
        // considera-se que dois double d e g são iguais se |d-g|<1e-9
        // determinante == 0 se:
        if (Math.abs(determinante) < 1e-9) {
            return null;
        } else {
            //Cálculamos o X e o Y do ponto em que intersetam
            // X = Dx / D
            // Y = Dy / D

            //Dx = (Cvetor * Bsegmento) - (Csegmento * Bvetor)
            double Dx = (Cvetor * Bsegmento) - (Csegmento * Bvetor);

            //Dy = (Avetor * Csegmento) - (Cvetor * Bvetor)
            double Dy = (Avetor * Csegmento) - (Cvetor * Bvetor);

            double pontoX = Dx/determinante;
            double pontoY = Dy/determinante;

            //verificamos agora se este ponto está compreendido no intervalo.
            Ponto pontoIntersecao = new Ponto(pontoX, pontoY);

            // Usamos a noção de que se distância (AC) + distância (CB) == distância (AB) --> então o ponto de interseção está no segmento de reta
            //cálculamos a distância de AB:
            double distanciaAB = Math.sqrt((pontoB.getY() - pontoA.getY()) * (pontoB.getY() - pontoA.getY()) + (pontoB.getX() - pontoA.getX()) * (pontoB.getX() - pontoA.getX()));
            //calculamos distância de AC:
            double distanciaAC = Math.sqrt((pontoIntersecao.getY() - pontoA.getY()) * (pontoIntersecao.getY() - pontoA.getY()) + (pontoIntersecao.getX() - pontoA.getX()) * (pontoIntersecao.getX() - pontoA.getX()));
            double distanciaBC = Math.sqrt((pontoIntersecao.getY() - pontoB.getY()) * (pontoIntersecao.getY() - pontoB.getY()) + (pontoIntersecao.getX() - pontoB.getX()) * (pontoIntersecao.getX() - pontoB.getX()));
            // considera-se que dois double d e g são iguais se |d-g| < 1e-9
            //logo: distanciaAC + distanciaBC == distanciaAB se Math.abs(distanciaAC + distanciaBC - distanciaAB) < 1e-9
            if (Math.abs(distanciaAC + distanciaBC - distanciaAB) < 1e-9) {
                return pontoIntersecao;
            } else{
                return null;
            }
        }
    }

    /**
     * Multiplica o X e o Y do vetor pelo escalar d.
     * @param d escalar
     * @throws IllegalArgumentException se o resultado for o vetor nulo (0,0)
     * @return vetor multiplicado pelo escalar d
     */
    public Vetor mult(double d) {
        return new Vetor(new Ponto(this.getX() * d, this.getY() * d));
    }

    /**
     * Soma o vetor atual com o vetor V
     * @param v vetor v
     * @throws IllegalArgumentException se o resultado for o vetor nulo (0,0)
     * @return novo vetor calculado a partir da soma do atual com o vetor v
     */
    public Vetor add(Vetor v) {
        return new Vetor(new Ponto(this.getX() + v.getX(), this.getY() + v.getY()));
    }

    /**
     * Subtrai o vetor atual da classe com o vetor passado por argumento V
     * @param v vetor a ser subtraído
     * @throws IllegalArgumentException se o resultado for o vetor nulo (0,0)
     * @return novo vetor resultante da subtração
     */
    public Vetor sub (Vetor v) {
        return new Vetor(new Ponto(this.getX() - v.getX(), this.getY() - v.getY()));
    }

    /**
     * conversão do Vetor para String, com o formato [x,y], respeitando as duas casas decimais
     * @return vetor em formato string [x,y]
     */
    public String toString() {
        return String.format("[%.2f,%.2f]", this.getX(), this.getY());
    }
}


