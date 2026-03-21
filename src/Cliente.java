import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Scanner;


/**
 * Classe que representa um vetor no plano.
 * @author Hugo_Domingues
 * @version 22-02-2026
 */
public class Cliente {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Primeira linha
        String stringPontos = sc.nextLine(); // metemos os pontos todos numa string do estilo: "0 1 1 1 4 4 4 3"

        //segunda linha input para colocar no segmento de reta parede mais tarde..
        double x1 = sc.nextDouble(), y1= sc.nextDouble();
        double x2 = sc.nextDouble(), y2 = sc.nextDouble();

        //arranjamos agora a primeira linha:
        String[] pontosSplit = stringPontos.split(" "); // separamos todos a cada espaço e metemos tudo num array. Cada elemento é uma coordenada
        Ponto[] pontosLidos = new Ponto[pontosSplit.length / 2]; // Logo, a quantidade de pontos no input vai ser o length do array a dividir por 2.
        double coordenadaX = 0;
        //inserir os pontos no array pontosLidos
        try {
            for (int i = 0; i < pontosSplit.length; i++) {
                double coordenadaAtual = Double.parseDouble(pontosSplit[i]);
                if (i % 2 == 0) {
                    coordenadaX = coordenadaAtual;
                } else {
                    // 1 / 2 = 0.5 = 0 ...... 3 / 2 = 1.5 = 1... e por aí adiante..
                    pontosLidos[i / 2] = new Ponto(coordenadaX, coordenadaAtual);
                }
            }
        } catch (NumberFormatException castError) {
            throw new IllegalArgumentException("input inválido.");
        }
        //tentamos criar a nova rota com pontos consecutivos diferentes..
        try {
            Route rota = new Route(pontosLidos); //Criamos uma nova rota, constituida pelos pontos lidos no input, tendo estes sido guardados todos no array pontosLidos
            System.out.printf("%.2f\n", rota.comprimentoTotal()); // temos de imnprimir isto antes de verificarmos se o segmento de reta parede é válido, senão um erro iria lançar e n seria improsso no ecrã o comprimento do segmento de reta ROTA.
            try {
                SegmentoReta parede = new SegmentoReta(new Ponto(x1, y1), new Ponto(x2, y2));
                ArrayList<Ponto> pontosIntersecao = rota.intersecao(parede);
                if (pontosIntersecao.isEmpty()) {
                    System.out.println("null");
                } else {

                    for (int i = 0; i < pontosIntersecao.size(); i++) {
                        if (i > 0) System.out.print(" "); //para evitar um espaço a mais no fim
                        System.out.print(pontosIntersecao.get(i).toString());
                    }
                    System.out.println();

                }
            } catch (IllegalArgumentException erroSegmento) {
                System.out.println(erroSegmento.getMessage());
            }
        } catch (IllegalArgumentException pontosIguaisConsecutivos) {
            System.out.println(pontosIguaisConsecutivos.getMessage());
        }
        sc.close();
    }
}