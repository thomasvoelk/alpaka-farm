# Java 2D Engine Selection — Tilemap Farm View Prototype

## Purpose and scope

We are scoping the next curriculum module after the Java/TDD and SQL "farm" curricula: a Stardew-Valley-*styled* (visually) tilemap farm view. Scope is strictly: **tilemap rendering + a walkable player character + clickable alpaca sprites that show real data pulled from Postgres via a Java persistence layer.** Explicitly out of scope: crops, seasons, day/night, NPCs/dialogue, economy, time simulation.

The learner is a FIAE apprentice with solid Java, TDD and SQL, some HTML, and **zero JavaScript/TypeScript and zero game-dev experience.** The existing sibling curricula are Maven projects, so Maven-friendliness is a real constraint, not a nice-to-have.

This document evaluates three options against five dimensions: learning curve, tutorial/community support, Maven-friendliness, licensing, and built-in tilemap/sprite/input support. Every factual claim below is cited to a primary source (official docs, official GitHub repo, license file, or Maven Central/Sonatype listing).

---

## Option 1: LibGDX

LibGDx describes itself as "a cross-platform Java game development framework based on OpenGL (ES), designed for Windows, Linux, macOS, Android, web browsers, and iOS" [libgdx/libgdx README](https://github.com/libgdx/libgdx). It is written in Java (with native C/C++ backends) and is the most widely used Java 2D/3D game framework.

**Learning curve.** The official "A Simple Game" tutorial builds a small bucket-and-raindrops game and is explicitly written for beginners: it explains Java basics relevant to the task (float vs. double, imports), structures code into `input()`, `logic()`, `draw()` methods, and explains the game-loop and delta-time concepts from scratch (*"If we multiply our movement by delta time, the movement will be consistent no matter what hardware we run this game on."*) [libgdx.com/wiki/start/a-simple-game](https://libgdx.com/wiki/start/a-simple-game). It assumes Java familiarity (which the apprentice has) but not prior game-dev knowledge — a good match.

**Tutorial/community support.** The project has an official Wiki covering nearly every API area (tile maps, input, sprites, etc.) [libgdx.com/wiki/](https://libgdx.com/wiki/), an official Discord, 25.4k GitHub stars and 6.5k forks [github.com/libgdx/libgdx](https://github.com/libgdx/libgdx), and a maintained "Demos & Tutorials" page [libgdx.com/wiki/start/demos-and-tutorials](https://libgdx.com/wiki/start/demos-and-tutorials). This is by far the deepest documentation/community base of the three options.

**Maven-friendliness — the weak point.** LibGDX's own Maven-integration article is blunt: *"Setting up your project with Maven is possible, but crossplatform support is heavily limited, and only desktop is known to work,"* and *"there is no official support if any issues should arise"* [libgdx.com/wiki/articles/maven-integration](https://libgdx.com/wiki/articles/maven-integration). The same page notes the official, recommended setup tool (`gdx-liftoff`, successor to `gdx-setup`) generates a **Gradle** project, not Maven [libgdx/gdx-liftoff README/Guide](https://github.com/libgdx/gdx-liftoff). That said, since our prototype is desktop-only (no Android/HTML5/iOS target), the "desktop-only Maven works" caveat is actually acceptable for this project's scope. The core artifacts are on Maven Central under `com.badlogicgames.gdx`, e.g. `com.badlogicgames.gdx:gdx:1.14.2` and `com.badlogicgames.gdx:gdx-backend-lwjgl3:1.13.5` [Maven Central: gdx](https://central.sonatype.com/artifact/com.badlogicgames.gdx/gdx) [Maven Central: gdx-backend-lwjgl3](https://central.sonatype.com/artifact/com.badlogicgames.gdx/gdx-backend-lwjgl3/1.13.1), so a hand-written `pom.xml` with these coordinates is workable for a desktop-only prototype — it's just not the officially blessed path and the learner would be swimming against the tutorial current, which assumes Gradle.

**Licensing.** Apache License, Version 2.0, per the repository's own `LICENSE` file [github.com/libgdx/libgdx/blob/master/LICENSE](https://github.com/libgdx/libgdx/blob/master/LICENSE). Fully permissive, free for any use including a personal mentoring project.

**Tilemap/sprite/input support.** Built in and mature:
- Tiled TMX support via `TmxMapLoader` and `OrthogonalTiledMapRenderer`, documented as *"a synchronous loader for TMX maps created with the Tiled tool"* [TmxMapLoader source](https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/maps/tiled/TmxMapLoader.java) [libgdx.com/wiki/graphics/2d/tile-maps](https://libgdx.com/wiki/graphics/2d/tile-maps). Tiled's own documentation lists libGDX among the libraries with TMX support: *"libgdx, a Java-based Android/desktop/HTML5 game library, provides a packer, loader and renderer for TMX maps"* [doc.mapeditor.org — Libraries and Frameworks](https://doc.mapeditor.org/en/stable/reference/support-for-tmx-maps/).
- Sprites via the `Sprite` class, covered directly in the official beginner tutorial (state such as position/size/rotation, draw ordering) [libgdx.com/wiki/start/a-simple-game](https://libgdx.com/wiki/start/a-simple-game).
- Keyboard/mouse input via the `InputProcessor` interface (`keyDown`, `keyUp`, `touchDown`, etc.), registered with `Input.setInputProcessor()`, plus an `InputAdapter` convenience base class so only needed methods must be overridden [libgdx.com/wiki/input/input-handling](https://libgdx.com/wiki/input/input-handling) [InputProcessor javadoc](https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/InputProcessor.html).

This is the only option of the three with all three needed features (tilemap, sprite, input) ready-made and officially documented together.

---

## Option 2: Mini2Dx (lightweight, Slick2D-style option)

Mini2Dx bills itself as *"A high-level cross-platform 2D game development API"* and states in its own README: *"The mini2Dx project was started in 2013 as an API-compatible replacement for Slick2D. Since then it has grown into a feature-rich framework for building all kinds of 2D video games,"* aiming to be *"beginner-friendly, master-ready"* [github.com/mini2Dx/mini2Dx README](https://github.com/mini2Dx/mini2Dx/blob/master/README.md). It is a high-level, simplified API layered **on top of LibGDX** rather than a from-scratch engine — this is what makes it lighter-weight in *API surface* while still riding on LibGDX's rendering/input/asset machinery.

We also checked the original Slick2D directly as the "successor" reference point: Tiled's own docs confirm *"Slick2D supports loading TMX maps through TiledMap"* [doc.mapeditor.org](https://doc.mapeditor.org/en/stable/reference/support-for-tmx-maps/), but the upstream Slick2D Mercurial repository has not been updated despite open bug reports and pull requests, which is why community forks like `slick2d-maven` exist purely to keep publishing patches [github.com/nguillaumin/slick2d-maven README](https://github.com/nguillaumin/slick2d-maven/blob/master/README.md). Slick2D itself is effectively dead upstream, which is why Mini2Dx (its spiritual successor) is the more honest "lightweight" candidate to evaluate.

**Learning curve.** Positioned explicitly as simpler/more beginner-friendly than raw LibGDX, per its own README framing above. However, because it wraps LibGDX, a learner would still eventually need to understand LibGDX concepts underneath for anything beyond the simplified API — there isn't a clean abstraction boundary documented for where Mini2Dx's simplifications end.

**Tutorial/community support.** Has an official site (mini2dx.org), Wiki, community forum, and Javadocs [mini2Dx README links](https://github.com/mini2Dx/mini2Dx/blob/master/README.md) [mini2dx.org](https://mini2dx.org/), but no dedicated tutorial section comparable to LibGDX's "A Simple Game." Community size is small: the GitHub project shows on the order of dozens of stars/forks (41 stars / 31 forks per project activity data) versus LibGDX's 25.4k stars.

**Maven-friendliness.** Published to Maven Central under `org.mini2Dx`, e.g. `org.mini2Dx:mini2dx-libgdx` and `org.mini2Dx:mini2dx-libgdx-desktop` [Maven Central: mini2dx-libgdx](https://central.sonatype.com/artifact/org.mini2Dx/mini2dx-libgdx) [Maven Central: mini2dx-libgdx-desktop](https://central.sonatype.com/artifact/org.mini2Dx/mini2dx-libgdx-desktop/2.0.0-beta.10), and its `parcl` packaging plugin is also there [Maven Central: parcl](https://central.sonatype.com/artifact/org.mini2Dx/parcl). Coordinates are real and resolvable — the mechanical Maven step is fine.

**Licensing.** BSD 3-Clause License for the project itself; classes derived from LibGDX code carry the Apache License 2.0 [github.com/mini2Dx/mini2Dx README](https://github.com/mini2Dx/mini2Dx/blob/master/README.md). Both are permissive and fine for a personal mentoring project.

**Tilemap/sprite/input support.** The repository contains a dedicated `/tiled` module (Tiled support), and since Mini2Dx sits on top of LibGDX it inherits sprite and input primitives, though details are not spelled out in a single beginner-oriented doc the way LibGDX's are.

**Maintenance risk — the deciding factor against this option.** The README states plainly: *"1.9.x will only receive bug fixes and optimisations going forward"* (1.9.0 shipped April 20, 2019) while active development has moved to a 2.0 line [github.com/mini2Dx/mini2Dx README](https://github.com/mini2Dx/mini2Dx/blob/master/README.md). As of this research, the 2.0 artifacts on Maven Central are still pre-release (`2.0.0-beta.18`, `2.0.0-alpha.28`) [Maven Central: mini2dx-libgdx](https://central.sonatype.com/artifact/org.mini2Dx/mini2dx-libgdx) [Maven Central: mini2dx-libgdx-desktop-lwjgl2 2.0.0-alpha.28](https://central.sonatype.com/artifact/org.mini2Dx/mini2Dx-libgdx-desktop-lwjgl2/2.0.0-alpha.28) — there is no stable 2.0 release, and the last stable line (1.9.x) is bugfix-only, six-plus years old. For a mentoring curriculum meant to be reused and maintained, betting on a project stuck in beta with a tiny community is a real risk.

---

## Option 3: Plain JavaFX (Canvas + input events, no dedicated game engine)

JavaFX is part of the OpenJDK-adjacent ecosystem (OpenJFX), giving a Java scene-graph UI toolkit with a `Canvas` node for immediate-mode 2D drawing and a standard event model for keyboard/mouse.

**Learning curve.** Potentially the gentlest on-ramp *conceptually* because it's "just Java" with no new mental model of a game engine's lifecycle/asset pipeline to learn — the learner already knows Java, and JavaFX's event-driven UI model resembles what she may have touched via HTML/basic UI concepts. However, this comes at a cost: there is no game loop, sprite system, camera, or tilemap renderer provided — the mentor/curriculum has to build all of that from primitives, which is exactly the kind of "boilerplate game infrastructure" work a game engine exists to avoid.

**Tutorial/community support.** Official getting-started docs exist at openjfx.io, including a dedicated Maven walkthrough ("Run HelloWorld using Maven") [openjfx.io/openjfx-docs/maven](https://openjfx.io/openjfx-docs/maven). Community support is large (JavaFX is a mainstream desktop UI toolkit), but none of it is game-dev-specific — there is no official "build a 2D game in JavaFX" tutorial track comparable to LibGDX's.

**Maven-friendliness — the strongest of the three.** JavaFX publishes official artifacts to Maven Central under `org.openjfx` (e.g. `org.openjfx:javafx-controls`) [Maven Central: javafx-controls](https://central.sonatype.com/artifact/org.openjfx/javafx-controls), and the official docs provide a ready-made Maven archetype command (`mvn archetype:generate -DarchetypeGroupId=org.openjfx -DarchetypeArtifactId=javafx-archetype-simple ...`) plus a documented `javafx-maven-plugin` for `mvn clean javafx:run` [openjfx.io/openjfx-docs/maven](https://openjfx.io/openjfx-docs/maven). This is the cleanest, most "official," most Maven-native setup of all three options — unsurprising, since JavaFX ships from the same lineage as the JDK the learner already builds with via Maven.

**Licensing.** GPL v2 with the Classpath Exception, "just like the OpenJDK" per the official OpenJFX site and the OpenJDK wiki [openjfx.io](https://openjfx.io/) [wiki.openjdk.org/spaces/OpenJFX/pages/8257538/Main](https://wiki.openjdk.org/spaces/OpenJFX/pages/8257538/Main). The Classpath Exception means the application using JavaFX does not itself need to be GPL-licensed or have its source distributed — safe and free for a personal mentoring project.

**Tilemap/sprite/input support — the weak point.** JavaFX ships no Tiled/TMX loader, no sprite class, and no camera/viewport abstraction; Tiled's own "Libraries and Frameworks" reference page, which explicitly surveys the Java ecosystem (listing libGDX, Slick2D, Cell2D, LITIENGINE, and the standalone `libtiled-java`/`TiledReader` readers), does not list JavaFX or any JavaFX-based tool at all [doc.mapeditor.org — Libraries and Frameworks](https://doc.mapeditor.org/en/stable/reference/support-for-tmx-maps/). Input handling is present and well-documented (`KeyEvent`, `onKeyPressed`/`onKeyReleased`, modifier states) via the standard `javafx.scene.input` package [openjfx.io javadoc — KeyEvent](https://openjfx.io/javadoc/25/javafx.graphics/javafx/scene/input/KeyEvent.html), so keyboard input alone is not a blocker — but everything tilemap- and sprite-related (parsing a `.tmx`/`.json` Tiled export, drawing tile layers efficiently, sprite-sheet slicing/animation, a walkable-region concept) would have to be hand-rolled by the mentor as teaching material, which is a significant amount of infrastructure to build and maintain outside of what the curriculum is actually trying to teach (persistence + a farm view).

---

## Comparison table

| Dimension | LibGDX | Mini2Dx | Plain JavaFX (Canvas) |
|---|---|---|---|
| Learning curve for Java-fluent, zero-game-dev learner | Gentle — official tutorial written for exactly this profile | Marketed as simpler than LibGDX, but wraps it, so underlying concepts still surface | Conceptually simplest ("just Java") but must build game concepts (loop, camera, sprites) from scratch |
| Tutorial/community support | Excellent — official wiki, official beginner tutorial, Discord, 25.4k GitHub stars | Sparse — official site/wiki/forum exist but no beginner tutorial track; tiny community (tens of stars) | Large general JavaFX community, but zero game-dev-specific official tutorials |
| Maven-friendliness | Weak officially — recommended tool (gdx-liftoff) generates Gradle; Maven "possible" but unsupported for cross-platform (acceptable here since we're desktop-only) | Fine mechanically — real Maven Central coordinates (`org.mini2Dx`) | Best — official Maven archetype, official `javafx-maven-plugin`, artifacts under `org.openjfx` on Maven Central |
| Licensing | Apache 2.0 — fully permissive | BSD 3-Clause (+ Apache 2.0 for LibGDX-derived parts) — fully permissive | GPL v2 + Classpath Exception — free to use, app need not be GPL |
| Built-in tilemap + sprite + input | All three, mature and documented together (TmxMapLoader/OrthogonalTiledMapRenderer, Sprite, InputProcessor) | Tiled module present, sprite/input inherited from LibGDX, but less unified documentation | Input only (KeyEvent etc.); no tilemap or sprite support — both must be hand-built |
| Project health / maintenance risk | Active: last release Oct 2025, large community | Stalled: stable line frozen since 2019, 2.0 still alpha/beta with no stable release | Actively maintained as part of OpenJDK ecosystem, but not a game framework at all |

---

## Recommendation

**Use LibGDX**, set up as a **desktop-only Maven project** using the official Maven Central coordinates (`com.badlogicgames.gdx:gdx`, `com.badlogicgames.gdx:gdx-backend-lwjgl3`, `com.badlogicgames.gdx:gdx-platform` natives) rather than the officially-recommended Gradle/gdx-liftoff path.

Reasoning, distilled:

1. **It is the only option that ships all three needed building blocks — Tiled tilemap loading/rendering, sprites, and keyboard/mouse input — mature, maintained, and documented together**, which is exactly the prototype's scope (tilemap + walkable character + clickable sprites). Mini2Dx has these too but only by inheriting LibGDX underneath, with far less documentation of its own. Plain JavaFX has none of the tilemap/sprite pieces and would force the curriculum to spend its early lessons building game infrastructure instead of connecting to Postgres.
2. **Its official "A Simple Game" tutorial is genuinely written for a beginner with Java skills and no game-dev background** — it explicitly teaches the game-loop/delta-time/input concepts a total game-dev newcomer needs, which fits the apprentice's profile precisely.
3. **It is by far the healthiest, most active, best-supported project of the three** (25.4k stars, official Discord, Apache-2.0-licensed, releases as recent as October 2025), versus Mini2Dx's stalled 1.9.x line and unreleased 2.0 beta.
4. **The Maven caveat is real but manageable for this project's actual scope.** LibGDX's own docs warn that Maven-based setup is unsupported for cross-platform builds — but this prototype is explicitly desktop-only (no Android/HTML5/iOS target), which is precisely the one configuration LibGDX's own Maven-integration article says is "known to work." The trade-off is that the mentor writes the `pom.xml` by hand (using the Maven Central coordinates cited above) instead of using the official Gradle-based `gdx-liftoff` generator, and should expect to rely on the wiki/community rather than official Maven support if something breaks.

JavaFX remains the better choice on Maven-friendliness alone, and could be revisited if a future need arises for a more UI-toolkit-style (forms/dialogs-heavy) farm-management app rather than a walkable tilemap view — but for this specific prototype, the missing tilemap/sprite infrastructure makes it the wrong primary choice. Mini2Dx is not recommended due to its stalled/unreleased project status, which is an unacceptable risk for curriculum material meant to be reused and extended over time.
