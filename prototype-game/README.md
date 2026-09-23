# PROTOTYPE — Java 2D farm-game slice

Throwaway artifact for wayfinder ticket "Prototype the Java 2D tile-farm game slice"
(`.scratch/next-curriculum/issues/05-prototype-game-slice.md` on `main`).

Answers: "does this feel like the right level of game" for the LibGDX
tile-farm path (ticket 01's engine pick), as a data point for the path
decision (ticket 06) against the Spring+Angular dashboard prototype (ticket 04).
Not production code — no tests, minimal error handling, plain JDBC instead of
the eventual jOOQ scaffold (ticket 02), hand-drawn colored-square "sprites"
instead of art.

## What it is

A single-screen LibGDX desktop app:

- A tilemap (built via `TiledMap`/`TiledMapTileLayer`/`OrthogonalTiledMapRenderer`
  — the real API ticket 01 picked LibGDX for, not a hand-rolled grid) showing
  two fenced pens (brown floor, dark-brown fence ring) on grass.
- A walkable player (blue square) — move with **WASD or arrow keys**.
- The `North Pasture Herd`'s 6 real alpacas (Pen A ×3, Pen B ×3), read live
  off the `farm` Postgres db via plain JDBC, placed inside their pens.
- **Click an alpaca** to select it (it grows slightly) and see a data panel
  in the bottom-left corner: name, pen, species, birth date, weight, fleece
  color. Each click also logs the same data to the console.

No economy, no day/night, no crops/seasons — out of scope per the map.

## How to run

1. Start the `farm` Postgres db (from the sibling `sql-curriculum` repo):
   ```
   cd ../sql-curriculum && docker compose up -d
   ```
2. From `prototype-game/`:
   ```
   mvn clean package
   java -XstartOnFirstThread -jar target/farm-game-prototype.jar
   ```
   (`-XstartOnFirstThread` is required by LWJGL3 on macOS.)

A window opens at 800×600. Walk around with WASD/arrows, click any alpaca in
either pen to inspect it.

## Status

Built, and smoke-tested headlessly (2026-09-23): launches cleanly on
LWJGL3/OpenGL, connects to the live `farm` Postgres db, loads and places all
6 `North Pasture Herd` animals across both pens, runs stably for 10+ seconds
with no exceptions. This session's sandbox has no screen-recording
permission, so the click-to-inspect interaction and visual feel are
**not yet visually verified** — Thomas needs to actually run it and play
with it to react to "does this feel like the right level of game," the way
he ran ticket 04's dashboard prototype live. Do not treat this prototype as
"reacted to" until that happens.
