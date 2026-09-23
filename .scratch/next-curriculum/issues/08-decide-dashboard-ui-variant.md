# Decide: which dashboard UI variant becomes phase 2's curriculum

Type: grilling
Status: open

## Question

[Prototype the Spring+Angular dashboard slice](04-prototype-dashboard-slice.md) built and got a positive reaction to three UI variants — A (ledger table), B (herd cards + drill-down), C (KPI stats) — without picking one; that wasn't its job. Persistence ([02](02-jooq-scaffold-or-mentored.md)) and the backend scaffold-or-mentored question ([03](03-spring-scaffold-or-mentored.md)) are now both settled, and phase 2 is confirmed to happen after phase 1 ([06](06-decide-dashboard-vs-game.md)).

Pick which variant — or a hybrid — becomes the actual phase 2 curriculum. This isn't cosmetic: the choice shapes the syllabus, which CRUD operations the scaffolded backend needs to expose, and what she actually wires up from Angular (her mentored surface, per ticket 03).

Worth surfacing while grilling, if they come up naturally rather than forcing them: whether auth is needed (current assumption: probably not) and the deployment story (current assumption: probably local-only) — both listed on the map's Not yet specified as minor opens. Don't block the core variant decision on resolving these if they don't come up.
