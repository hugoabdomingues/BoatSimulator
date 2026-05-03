import java.util.List;

/**
 * Record que funciona como Data Transfer Object para enviar as atualizações calculadas pela engine para o GUI
 * @author
 * @version 26-04-2026
 * @param barcosAtivos Lista de códigos dos barcos atualmente a navegar
 * @param localizacaoBarcosAtivos Lista das posições (Ponto) correspondentes aos barcos
 * @param mostrarHitboxNavio Lista de booleanos indicando se a hitbox deve ser desenhada para cada barco
 * @param obstaculos Lista de obstáculos presentes no mapa
 * @param corrente Vetor da corrente marítima atual
 * @param barcosFila Lista de informação visual dos barcos a aguardar nos portos
 */
public record NavegacaoUpdate(List<String> barcosAtivos, List<Ponto> localizacaoBarcosAtivos, List<Obstaculo> obstaculos, Vetor corrente, List<String> barcosFila) {
}

