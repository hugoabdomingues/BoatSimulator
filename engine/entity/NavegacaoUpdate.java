package entity;

import geometria.Ponto;
import geometria.Vetor;

import java.util.List;

/**
 * Record que funciona como Data Transfer Object para enviar as atualizações calculadas pela engine para o GUI
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 26-04-2026
 * @param barcosAtivos Lista de códigos dos barcos atualmente a navegar
 * @param localizacaoBarcosAtivos Lista das posições (geometria.Ponto) correspondentes aos barcos
 * @param mostrarHitboxNavio Lista de booleanos indicando se a hitbox deve ser desenhada para cada barco
 * @param obstaculos Lista de obstáculos presentes no mapa
 * @param corrente geometria.Vetor da corrente marítima atual
 * @param barcosFila Lista de informação visual dos barcos a aguardar nos portos
 * @param tempoAtual Tempo de simulação atual, usado para mostrar o relógio no GUI
 */
public record NavegacaoUpdate(List<String> barcosAtivos,
                              List<Ponto> localizacaoBarcosAtivos,
                              List<Boolean> mostrarHitboxNavio,
                              List<Obstaculo> obstaculos,
                              Vetor corrente,
                              List<String> barcosFila,
                              List<Porto> portos,
                              List<Route> rotas,
                              double tempoAtual) {
}

