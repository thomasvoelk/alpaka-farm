# Decide: dashboard vs. farm game as the next curriculum

Type: grilling
Status: resolved
Blocked by: 04, 05

## Question

Having reacted to both the Spring+Angular dashboard prototype (04) and the Java 2D farm-game prototype (05), decide which becomes the apprentice's actual next mentored curriculum.

Weigh: her motivation/excitement reaction to each prototype, workplace-stack relevance (soft preference, not decisive), Thomas's own bandwidth to build/maintain scaffolding for each, and how well each teaches something distinct from what she's already learned in the Java and SQL curricula.

Record the losing path's fate (shelved for later vs. ruled out entirely).

## Answer

**No losing path — both get built, sequenced: the farm-game curriculum first, the Spring+Angular dashboard curriculum second.**

Reasoning:
- Her reaction to both prototypes was "ultra exciting" — a genuine toss-up, not a discriminator.
- Thomas's "bandwidth to build/maintain scaffolding" turned out not to be a real factor: Thomas isn't writing either the scaffolding or the curriculum himself — Claude is, for both. That framing in this ticket's original question was a wrong assumption; strike it as a factor for any future ticket that leans on it (e.g. 03).
- Distinctness: the game path is the bigger stretch from her Java/SQL/TDD background (game loop, rendering, real-time input) and is "more fun"; the dashboard path is closer to her existing skills but teaches the actual workplace stack (Spring MVC+REST, Angular). Rather than trading one off against the other, sequencing gets both: fun/novelty first (matches "no deadline, wants to see something" energy), workplace-relevance second.

This reframes the map's destination from a single either/or pick to a two-phase plan. Phase 1 (game) has everything needed to spec now: engine picked (01), persistence approach picked (02), prototype validated (05). Phase 2 (dashboard) is validated as worth doing (04) but its own remaining decision (03: Spring mentored-or-scaffold) doesn't need resolving until phase 1 is underway — it's real, just not urgent, so it stays open and unblocked rather than closed.
