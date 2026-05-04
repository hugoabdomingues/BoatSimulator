package entity;

import geometria.Ponto;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * Classe que representa o porto
 * @author G2_PL1
 * Version 29-04-2026
 * @inv A localização e nome não podem ser nulos
 *
 */
public class Porto {

    private String nome;
    private Ponto localizacao;
    private PriorityQueue<String> listaDeEspera;

    /**
     *
     * @param nome Nome do porto
     * @param localizacao Localização do porto
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
     *
     * @param tempoAtual Hora atual
     * @return entity.Barco pronto a sair do porto
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
     *
     * @return Nome do porto
     */
    public String getNome(){

        return nome;
    }

    /**
     *
     * @return Localização do entity.Porto
     */
    public Ponto getLocalizacao() {

        return localizacao;
    }

    /**
     *
     * @return Lista de agendamentos do porto
     */
    public List<String> getStringBarcosFila() {

        return new ArrayList<>(listaDeEspera);
    }

    /**
     *
     * @param barco entity.Barco a ser agendado no porto
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