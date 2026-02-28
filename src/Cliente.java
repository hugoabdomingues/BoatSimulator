import java.util.Scanner;

/**
 * Classe que representa um vetor no plano.
 * @author Hugo_Domingues
 * @version 22-02-2026
 */
public class Cliente {
    /**
     * Método principal que lê os dados de entrada e imprime o resultado.
     * @param args Argumentos do terminal.
     */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //1ª linha (coordenadas cartesianas dos pontos A e B)
        Ponto pontoA = new Ponto(sc.nextDouble(),sc.nextDouble());
        Ponto pontoB = new Ponto(sc.nextDouble(), sc.nextDouble());
        //2ª linha (vetor que indica a velocidade do vento em cada eixo X e Y)
        Vetor vetorW = new Vetor(new Ponto(sc.nextDouble(), sc.nextDouble()));
        //3ª linha (tempo desejado para efetuar a viagem de A a B)
        double t = sc.nextFloat();

        //output
        System.out.println(vetorW.intersect(new SegmentoReta(pontoA, pontoB)));


        //System.out.println(new SegmentoReta(pontoA, pontoB));

        //System.out.println(String.format("%.2f", vetor1.modulo()));
        //double produtoInterno = vetor1.produtoInterno(vetor2);
        //double cossineSimilarity = vetor1.cossineSimilarity(vetor2);
        //System.out.println(String.format(("%.2f"), cossineSimilarity));
        sc.close();
    }
}