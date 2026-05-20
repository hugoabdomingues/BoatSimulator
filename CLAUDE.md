# CLAUDE.md — Simulador de Navegação Náutica (POO 2025/26)

## Visão Geral

Simulador de navegação náutica em Java com interface gráfica Swing.
A cada tick da simulação, os barcos movem-se ao longo de rotas calculadas por Dijkstra,
evitam obstáculos fixos (ilhas/polígonos) e móveis (tempestades/círculos), e resolvem
colisões entre si por ordem lexicográfica do código de viagem.

**Autores:** Ana Sofia Almeida 68747 · Renato Chumbinho 84804 · Hugo Domingues 86128
**Cadeira:** Programação Orientada por Objetos — Universidade do Algarve

---

## Estrutura de Pacotes

```
src/
├── geometria/          # Classes geométricas puras (sem lógica de negócio)
│   ├── Ponto.java
│   ├── Vetor.java
│   ├── SegmentoReta.java
│   ├── Circulo.java
│   ├── Poligono.java
│   ├── Triangulo.java
│   ├── Retangulo.java
│   ├── Quadrado.java
│   └── NodeDijkstra.java
├── entity/             # Lógica de negócio / engine da simulação
│   ├── Mapa.java           ← motor central (Singleton)
│   ├── Barco.java
│   ├── Porto.java
│   ├── Route.java
│   ├── Navegador.java
│   ├── AutoPilot.java
│   ├── Obstaculo.java      ← abstract
│   ├── Ilha.java           ← extends Obstaculo
│   ├── Tempestade.java     ← extends Obstaculo
│   ├── NavegacaoUpdate.java ← record DTO
│   └── CenarioLoader.java  ← utility class estática
├── gui/                # Interface gráfica Swing
│   ├── View.java           ← JFrame principal
│   ├── MapaView.java       ← JPanel que desenha o mapa
│   └── MenuView.java       ← JPanel do menu inicial
└── controller/
    └── Controller.java     ← MVC controller
```

---

## Padrões de Projeto

### 1. Singleton — `Mapa`
- Construtor `private`, acesso via `Mapa.getInstancia()`
- `resetInstancia()` existe **apenas para testes unitários** — nunca chamar em produção
- Toda a simulação vive nesta instância única

### 2. MVC — `Controller` + `View`/`MapaView`/`MenuView` + `entity/*`
- **Model:** tudo em `entity/` — o `Mapa` é o motor central
- **View:** `View`, `MapaView`, `MenuView` — só desenha, zero lógica
- **Controller:** único que chama `mapa.tick()` e `mapa.setCorrente()`
- O `MapaView` **nunca** acede ao `Mapa` diretamente — só conhece `NavegacaoUpdate`

### 3. DTO com record — `NavegacaoUpdate`
- Criado em `Mapa.getNavegUpdate()` a cada tick
- Imutável (record Java) — o GUI não pode alterar o estado da simulação
- Campos: `barcosAtivos`, `localizacaoBarcosAtivos`, `mostrarHitboxNavio`,
  `obstaculos`, `corrente`, `barcosFila`, `portos`, `rotas`, `tempoAtual`

### 4. Template Method — `Obstaculo` → `Ilha` / `Tempestade`
- `Obstaculo.verificaColisao(Route)` é abstract
- `Ilha` delega em `Poligono.intersect()`
- `Tempestade` delega em `Circulo.intersectCirculo()`
- `Mapa` chama `verificaColisao()` sem saber o tipo concreto

---

## Loop Principal da Simulação

