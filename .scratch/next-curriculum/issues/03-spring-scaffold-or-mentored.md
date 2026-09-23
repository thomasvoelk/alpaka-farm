# Spring backend/controller: mentored or provided scaffold?

Type: grilling
Status: resolved

## Question

The Spring MVC+REST / Angular dashboard curriculum is going ahead as **phase 2**, after the farm-game curriculum (see [Decide: dashboard vs. farm game as the next curriculum](06-decide-dashboard-vs-game.md) — both paths are being built, game first). Should the Spring controller/backend be something she writes and learns (introducing Spring hands-on, matching the workplace stack), or something Thomas provides as a working scaffold so her learning stays focused on Angular?

Thomas leans toward introducing a real MVC controller with REST resources but is open to scaffolding it instead. Not urgent — phase 1 (game) doesn't depend on this — but no longer conditional on a path choice, so it's open and unblocked whenever it's useful to resolve.

## Answer

**Full scaffold, agent-provided — same treatment as jOOQ (ticket 02).** For phase 2's initial build, Thomas/Claude provide a working Spring MVC+REST backend as a black box; her mentored work is Angular only. Thomas's reasoning: the priority is getting to something she can see working quickly, not maximizing what's mentored on the first pass.

Fact-check against the Ausbildungsrahmenplan (researched during this ticket, primary source `gesetze-im-internet.de/fiausbv`): no competency names REST/HTTP/Spring specifically, so "the framework itself isn't exam content" holds the same way it did for jOOQ. But the underlying skill this would have exercised — §4 Abs. 3 Nr. 1 d, "Anwendungslösungen unter Berücksichtigung der bestehenden Systemarchitektur entwerfen und realisieren" (design *and* implement application solutions), a 25-week block, the single largest allocation in the framework — is far more exam-central than storage-integration (5 weeks) was. That's a stronger case for eventually mentoring backend/REST development than jOOQ had — just not on this pass.

So: writing the Spring controller/backend herself (and going deeper on persistence) is deferred to a **future, separate curriculum** — not part of this map. See the map's Out of scope section.
