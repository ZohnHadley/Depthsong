package co.px.depthsong;

import co.px.depthsong.engin.ECS.core.EntityContext;
import co.px.depthsong.engin.enginUtils.GeneralTimer;
import co.px.depthsong.engin.engineCore.engine_managers.GameManager;
import co.px.depthsong.game.models.entities.ClientPlayer;
import co.px.depthsong.game.models.entities.LevelGen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {

    private GameManager gameManager;
//    FrameBuffer frameBuffer;
    private boolean gameLaunched = false;
    @Override
    public void create() {
//        jsonUtil = JsonUtil.getInstance();
//        frameBuffer = new FrameBuffer(Pixmap.Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        gameManager = GameManager.getInstance();
        LevelGen.getInstance();

        ClientPlayer.getInstance();
        gameManager.getGameCamera().setTarget(EntityContext.getInstance().getPlayer());

    }

    // This method is called every frame. optional methode could just use render
    private void update(float deltaTime) {
        GeneralTimer.getInstance().updateRuntime(deltaTime);
        if(!gameLaunched){
            gameManager.start();
            gameLaunched = true;
        }
        gameManager.update(deltaTime);

//        gameManager.getScreenManager().getCurrentScreen().update();
    }

    @Override
    public void resize(int width, int height) {

//        gameManager.getScreenManager().getCurrentScreen().resize(width, height);
        gameManager.getRenderingSystem().resize();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_BLEND_COLOR);
        update(Gdx.graphics.getDeltaTime());
    }


    @Override
    public void dispose() {
        gameManager.getRenderingSystem().dispose();
    }


}