```
Controller.iniciarTemporizador()  →  Timer a cada 100ms
    └── mapa.tick(0.1)
            ├── tempoAtual += deltaTempo
            ├── para cada porto: porto.verificarFila(tempoAtual)
            │       └── se barco pronto: lancarBarcoSePossivel(porto, barco)
            │               └── calcularRotaDijkstra → new Navegador → naviosAtivos.add
            ├── processarNavios(deltaTempo)
            │       ├── barco.getEstado() == false (a navegar):
            │       │       tempoAViajar = tempoAtual - barco.getHora()
            │       │       novaPosicao = navegador.pontoDaRotaNoTempoT(vl, t, corrente)
            │       │       se null → barcosQueChegaram.add(barco)
            │       └── barco.getEstado() == true (parado):
            │               barco.setHora(hora + deltaTempo)  ← evita teletransporte
            ├── remover barcosQueChegaram de naviosAtivos
            └── verificar colisões:
                    ├── todos: setEstado(false)              ← anti-deadlock
                    └── pares: barco1.intersetarBarco(barco2)
    └── NavegacaoUpdate update = mapa.getNavegUpdate()
    └── mapaView.atualizar(update)
    └── janela.repaint()
```

---

## Pathfinding — Dijkstra

**Método público:** `Mapa.calcularRotaDijkstra(Ponto partida, Ponto destino)`

### Fase 1 — Construção do grafo
`obterEstradasSegurasComIntersecoes(partida, destino)`:
1. `getEstradasSeguras()` — filtra segmentos de todas as rotas que não colidem com obstáculos
2. Para cada par de estradas seguras, calcula interseções com `estradaA.intersectRotas(estradaB)`
3. Adiciona pontos de interseção, `partida` e `destino` às estradas que os contêm (`adicionarPontoSeValido`)
4. `fragmentarTodasAsEstradas()` → `gerarSegmentosFragmentados()`: ordena pontos por `distanciaAoLongoDoSegmento()` e parte cada estrada em segmentos menores

Resultado: lista de `SegmentoReta` curtos onde cada interseção de rotas é um nó navegável.

### Fase 2 — Estruturas de dados
- `PriorityQueue<NodeDijkstra>` — explora sempre o nó de menor custo
- `Map<Ponto, Double> distancias` — menor distância conhecida desde a partida
- `Map<Ponto, Ponto> deOndeVim` — para reconstruir o caminho

### Fase 3 — Ciclo principal
1. `fila.poll()` → `pontoOndeEstou`
2. Se `equals(destino)` → `reconstruirRota(destino, deOndeVim)` e retorna
3. Verifica `distanciaPercorrida <= distancias.get(pontoOndeEstou)` (evita reprocessar nós obsoletos)
4. Para cada estrada: `obterVizinho(estrada, pontoOndeEstou)` devolve o extremo oposto
5. Se caminho mais curto → actualiza `distancias`, `deOndeVim`, adiciona à fila

**Retorna `null`** se não existir caminho navegável.

### Métodos auxiliares internos (todos `private`)
| Método | Descrição |
|--------|-----------|
| `reconstruirRota(destino, deOndeVim)` | Percorre `deOndeVim` do destino à origem para gerar a `Route` |
| `obterVizinho(estrada, pontoAtual)` | Devolve o extremo oposto se `pontoAtual` for um dos extremos |
| `isCaminhoLivre(a, b)` | Cria `Route` temporária e verifica colisão com todos os obstáculos |
| `getEstradasSeguras()` | Filtra segmentos das rotas sem obstáculos |
| `obterEstradasSegurasComIntersecoes(p, d)` | Versão completa com fragmentação nos pontos de interseção |
| `adicionarPontoSeValido(pontos, estrada, p...)` | Varargs — adiciona ponto só se estiver no segmento e não for duplicado |
| `pontoEstaNoSegmento(p, seg)` | `abs(d(A,P) + d(P,B) - d(A,B)) < 1e-9` |
| `distanciaAoLongoDoSegmento(seg, p)` | `d(pontoA, p)` — ordena pontos no segmento |
| `fragmentarTodasAsEstradas(estradas, map)` | Agrega fragmentos de todas as estradas |
| `gerarSegmentosFragmentados(estrada, pontos)` | Ordena e cria `SegmentoReta` entre consecutivos |
| `lancarBarcoSePossivel(porto, barco)` | Calcula rota e adiciona barco aos naviosAtivos |
| `processarNavios(deltaTempo)` | Move todos os barcos e devolve os que chegaram |
| `obterLocalizacaoPorto(nome)` | Devolve `Ponto` do porto com aquele nome |

