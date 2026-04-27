import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Porto {

    private char nome;
    private Ponto localizacao;
    private PriorityQueue<String> listaDeEspera;

    public Porto(char nome, Ponto localizacao) {
        this.nome = nome;
        this.localizacao = localizacao;
        this.listaDeEspera = new PriorityQueue<String>();
    }

    public Barco verificarFila(double tempoAtual) {

        return null;
    }

    public char getNome(){

        return nome;
    }

    public Ponto getLocalizacao() {

        return localizacao;
    }

    public List<String> getStringBarcosFila() {

        return new ArrayList<>(listaDeEspera);
    }

    public void agendarBarco(String barco) {

        this.listaDeEspera.add(barco);
    }

}