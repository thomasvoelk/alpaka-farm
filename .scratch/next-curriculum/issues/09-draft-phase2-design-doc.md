# Draft the phase 2 (dashboard curriculum) DESIGN.md

Type: task
Status: open
Blocked by: 08

## Question

Not a decision — mirrors [Draft the phase 1 (game curriculum) DESIGN.md](07-draft-phase1-design-doc.md). Draft `DESIGN.md`-level spec content for phase 2, in the style of the phase 1 DESIGN.md (`DESIGN.md` at the repo root) and matching the two existing curriculum DESIGN.md docs.

Cover, drawing on the map's resolved decisions:
- Chosen UI variant (or hybrid) and why — see [Decide: which dashboard UI variant becomes phase 2's curriculum](08-decide-dashboard-ui-variant.md).
- Backend treatment: fully agent-provided scaffold, consumed as a black box — her mentored work is Angular only. See [Spring backend/controller: mentored or provided scaffold?](03-spring-scaffold-or-mentored.md).
- Persistence: the same jOOQ bridge phase 1 uses, shared read access to the `farm` database. See [jOOQ persistence bridge: mentored phase or provided scaffold?](02-jooq-scaffold-or-mentored.md).
- Syllabus / lesson breakdown, scoped to Angular-side work only (components, services, HTTP calls against the given backend) — genuinely open, this ticket's job to design.
- Scaffold-vs-taught boundaries per lesson, self-check approach for a live Angular app (consult how phase 1's DESIGN.md resolved the equivalent question for a live LibGDX app, but don't assume the same split applies — Angular's testability shape is different).
- Auth and deployment story, per whatever ticket 08 settles (or, if left undecided there, state the working default and flag it as unreacted-to, the way phase 1's DESIGN.md did for its visual identity).
- Match the existing curricula's git-workflow conventions where they still apply (TDD-style commits are a natural fit for Angular component/service tests; confirm against ticket 08's answer whether TDD or a different self-check model fits better, the way phase 1 diverged from the SQL curriculum's Expected-Result style for principled reasons).

Authoring the lessons themselves stays out of scope for this ticket — only the DESIGN.md-level spec.
