# Next Curriculum: Persistence Bridge & Visualization App

## Destination

Both visualization concepts are being built, sequenced — a Java 2D tile-based farm game (LibGDX, walkable player character, no economy/time simulation) as **phase 1**, then a Spring MVC+REST backend with an Angular frontend dashboard as **phase 2** — both reading from a jOOQ-backed persistence bridge connecting the existing Java alpaca objects to the SQL curriculum's shared Postgres `farm` database (see [Decide: dashboard vs. farm game as the next curriculum](issues/06-decide-dashboard-vs-game.md)).

Reaching the end means a DESIGN.md-level spec for **both phases** (syllabus, lesson skeleton, scaffold-vs-taught boundaries) in the style of the two existing curriculum DESIGN.md docs, ready to start authoring lessons. **Phase 1 reached**: `DESIGN.md` is written at the repo root — see [Draft the phase 1 (game curriculum) DESIGN.md](issues/07-draft-phase1-design-doc.md). **Phase 2 in progress**: its backend-scaffolding question is settled ([Spring backend/controller: mentored or provided scaffold?](issues/03-spring-scaffold-or-mentored.md) — full scaffold, mentored work is Angular only), but its UI variant is still an open decision ([Decide: which dashboard UI variant becomes phase 2's curriculum](issues/08-decide-dashboard-ui-variant.md)), blocking [Draft the phase 2 (dashboard curriculum) DESIGN.md](issues/09-draft-phase2-design-doc.md). Authoring the lessons themselves (either phase) is out of scope for this map.

## Notes

Domain: mentored apprentice curriculum design (she's training as a Fachinformatiker/in Anwendungsentwicklung — FIAE). Sibling to two existing repos, both established patterns worth matching:
- `tv-old-mac-donald-had-a-farm-new-and-better` — Java/TDD curriculum, red-green-refactor, git micro-commit per green test.
- `sql-curriculum` ("The Data Barn") — SQL curriculum against a fixed seeded Postgres `farm` database, self-check via "Expected Result" `<details>` blocks (no automated grading), git commit per completed task. Its `DESIGN.md` documents the design rationale in full and is a good template for whatever DESIGN.md this map produces.

Skills every session should consult: `/grilling` and `/domain-modeling` for grilling-type tickets; `/prototype` for the two prototype tickets; `/research` for the engine-choice ticket.

Phase 1 is fully resolved through [Draft the phase 1 (game curriculum) DESIGN.md](issues/07-draft-phase1-design-doc.md) — a `task`-type ticket that carried execution into the map (an intentional override of wayfinder's default plan-only scope, since the map's destination *for that phase* was the document itself). Ticket [09](issues/09-draft-phase2-design-doc.md) carries the same override forward for phase 2, once [ticket 08](issues/08-decide-dashboard-ui-variant.md) unblocks it. What remains outside those two tickets (real art, theming reaction, exact phase-2 timing) is future work, tracked in Not yet specified rather than blocking anything here.

Standing preferences:
- No Hibernate/JPA — jOOQ only, deliberately kept simple.
- No fixed deadline. She's mid-way through both existing curricula. She's said she'd like to "see something" at some point — a motivational signal, not a hard trigger or deadline.
- Workplace-stack relevance (Spring MVC+REST, Angular — "our stack at work") is a nice-to-have factor in the eventual path decision, not decisive on its own. Thomas is open to other tech choices.
- Her background going in: Java, TDD, SQL, some HTML. No JS/TS, no game-dev experience.
- Thomas has previously hand-built a 2D game engine in Java from scratch (via a tutorial series) but prefers reusing an existing engine this time, for simplicity.
- Thomas does not write scaffolding or curriculum content himself — Claude builds both (scaffold code and the curriculum/lesson material) for whichever phase is active. "Thomas's bandwidth" is not a factor in path/scope decisions.

## Decisions so far

- [Pick a Java 2D game engine for the farm-game prototype](issues/01-pick-java-2d-engine.md) — LibGDX, desktop-only Maven setup; full comparison and citations on the `research/java-2d-engine` branch.
- [jOOQ persistence bridge: mentored phase or provided scaffold?](issues/02-jooq-scaffold-or-mentored.md) — Full scaffold, agent-provided (codegen + repository class); she consumes it as a black box. jOOQ isn't exam-relevant even though database integration generally is.
- [Prototype the Spring+Angular dashboard slice](issues/04-prototype-dashboard-slice.md) — Positive reaction to the dashboard concept across all 3 UI variants; no variant chosen (not this ticket's job). Dashboard path is a live candidate going into the path decision (06).
- [Prototype the Java 2D tile-farm game slice](issues/05-prototype-game-slice.md) — Positive reaction ("like it very much, aside from the graphics") to the tilemap-walk-click-to-inspect concept. Game path is a live candidate going into the path decision (06).
- [Decide: dashboard vs. farm game as the next curriculum](issues/06-decide-dashboard-vs-game.md) — No losing path: both get built, sequenced — game curriculum first (phase 1), dashboard curriculum second (phase 2). Reaction to both was equally strong; sequencing gets the bigger stretch/fun (game) first and the workplace-stack relevance (dashboard) second.
- [Spring backend/controller: mentored or provided scaffold?](issues/03-spring-scaffold-or-mentored.md) — Full scaffold for phase 2's first pass, same as jOOQ; her mentored work is Angular only. Mentored backend/REST development deferred to a future, separate curriculum.
- [Draft the phase 1 (game curriculum) DESIGN.md](issues/07-draft-phase1-design-doc.md) — `DESIGN.md` written at the repo root: schema reused as-is (read-only, loaded once at startup), TDD format matching the Java curriculum (testable core / untested render glue / phase-end demo, not the SQL curriculum's Expected-Result style), a first-draft 6-phase/~20-lesson syllabus, and a first-pass (unreacted-to) visual identity — "The Open Pasture," pasture green, boot-print mark.

## Not yet specified

- Whether "The Open Pasture" naming/visual identity (proposed in the phase 1 DESIGN.md) actually lands — first-pass only, unreacted-to by the apprentice.
- Real art assets for the phase 1 game (replacing placeholder colored squares) — an execution-time decision for whoever authors the lessons, not a design-doc-level one.
- When exactly phase 2 (dashboard) starts relative to phase 1 (game) — strictly after phase 1 wraps, or once phase 1 is far enough along — hasn't been discussed; not urgent yet.
- Which CRUD operations the scaffolded backend exposes and which of those she wires up from Angular (backend itself is scaffolded per ticket 03, so this is Angular-side scope only) — depends on the UI variant chosen in [ticket 08](issues/08-decide-dashboard-ui-variant.md), not yet specifiable ahead of it.

## Out of scope

- A dedicated database-integration/persistence curriculum (jOOQ or otherwise, hands-on). Ruled out while resolving [jOOQ persistence bridge: mentored phase or provided scaffold?](issues/02-jooq-scaffold-or-mentored.md): jOOQ specifically isn't exam-relevant, so the bridge is a scaffold instead. Stays a possible future, separate effort.
- A mentored Spring MVC+REST backend-development curriculum (hands-on). Ruled out while resolving [Spring backend/controller: mentored or provided scaffold?](issues/03-spring-scaffold-or-mentored.md): the priority for phase 2's first pass is something visible quickly, so the backend is scaffolded and her mentored work stays on Angular. Stays a possible future, separate effort — and per that ticket's Ausbildungsrahmenplan fact-check, a stronger candidate for one than jOOQ was.
- Literal Stardew Valley game systems — crops, seasons, day/night cycles, NPCs/dialogue, an economy. Ruled out during charting: only the tilemap-farm-view-plus-walkable-character tier is in scope, not a game-sim slice.
- Building a custom 2D engine from scratch. Ruled out during charting in favor of an existing Java 2D engine (feeds the engine-research ticket).
- Hibernate/JPA as the persistence layer. Ruled out during charting in favor of jOOQ.
