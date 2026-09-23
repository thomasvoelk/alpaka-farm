# jOOQ persistence bridge: mentored phase or provided scaffold?

Type: grilling
Status: resolved

## Question

Should wiring the Java alpaca objects to the shared Postgres `farm` database via jOOQ be its own mentored curriculum phase (she writes the jOOQ queries/repository herself, task-by-task like the existing curricula), or should Thomas provide a working repository class as scaffolding so she can move straight to the visualization work?

Thomas is currently torn between these. Resolve by weighing how much of a distinct, transferable skill jOOQ-based persistence is (compare: window functions were demoted out of the SQL curriculum's core path for not being exam-relevant — is persistence more or less exam/apprenticeship-relevant?) against the goal of getting her to something visible sooner, given there's no fixed deadline.

## Answer

**Full scaffold, agent-provided — not a mentored phase.** Claude (not Thomas, not the apprentice) builds the persistence layer end to end: both the jOOQ code-generation output (the Maven codegen plugin run once against the `farm` schema to produce the generated table/record classes) and the hand-written repository class wrapping it (e.g. `AlpacaRepository`). She consumes the finished repository as a pure black box — no curriculum time spent on either output, no walkthrough.

Reasoning: the Ausbildungsrahmenplan's statutory storage-integration competency (§4 Abs. 2 Nr. 9 b), "Speicherlösungen, insbesondere Datenbanksysteme, integrieren") is about the general skill, not the specific tool — jOOQ itself isn't exam content, so a mentored jOOQ phase wouldn't actually be teaching to that competency. That removes the case for spending mentored time on it, leaving "get to something visible sooner" as the deciding factor.

Since the bridge is scaffolded rather than mentored, it's built once as shared foundation ahead of whichever prototype needs it (04, 05) — same jOOQ wiring underneath both, regardless of which path wins.

A dedicated database-integration/persistence curriculum (jOOQ or otherwise, hands-on) remains a possible **future, separate effort** — not part of this map. See the map's Out of scope section.
