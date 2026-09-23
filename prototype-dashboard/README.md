# PROTOTYPE — Spring+Angular dashboard slice

Throwaway artifact for wayfinder ticket "Prototype the Spring+Angular dashboard slice"
(`.scratch/next-curriculum/issues/04-prototype-dashboard-slice.md` on `main`).

Answers: "what does seeing results look like" for the Spring MVC+REST + Angular
dashboard path, as a data point for the path decision (ticket 06) against the
Java 2D farm-game prototype (ticket 05). Not production code — no tests, minimal
error handling, plain JDBC instead of the eventual jOOQ scaffold.

## What it is

- `backend/` — a minimal Spring Boot app with one endpoint,
  `GET /api/farm-overview`, returning herds → pens → animals (each with its
  latest shearing + sale) as nested JSON, read straight off the real `farm`
  Postgres database via plain JDBC.
- `frontend/` — a minimal Angular app with **three structurally different**
  dashboard variants, switchable via `?variant=A|B|C` and a floating bottom
  bar (arrow keys also cycle):
  - **A — Ledger table**: dense flat table, one row per animal. Matches the
    SQL curriculum's "Data Barn" ledger aesthetic most closely.
  - **B — Herd cards + drill-down**: herd cards containing pen sections and
    animal chips; click an alpaca to open a detail drawer with its data. This
    is the "click an alpaca to see its data" interaction pattern.
  - **C — KPI dashboard**: aggregate-first — stat tiles (animal count, herd
    count, total fleece revenue, avg. last fleece weight) plus a per-herd bar
    breakdown.

## How to run

1. Start the `farm` Postgres db (from the sibling `sql-curriculum` repo):
   ```
   cd ../sql-curriculum && docker compose up -d
   ```
2. Backend (from `prototype-dashboard/backend/`):
   ```
   mvn spring-boot:run
   ```
   Serves `http://localhost:8080/api/farm-overview`.
3. Frontend (from `prototype-dashboard/frontend/`, after `npm install` once):
   ```
   npx ng serve
   ```
   Open `http://localhost:4200` — flip through variants with the bottom bar
   or arrow keys.

Verified working 2026-09-23 against the live `farm` db (16 animals, 3 herds).

## Status

Built and smoke-tested (all three variants render real data; drill-down in B
confirmed working). Awaiting Thomas's live reaction — which variant (if any)
reads best, and how this path feels next to the farm-game prototype (ticket
05) — before ticket 04 can actually close. Do not treat any variant as
"chosen" until that reaction happens.
