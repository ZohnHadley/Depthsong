package co.px.depthsong.layers.managers;

import co.px.depthsong.layers.models.GUIBaseScreen;
import com.badlogic.gdx.Gdx;

public class ScreenManager {
    private static ScreenManager instance = null;
    private GUIBaseScreen currentScreen;
    private GUIBaseScreen previousScreen;

    private ScreenManager() {
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public GUIBaseScreen getCurrentScreen() {

        return currentScreen;
    }

    public GUIBaseScreen getPreviousScreen() {
        return previousScreen;
    }

    public void setCurrentScreen(GUIBaseScreen screen) {
        if (currentScreen != null) {
            previousScreen = currentScreen;
            currentScreen.clear();
        }

        currentScreen = screen;
        currentScreen.show();
        Gdx.input.setInputProcessor(screen.getStage());
    }
}
