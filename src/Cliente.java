import java.util.ArrayList;
import java.util.Scanner;


/**
 * Classe main.
 * @author Hugo_Domingues
 * @version 22-02-2026
 */
public class Cliente {

    public static void main() {
        Scanner sc = new Scanner(System.in);
        //Primeira linha tem te conter um número par de números:
        //metemos tudo numa string e dividimo-la
        String linha1 = sc.nextLine();
        String[] stringSplit = linha1.split(" ");
        int numeroDeDoubles = stringSplit.length;
        if (numeroDeDoubles % 2 != 0) { //se nao for par: erro
            System.out.println("Rota:iv");
        } else {
            try {
                //criamos um array com numero de indices = metade do numero de doubles que temos no input
                Ponto[] pontosRota = new Ponto[numeroDeDoubles / 2];
                for (int i = 0; i < numeroDeDoubles / 2; i = i + 2) {
                    double coordenadaX = Double.parseDouble(stringSplit[i]);
                    double coordenadaY = Double.parseDouble(stringSplit[i + 1]);
                    Ponto pontoRotaAtual = new Ponto(coordenadaX, coordenadaY);
                    pontosRota[i] = pontoRotaAtual;
                }
                Route rota = new Route(pontosRota);
                //Get wind speed and direction
                Vetor wt = new Vetor(sc.nextDouble(), sc.nextDouble());
                //Get linear speed
                double vl = sc.nextDouble();
                sc.close();
                System.out.printf("%.2f\n", rota.comprimentoTotal());


            } catch (NumberFormatException erro) {
                System.out.println("input inválido.");
            }
        }
    }
}