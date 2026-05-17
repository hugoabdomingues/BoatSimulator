package gui;

import entity.Ilha;
import entity.NavegacaoUpdate;
import entity.Obstaculo;
import entity.Route;
import geometria.Poligono;
import geometria.Ponto;
import geometria.Circulo;

import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * Classe principal da interface gráfica da simulação.
 * Recebe um NavegacaoUpdate do Controller (padrão MVC) e pinta o estado atual do mapa.
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 * @version 14-05-2026
 */
public class MapaView extends JPanel {
    //definimos uma escala
    //sem esta escala o raio de colisão dos barcos seria interpretado como 1 pixel e n iria funcionar como susposto. Assim raio = 1 é na verdade igual a 10 pixeis
    private static final int SCALE = 10; // 1 unidade lógica = 10 píxeis
    private NavegacaoUpdate lastUpdate; // ultimo estado recebido do Controller via atualizar()

    /**
     * Construtor do MapaView
     */
    public MapaView() {
        this.setBackground(new Color(173, 216, 230)); // cor de "água"
    }

    /**
     * Recebe o estado atual do mapa vindo do Controller e guarda-o para o próximo repaint.
     * @param update O DTO com todos os dados necessários para desenhar o mapa (barcos, rotas, etc.)
     */
    public void atualizar(NavegacaoUpdate update) {
        this.lastUpdate = update;
    }

    /**
     * Desenha o painel com o último estado recebido.
     * Chamado automaticamente pelo Swing após cada janela.repaint() no Controller.
     * @param g Graphics, o contexto gráfico fornecido pelo Swing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Limpa a tela antes de desenhar o novo frame
        if (lastUpdate == null) return; // ainda nao recebemos nenhum update, nao ha nada para desenhar
        Graphics2D g2d = (Graphics2D) g; // fazemos cast do pincel classico para a sua versão mais moderna
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Liga o Anti-aliasing para as formas nao parecerem pixelizadas
        paintRotas(g2d);
        paintIlhas(g2d);
        paintBarcos(g2d);
        paintRelogio(g2d);
        paintTempestades(g2d);

    }

    /**
     * Desenha o relógio com o tempo atual da simulação no canto superior esquerdo.
     * @param g2d Graphics2D
     */
    private void paintRelogio(Graphics2D g2d) {
        //definimos a cor do pincel neste momento (antes de começarmos a desenhar)
        g2d.setColor(Color.BLACK);
        //parametros: fillRoundRect(x, y, largura, altura, arcoX, arcoY)
        g2d.fillRoundRect(8, 8, 110, 28, 8, 8);
        //mudamos a cor para branco
        g2d.setColor(Color.WHITE);
        //escolhemos a fonte da escrita
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString(String.format("T = %5.1f", lastUpdate.tempoAtual()), 14, 27); //metemos o 5 para dar um espaço vazio entre o = e o n+úmero
    }

    /**
     * Desenha todos os barcos ativos no mapa com a sua posição atual.
     * @param g2d Graphics2D
     */
    private void paintBarcos(Graphics2D g2d) {
        List<Ponto> posicoes = lastUpdate.localizacaoBarcosAtivos();
        List<Boolean> hitboxes = lastUpdate.mostrarHitboxNavio();
        for (int i = 0; i < posicoes.size(); i++) {
            // multiplicamos por SCALE para converter unidades lógicas em píxeis
            int cx = (int) Math.round(posicoes.get(i).getX() * SCALE);
            int cy = (int) Math.round(posicoes.get(i).getY() * SCALE);

            //hitbox visível só quando o barco está em zona de colisão (raio 4 lógico = 40 píxeis de diâmetro)
            if (hitboxes.get(i)) {
                int r = 20; // raio em píxeis (2 unidades lógicas × SCALE)
                g2d.setColor(new Color(255, 0, 0, 80));
                g2d.fillOval(cx - r, cy - r, r * 2, r * 2);
                g2d.setColor(Color.RED);
                g2d.drawOval(cx - r, cy - r, r * 2, r * 2);
            }

            // Barco: casco trapezoidal + mastro + vela triangular
            // Casco
            int[] cxPts = {cx - 10, cx + 10, cx + 7, cx - 7};
            int[] cyPts = {cy,      cy,      cy + 6,  cy + 6};
            g2d.setColor(new Color(101, 67, 33));
            g2d.fillPolygon(cxPts, cyPts, 4);
            g2d.setColor(new Color(60, 30, 10));
            g2d.setStroke(new BasicStroke(1));
            g2d.drawPolygon(cxPts, cyPts, 4);
            // Mastro
            g2d.setColor(new Color(60, 30, 10));
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawLine(cx, cy, cx, cy - 15);
            // Vela
            int[] sxPts = {cx, cx + 12, cx};
            int[] syPts = {cy - 15, cy - 5, cy};
            g2d.setColor(new Color(245, 230, 180));
            g2d.fillPolygon(sxPts, syPts, 3);
            g2d.setColor(new Color(180, 160, 100));
            g2d.setStroke(new BasicStroke(1));
            g2d.drawPolygon(sxPts, syPts, 3);
        }
    }

