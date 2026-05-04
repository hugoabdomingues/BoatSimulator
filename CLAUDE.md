# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Test

This is an IntelliJ IDEA project (no Maven/Gradle). The intended workflow is to build and run tests through the IDE.

**Running tests from the command line** requires JUnit 5 jars on the classpath. JUnit 5.14.0 and JUnit Platform are declared in `BoatSimulator.iml` as module-level dependencies. From IntelliJ: right-click any test class or the `engine/test/` directory and choose **Run**.

To run a single test class or method, use the IDE's gutter icons or the Run Configuration dialog.

The `Mapa` singleton must be reset between tests — every test suite that touches `Mapa` calls `Mapa.resetInstancia()` in a `@BeforeEach`.

## Architecture

The engine is split into two packages inside `engine/`:

### `geometria/` — Mathematical primitives
Foundational geometry with no simulation dependencies:
- **`Ponto`** — 2D coordinate; distance calculations.
- **`Vetor`** — Direction/displacement vector; scalar operations and segment-intersection logic used for collision detection.
- **`SegmentoReta`** — Line segment; parametric intersection between two segments.
- **`Poligono` / `Circulo` / `Retangulo` / `Quadrado` / `Triangulo`** — Shapes used as obstacle geometry. `Circulo` solves intersection via the quadratic formula; `Poligono` iterates over edges.
- **`NodeDijkstra`** — Lightweight node wrapper for the Dijkstra pathfinding inside `Mapa`.

### `entity/` — Simulation domain
- **`Mapa`** *(Singleton)* — The simulation engine. Owns all boats, ports, obstacles, and the water current vector. `tick(deltaTempo)` is the main update loop: it advances boat positions, checks arrivals, and resolves collisions. Also hosts `calcularRotaDijkstra()` for finding the shortest obstacle-free path between two ports.
- **`Barco`** — A boat: position, linear velocity, departure/destination ports, assigned `Navegador`. Collision priority is lexicographic by boat code; the lower-priority boat calls `parar()` / `retomar()`.
- **`Porto`** — A port: holds a `PriorityQueue` of scheduled boats sorted by departure time. `verificarFila(tempoAtual)` returns the next boat ready to depart.
- **`Navegador`** — Route-level kinematics: computes position at time *t* accounting for multi-segment routes and water current.
- **`AutoPilot`** — Single-segment kinematics: time to traverse a segment and effective velocity vector given the current.
- **`Route`** — An ordered array of `Ponto` waypoints; computes total length and intersection tests against obstacles.
- **`Obstaculo`** *(abstract)* — Base class for `Ilha` (polygon obstacle) and `Tempestade` (circular storm, radius 0–3).
- **`NavegacaoUpdate`** *(record)* — DTO sent to the GUI layer with current boat positions, obstacle state, and current vector.

### `GUI/`
Currently an empty placeholder; the GUI has not been implemented yet.

### Key invariants
- Water current affects actual velocity: `v_actual = v_motor − current` (vector subtraction).
- `Mapa` is a singleton; tests must call `Mapa.resetInstancia()` to get a clean state.
- Dijkstra builds a graph of ports as nodes; edge weights are route lengths, and edges that pass through any obstacle are excluded.