---

## Colisões entre Barcos

**Onde:** dentro de `Mapa.tick()`, após `processarNavios()`

```java
// anti-deadlock: reset de todos primeiro
for (Barco barco : this.naviosAtivos) barco.setEstado(false);

// verificação par a par
for (Barco barco1 : this.naviosAtivos)
    for (Barco barco2 : this.naviosAtivos)
        if (barco1 != barco2) barco1.intersetarBarco(barco2);
```

**`Barco.intersetarBarco(outro)`:**
```java
public boolean intersetarBarco(Barco outro) {
    double distancia = this.posicaoAtual.distanciaPontos(outro.getPosicaoAtual());
    if (distancia < 2.0) {                         // raio 1 + raio 1
        if (this.codigo.compareTo(outro.getCodigo()) < 0) {
            this.setEstado(true);                  // lexicograficamente menor espera
        }
        return true;
    }
    return false;
}
```

**Anti-teletransporte:** enquanto `espera == true`:
```java
barco.setHora(barco.getHora() + deltaTempo);
```

**Hitbox visual:** em `getNavegUpdate()`, a hitbox é mostrada quando `distancia < 4.0`
(raio visual maior que o raio de colisão real de 2.0 — para feedback visual antecipado).

**Colisões com obstáculos:** resolvidas antes da partida via `isCaminhoLivre()` no Dijkstra.

---

## Corrente Marítima — Como Afeta o Movimento

A corrente é passada do `Mapa` ao `Navegador` em cada `processarNavios()`:
```java
navegador.pontoDaRotaNoTempoT(barco.getVl(), tempoAViajar, this.corrente)
```

Quando o barco está a meio de um segmento, `pontoSegmentoCorrente()` usa `AutoPilot`:
```java
AutoPilot piloto = new AutoPilot(pontoA, pontoB);
double tempoDeAB = piloto.time(vl);
Vetor velocidadeReal = piloto.speed(corrente, tempoDeAB).add(corrente);
Vetor AtePontoT = velocidadeReal.mult(tempoDeAB * percentDistPercorrer);
return pontoA.move(AtePontoT);
```

Se `corrente == null`, usa deslocamento simples sem compensação.

**`AutoPilot.speed(corrente, t)`:** `v = (deslocamento / t) - corrente`
O barco compensa a corrente para manter velocidade linear constante.

---

## Geração de Tempestades

**Método:** `Mapa.gerarTempestades(int tempestadesRotas, int tempestadesAleatorias)`

- **Nas rotas:** escolhe rota aleatória, ponto intermédio (índice 1 a length-2), raio ∈ [1.0, 3.0)
- **Aleatórias:** posição aleatória (x: 5–95, y: 5–65), raio ∈ [1.0, 3.0)
- Chamado em `CenarioLoader`: `mapa.gerarTempestades(2, 2)` → 4 tempestades no total
- `Tempestade` lança exceção se `raio >= 3.0` — `rand.nextDouble()` é exclusivo, nunca atinge 3.0

---

## Código do Barco

Formato: `<nomePorto><horaPartidaComoInt>` — ex: `"A1"`, `"C2"`, `"B8"`

```java
this.codigo = this.partida + (int)this.horaDePartida;
```

O `Porto` passa só `this.nome`. A `hora` é `double` no construtor mas cast para `int` no código.
`setHora()` ignora silenciosamente valores fora de [0, 24].

---

## Hitbox dos Barcos

Campo `hitbox: Circulo` em `Barco`, sempre sincronizado com `posicaoAtual`:
```java
public void setPosicaoAtual(Ponto novaPosicao) {
    this.posicaoAtual = novaPosicao;
    this.hitbox = new Circulo(novaPosicao, 1);  // raio fixo = 1
}
```

---

## Vetor — Atenção ao Vetor Nulo

`Vetor` lança `IllegalArgumentException` se construído com `Ponto(0,0)`.

