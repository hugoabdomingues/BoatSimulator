public class Barco {
    private String codigo;
    private double vl;
    private char dest;
    private int hora;
    private Navegador navegador;
    private boolean espera;
    private Ponto posicao_atual;
    private Circulo hitbox;

    public Barco(String codigo, double vl, char destino, int hora,Ponto posicao_atual, Navegador navegador){
        this.codigo = codigo;
        this.vl = vl;
        this.dest = destino;
        this.hora = hora;
        this.navegador = navegador; //Nao podemos usar navegador, pq após analisar a rota mais rápida temos que verificar se o SegmentoReta não está ocupada
        this.espera = false;
        this.posicao_atual = posicao_atual;
        this.hitbox = new Circulo(posicao_atual, 1);
    }

    public String getCodigo(){
        return this.codigo;
    }

    public double getVl(){
        return this.vl;
    }

    public Ponto getPosicao_atual(){
        return this.posicao_atual;
    }



    private void parar(){

    }

    private void retomar(){

    }

    public void mostrarHitboxes() {
        // TODO
    }

    public boolean intersetarBarco(Barco barco){
        return true;
    }

    private boolean temPrioridade(Barco barco){
        return true;
    }

}
