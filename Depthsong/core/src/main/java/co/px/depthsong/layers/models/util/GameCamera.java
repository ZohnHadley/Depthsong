package co.px.depthsong.layers.models.util;

import co.px.depthsong.ECS.abstractClasses.EcsEntity;
import co.px.depthsong.ECS.entityContext.EntityContext;
import co.px.depthsong.gameUtils.GeneralUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class GameCamera {
    private final OrthographicCamera camera;
    private static GameCamera instance;
    private float cameraZoom = 0.5f;

    private EcsEntity target;
    private Vector2 targetPosition;
    @Getter(AccessLevel.NONE)
    private Vector2 position;


    private GameCamera() {
        camera = new OrthographicCamera(GeneralUtils.display_width,  GeneralUtils.display_height);
        position = new Vector2(0, 0);
        if (EntityContext.getInstance().getPlayer() != null)
            setTarget(EntityContext.getInstance().getPlayer());
    }

    public static GameCamera getInstance() {
        if (instance == null) {
            instance = new GameCamera();
        }
        return instance;
    }

    public void update(float deltaTime) {
        // Handle zoom controls
        if (Gdx.input.isKeyPressed(Input.Keys.Q) && cameraZoom < 0.75f) {
            cameraZoom += 0.02f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E) && cameraZoom > 0.25f) {
            cameraZoom -= 0.02f;
        }

        // Move position (your WASD movement)
        if (Gdx.input.isKeyPressed(Input.Keys.W)) position.y += 32 * deltaTime * 10f;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) position.y -= 32 * deltaTime * 10f;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) position.x += 32 * deltaTime * 10f;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) position.x -= 32 * deltaTime * 10f;

        // Apply target or manual position
        camera.position.set(position.x, position.y, 0);

        // Apply zoom smoothly
        camera.zoom = (float) GeneralUtils.lerp(camera.zoom, cameraZoom, 0.7f);

        // Update matrices — critical before unproject()
        camera.update(true);
    }


    private void moveCameraToTarget() {
        targetPosition.x = target.getTransform().getPosition().x;
        targetPosition.y = target.getTransform().getPosition().y;

        camera.position.set(GeneralUtils.lerp(camera.position, targetPosition, 0.1f));
    }

    public void setViewPort(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(GeneralUtils.lerp(camera.position, position, 0.1f));
        camera.update();
    }

    public Vector2 getViewPortCenter(){
        return new Vector2(getCamera().viewportWidth * 0.5f, getCamera().viewportWidth * 0.5f);
    }


    public void setTarget(EcsEntity target) {
        this.target = target;
        targetPosition = GeneralUtils.vector3ToVector2(target.getTransform().getPosition());
    }
}
