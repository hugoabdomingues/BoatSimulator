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
            throw new IllegalArgumentException("Porto: A localização não pode ser nula.");
        }

        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Porto: Nome inválido.");
        }

        this.nome = nome;
        this.localizacao = localizacao;
        this.listaDeEspera = new PriorityQueue<String>();
    }

    /**
     *
     * @param tempoAtual Hora atual
     * @return Barco pronto a sair do porto
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

            //o código é o nome do porto + hora da partida.. por exemplo: A12
            String boatCode = this.nome + partida;

            Barco barcoSaida = new Barco (boatCode, vl, destino, partida, this.localizacao);

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
     * @return Localização do Porto
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
     * @param barco Barco a ser agendado no porto
     */
    public void agendarBarco(String barco) {

        if (barco == null || barco.isEmpty()) {
            throw new IllegalArgumentException("Porto: Agendamento nulo");
        }

        if (!barco.contains("T=")) {
            throw new IllegalArgumentException("Porto: Formato inválido");
        }

        this.listaDeEspera.add(barco);
    }

}