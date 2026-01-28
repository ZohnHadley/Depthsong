package co.px.depthsong.engin.ECS.runtime.systems;

import co.px.depthsong.engin.ECS.core.ComponentList;
import co.px.depthsong.engin.ECS.core.abstractClasses.EcsEntity;
import co.px.depthsong.engin.ECS.core.EntityContext;
import co.px.depthsong.engin.ECS.core.interfaces.IEcsSystem;
import co.px.depthsong.engin.ECS.runtime.components.ComponentBoxCollider;
import co.px.depthsong.engin.ECS.runtime.components.ComponentSprite;
import co.px.depthsong.engin.ECS.runtime.components.ComponentTransform;
import co.px.depthsong.engin.ECS.utils.MasterShapeRenderer;
import co.px.depthsong.engin.engineCore.util.GameCamera;
import co.px.depthsong.game.models.Level.GroundColumn;
import co.px.depthsong.game.models.Level.LevelGen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

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
    private MasterShapeRenderer SHAPERENDERER = MasterShapeRenderer.getInstance();

    private FrameBuffer gameViewFrameBuffer;

    private int window_width;
    private int window_height;

    private RenderingSystem() {
        instance = this;

        LEVEL_SPRITE_BATCH = new SpriteBatch();
        ENTITIES_SPRITE_BATCH = new SpriteBatch();
        UI_BATCH = new SpriteBatch();

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



    private void renderEntities() {


        ENTITIES_SPRITE_BATCH.setProjectionMatrix(GameCamera.getInstance().combined);
        for (EcsEntity ent : EntityContext.getInstance().getEntities().values()) {

            if (ent != null) {
                ComponentList components = ent.getComponentList();

                ComponentTransform transformComponent = (ComponentTransform) components.get(ComponentTransform.class);
                ComponentBoxCollider boxColliderComponent = (ComponentBoxCollider) components.get(ComponentBoxCollider.class);

                //Draw entity with sprites
                if(components.has(ComponentSprite.class)) {
                    ComponentSprite spriteComponent = (ComponentSprite) components.get(ComponentSprite.class);

                    ENTITIES_SPRITE_BATCH.begin();
                        if (isVisible(transformComponent.getPosition(), spriteComponent.getSprite().getWidth(), spriteComponent.getSprite().getHeight(), GameCamera.getInstance(), 8f, false)) {
                            boxColliderComponent.setSize(new Vector2(spriteComponent.getSprite().getWidth(), spriteComponent.getSprite().getHeight()));
                            boxColliderComponent.setPosition(new Vector2(transformComponent.getPosition().x, transformComponent.getPosition().y));

                            transformComponent.setSize(new Vector3(spriteComponent.getSprite().getWidth(), spriteComponent.getSprite().getHeight(), 0));

                            spriteComponent.getSprite().setPosition(transformComponent.getPosition().x, transformComponent.getPosition().y);
                            spriteComponent.getSprite().draw(ENTITIES_SPRITE_BATCH);
                        }
                    ENTITIES_SPRITE_BATCH.end();
                }

            }
        }

    }

    private boolean isVisible(Vector3 position,
                              float width, float height,
                              GameCamera camera,
                              float margin,
                              boolean positionIsCenter) {

        // Camera bounds (world space, zoom-aware)
        float halfW = (camera.viewportWidth * camera.zoom) * 0.5f;
        float halfH = (camera.viewportHeight * camera.zoom) * 0.5f;

        float camLeft = camera.getVirtual_position().x - halfW - margin;
        float camRight = camera.getVirtual_position().x + halfW + margin;
        float camBottom = camera.getVirtual_position().y - halfH - margin;
        float camTop = camera.getVirtual_position().y + halfH + margin;

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
        ScreenUtils.clear(0.01f,0.2f,0.5f,1);

        SHAPERENDERER.setProjectionMatrix(GameCamera.getInstance().combined);
        SHAPERENDERER.begin(ShapeRenderer.ShapeType.Filled);
            for(float row_x_position_adjusted : LevelGen.getInstance().getColumns().keySet()) {
                List<GroundColumn> columns = LevelGen.getInstance().getColumns().get(row_x_position_adjusted);
                for (GroundColumn column : columns) {
                    if(isVisible(new Vector3(column.getPosition().x , -column.getPosition().y * 1.5f, 0), column.getDimensions().x, column.getDimensions().y, GameCamera.getInstance(), 0, true)){
                        SHAPERENDERER.setColor(column.getColor());
                        SHAPERENDERER.rect(column.getPosition().x   , -column.getPosition().y * 1.5f, column.getDimensions().x, column.getDimensions().y);
                    }
                }
//                SHAPERENDERER.rect(node.x, (index*5), node.y, 32);
            }
        SHAPERENDERER.end();

        renderEntities();
    }

    public void dispose() {
        LEVEL_SPRITE_BATCH.dispose();
        ENTITIES_SPRITE_BATCH.dispose();
        UI_BATCH.dispose();
    }
}
