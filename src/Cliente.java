import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Vetor vetor1 = new Vetor(sc.nextDouble(),sc.nextDouble());
        Vetor vetor2 = new Vetor(sc.nextDouble(), sc.nextDouble());
        //System.out.println(String.format("%.2f", vetor1.modulo()));
        //double produtoInterno = vetor1.produtoInterno(vetor2);
        double cossineSimilarity = vetor1.cossineSimilarity(vetor2);
        System.out.println(String.format(("%.2f"), cossineSimilarity));
    }
}