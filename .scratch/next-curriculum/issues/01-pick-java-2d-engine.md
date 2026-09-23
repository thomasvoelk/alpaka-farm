# Pick a Java 2D game engine for the farm-game prototype

Type: research
Status: resolved

## Question

Which existing Java 2D game engine best fits building a Stardew-Valley-styled tilemap with a walkable player character, for a learner who knows Java well but has never done game development?

Compare at least LibGDX against 1–2 alternatives (e.g. a lighter option, and a plain JavaFX/Swing-canvas-only option) on:
- Learning curve for a Java-fluent beginner with zero game-dev background
- Tutorial/community support and documentation quality
- Maven-friendliness (the existing curricula are Maven projects)
- Licensing
- Built-in support for tilemaps, sprites, and keyboard input

Recommend one engine to build the prototype in ticket 05 with.

## Answer

**LibGDX**, set up as a desktop-only Maven project (hand-assembled `com.badlogicgames.gdx:gdx` + `gdx-backend-lwjgl3` coordinates — LibGDX's official generator targets Gradle, not Maven; desktop-only is the one Maven configuration its own docs confirm works).

Why: it's the only candidate with mature, officially-documented Tiled tilemap loading, sprites, and keyboard/mouse input together — exactly this prototype's scope — plus an official "A Simple Game" tutorial written for a Java-fluent, zero-game-dev learner, and the healthiest project of the options considered (25.4k GitHub stars, Apache 2.0, active releases through Oct 2025).

Rejected: **Mini2Dx** (lighter, real Maven Central coordinates, but its active 2.0 line is still alpha/beta — too risky for reusable curriculum material; the stable line has been bugfix-only since 2019). **Plain JavaFX/Canvas** (best Maven story, fine licensing, but zero built-in tilemap/sprite infrastructure — early lessons would build game plumbing instead of teaching the actual point).

Full findings and source citations: `research/java-2d-engine.md` on branch `research/java-2d-engine` (commit `860b6a4`).
