package gui;

import entity.NavegacaoUpdate;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PainelSimulador extends JPanel {
    private NavegacaoUpdate ultimoUpdate;

    public PainelSimulador() {
        //Cor de fundo (Azul claro)
        setBackground(new Color(173, 216, 230)); 
    }

    /**
     * O motor do jogo vai chamar este método em cada tick para entregar os dados novos.
     */
    public void atualizar(NavegacaoUpdate update) {
        this.ultimoUpdate = update;
        //o repaint() diz ao java para voltar a desenhar tudo, pois os dados mudaram
        repaint(); 
    }

    /**
     * fazemos todos o desenhos neste método.
     * O Java chama este método automaticamente quando fazemos repaint().
     */
    @Override
    protected void paintComponent(Graphics g) {
        //limpa o ecrã com a cor de fundo (Azul)
        super.paintComponent(g); 
        
        //transforma o pincel (Graphics) num pincel moderno 2D (Graphics2D)
        Graphics2D g2d = (Graphics2D) g;

        if (ultimoUpdate == null) {
            return; //se ainda não recebemos dados da engine, não tentamos desenhar nada
        }

        // TEMOS DE ADICIONAR O DESENHO PASSO A PASSO ..... por fazer
        // desenharPortos(g2d);
        // desenharRotas(g2d);
        // desenharObstaculos(g2d);
        // desenharBarcos(g2d);
    }
}
