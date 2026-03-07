import java.util.Scanner;

/**
 * Classe que representa um vetor no plano.
 * @author Hugo_Domingues
 * @version 22-02-2026
 */
public class Cliente {

    public static void main() {
        Scanner sc = new Scanner(System.in);
        //Get start and finish points
        Ponto start = new Ponto(sc.nextDouble(), sc.nextDouble());
        Ponto finish = new Ponto(sc.nextDouble(), sc.nextDouble());
        Vetor r = new Vetor(finish).sub(new Vetor(start));
        //Get wind speed and direction
        Vetor w = new Vetor(sc.nextDouble(), sc.nextDouble());
        //Get the required time to reach the finish point
        double t = sc.nextDouble();
        sc.close();
        //Compute the required speed to reach the finish point in time t
        Vetor s = r.sub(w).mult(1/t);
        IO.println(s);

    }
}