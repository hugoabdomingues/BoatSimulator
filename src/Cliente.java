import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ponto pontoA = new Ponto(sc.nextDouble(),sc.nextDouble());
        Vetor vetorB = new Vetor(new Ponto(sc.nextDouble(), sc.nextDouble()));

        System.out.println(new SegmentoReta(pontoA, vetorB));
        //System.out.println(String.format("%.2f", vetor1.modulo()));
        //double produtoInterno = vetor1.produtoInterno(vetor2);
        //double cossineSimilarity = vetor1.cossineSimilarity(vetor2);
        //System.out.println(String.format(("%.2f"), cossineSimilarity));
        sc.close();
    }
}