    /**
     * Desenha todas as rotas do mapa
     * @param g2d GraphicsG2D
     */
    private void paintRotas(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(1));
        for (Route rota : lastUpdate.rotas()) {
            Ponto[] pontos = rota.getPontos();
            for (int i = 1; i < pontos.length; i++) {
                // convertemos cada ponto de unidades lógicas para píxeis com o SCALE
                g2d.drawLine(
                    (int)(pontos[i-1].getX() * SCALE), (int)(pontos[i-1].getY() * SCALE),
                    (int)(pontos[i].getX()   * SCALE), (int)(pontos[i].getY()   * SCALE)
                );
            }
            for (Ponto p : pontos) {
                int px = (int)(p.getX() * SCALE);
                int py = (int)(p.getY() * SCALE);
                g2d.fillOval(px - 4, py - 4, 8, 8);
            }
        }
    }

    /**
     * Desenha todas as ilhas do mapa como polígonos preenchidos.
     * @param g2d Graphics2D
     */
    private void paintIlhas(Graphics2D g2d) {
        for (Obstaculo obstaculo : lastUpdate.obstaculos()) {
            if (!(obstaculo instanceof Ilha ilha)) continue; // só processamos Ilhas

            Ponto[] vertices = ilha.getIlha().getVertices();

            // construimos arrays de coordenadas x e y em píxeis para o fillPolygon / drawPolygon
            int[] xs = new int[vertices.length];
            int[] ys = new int[vertices.length];
            for (int i = 0; i < vertices.length; i++) {
                xs[i] = (int) Math.round(vertices[i].getX() * SCALE);
                ys[i] = (int) Math.round(vertices[i].getY() * SCALE);
            }

            // preenchimento da ilha
            g2d.setColor(new Color(139, 90, 43)); // castanho-areia
            g2d.fillPolygon(xs, ys, vertices.length);

            // contorno da ilha
            g2d.setColor(new Color(80, 45, 10)); // castanho escuro
            g2d.setStroke(new BasicStroke(2));
            g2d.drawPolygon(xs, ys, vertices.length);
        }
    }

    /**
     * Desenha todas as Tempestades do mapa
     * @param g2d Graphics2D
     */
    private void paintTempestades(Graphics2D g2d) {

        Color corTempestade = new Color(70, 130, 180, 60);
        Color bordaTempestade = new Color(70, 130, 180, 150);

        for (entity.Obstaculo obs : lastUpdate.obstaculos()) {
            if (obs instanceof entity.Tempestade) {
                entity.Tempestade t = (entity.Tempestade) obs;

                // É formado o círculo
                Circulo c = t.getForma();
                double raio = c.getRaio();
                double x = c.getCentro().getX();
                double y = c.getCentro().getY();

                int rPixel = (int) (raio * SCALE);
                int diametro = rPixel * 2;
                int px = (int) (x * SCALE) - rPixel;
                int py = (int) (y * SCALE) - rPixel;

                // Preencher
                g2d.setColor(corTempestade);
                g2d.fillOval(px, py, diametro, diametro);

                // Desenhar o limite
                g2d.setColor(bordaTempestade);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawOval(px, py, diametro, diametro);
            }
        }


    }
}
