public class Barco {
    private String codigo;
    private double vl;
    private String destino;
    private double vl_temp;
    private Navegador navegador;
    private boolean espera;
    private Ponto posicao_atual;
    private Circulo hitbox;
    private double hora;

    public Barco(String partida,double vl, String destino, int hora,Ponto posicao_atual){
        if(hora <= 24 && hora > 0 && vl > 0 ){
            this.vl = vl;
            this.hora = hora;
            this.codigo = partida+String.valueOf(hora);
            this.destino = destino;
            this.espera = false;
            this.posicao_atual = posicao_atual;
            this.hitbox = new Circulo(posicao_atual, 1);
        }
        else{
            throw new IllegalArgumentException("Barco:iv");
        }

    }

    public boolean getEstado(){
        return this.espera;
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

    public int getHora(){
        return this.hora;
    }

    public Navegador getNavegador () {
        return this.navegador;
    }
    public String getDestino() {
        return this.destino;
    }
    //SETTERS
    public void setPosicaoAtual (Ponto novaPosicao) {
        this.posicao_atual = novaPosicao;
    }
    public void setNavegador(Navegador navegador) {
        this.navegador = navegador;
    }
    public void setHora (double hora) {
        this.hora = hora;
    }
    public void setEstado(boolean espera) {
        this.espera = espera;
    }



    //O barco anda com vl (velocidade linear)
    private void parar(){
        this.espera = true;
        this.vl_temp = this.vl;
        this.vl = 0;
    }

    private void retomar(){
        this.vl = this.vl_temp;
        this.espera = false;
    }
    //Nao Sei como o GUI mostra
    public void mostrarHitboxes() {
        // TODO
    }

    public boolean intersetarBarco(Barco barco){
         double distancia = this.getPosicao_atual().distanciaPontos(barco.getPosicao_atual());
        if(distancia < 2){
            if(this.temPrioridade(barco)){
                barco.parar();
            }
            else{
                this.parar();
            }
            return true;
        }
        if(this.espera){
            this.retomar();
        }
        if(barco.espera){
            barco.retomar();
        }
        return false;
    }
    //Não existe nome de código igual
    private boolean temPrioridade(Barco barco){
        int valor =  this.codigo.compareTo(barco.getCodigo());
        if(valor > 0){
            return true; //this.barco é maior
        }
        else
        {
            return false; //this.barco é menor
        }
    }

}
