package entity;

import geometria.Ponto;
import entity.Barco;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * Classe que representa o porto
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * Version 29-04-2026
 *
 */
public class Porto {

    private String nome;
    private Ponto localizacao;
    private PriorityQueue<String> listaDeEspera;

    /**
     *Construtor do Porto
     * @param nome Nome do porto
     * @param localizacao Localização do porto
     * @inv A localização e nome não podem ser nulos
     */
    public Porto(String nome, Ponto localizacao) {

        if (localizacao == null) {
            throw new IllegalArgumentException("entity.Porto: A localização não pode ser nula.");
        }

        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("entity.Porto: Nome inválido.");
        }

        this.nome = nome;
        this.localizacao = localizacao;
        //temos de ter em conta que se tivewrmos T=10 e T=9, sabendo que o Java organiza Strings alfabeticamente, o Java vai achar que "T=10" vem antes de "T=9" (porque o 1 vem antes do 9)
        //assim fazemos:
        this.listaDeEspera = new PriorityQueue<>((barco1, barco2) -> {
            int hora1 = Integer.parseInt(barco1.split("[=,]")[1].trim());
            int hora2 = Integer.parseInt(barco2.split("[=,]")[1].trim());
            return Integer.compare(hora1, hora2);
        });
    }

    /**
     * Verifica a lista de espera do porto e instancia um novo Barco caso tenha chegado a hora da sua partida.
     * @param tempoAtual O tempo ou hora atual da simulação
     * @return O objeto Barco instanciado e pronto a navegar, ou null se não houver barcos prontos
     */
    public Barco verificarFila(double tempoAtual) {

        //Verificar se existem barcos no porto
        if (listaDeEspera.isEmpty()){

            return null;
        }

        //Verificar se o barco está pronto para partir do porto
        String firstBoat = listaDeEspera.peek();
        String [] splitString = firstBoat.split("[=,]");
        String horaString = splitString[1].trim();
        int partida = Integer.parseInt(horaString);

        //Caso o barco esteja pronto então é criado
        if (partida <= tempoAtual) {

            String destino = splitString[2].trim();
            String velocidadeLinear = splitString[3].trim();
            Double vl = Double.parseDouble(velocidadeLinear);
            //o código é construído dentro do Barco: nomePorto + hora --> exemplo: "A12"
            Barco barcoSaida = new Barco(this.nome, vl, destino, partida, this.localizacao);

            //Remover o barco criado da lista de espera
            listaDeEspera.poll();
            return barcoSaida;
        }
        return null;
    }

    /**
     * Getter do nome do porto.
     * @return String
     */
    public String getNome(){

        return nome;
    }

    /**
     * Getter da localizacao do Porto
     * @return Ponto
     */
    public Ponto getLocalizacao() {

        return localizacao;
    }

    /**
     * Getter da lista com os agendamentos (em formato de texto) dos barcos que aguardam partida.
     * @return Lista de Strings com a informação dos barcos na fila
     */
    public List<String> getStringBarcosFila() {

        return new ArrayList<>(listaDeEspera);
    }

    /**
     * Regista a entrada de um novo agendamento na lista de espera do porto, ordenando-o pela sua hora de partida.
     * @param barco String com a informação de agendamento do barco
     * @inv se a string for nula ou não seguir o formato esperado
     */
    public void agendarBarco(String barco) {

        if (barco == null || barco.isEmpty()) {
            throw new IllegalArgumentException("entity.Porto: Agendamento nulo");
        }

        if (!barco.contains("T=")) {
            throw new IllegalArgumentException("entity.Porto: Formato inválido");
        }

        this.listaDeEspera.add(barco);
    }

}