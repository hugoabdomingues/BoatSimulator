package entity;

import geometria.Circulo;
import geometria.Ponto;

/**
 * Classe que representa o Barco
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 14/05/2026
 */
public class Barco {
    private String codigo;
    private String partida;
    private double vl;
    private String destino;
    private entity.Navegador navegador;
    private boolean espera;
    private Ponto posicaoAtual;
    private Circulo hitbox;
    private double horaDePartida;

    /**
     * Construtor do Barco
     * @param partida Nome do Porto de partida
     * @param vl double velocidade linear do Barco
     * @param destino nome do Porto de destino
     * @param hora hora de saida do barco
     * @param posicao_atual Ponto
     * @inv a hora de partida nao pode ser negativo nem superior a 24, a vl nao pode ser menor ou igual a 0
     */
    public Barco(String partida,double vl, String destino, double hora,Ponto posicao_atual){
        if(hora <= 24 && hora > 0 && vl > 0 ){
            this.vl = vl;
            this.partida = partida;
            this.horaDePartida = hora;
            this.codigo = this.partida + (int)this.horaDePartida;
            this.destino = destino;
            this.espera = false;
            this.posicaoAtual = posicao_atual;
            this.hitbox = new Circulo(posicao_atual, 1);
        }
        else{
            throw new IllegalArgumentException("entity.Barco:iv");
        }

    }

    /**
     * Getter que representa o estado do barco (estados: parar, andar)
     * @return valor booleano
     */
    public boolean getEstado(){
        return this.espera;
    }

    /**
     * Getter do Codigo de partida do Barco
     * @return String
     */
    public String getCodigo(){
        return this.codigo;
    }


    /**
     * Getter da velocidade linear do Barco
     * @return double
     */
    public double getVl(){
        return this.vl;
    }

    /**
     * Getter da posicao atual do Barco
     * @return Ponto
     */
    public Ponto getPosicaoAtual(){
        return this.posicaoAtual;
    }

    /**
     * Getter que representa a hora de partida do Barco
     * @return double
     */
    public double getHora(){
        return this.horaDePartida;
    }

    /**
     * Getter do Navegador do Barco
     * @return Navegador
     */
    public Navegador getNavegador () {
        return this.navegador;
    }

    /**
     * Getter do nome do Porto de destino
     * @return String
     */
    public String getDestino() {
        return this.destino;
    }

    public Circulo getHitbox() {
        return this.hitbox;
    }

    /**
     * Setter da Posicao Atual do barco
     * @param novaPosicao Ponto
     */
    public void setPosicaoAtual(Ponto novaPosicao) {
        this.posicaoAtual = novaPosicao;
        this.hitbox = new Circulo(novaPosicao, 1);
    }


    /**
     * Setter
     * @param navegador Navegador
     */
    public void setNavegador(Navegador navegador) {
        this.navegador = navegador;
    }

    /**
     * Setter da hora de partida
     * @param hora double
     */
    public void setHora (double hora) {
        if(hora < 0 || hora > 24){
            return;
        }
        this.horaDePartida = hora;
    }

    /**
     * Setter do estado do Barco
     * @param espera boolean
     */
    public void setEstado(boolean espera) {
        this.espera = espera;
    }

    /**
     * Verifica se um Barco interseta com outro Barco
     * @param outro Barco
     * @return boolean
     */
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
