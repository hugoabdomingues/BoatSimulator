package entity;

import geometria.Circulo;
import geometria.Ponto;

public class Barco {
    private String codigo;
    private double vl;
    private String destino;
    private Navegador navegador;
    private boolean espera;
    private Ponto posicaoAtual;
    private Circulo hitbox;
    private double horaDePartida;

    public Barco(String partida,double vl, String destino, int hora,Ponto posicao_atual){
        if(hora <= 24 && hora > 0 && vl > 0 ){
            this.vl = vl;
            this.horaDePartida = hora;
            this.codigo = partida+String.valueOf(hora);
            this.destino = destino;
            this.espera = false;
            this.posicaoAtual = posicao_atual;
            this.hitbox = new Circulo(posicao_atual, 1);
        }
        else{
            throw new IllegalArgumentException("entity.Barco:iv");
        }

    }

    //GETTERS
    public boolean getEstado(){
        return this.espera;
    }

    public String getCodigo(){
        return this.codigo;
    }

    public double getVl(){
        return this.vl;
    }

    public Ponto getPosicaoAtual(){
        return this.posicaoAtual;
    }

    public double getHora(){
        return this.horaDePartida;
    }

    public Navegador getNavegador () {
        return this.navegador;
    }

    public String getDestino() {
        return this.destino;
    }

    //SETTERS
    public void setPosicaoAtual(Ponto novaPosicao) {
        this.posicaoAtual = novaPosicao;
        this.hitbox = new Circulo(novaPosicao, 1);
    }

    public Circulo getHitbox() {
        return this.hitbox;
    }

    public void setNavegador(Navegador navegador) {
        this.navegador = navegador;
    }

    public void setHora (double hora) {
        this.horaDePartida = hora;
    }

    public void setEstado(boolean espera) {
        this.espera = espera;
    }



public boolean intersetarBarco(Barco outro) {
        double distancia = this.posicaoAtual.distanciaPontos(outro.getPosicaoAtual());
        if (distancia < 2.0) {
            if (this.codigo.compareTo(outro.getCodigo()) < 0) {
                this.setEstado(true); // este é o lexicograficamente menor --> espera
            }
            return true;
        }
        return false;
    }

}
