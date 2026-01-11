package co.px.depthsong.engineCore.models.util;

import co.px.depthsong.ECS.core.ComponentList;
import co.px.depthsong.ECS.core.abstractClasses.EcsEntity;
import co.px.depthsong.ECS.runtime.components.ComponentTransform;
import co.px.depthsong.ECS.core.EntityContext;
import co.px.depthsong.enginUtils.GeneralUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GameCamera {
    private final OrthographicCamera camera;
    private static GameCamera instance;
    private float cameraZoom = 0.5f;

    @Getter
    @Setter
    private EcsEntity target;
    @Getter(AccessLevel.NONE)
    private Vector2 position;


    private GameCamera() {
        camera = new OrthographicCamera(GeneralUtils.display_width,  GeneralUtils.display_height);
        position = new Vector2(0, 0);

    }

    public static GameCamera getInstance() {
        if (instance == null) {
            instance = new GameCamera();
        }
        return instance;
    }

    public void update(float deltaTime) {
        // Handle zoom controls
        if (Gdx.input.isKeyPressed(Input.Keys.Q) && cameraZoom < 0.50f) {
            cameraZoom += 0.02f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E) && cameraZoom > 0.40f) {
            cameraZoom -= 0.02f;
        }

        // Move position (your WASD movement)
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) position.y += 32 * deltaTime * 10f;
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) position.y -= 32 * deltaTime * 10f;
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) position.x += 32 * deltaTime * 10f;
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) position.x -= 32 * deltaTime * 10f;
        moveCameraToTarget();


        // Apply zoom smoothly
        camera.zoom = (float) GeneralUtils.lerp(camera.zoom, cameraZoom, 0.7f);

        // Update matrices — critical before unproject()
        camera.update(true);
    }


    private void moveCameraToTarget() {

        ComponentList targetComponents = target.getComponentList();
        ComponentTransform transform = ((ComponentTransform)targetComponents.get(ComponentTransform.class));

        Vector2 targetPosition = new Vector2(transform.getPosition().x, transform.getPosition().y);

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


}
