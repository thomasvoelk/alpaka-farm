# Prototype the Spring+Angular dashboard slice

Type: prototype
Status: resolved

## Question

Build a cheap, rough Spring MVC+REST backend exposing a small slice of the real `farm` Postgres data (e.g. a herds/pens/animals list, maybe one shearing record), plus a minimal Angular frontend rendering it — enough for Thomas to react to "what does seeing results look like" on this path.

Use the `/prototype` skill. Link the prototype as an asset on this ticket.

## Comments

Prototype built and smoke-tested (2026-09-23): a minimal Spring Boot backend (plain JDBC, no jOOQ yet — that's the real scaffold from ticket 02) serving `GET /api/farm-overview` off the live `farm` Postgres db, plus an Angular frontend with three structurally different variants — **A** ledger table, **B** herd cards with click-to-drill-down on an alpaca, **C** KPI stat dashboard — switchable via `?variant=A|B|C`. All three verified rendering real data (16 animals, 3 herds) in-browser; drill-down interaction in B confirmed working.

Full code + run instructions: `prototype/dashboard-slice` branch (commit `6047fd0`), `prototype-dashboard/README.md`.

## Answer

Thomas's live reaction (2026-09-23, servers run from a throwaway worktree off `prototype/dashboard-slice`): positive across the board — he liked all three variants, no reservations about the dashboard concept itself. He didn't pick a favorite variant, and didn't need to: this ticket's job was to test whether the dashboard path feels worth pursuing at all, not to lock a UI design. It does.

Which variant (A ledger / B cards+drill-down / C KPI stats) — or some hybrid — actually gets built is deferred to real design work if the dashboard path wins at [Decide: dashboard vs. farm game as the next curriculum](06-decide-dashboard-vs-game.md); moved to the map's Not yet specified.
