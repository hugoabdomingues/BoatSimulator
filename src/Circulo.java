import java.util.ArrayList;

/**
 * Classe que representa um Círculo
 * @author Hugo_Domingues
 * @version 22-03-2026
 * @inv Os círculos têm o raio maior que zero.
 */
public class Circulo {

    private double raio;
    private Ponto centro;

    /**
     * Construtor do círculo
     *
     * @param centro ponto do centro do círculo
     * @param raio   raio do círculo
     */
    public Circulo(Ponto centro, double raio) {
        // se o raio for menor ou igual a 0:
        if (raio < 1e-9) {
            throw new IllegalArgumentException("Circulo:iv");
        }
        this.centro = centro;
        this.raio = raio;
    }

    /**
     * calcular se a rota interseta o circulo em algum ponto
     * @see "apontamentos no caderno"
     * @param rota rota que vamos testar se colide com o nosso objeto
     * @return Arraylist com os pontos de interseção ou um Arraylist vazio, caso n haja interseção
     */
    public ArrayList<Ponto> intersectCirculo(Route rota) {
        ArrayList<Ponto> colisoes = new ArrayList<>();
        Ponto[] pontosRota = rota.getPonto();

        for (int i = 0; i < pontosRota.length - 1; i++) {
            //coordenadas do ponto A e B do segmento atual da rota
            //Ponto A
            double pontoAx = pontosRota[i].getX();
            double pontoAy = pontosRota[i].getY();
            //Ponto B
            double pontoBx = pontosRota[i + 1].getX();
            double pontoBy = pontosRota[i + 1].getY();

            //coordenadas do centro do circulo
            double centroCx = this.centro.getX();
            double centroCy = this.centro.getY();

            //VETORES: (ver caderno para perceber)
            // (VetorCAx + Vetor ABx)² + (VetorCAy - VetorABy)² = raio²
            // vetor AB: direção e comprimento do segmento
            double vetorABx = pontoBx - pontoAx;
            double vetorABy = pontoBy - pontoAy;

            // vetor do centro do círculo até ao ponto A
            // usado para saber a posição relativa do círculo em relação ao segmento
            double vetorCAx = pontoAx - centroCx;
            double vetorCAy = pontoAy - centroCy;

            // esta equação resulta de substituir P(t) = A + t*(B-A) na equação do círculo (ver caderno)
            // coeficientes da equação quadrática a*t² + b*t + c = 0
            //coeficiente a: vetorABx² + vetorABy²
            double a = Math.pow(vetorABx,2) + Math.pow(vetorABy,2);
            //coeficiente b:  2 * vetorCAx * vetorABx + vetorCAy * vetor ABy
            double b = 2 * (vetorCAx * vetorABx + vetorCAy * vetorABy);
            //coeficiente c: vetorCAx² + vetorCAy² - r²
            double c = Math.pow(vetorCAx,2) + Math.pow(vetorCAy,2) - Math.pow(raio,2);

            // o discriminante (parte que está dentro da raíz quadrada) diz-nos quantas soluções existem:
            // discriminante < 0 --> o segmento não toca o círculo
            // discriminante = 0 --> o segmento é tangente (toca num ponto)
            // discriminante > 0 --> o segmento atravessa o círculo (dois pontos)
        double discriminante = b * b - 4 * a * c;

        if (discriminante < -1e-9) {
                // sem interseção, passa para o próximo segmento
                continue;
        }

            // corrige erros de arredondamento que podem dar discriminante ligeiramente negativo quando devia ser zero (caso tangente)
        discriminante = Math.max(0, discriminante);

            // fórmula resolvente: t = (-b ± sqrt(discriminante)) / 2a
            // t representa a posição no segmento: t=0 é o ponto A, t=1 é o ponto B
        double t1 = (-b - Math.sqrt(discriminante)) / (2 * a);
        double t2 = (-b + Math.sqrt(discriminante)) / (2 * a);

            // só nos interessa t entre 0 e 1 — fora desse intervalo
            // a interseção acontece na reta infinita mas não no segmento
        if (t1 >= -1e-9 && t1 <= 1 + 1e-9) {
                // calculamos o ponto de interseção a partir de P(t) = A + t*(B-A) !!!
                colisoes.add(new Ponto(pontoAx + t1 * vetorABx, pontoAy + t1 * vetorABy));
        }

            // só adicionamos t2 se for diferente de t1, ou seja, se o discriminante for POSITIVO (> 0) ... caso contrário t1 == t2 e estaríamos a adicionar o mesmo ponto duas vezes
        if (Math.abs(discriminante) >= 1e-9 && t2 >= -1e-9 && t2 <= 1 + 1e-9) {
                colisoes.add(new Ponto(pontoAx + t2 * vetorABx, pontoAy + t2 * vetorABy));
            }
        }
        return colisoes;
    }
}