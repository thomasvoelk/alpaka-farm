package farm.prototype.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector3;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * PROTOTYPE — wayfinder ticket 05: tilemap + walkable player + click-an-alpaca
 * to see its real Postgres data. No economy/time systems — out of scope.
 *
 * Throwaway artifact for `.scratch/next-curriculum/issues/05-prototype-game-slice.md`.
 * Answers "does this feel like the right level of game" for the farm-game path,
 * as a data point for the path decision (ticket 06) against the dashboard
 * prototype (ticket 04).
 */
public class FarmGame extends ApplicationAdapter {

    static final int TILE_SIZE = 40;
    static final int COLS = 20;
    static final int ROWS = 15;
    public static final int WORLD_WIDTH = COLS * TILE_SIZE;
    public static final int WORLD_HEIGHT = ROWS * TILE_SIZE;

    // Two pens, side by side, in the lower-middle of the map. Rest is grass.
    private static final int PEN_A_COL0 = 2, PEN_A_COL1 = 8, PEN_ROW0 = 3, PEN_ROW1 = 9;
    private static final int PEN_B_COL0 = 11, PEN_B_COL1 = 17;

    private static final float PLAYER_SPEED = 220f;
    private static final float PLAYER_RADIUS = 14f;
    private static final float ANIMAL_RADIUS = 12f;
    private static final float CLICK_RADIUS = 22f;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    private Texture playerTexture;
    private Texture alpacaTexture;

    private float playerX;
    private float playerY;

    private List<PlacedAnimal> placedAnimals;
    private PlacedAnimal selected;

    private record PlacedAnimal(AnimalRow data, float x, float y) {
    }

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.getData().setScale(1.15f);

        buildTilemap();
        buildSprites();
        placedAnimals = loadAndPlaceAnimals();

