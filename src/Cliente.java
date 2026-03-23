import java.util.ArrayList;
import java.util.Scanner;


/**
 * Classe main.
 * @author Hugo_Domingues
 * @version 22-02-2026
 */
public class Cliente {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Primeira linha
        String primeiraLinha = sc.nextLine(); // metemos os pontos todos numa string do estilo: "0 1 1 1 4 4 4 3"
        //segunda linha input
        String segundaLinha = sc.nextLine();

        //arranjamos agora a primeira linha:
        String[] pontosSplit = primeiraLinha.split(" "); // separamos todos a cada espaço e metemos tudo num array. Cada elemento é uma coordenada
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
                    //Quando i=1, 1/2 = 0 — primeiro vértice.
                    pontosLidos[i / 2] = new Ponto(coordenadaX, coordenadaAtual);
                }
            }
        } catch (NumberFormatException castError) {
            throw new IllegalArgumentException("input inválido.");
        }


        //tentamos criar a nova rota com pontos consecutivos diferentes..
        try {
            Route rota = new Route(pontosLidos); //Criamos uma nova rota, constituida pelos pontos lidos no input, tendo estes sido guardados todos no array pontosLidos

            //arranjmos agora a segunda linha:
            String[] segundaLinhaSplit = segundaLinha.split(" ");
            String formaGeometrica = segundaLinhaSplit[0]; // P T R Q C
            ArrayList<Ponto> colisoes = new ArrayList<>();

            //se for um circulo...
            if (formaGeometrica.equals("C")) {
                try {
                    Ponto centro = new Ponto(Double.parseDouble(segundaLinhaSplit[1]), Double.parseDouble(segundaLinhaSplit[2]));
                    double raio = Double.parseDouble(segundaLinhaSplit[3]);
                    try {
                        Circulo circulo = new Circulo(centro, raio);
                        colisoes = circulo.intersectCirculo(rota);
                        if (colisoes.isEmpty()){
                            System.out.println("null");
                        }else {
                            for (int i = 0; i < colisoes.size(); i++) {
                                if (i > 0) System.out.print(" ");
                                System.out.print(colisoes.get(i).toString());
                            }
                            System.out.println();
                        }
                    } catch (IllegalArgumentException invarianteCirculo) {
                        System.out.println(invarianteCirculo.getMessage());
                    }
                } catch (NumberFormatException castingError) {
                    throw new NumberFormatException("Erro: input inválito.");
                }
                //se nao for um circulo:
                //primeiro guardamos os vertices do input
            } else {
                Ponto[] vertices = new Ponto[(segundaLinhaSplit.length - 1) / 2];
                try {
                    int indiceVertice = 0;
                    for (int i = 1; i < segundaLinhaSplit.length; i= i+2) {
                        try {
                            double x = Double.parseDouble(segundaLinhaSplit[i]);
                            double y = Double.parseDouble(segundaLinhaSplit[i + 1]);
                            vertices[indiceVertice] = new Ponto(x, y);
                            indiceVertice++;
                        }catch (NumberFormatException castingError) {
                            throw new NumberFormatException("input invalido");
                        }
                    }
                } catch (NumberFormatException castingError) {
                    throw new NumberFormatException("Erro: input inválito.");
                }

                try {
                    Poligono obstaculo = switch (formaGeometrica) {
                        case "P" -> new Poligono(vertices);
                        case "T" -> new Triangulo(vertices);
                        case "R" -> new Retangulo(vertices);
                        case "S" -> new Quadrado(vertices);
                        default -> throw new IllegalArgumentException("Forma desconhecida");
                    };
                    colisoes = obstaculo.intersect(rota);
                    if (colisoes.isEmpty()) {
                        System.out.println("null");
                    } else {
                        for (int i = 0; i < colisoes.size(); i++) {
                            if (i > 0) System.out.print(" ");
                            System.out.print(colisoes.get(i).toString());
                        }
                        System.out.println();
                    }
                } catch (IllegalArgumentException invariantesErro) {
                    System.out.println(invariantesErro.getMessage()); //apanha "Poligono:iv", "Triangulo:iv", etc.
                }
            }
            }catch(IllegalArgumentException pontosIguaisConsecutivos){
            System.out.println(pontosIguaisConsecutivos.getMessage());
        }
        sc.close();
    }
}