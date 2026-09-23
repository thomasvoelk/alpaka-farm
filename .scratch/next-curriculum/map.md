# Next Curriculum: Persistence Bridge & Visualization App

## Destination

A decision on which visualization concept becomes the apprentice's next curriculum — a Spring MVC+REST backend with an Angular frontend dashboard, or a Java 2D tile-based farm game (LibGDX-style engine, walkable player character, no economy/time simulation) — both reading from a jOOQ-backed persistence bridge connecting the existing Java alpaca objects to the SQL curriculum's shared Postgres `farm` database.

Reaching the end means a DESIGN.md-level spec for the chosen path (syllabus, lesson skeleton, scaffold-vs-taught boundaries) in the style of the two existing curriculum DESIGN.md docs, ready to start authoring lessons. Authoring the lessons themselves is out of scope for this map.

## Notes

Domain: mentored apprentice curriculum design (she's training as a Fachinformatiker/in Anwendungsentwicklung — FIAE). Sibling to two existing repos, both established patterns worth matching:
- `tv-old-mac-donald-had-a-farm-new-and-better` — Java/TDD curriculum, red-green-refactor, git micro-commit per green test.
- `sql-curriculum` ("The Data Barn") — SQL curriculum against a fixed seeded Postgres `farm` database, self-check via "Expected Result" `<details>` blocks (no automated grading), git commit per completed task. Its `DESIGN.md` documents the design rationale in full and is a good template for whatever DESIGN.md this map produces.

Skills every session should consult: `/grilling` and `/domain-modeling` for grilling-type tickets; `/prototype` for the two prototype tickets; `/research` for the engine-choice ticket.

Standing preferences:
- No Hibernate/JPA — jOOQ only, deliberately kept simple.
- No fixed deadline. She's mid-way through both existing curricula. She's said she'd like to "see something" at some point — a motivational signal, not a hard trigger or deadline.
- Workplace-stack relevance (Spring MVC+REST, Angular — "our stack at work") is a nice-to-have factor in the eventual path decision, not decisive on its own. Thomas is open to other tech choices.
- Her background going in: Java, TDD, SQL, some HTML. No JS/TS, no game-dev experience.
- Thomas has previously hand-built a 2D game engine in Java from scratch (via a tutorial series) but prefers reusing an existing engine this time, for simplicity.

## Decisions so far

_(none yet — map just charted)_

## Not yet specified

- Final syllabus / lesson breakdown for whichever path wins.
- Whether the jOOQ bridge reuses the existing `farm` schema tables (`staff`/`herds`/`pens`/`animals`/`shearing_records`/`fleece_sales`) as-is, or needs new tables/columns for the Java objects — depends on the jOOQ-role decision and the path decision.
- Theming/naming continuity for this curriculum (mascot, accent color, narrative framing) — both existing curricula got a deliberate visual identity; this one hasn't been discussed.
- If the dashboard path wins: what CRUD operations she implements, whether auth is needed (probably not), deployment story (probably local-only).
- If the game path wins: save/load, whether the world reflects live Postgres state on every interaction or a cached snapshot, how "clicking an alpaca to see its data" is presented in-game.
- How self-checking ("Expected Result" style, matching both existing curricula) carries into a live interactive app rather than static HTML lessons.

## Out of scope

- Literal Stardew Valley game systems — crops, seasons, day/night cycles, NPCs/dialogue, an economy. Ruled out during charting: only the tilemap-farm-view-plus-walkable-character tier is in scope, not a game-sim slice.
- Building a custom 2D engine from scratch. Ruled out during charting in favor of an existing Java 2D engine (feeds the engine-research ticket).
- Hibernate/JPA as the persistence layer. Ruled out during charting in favor of jOOQ.
