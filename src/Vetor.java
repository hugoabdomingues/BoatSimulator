import java.awt.*;

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
     *
     * @param a Ponto a partir do qual vamos criar um vetor
     */
    public Vetor(Ponto a) {
        if (a.getX() == 0 && a.getY() == 0) { //invariante
            System.out.println("Vetor:iv");
            System.exit(0); // o 0 significa que o programa terminou normalmente. É uma maneira de forçar a paragem do programa
        }

        this.p = a;
    }

    /**
     * Cálcula o módulo do vetor
     *
     * @return O módulo
     */
    public double modulo() {
        return Math.sqrt(Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2));
    }

    /**
     * Cálcula o produto interno do vetor com outro vetor (pedido no input)
     * No Construtor (this.x, this.y) definimos as características que são fixas do objeto. O vetor vai ter sempre estas coordenadas X e Y dele.
     * Já nos Parênteses da função definimos os valores que o vetor precisa apenas neste momento para fazer uma conta.
     *
     * @param v2 Segundo vetor inserido.
     * @return resultado do produto interno
     */
    public double produtoInterno(Vetor v2) {
        return this.p.getX() * v2.getX() + this.getY() * v2.getY(); //multiplicamos o ponto do construtor (primeiro scan) com o segundo ponto que vem do scan diretamente para o argumento da função
    }

    /**
     * Cálcula o Cossine similarity entre o vetor atual da classe com outro vetor
     *
     * @param v2 segundo vetor pedido no input
     * @return O resultado da fórmula
     */
    public double cossineSimilarity(Vetor v2) {
        return produtoInterno(v2) / (this.modulo() * v2.modulo());
    }

    /**
     * Assessor/getter da coordenada x do vetor
     *
     * @return O x do vetor
     */
    public double getX() {
        return this.p.getX();
    }

    /**
     * Assessor/getter da coordenada y do vetor
     *
     * @return O y do vetor
     */
    public double getY() {
        return this.p.getY();
    }

    /**
     * Assessor/getter do ponto que forma o vetor
     *
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


        // Ax + By = C
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

        if (determinante == 0) {
            return null;
        } else {
            //Cálculamos o X e o Y do ponto em que intersetam
            // X = Dx / D
            // Y = Dy / D

            //Dx = (Cvetor * Bsegmento) - (Csegmento * Asegmento)
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
            if (distanciaAC + distanciaBC == distanciaAB) {
                return pontoIntersecao;
            } else{
                return null;
            }
        }
    }
}


