package entity;

import geometria.Ponto;

/**
 * Classe responsável por criar os objetos no mapa
 * @author Ana Sofia Almeida 68747
 * @author Renato Chumbinho 84804
 * @author Hugo Domingues 86128
 */
public class CenarioLoader {

    /**
     * Carrega o cenário no mapa.
     * Espaço lógico: 100 x 70 unidades (1 unidade = 10 px).
     *
     * Portos: A(10,10) B(10,60) C(90,60) D(90,10)
     *
     * Ilhas (fora de todas as rotas):
     *   Ilha1: triângulo no topo centro  (x=48-52, y=2-6)
     *   Ilha2: quad na margem esquerda   (x=2-4,   y=32-38)
     *   Ilha3: triângulo no fundo centro (x=48-52, y=63-68)
     *   Ilha4: quad na margem direita    (x=96-98, y=32-38)
     *
     * Rotas (cada uma com 4 pontos = 3 segmentos):
     *   R1: A→B  (margem oeste)
     *   R2: C→D  (margem este)
     *   R3: A→D  (horizontal pelo centro, y=35)   ← intersecta R5 e R6
     *   R4: B→C  (horizontal pelo sul, y=55)
     *   R5: A→C  (diagonal descendente)            ← intersecta R3 e R6
     *   R6: B→D  (diagonal ascendente)             ← intersecta R3 e R5
     *
     * Tempestades sobre as rotas:
     *   T1(50,35, r=2.5): intersecta R3, R5 e R6
     *   T2(70,35, r=2.5): intersecta R3 e R6
     *
     * @param mapa A instância do Mapa onde o cenário vai ser carregado
     */
    public static void carregarCenario(Mapa mapa) {
        // ── PORTOS ──────────────────────────────────────────────────────
        Porto a = new Porto("A", new Ponto(10, 10));
        Porto b = new Porto("B", new Ponto(10, 60));
        Porto c = new Porto("C", new Ponto(90, 60));
        Porto d = new Porto("D", new Ponto(90, 10));

        mapa.addPorto(a);
        mapa.addPorto(b);
        mapa.addPorto(c);
        mapa.addPorto(d);

        // ── ILHAS (obstáculos fixos – NÃO intersectam nenhuma rota) ────
        // Ilha1 – grande pentágono no topo-centro (x=35-65, y=2-24)
        // R3 está em y=35, R5/R6 entram nesta zona em y≥28 → margem segura
        mapa.addObstaculo(new Ilha("Ilha1", new Ponto[]{
                new Ponto(35,  2),
                new Ponto(65,  2),
                new Ponto(65, 22),
                new Ponto(50, 25),
                new Ponto(35, 22)
        }));

        // Ilha2 – grande retângulo na margem esquerda-centro (x=12-23, y=33-48)
        // R3 em x=23 passa em y=26 (acima da ilha); R6 em x=23 passa em y=50 (abaixo)
        mapa.addObstaculo(new Ilha("Ilha2", new Ponto[]{
                new Ponto(12, 33),
                new Ponto(23, 33),
                new Ponto(23, 48),
                new Ponto(12, 48)
        }));

        // Ilha3 – grande pentágono no fundo-centro (x=35-65, y=57-68)
        // R4 está em y=55 → margem segura de 2 unidades
        mapa.addObstaculo(new Ilha("Ilha3", new Ponto[]{
                new Ponto(35, 57),
                new Ponto(65, 57),
                new Ponto(65, 68),
                new Ponto(50, 65),
                new Ponto(35, 68)
        }));
        // Ilha4 – grande retângulo na margem direita-centro (x=77-88, y=33-48)
        // R3 em x=77 passa em y=26 (acima); R5 em x=77 passa em y=52 (abaixo)
        mapa.addObstaculo(new Ilha("Ilha4", new Ponto[]{
                new Ponto(77, 33),
                new Ponto(88, 33),
                new Ponto(88, 48),
                new Ponto(77, 48)
        }));

        // ── ROTAS (6 rotas, cada uma com 4 pontos = 3 segmentos) ────────

        // R1: A → B  (margem oeste, desvia para x=6)
        mapa.addRota(new Route(new Ponto[]{
                new Ponto(10, 10),
                new Ponto( 6, 28),
                new Ponto( 6, 43),
                new Ponto(10, 60)
        }));

        // R2: C → D  (margem este, desvia para x=94)
        mapa.addRota(new Route(new Ponto[]{
                new Ponto(90, 60),
                new Ponto(94, 43),
                new Ponto(94, 28),
                new Ponto(90, 10)
        }));

        // R3: A → D  (passa pelo centro a y=35) ← intersecta R5 e R6
        mapa.addRota(new Route(new Ponto[]{
                new Ponto(10, 10),
                new Ponto(30, 35),
                new Ponto(70, 35),
                new Ponto(90, 10)
        }));

        // R4: B → C  (passa pelo sul a y=55)
        mapa.addRota(new Route(new Ponto[]{
                new Ponto(10, 60),
                new Ponto(30, 55),
                new Ponto(70, 55),
                new Ponto(90, 60)
        }));

        // R5: A → C  (diagonal descendente) ← intersecta R3 e R6 em (50,35)
        mapa.addRota(new Route(new Ponto[]{
                new Ponto(10, 10),
                new Ponto(35, 26),
                new Ponto(50, 35),
                new Ponto(90, 60)
        }));

        // R6: B → D  (diagonal ascendente) ← intersecta R3 e R5 em (50,35)
        mapa.addRota(new Route(new Ponto[]{
                new Ponto(10, 60),
                new Ponto(35, 44),
                new Ponto(50, 35),
                new Ponto(90, 10)
        }));



        // ── BARCOS AGENDADOS ─────────────────────────────────────────────
        // Barcos em rotas diferentes que se vão encontrar no cruzamento central
        a.agendarBarco("T=1,C,5.0");  // usa R5 (A→C) – passa pelo cruzamento central
        b.agendarBarco("T=1,D,5.0");  // usa R6 (B→D) – cruza R5 no centro
        a.agendarBarco("T=4,D,6.0");  // usa R3 (A→D) – cruza R5 e R6
        c.agendarBarco("T=2,A,5.0");
        d.agendarBarco("T=6,B,4.5");
        b.agendarBarco("T=8,C,4.5");
        // ── TEMPESTADES ────────────────────────────────
        mapa.gerarTempestades(2, 2);
    }
}
