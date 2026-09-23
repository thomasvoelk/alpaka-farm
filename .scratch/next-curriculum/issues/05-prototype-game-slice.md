# Prototype the Java 2D tile-farm game slice

Type: prototype
Status: resolved
Blocked by: 01

## Question

Using the engine chosen in ticket 01, build a cheap, rough prototype: a tilemap rendering of a pen/herd, a walkable player character, and clicking an alpaca sprite shows its real data (from the `farm` Postgres db, however roughly wired). No economy/time systems — just enough to react to "does this feel like the right level of game."

Use the `/prototype` skill. Link the prototype as an asset on this ticket.

## Comments

Prototype built and smoke-tested (2026-09-23): a LibGDX desktop app —
tilemap (real `TiledMap`/`OrthogonalTiledMapRenderer` API) with two fenced
pens, a walkable player (WASD/arrows), and the `North Pasture Herd`'s 6 real
alpacas (Pen A ×3, Pen B ×3) read live off the `farm` Postgres db via plain
JDBC (that's ticket 02's jOOQ scaffold's job, not needed for this throwaway).
Clicking an alpaca selects it and shows a data panel (name, pen, species,
birth date, weight, fleece color), plus logs the same to console.

Verified headlessly: builds clean, launches on LWJGL3/OpenGL, connects to
the live db, loads and places all 6 animals across both pens, runs 10+
seconds with no exceptions. This session's sandbox has no screen-recording
permission, so the actual click-to-inspect feel and visuals are **not yet
seen by anyone** — unlike ticket 04, where Thomas ran the dashboard
prototype live himself. Thomas needs to run this one the same way before
this ticket can resolve.

Full code + run instructions: `prototype/game-slice` branch (commit
`71954ff`), `prototype-game/README.md`. Run with:
```
cd ../sql-curriculum && docker compose up -d   # if not already running
git worktree add ../alpaka-farm.prototype-game-slice prototype/game-slice
cd ../alpaka-farm.prototype-game-slice/prototype-game
mvn clean package
java -XstartOnFirstThread -jar target/farm-game-prototype.jar
```

## Answer

Thomas's live reaction (2026-09-23, run directly from the `prototype/game-slice`
worktree): **positive — "I like it very much (aside from the graphics)."**
He walked the player around and clicked an alpaca; the click-to-inspect
interaction worked as expected. The graphics caveat is the expected
placeholder-color-square look of a throwaway prototype (no art assets, per
the `/prototype` skill's "skip the polish" rule) — not a reservation about
the tilemap-walk-click concept itself, which is what this ticket's question
was actually testing.

Like ticket 04, this only captures Thomas's own reaction — the apprentice
hasn't seen either prototype yet; that stays open for whoever builds the
real curriculum. Game path is a live candidate going into the path decision
(06), alongside the dashboard path.
