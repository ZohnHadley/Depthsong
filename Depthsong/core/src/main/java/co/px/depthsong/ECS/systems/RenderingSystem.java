package co.px.depthsong.ECS.systems;

import co.px.depthsong.ECS.abstractClasses.EcsEntity;
import co.px.depthsong.ECS.entityContext.EntityContext;
import co.px.depthsong.layers.models.GameObject2D;
import co.px.depthsong.layers.models.util.GameCamera;
import co.px.depthsong.layers.models.util.VirtualMouse;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class RenderingSystem {
    @Getter(AccessLevel.NONE)
    private static RenderingSystem instance = null;

    @Getter(AccessLevel.NONE)
    private final SpriteBatch GAME_VIEW_BATCH;
    private FrameBuffer gameViewFrameBuffer;

    @Getter(AccessLevel.NONE)
    private final SpriteBatch UI_BATCH;

    private final ShapeRenderer SHAPERENDERER;
    private int window_width;
    private int window_height;

    private RenderingSystem() {
        instance = this;
        GAME_VIEW_BATCH = new SpriteBatch();
        gameViewFrameBuffer = new FrameBuffer(Pixmap.Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        UI_BATCH = new SpriteBatch();
        SHAPERENDERER = new ShapeRenderer();
        window_width = Gdx.graphics.getWidth();
        window_height = Gdx.graphics.getHeight();
    }

    public static RenderingSystem getInstance() {
        if(instance == null){
            instance = new RenderingSystem();
        }
        return instance;
    }

    private void refreshDimensions(){
        window_width = Gdx.graphics.getWidth();
        window_height = Gdx.graphics.getHeight();
    }

    public void resize() {
        refreshDimensions();
        float scale = (float) window_width / window_height;
        int width = window_width;
        int height = window_height;

        if(scale > 1.5f){
            //landscape screen
            width = (int)(height * scale);
        } else {
            //portrait
            height = (int)(width / scale);
        }

        int posX = (int)((window_width*0.5f) - (width*0.5f));
        int posY = (int) ((window_height*0.5f) - (height*0.5f));
        GameCamera.getInstance().setViewPort(width, height);
        //gameManager.getScreenManager().getCurrentScreen().resize(posX, posY, width, height);
    }

    private boolean isVisible(Vector2 position,
                                    Sprite sprite,
                                    OrthographicCamera camera,
                                    float margin,
                                    boolean positionIsCenter) {

        // Camera bounds (world space, zoom-aware)
        float halfW = (camera.viewportWidth  * camera.zoom) * 0.5f;
        float halfH = (camera.viewportHeight * camera.zoom) * 0.5f;

        float camLeft   = camera.position.x - halfW - margin;
        float camRight  = camera.position.x + halfW + margin;
        float camBottom = camera.position.y - halfH - margin;
        float camTop    = camera.position.y + halfH + margin;

        // Object bounds
        float w = sprite.getWidth();
        float h = sprite.getHeight();

        float objLeft, objBottom;
        if (positionIsCenter) {
            objLeft   = position.x - w * 0.5f;
            objBottom = position.y - h * 0.5f;
        } else {
            objLeft   = position.x;
            objBottom = position.y;
        }

        float objRight = objLeft + w;
        float objTop   = objBottom + h;

        // AABB overlap
        return objRight > camLeft &&
            objLeft  < camRight &&
            objTop   > camBottom &&
            objBottom < camTop;
    }


    private void renderEntities() {
        boolean collided = false;

        for (EcsEntity ent : EntityContext.getInstance().getEntities().values()) {
            if (ent instanceof GameObject2D) {
                GameObject2D gameObj = (GameObject2D) ent;
                if(isVisible(gameObj.getPosition(), gameObj.getSprite(), GameCamera.getInstance().getCamera(), 8f, false)){
                    gameObj.draw(GAME_VIEW_BATCH);

                }
            }
        }

    }
    private void renderDebug(){
        SHAPERENDERER.setProjectionMatrix(GameCamera.getInstance().getCamera().combined);
        SHAPERENDERER.begin(ShapeRenderer.ShapeType.Line);
        SHAPERENDERER.setColor(Color.WHITE);
        for (EcsEntity ent : EntityContext.getInstance().getEntities().values()) {
            if (ent instanceof GameObject2D) {
                GameObject2D gameObj = (GameObject2D) ent;
                if(isVisible(gameObj.getPosition(), gameObj.getSprite(), GameCamera.getInstance().getCamera(), 8f, false)){
                    SHAPERENDERER.rect(gameObj.getPosition().x-1.5f, gameObj.getPosition().y-1.5f,3,3);
                    SHAPERENDERER.setColor(Color.RED);
                    SHAPERENDERER.rect(gameObj.getComponentCubeCollider().getPosition().x, gameObj.getComponentCubeCollider().getPosition().y, (float) gameObj.getComponentCubeCollider().getDimensions().getWidth(), (float) gameObj.getComponentCubeCollider().getDimensions().getHeight());

                }
            }
        }
        SHAPERENDERER.setColor(Color.BLUE);

        // VirtualMouse in world space
        SHAPERENDERER.rect(
            VirtualMouse.getInstance().getPosition().x,
            VirtualMouse.getInstance().getPosition().y,
            (float) VirtualMouse.getInstance().getDimensions().getWidth(), (float) VirtualMouse.getInstance().getDimensions().getHeight()
        );
        SHAPERENDERER.end();
    }

    public void render() {
        ScreenUtils.clear(0.7f, 0.7f, 0.16f, 1f);
        //if (gameManager.isPlayerCreated()) {

        GAME_VIEW_BATCH.begin();
        GAME_VIEW_BATCH.setProjectionMatrix(GameCamera.getInstance().getCamera().combined);
            renderEntities();
        GAME_VIEW_BATCH.end();

        // Draw camera-space (world) things
//        SHAPERENDERER.setProjectionMatrix(GameCamera.getInstance().getCamera().combined);
//        SHAPERENDERER.begin(ShapeRenderer.ShapeType.Line);
//        SHAPERENDERER.setColor(debugColor);
//
//        // VirtualMouse in world space
//        SHAPERENDERER.rect(
//            VirtualMouse.getInstance().getPosition().x,
//            VirtualMouse.getInstance().getPosition().y,
//            (float) VirtualMouse.getInstance().getDimensions().getWidth(), (float) VirtualMouse.getInstance().getDimensions().getHeight()
//        );
//        SHAPERENDERER.end();
        renderDebug();

//        UI_BATCH.begin();
//            gameManager.getScreenManager().getCurrentScreen().render();
//        UI_BATCH.end();
    }

    public void dispose() {
        GAME_VIEW_BATCH.dispose();
        UI_BATCH.dispose();
    }
}