Guard obrigatório em `Navegador.pontoDaRotaNoTempoT()`:
```java
if (distanciaRestante < 1e-9) return pontoA;
```
**Nunca remover este guard.**

`gerarSegmentosFragmentados()` protege com `!p1.equals(p2)` antes de criar `SegmentoReta`.

---

## Escala do GUI

```
1 unidade lógica = 10 píxeis  (SCALE = 10 em MapaView)
Janela: 1000×700 px  →  espaço lógico: 100×70 unidades
```

---

## Cenário — CenarioLoader

**Portos:** A(10,10)  B(10,60)  C(90,60)  D(90,10)

**Ilhas (não intersectam nenhuma rota):**
| Ilha | Forma | Zona |
|------|-------|------|
| Ilha1 | Pentágono | topo-centro (x=35–65, y=2–25) |
| Ilha2 | Retângulo | margem esquerda (x=12–23, y=33–48) |
| Ilha3 | Pentágono | fundo-centro (x=35–65, y=57–68) |
| Ilha4 | Retângulo | margem direita (x=77–88, y=33–48) |

**Rotas (6, com 4 pontos = 3 segmentos cada):**
| Rota | Trajeto | Intersecções |
|------|---------|-------------|
| R1 | A→B (x=6) | — |
| R2 | C→D (x=94) | — |
| R3 | A→D (y=35, horizontal) | R5 e R6 |
| R4 | B→C (y=55, horizontal) | — |
| R5 | A→C (diagonal ↘) | R3 e R6 em (50,35) |
| R6 | B→D (diagonal ↗) | R3 e R5 em (50,35) |

**Barcos agendados:**
```
A: T=1→C vl=5.0   (usa R5 — passa pelo cruzamento central)
B: T=1→D vl=5.0   (usa R6 — cruza R5 em (50,35) → demonstra colisão)
A: T=4→D vl=6.0   (usa R3 — cruza R5 e R6)
C: T=2→A vl=5.0
D: T=6→B vl=4.5
B: T=8→C vl=4.5
```

**Tempestades:** 2 nas rotas + 2 aleatórias via `mapa.gerarTempestades(2, 2)`.

---

## Invariantes

| Classe | Invariante |
|--------|-----------|
| `Mapa` | Singleton; `tempoAtual` nunca recua |
| `Barco` | `hora ∈ (0,24] && vl > 0`; `setHora` ignora valores fora de [0,24] |
| `Vetor` | `modulo() > 0` — nunca `Ponto(0,0)` |
| `SegmentoReta` | `pontoA != pontoB` |
| `Poligono` | ≥ 3 vértices |
| `Triangulo` | exatamente 3 vértices, não colineares |
| `Retangulo` | 4 vértices, lados opostos iguais, diagonais iguais |
| `Quadrado` | Retangulo + lados adjacentes iguais |
| `Circulo` | `raio > 1e-9` |
| `Tempestade` | `centro != null && raio > 0 && raio < 3` |
| `Obstaculo` | `id != null && !id.isEmpty()` |
| `Navegador` | `rota != null` |
| `Porto` | `nome != null && !nome.isEmpty() && localizacao != null` |

Doubles são iguais se `|d - g| < 1e-9`. **Nunca usar `==` para doubles.**

---

## O que NÃO fazer

- Não criar segunda instância de `Mapa` — usar sempre `Mapa.getInstancia()`
- Não chamar `resetInstancia()` fora de testes
- Não remover o guard `if (distanciaRestante < 1e-9)` em `Navegador`
- Não construir `Vetor` com resultado `(0,0)`
- Não aceder ao `Mapa` a partir do `MapaView` — usar sempre o DTO
- Não modificar `NavegacaoUpdate` após criação (record — imutável)
- Não passar código completo ao construtor de `Barco` — passar só nome do porto
- Não usar `raio >= 3.0` em `Tempestade`
- Não remover `!p1.equals(p2)` em `gerarSegmentosFragmentados()`
- Não alterar `setHora()` para aceitar valores negativos — o anti-teletransporte depende disso
