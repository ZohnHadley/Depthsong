package co.px.depthsong.engineCore.engine_managers;

import co.px.depthsong.engineCore.models.GUIScreen;
import com.badlogic.gdx.Gdx;

public class ScreenManager {
    private static ScreenManager instance = null;
    private GUIScreen currentScreen;
    private GUIScreen previousScreen;

    private ScreenManager() {
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public GUIScreen getCurrentScreen() {

        return currentScreen;
    }

    public GUIScreen getPreviousScreen() {
        return previousScreen;
    }

    public void setCurrentScreen(GUIScreen screen) {
        if (currentScreen != null) {
            previousScreen = currentScreen;
            currentScreen.clear();
        }

        currentScreen = screen;
        currentScreen.show();
        Gdx.input.setInputProcessor(screen.getStage());
    }
}
