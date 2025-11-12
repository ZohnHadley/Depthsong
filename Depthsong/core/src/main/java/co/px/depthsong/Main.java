package co.px.depthsong;

import co.px.depthsong.layers.models.GameLevel;
import co.px.depthsong.layers.services.JsonService;
import co.px.depthsong.layers.services.level.GameLevelService;
import co.px.depthsong.gameUtils.GeneralTimer;
import co.px.depthsong.gameUtils.StructGameScreens;
import co.px.depthsong.layers.models.entities.ClientPlayer;
import co.px.depthsong.layers.managers.GameManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {

    //////
    private GameLevelService gameLevelService;
    private GameManager gameManager;
    boolean isDebug = false;
    FrameBuffer frameBuffer;

    GameLevel testlevel;
    @Override
    public void create() {
        //frameBuffer = new FrameBuffer(Pixmap.Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

        gameManager = GameManager.getInstance();
        gameLevelService = GameLevelService.getInstance();
        gameLevelService.loadAllPrebuiltLevels();
//        for(int i = 0; i < 2000; i++) {
//        }
        ClientPlayer entity = ClientPlayer.getInstance();
        entity.getTransform().setPosition(new Vector3(0, 0, 0));

        gameManager.getScreenManager().setCurrentScreen(StructGameScreens.mainMenu);
        testlevel = new  GameLevel();
        Gdx.app.log("GameLevelService", JsonService.getInstance().toJson(entity));
    }

    // This method is called every frame. optional methode could just use render
    private void update(float deltaTime) {
        GeneralTimer.runTime += deltaTime;

        gameManager.update(deltaTime);

//        gameManager.getScreenManager().getCurrentScreen().update();
    }

    @Override
    public void resize(int width, int height) {
        // This method is called when the window is resized.
        // You can use it to adjust the viewport size.

        gameManager.getScreenManager().getCurrentScreen().resize(width, height);
        gameManager.getRenderingSystem().resize();
    }

    @Override
    public void render() {
        //update stuff
        update(Gdx.graphics.getDeltaTime());
    }


    @Override
    public void dispose() {
        gameManager.getRenderingSystem().dispose();
    }


}
