package farm.prototype.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/** PROTOTYPE — desktop-only launcher, per ticket 01's Maven-desktop-only decision. */
public class Lwjgl3Launcher {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Farm Game Prototype — wayfinder ticket 05 (throwaway)");
        config.setWindowedMode(FarmGame.WORLD_WIDTH, FarmGame.WORLD_HEIGHT);
        config.useVsync(true);
        new Lwjgl3Application(new FarmGame(), config);
    }
}
