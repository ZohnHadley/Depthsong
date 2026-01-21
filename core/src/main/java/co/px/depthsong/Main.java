package co.px.depthsong;

import co.px.depthsong.ECS.core.EntityContext;
import co.px.depthsong.engineCore.models.GameLevel;
import co.px.depthsong.engineCore.engine_managers.GameLevelManager;
import co.px.depthsong.enginUtils.GeneralTimer;
import co.px.depthsong.enginUtils.GameScreensList;
import co.px.depthsong.engineCore.engine_managers.GameManager;
import co.px.depthsong.enginUtils.JsonUtil;
import co.px.depthsong.engineCore.models.entities.ClientPlayer;
import co.px.depthsong.engineCore.models.entities.OtherPlayer;
import co.px.depthsong.engineCore.models.entities.PineTree;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {

    private GameManager gameManager;
//    FrameBuffer frameBuffer;

    @Override
    public void create() {
//        jsonUtil = JsonUtil.getInstance();
        gameManager = GameManager.getInstance();
//        frameBuffer = new FrameBuffer(Pixmap.Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

        ClientPlayer.getInstance();
        new OtherPlayer();
        PineTree pineTree = new PineTree();
        pineTree.getComponentTransform().setPosition(10,10,10);
        gameManager.getGameCamera().setTarget(EntityContext.getInstance().getPlayer());

//        gameLevelManager.loadAll();

    }

    // This method is called every frame. optional methode could just use render
    private void update(float deltaTime) {
        GeneralTimer.runTime += deltaTime;

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
        //update stuff
        update(Gdx.graphics.getDeltaTime());
    }


    @Override
    public void dispose() {
        gameManager.getRenderingSystem().dispose();
    }


}
