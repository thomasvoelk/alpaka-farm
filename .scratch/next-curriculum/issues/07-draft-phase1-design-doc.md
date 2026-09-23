# Draft the phase 1 (game curriculum) DESIGN.md

Type: task
Status: resolved

## Question

Not a decision — the map's destination is reached by producing this document. Draft `DESIGN.md` for the phase 1 farm-game curriculum, matching the style and depth of the two existing curriculum DESIGN.md docs (`tv-old-mac-donald-had-a-farm-new-and-better` and `sql-curriculum`'s "The Data Barn").

Cover, drawing on the map's resolved decisions:
- Engine & setup: LibGDX, desktop-only Maven project (hand-assembled `gdx` + `gdx-backend-lwjgl3` coordinates) — see [Pick a Java 2D game engine for the farm-game prototype](01-pick-java-2d-engine.md).
- Persistence: jOOQ bridge to the shared Postgres `farm` database is fully agent-provided scaffold (codegen + repository class), consumed as a black box — no curriculum time spent on it. See [jOOQ persistence bridge: mentored phase or provided scaffold?](02-jooq-scaffold-or-mentored.md).
- Scope tier validated by prototype: tilemap rendering, walkable player character, click-an-alpaca-to-inspect-its-real-data. No economy/time simulation, no crops/seasons/NPCs — see [Prototype the Java 2D tile-farm game slice](05-prototype-game-slice.md) and the map's Out of scope section.
- Syllabus / lesson breakdown, scaffold-vs-taught boundaries per lesson, self-check approach — these are genuinely open and are this ticket's job to design, not pre-decided elsewhere. Consult the map's Not yet specified section for the open threads to resolve while drafting (theming/naming continuity, save/load and live-vs-cached Postgres state, how "Expected Result"-style self-checking carries into a live interactive app).
- Match the existing curricula's git-workflow conventions (TDD red-green-refactor micro-commits, per the Java curriculum).

Authoring the lessons themselves stays out of scope for this ticket — only the DESIGN.md-level spec.

## Answer

Drafted `DESIGN.md` at the repo root (matching `sql-curriculum`'s DESIGN.md location and structure). Highlights:

- **Persistence**: schema reused as-is, read-only — no new tables/columns needed (resolves the map's open schema question). Loaded once at startup, not re-queried per interaction, matching the prototype's actual behavior.
- **Format**: TDD, matching the Java curriculum, not the SQL curriculum's "Expected Result" self-check — the doc resolves *why*: most of the game's interesting behavior (movement/collision math, click hit-testing, data-panel field mapping) is plain testable Java pulled out of the render loop; the untested render/input glue gets the same treatment the Java curriculum already gives non-TDD'd plumbing (its own lesson 00), and purely visual feel is covered by the existing phase-end-demo mechanism, not a new one.
- **Syllabus**: first-draft 6 phases / ~20 lessons / ~10–12 weeks (Explore the Pasture → The Tilemap → Walking the Farm → Meet the Herd → Click to Inspect → Polish & Review), sized against the Java curriculum's own granularity as the closer analog.
- **Theming**: first-pass proposal only, unreacted-to — name "The Open Pasture," pasture-green accent (distinct from Java's purple and SQL's blue), a boot-print/compass mark keeping the sibling-curricula corner-dot device. Flagged explicitly as open until she reacts to it.
- **Scaffold-vs-taught**: engine bootstrap + persistence bridge are given; game-loop, movement, tilemap loading, and click-to-inspect are hers to build, TDD'd where the logic is pure.

This reaches the map's destination for phase 1 — the DESIGN.md is ready to hand off for lesson authoring. Phase 2's dashboard DESIGN.md remains future work, not part of this map (see the map's Notes).