        playerX = WORLD_WIDTH / 2f;
        playerY = (ROWS - 2) * TILE_SIZE;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 world = camera.unproject(new Vector3(screenX, screenY, 0));
                selected = pickAnimal(world.x, world.y);
                if (selected != null) {
                    AnimalRow a = selected.data();
                    System.out.printf(
                        "[click] %s (%s) — %s, born %s, %.1fkg, fleece: %s%n",
                        a.name(), a.penLabel(), a.species(), a.birthDate(), a.weightKg(), a.fleeceColor());
                }
                return true;
            }
        });
    }

    private void buildTilemap() {
        Texture grassTex = solidTexture(new Color(0.36f, 0.62f, 0.29f, 1f));
        Texture penTex = solidTexture(new Color(0.62f, 0.49f, 0.32f, 1f));
        Texture fenceTex = solidTexture(new Color(0.36f, 0.24f, 0.14f, 1f));

        StaticTiledMapTile grassTile = new StaticTiledMapTile(new TextureRegion(grassTex));
        StaticTiledMapTile penTile = new StaticTiledMapTile(new TextureRegion(penTex));
        StaticTiledMapTile fenceTile = new StaticTiledMapTile(new TextureRegion(fenceTex));

        TiledMapTileLayer layer = new TiledMapTileLayer(COLS, ROWS, TILE_SIZE, TILE_SIZE);
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                if (isPenInterior(col, row)) {
                    cell.setTile(penTile);
                } else if (isPenFence(col, row)) {
                    cell.setTile(fenceTile);
                } else {
                    cell.setTile(grassTile);
                }
                layer.setCell(col, row, cell);
            }
        }

        map = new TiledMap();
        map.getLayers().add(layer);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    private void buildSprites() {
        playerTexture = solidTexture(new Color(0.2f, 0.35f, 0.85f, 1f));
        alpacaTexture = solidTexture(new Color(0.95f, 0.92f, 0.85f, 1f));
    }

    /** A flat-color square texture — no art assets for a throwaway prototype. */
    private Texture solidTexture(Color color) {
        Pixmap pixmap = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private boolean inRange(int v, int lo, int hi) {
        return v >= lo && v <= hi;
    }

    private boolean isPenInterior(int col, int row) {
        return (inRange(col, PEN_A_COL0, PEN_A_COL1) && inRange(row, PEN_ROW0, PEN_ROW1))
            || (inRange(col, PEN_B_COL0, PEN_B_COL1) && inRange(row, PEN_ROW0, PEN_ROW1));
    }

    private boolean isPenFence(int col, int row) {
        boolean nearA = inRange(col, PEN_A_COL0 - 1, PEN_A_COL1 + 1) && inRange(row, PEN_ROW0 - 1, PEN_ROW1 + 1);
        boolean nearB = inRange(col, PEN_B_COL0 - 1, PEN_B_COL1 + 1) && inRange(row, PEN_ROW0 - 1, PEN_ROW1 + 1);
        return (nearA || nearB) && !isPenInterior(col, row);
    }

    private List<PlacedAnimal> loadAndPlaceAnimals() {
        List<AnimalRow> rows;
        try {
            rows = FarmDataService.fetchHerdAnimals("North Pasture Herd");
        } catch (SQLException e) {
            System.err.println("[farm-game] Could not reach the farm Postgres db — is `docker compose up -d` running in sql-curriculum? " + e.getMessage());
            rows = new ArrayList<>();
        }

        Map<String, List<AnimalRow>> byPen = new LinkedHashMap<>();
        for (AnimalRow row : rows) {
            byPen.computeIfAbsent(row.penLabel(), k -> new ArrayList<>()).add(row);
        }

        int[][] penBounds = {{PEN_A_COL0, PEN_A_COL1}, {PEN_B_COL0, PEN_B_COL1}};
        int middleRow = (PEN_ROW0 + PEN_ROW1) / 2;

        List<PlacedAnimal> placed = new ArrayList<>();
        int penIndex = 0;
        for (List<AnimalRow> penAnimals : byPen.values()) {
            int[] bounds = penBounds[Math.min(penIndex, penBounds.length - 1)];
            int colStart = bounds[0], colEnd = bounds[1];
            int n = penAnimals.size();
            float spacing = (colEnd - colStart) / (float) (n + 1);
            for (int i = 0; i < n; i++) {
                float col = colStart + spacing * (i + 1);
                float x = col * TILE_SIZE + TILE_SIZE / 2f;
                float y = middleRow * TILE_SIZE + TILE_SIZE / 2f;
                placed.add(new PlacedAnimal(penAnimals.get(i), x, y));
            }
            penIndex++;
        }
        System.out.println("[farm-game] Loaded " + placed.size() + " animals from the farm db across " + byPen.size() + " pens.");
        return placed;
    }

    private PlacedAnimal pickAnimal(float worldX, float worldY) {
        PlacedAnimal closest = null;
        float closestDist = CLICK_RADIUS;
        for (PlacedAnimal a : placedAnimals) {
            float dist = Vector3.dst(worldX, worldY, 0, a.x(), a.y(), 0);
            if (dist <= closestDist) {
                closest = a;
                closestDist = dist;
            }
        }
        return closest;
    }

    private void handleInput(float delta) {
        float dx = 0, dy = 0;
        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) dx -= 1;
        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) dx += 1;
        if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) dy += 1;
        if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) dy -= 1;

        playerX += dx * PLAYER_SPEED * delta;
        playerY += dy * PLAYER_SPEED * delta;
        playerX = Math.max(PLAYER_RADIUS, Math.min(WORLD_WIDTH - PLAYER_RADIUS, playerX));
        playerY = Math.max(PLAYER_RADIUS, Math.min(WORLD_HEIGHT - PLAYER_RADIUS, playerY));
    }

    @Override
    public void render() {
        handleInput(Gdx.graphics.getDeltaTime());

        clearScreen();
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (PlacedAnimal a : placedAnimals) {
            float r = a == selected ? ANIMAL_RADIUS * 1.3f : ANIMAL_RADIUS;
            batch.draw(alpacaTexture, a.x() - r, a.y() - r, r * 2, r * 2);
        }
        batch.draw(playerTexture, playerX - PLAYER_RADIUS, playerY - PLAYER_RADIUS, PLAYER_RADIUS * 2, PLAYER_RADIUS * 2);
        font.setColor(Color.WHITE);
        font.draw(batch, "WASD / arrows to move  -  click an alpaca to inspect", 12, WORLD_HEIGHT - 10);
        batch.end();

        if (selected != null) {
            drawSelectedPanel();
        }
    }

    private void drawSelectedPanel() {
        AnimalRow a = selected.data();
        float panelX = 12, panelY = 12, panelW = 340, panelH = 118;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, 0.72f);
        shapeRenderer.rect(panelX, panelY, panelW, panelH);
        shapeRenderer.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setColor(Color.WHITE);
        float textX = panelX + 12;
        float textY = panelY + panelH - 12;
        font.draw(batch, a.name() + "  (" + a.penLabel() + ")", textX, textY);
        font.draw(batch, "Species: " + a.species(), textX, textY - 24);
        font.draw(batch, "Born: " + a.birthDate() + "   Weight: " + a.weightKg() + " kg", textX, textY - 48);
        font.draw(batch, "Fleece color: " + a.fleeceColor(), textX, textY - 72);
        batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.08f, 0.09f, 0.1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
        mapRenderer.dispose();
        map.dispose();
        playerTexture.dispose();
        alpacaTexture.dispose();
    }
}
