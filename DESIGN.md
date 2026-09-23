# Design doc — The Open Pasture (farm-game curriculum, phase 1)

Planning notes from the design session that produced this repo's scaffold. For Thomas's reference across sessions — not learner-facing (learner-facing content is `curriculum/`).

Produced by [Draft the phase 1 (game curriculum) DESIGN.md](.scratch/next-curriculum/issues/07-draft-phase1-design-doc.md), the closing ticket of the [next-curriculum wayfinder map](.scratch/next-curriculum/map.md) — see that map for the full decision trail this doc draws on.

## Who this is for

The same apprentice as the other two curricula: training as a German "Fachinformatiker/in für Anwendungsentwicklung" (FIAE). Coming into this one with Java, TDD, and SQL under her belt (`tv-old-mac-donald-had-a-farm-new-and-better`, `sql-curriculum`), plus some HTML — but zero game-dev experience and no prior exposure to a rendering/game-loop style of programming. Mentor check-ins (phase-end demos) carry over from both prior curricula.

This is **phase 1** of a two-phase plan (see the map's destination): this game curriculum first, a Spring MVC+REST/Angular dashboard curriculum second. The two share one persistence bridge but are otherwise independent — this doc covers phase 1 only.

## What's deliberately different from — and the same as — the prior curricula

**Same as the Java curriculum:** this is Java, so it stays **TDD** — red→green→refactor, one commit per green test, same `feat:`/`test:`/`refactor:`/`fix:` prefixes, same phase-end mentor demo. This was decided going into the map (see the map's Notes) specifically so she isn't learning a second workflow at the same time as a new domain (game programming). Lesson format also follows the Java curriculum's shape (Story → test list → Acceptance Criteria → Hints → phase-end demo), not the SQL curriculum's "Expected Result" self-check — see [Self-check in a live app](#self-check-in-a-live-app) for why.

**Different from both prior curricula:** the domain. Neither Java nor SQL prepared her for a render loop, real-time keyboard input, or screen-space coordinates — this is the "bigger stretch" phase 1 was chosen to be (see [Decide: dashboard vs. farm game as the next curriculum](.scratch/next-curriculum/issues/06-decide-dashboard-vs-game.md)). Two new tools also enter here that neither prior curriculum used: **LibGDX** (the engine) and **Tiled** (the map editor used to build the tilemap she'll load).

**Different from the SQL curriculum specifically:** persistence is not mentored here either — same treatment, different reason. The SQL curriculum *is* the mentored database curriculum; this one consumes a database that's already built, through a scaffold she never opens. See [The persistence bridge](#the-persistence-bridge).

## The persistence bridge

Per [jOOQ persistence bridge: mentored phase or provided scaffold?](.scratch/next-curriculum/issues/02-jooq-scaffold-or-mentored.md): **fully agent-provided, consumed as a black box.** Two pieces, both already sitting in the repo before lesson 1 starts:

1. **jOOQ codegen output** — the Maven codegen plugin run once against the `farm` schema (the same schema and running Postgres instance the SQL curriculum uses — see `sql-curriculum/db/schema.sql`), producing generated table/record classes.
2. **A hand-written repository class** (e.g. `AlpacaRepository`) wrapping the generated code with the handful of queries the game actually needs — modeled on the read query the prototype used directly (join `herds` → `pens` → `animals` for a named herd), but going through jOOQ instead of the prototype's raw JDBC.

**Schema:** reused as-is, read-only. This resolves the map's open question about whether the bridge needs new tables — it doesn't. The prototype proved the existing `staff`/`herds`/`pens`/`animals`/`shearing_records`/`fleece_sales` tables have everything a tilemap-and-inspect game needs (name, species, birth date, weight, fleece color, pen assignment); the game reads, never writes. No migration, no new columns, nothing added to the SQL curriculum's schema — the two curricula share the database read-only from this side.

**Load timing:** loaded once at startup, not re-queried per interaction. The prototype already does this (`FarmDataService.fetchHerdAnimals`, called once, not per click) and it's the right call for the final version too: the game doesn't mutate farm data, so there's nothing to go stale during a play session, and re-hitting Postgres on every click would be needless latency for no benefit. If a later phase adds any write path, this decision gets revisited — not expected in phase 1's scope.

She never opens the generated code or the repository class. It's there from lesson 1, the same way the Java curriculum's lesson 00 ("Explore the Barn") hands her a pre-written example to read before she writes her own — except here the pre-written part stays pre-written for the whole phase, not just lesson 0.

## Engine & project setup

**LibGDX**, desktop-only, per [Pick a Java 2D game engine for the farm-game prototype](.scratch/next-curriculum/issues/01-pick-java-2d-engine.md). Hand-assembled Maven coordinates (`com.badlogicgames.gdx:gdx` + `gdx-backend-lwjgl3` + the `natives-desktop` classifier) — LibGDX's official generator targets Gradle, so there's no generator to run; the prototype's `pom.xml` is the template (Java 17, `gdx.version` 1.14.2, `maven-shade-plugin` to produce a runnable jar, `Lwjgl3Launcher` as main class). `-XstartOnFirstThread` is required at runtime on macOS — worth calling out explicitly in lesson 1, since the error if it's missing is opaque.

**Project shape**, matching the Java curriculum's actual layout (not the prototype's flat one):

- Root-level Maven project (`pom.xml`, `src/`) — one continuously-evolving codebase the learner commits into across the whole phase, not a separate folder per lesson.
- `curriculum/phase-N-name/lesson-NN-name/README.md` — lesson instructions only, same as the Java curriculum. No code lives under `curriculum/`.
- A `solution` branch for reference, same convention as the Java curriculum.
- Persistence bridge (jOOQ generated code + repository) present in `src/` from the start, per the black-box treatment above.
- Package name `farm.game` (dropping `.prototype` from the prototype's `farm.prototype.game`).

Lesson 0 ("Explore the Pasture," mirroring "Explore the Barn") hands her a scaffolded skeleton — LibGDX bootstrapped, an empty window opening, the persistence repository wired in but unused — and has her run it, read it, and break/fix one small thing before building anything of her own. This is where the two new tools (LibGDX, and a first look at a `.tmx` file Tiled produces) get introduced as artifacts to explore, not yet to build.

## Scope tier

Validated by the prototype ([Prototype the Java 2D tile-farm game slice](.scratch/next-curriculum/issues/05-prototype-game-slice.md), branch `prototype/game-slice`) and Thomas's own reaction to running it ("I like it very much, aside from the graphics"):

**In scope:** a tilemap (real pens on grass, built in Tiled and loaded via LibGDX's `TiledMap`/`OrthogonalTiledMapRenderer`), a walkable player character (WASD/arrow keys, collision against pen fences), and clicking an alpaca sprite to see its real data pulled through the persistence bridge (name, pen, species, birth date, weight, fleece color).

**Out of scope** (per the map's Out of scope section — not to be re-litigated within this phase): any economy, day/night cycle, crops, seasons, or NPC dialogue. This is a tilemap-farm-view-plus-walkable-character tier, not a game-sim slice.

The graphics themselves stay simple — the prototype's placeholder colored-square sprites were flagged by Thomas as an expected artifact of a throwaway, not a reservation about the concept, and real curriculum art is a separate, later concern from this DESIGN.md (see [Not yet specified](#not-yet-specified)).

## Self-check in a live app

Resolves the map's open question on how "Expected Result"-style self-checking (the SQL curriculum's mechanism) carries into an interactive app: **it mostly doesn't, and that's fine — the Java curriculum's TDD model already has an answer.**

The reason the SQL curriculum needed a different mechanism is that SQL has no test framework in play — a query's result *is* the observable output, so comparing it against a fixed expected result is the natural check. A LibGDX game is the opposite: most of its interesting behavior (does the fence stop the player, does clicking a sprite's bounds select the right alpaca, does the data panel show the right fields) is logic that can be pulled out of the render loop and unit-tested directly, exactly like the Java curriculum already does for non-visual behavior.

So the split is:

- **Testable core, TDD'd like the Java curriculum**: movement/collision math (given a position, an input vector, and pen bounds, what's the new position — a pure function), click hit-testing (given a click point and a list of sprite bounds, which alpaca — if any — was hit), and the data-panel's field mapping (given an `Alpaca`, what's the display string). All of this is plain Java, testable with JUnit, no LibGDX runtime needed in the test.
- **Untested glue, same as both prior curricula already accept**: the actual `render()`/`draw()` calls, texture loading, and input-polling plumbing. Neither the Java nor SQL curriculum tests rendering or I/O wiring either — the Java curriculum's own lesson 00 is explicitly "read, run, break, and fix," not test-driven, and this project's equivalent glue gets the same treatment.
- **Phase-end demo carries the rest**: for behavior that's inherently visual (does walking around a tilemap *feel* right, does the click-to-inspect interaction feel responsive), the mentor demo already used by the Java curriculum (walk through a class, answer Reflect questions, show green tests) extends naturally to "show me the game running and walk me through what you built" — no new mechanism needed.

## Syllabus (first draft — 6 phases, ~20 lessons, ~10–12 weeks)

Sized against the Java curriculum's own granularity (24 lessons / 13 weeks) rather than the SQL curriculum's (32 lessons / 14 weeks) — closer analog, since this is TDD'd Java content of similar conceptual density, not a wide single-mental-model sweep like SQL. Like both prior curricula's syllabi, treat this as a working draft: lesson count/boundaries may shift once lessons are actually written (see the SQL curriculum's own note on this — it under- and over-split a couple of lessons in practice).

| Phase | Weeks | Lessons |
|---|---|---|
| 1 — Explore the Pasture | 1–2 | 00 Explore the Pasture (read/run/break the scaffold — engine, window, persistence bridge as given) · 01 The Game Loop (`render()`/`update()`, what runs every frame) · 02 Your First Sprite (drawing a texture at a position) · 03 Listening to the Keyboard (input polling, moving a square) |
| 2 — The Tilemap | 3–4 | 04 Meet Tiled (the editor, building a small map by hand — not code) · 05 Loading a Tilemap (`TiledMap`, `OrthogonalTiledMapRenderer`) · 06 Camera and Bounds (keeping the view inside the map) |
| 3 — Walking the Farm | 5–6 | 07 A Walkable Player (movement math as a **tested**, pure function) · 08 Fences Are Solid (collision against pen bounds — tested) · 09 Putting It on Screen (wiring the tested movement into the render loop) |
| 4 — Meet the Herd | 7–8 | 10 Reading the Repository (using the persistence bridge — a black box, no jOOQ code touched) · 11 Placing Alpacas in Their Pens (mapping `Alpaca` records to sprite positions) · 12 Species and Fleece Color (varying sprite appearance by real data — still placeholder shapes/colors, not art) |
| 5 — Click to Inspect | 9–10 | 13 Hit-Testing a Click (given a click point, which sprite — tested) · 14 Selecting an Alpaca (visual feedback on selection) · 15 The Data Panel (rendering the selected alpaca's fields) · 16 Logging for Debugging (console output alongside the panel, matching the prototype) |
| 6 — Polish & Review *(capstone)* | 11–12 | 17 Refactoring Pass (green tests as a safety net, same framing as the Java curriculum's own refactoring lesson) · 18 Naming and Theming (applying [visual identity](#visual-identity) — accent color, mascot touches) · 19 Grand Review (phase-end demo: walk the mentor through the running game) |

## Lesson skeleton

Same shape as the Java curriculum, unchanged: `Story` → test list → 🔴 failing test → 🟢 pass → 🔁 refactor → 💾 commit, repeated per behavior within the lesson. **Acceptance Criteria** stated up front, **Hints** (3-tier escalating) available on demand, a Socratic Copilot/Claude prompt for when she's stuck after hints, and a phase-end demo at the close of each phase (see [Self-check in a live app](#self-check-in-a-live-app) for why visual/rendering behavior leans on the demo rather than an "Expected Result" block).

One addition specific to this curriculum: lessons that touch rendering or input (most of Phases 1–2, and the "wiring" lessons in 3 and 5) should say explicitly, in the lesson's own text, which part is being TDD'd and which part is glue she just writes and runs — so the testable/untested split above is visible to her, not just to this doc.

## Visual identity

First-pass proposal, not yet seen by the apprentice (same caveat both prototypes carry — this stays open until she reacts to it, same as the map's Not yet specified note on theming flagged before this ticket). Aims for the same "recognizable sibling, not identical twin" relationship the SQL curriculum struck with the Java one:

- **Accent color**: a grass/pasture green, distinct from the Java curriculum's grape purple and the SQL curriculum's denim blue — and thematically apt, since this curriculum is the one that actually puts her *outside*, walking the pasture, rather than in a barn or at a database console.
- **Mascot**: a boot-print or small compass mark, keeping the four-corner-accent-dot device the SQL curriculum's mark reused from the Java curriculum's alpaca mark, for family resemblance across all three.
- **Name**: "The Open Pasture," continuing the pattern of a curriculum-specific name distinct from the repo name (parallel to "The Data Barn"). Working title — revisit once she's seen the game running.

No design-system asset work is scoped in this doc (no shared `style.css`/mark SVG produced here) — that's execution, for whoever starts building Phase 1's lessons, same boundary the map already draws around this ticket (see the map's Notes: this ticket is scoped to the DESIGN.md, not to authoring).

## Theme / narrative

Continues the same world and timeline as both prior curricula: Farmer Fiona's farm, now far enough along that she wants to *see* it, not just query it. In-fiction framing: the farm's records live in the database the SQL curriculum built against; this curriculum builds Fiona a walkable, visual map of that same farm — same animals, same pens, same data, viewed from a new angle. No new characters or plot beats needed; this is a viewing tool for the existing farm, not a new chapter of it.

## Not yet specified

Carried forward from the map, still genuinely open after this doc — either because they're execution-time decisions (belong to whoever authors the lessons, not this design pass) or because they depend on a reaction this doc can't produce on its own:

- Real art assets (replacing placeholder colored squares) — a later decision, likely made once lessons are actually being built and there's something to react to.
- Whether "The Open Pasture" naming/theming lands — unreacted-to, same status as the rest of the visual identity section above.
- Exact `.tmx` tilemap content beyond "two fenced pens on grass" (the prototype's scope) — a lesson-authoring-time decision, not a design-doc-level one.
- When phase 2 (dashboard) starts relative to this phase — still not urgent, per the map.

## Out of scope

Unchanged from the map's Out of scope section — repeated here only as a pointer, not restated: a mentored persistence curriculum, a mentored Spring/REST backend curriculum, literal Stardew-Valley game systems (economy/seasons/NPCs), and a custom-built engine. See [the map](.scratch/next-curriculum/map.md#out-of-scope) for the reasoning behind each.
