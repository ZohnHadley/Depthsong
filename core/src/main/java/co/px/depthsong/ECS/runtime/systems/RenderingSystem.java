package co.px.depthsong.ECS.runtime.systems;

import co.px.depthsong.ECS.core.ComponentList;
import co.px.depthsong.ECS.core.abstractClasses.EcsEntity;
import co.px.depthsong.ECS.core.EntityContext;
import co.px.depthsong.ECS.core.interfaces.IEcsSystem;
import co.px.depthsong.ECS.runtime.components.ComponentBoxCollider;
import co.px.depthsong.ECS.runtime.components.ComponentSprite;
import co.px.depthsong.ECS.runtime.components.ComponentTransform;
import co.px.depthsong.engineCore.models.GameObject2D;
import co.px.depthsong.engineCore.models.util.GameCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class RenderingSystem implements IEcsSystem {

    @Getter(AccessLevel.NONE)
    private static RenderingSystem instance = null;

    @Getter(AccessLevel.NONE)
    private final SpriteBatch LEVEL_SPRITE_BATCH;
    @Getter(AccessLevel.NONE)
    private final SpriteBatch ENTITIES_SPRITE_BATCH;
    @Getter(AccessLevel.NONE)
    private final SpriteBatch UI_BATCH;
    @Getter(AccessLevel.NONE)
    private final ShapeRenderer SHAPERENDERER;

    private FrameBuffer gameViewFrameBuffer;

    private int window_width;
    private int window_height;

    private RenderingSystem() {
        instance = this;

        LEVEL_SPRITE_BATCH = new SpriteBatch();
        ENTITIES_SPRITE_BATCH = new SpriteBatch();
        UI_BATCH = new SpriteBatch();
        SHAPERENDERER = new ShapeRenderer();

        gameViewFrameBuffer = new FrameBuffer(Pixmap.Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        window_width = Gdx.graphics.getWidth();
        window_height = Gdx.graphics.getHeight();
    }

    public static RenderingSystem getInstance() {
        if (instance == null) {
            instance = new RenderingSystem();
        }
        return instance;
    }

    public void resize() {

        window_width = Gdx.graphics.getWidth();
        window_height = Gdx.graphics.getHeight();

        float scale = (float) window_width / window_height;
        int width = window_width;
        int height = window_height;

        if (scale > 1.5f) {
            //landscape screen
            width = (int) (height * scale);
        } else {
            //portrait
            height = (int) (width / scale);
        }

        // int posX = (int) ((window_width * 0.5f) - (width * 0.5f));
        // int posY = (int) ((window_height * 0.5f) - (height * 0.5f));
        GameCamera.getInstance().setViewPort(width, height);
        //gameManager.getScreenManager().getCurrentScreen().resize(posX, posY, width, height);
    }

    private void drawCurrentLevel(){

    }

    private void drawEntity(EcsEntity object) {
        ComponentList components = object.getComponentList();

        ComponentTransform transform = (ComponentTransform) components.get(ComponentTransform.class);
        ComponentBoxCollider boxCollider = (ComponentBoxCollider) components.get(ComponentBoxCollider.class);

        //if entity has sprite
        if (components.has(ComponentSprite.class)) {
            ComponentSprite sprite = (ComponentSprite) components.get(ComponentSprite.class);

            boxCollider.setSize(new Vector2(sprite.getSprite().getWidth(), sprite.getSprite().getHeight()));
            boxCollider.setPosition(new Vector2(transform.getPosition().x, transform.getPosition().y));
            transform.setSize(new Vector3(sprite.getSprite().getWidth(), sprite.getSprite().getHeight(), 0));
            sprite.getSprite().setPosition(transform.getPosition().x, transform.getPosition().y);
            sprite.getSprite().draw(ENTITIES_SPRITE_BATCH);
        }
    }

    private void renderEntities() {
        boolean collided = false;

        for (EcsEntity ent : EntityContext.getInstance().getEntities().values()) {
            if (ent instanceof GameObject2D) {

                GameObject2D gameObj = (GameObject2D) ent;
                ComponentList gameObjectComponentList = ent.getComponentList();

                Sprite gameObjectSprite = ((ComponentSprite) gameObjectComponentList.get(ComponentSprite.class)).getSprite();

                if (isVisible(gameObj.getComponentTransform().getPosition(), gameObjectSprite.getWidth(), gameObjectSprite.getHeight(), GameCamera.getInstance().getCamera(), 8f, false)) {
                    drawEntity(gameObj);
                }
            }
        }

    }

    private boolean isVisible(Vector3 position,
                              float width, float height,
                              OrthographicCamera camera,
                              float margin,
                              boolean positionIsCenter) {

        // Camera bounds (world space, zoom-aware)
        float halfW = (camera.viewportWidth * camera.zoom) * 0.5f;
        float halfH = (camera.viewportHeight * camera.zoom) * 0.5f;

        float camLeft = camera.position.x - halfW - margin;
        float camRight = camera.position.x + halfW + margin;
        float camBottom = camera.position.y - halfH - margin;
        float camTop = camera.position.y + halfH + margin;

        // Object bounds
        float w = width;
        float h = height;

        float objLeft, objBottom;
        if (positionIsCenter) {
            objLeft = position.x - w * 0.5f;
            objBottom = position.y - h * 0.5f;
        } else {
            objLeft = position.x;
            objBottom = position.y;
        }

        float objRight = objLeft + w;
        float objTop = objBottom + h;

        // AABB overlap
        return objRight > camLeft &&
            objLeft < camRight &&
            objTop > camBottom &&
            objBottom < camTop;
    }

    public void render() {
        ScreenUtils.clear(0.3f, 0.5f, 1f, 1f);
        //if (gameManager.isPlayerCreated()) {
        LEVEL_SPRITE_BATCH.begin();
        LEVEL_SPRITE_BATCH.setProjectionMatrix(GameCamera.getInstance().getCamera().combined);
        drawCurrentLevel();
        LEVEL_SPRITE_BATCH.end();

        ENTITIES_SPRITE_BATCH.begin();
        ENTITIES_SPRITE_BATCH.setProjectionMatrix(GameCamera.getInstance().getCamera().combined);
        renderEntities();
        ENTITIES_SPRITE_BATCH.end();

    }

    public void dispose() {
        LEVEL_SPRITE_BATCH.dispose();
        ENTITIES_SPRITE_BATCH.dispose();
        UI_BATCH.dispose();
    }
}
