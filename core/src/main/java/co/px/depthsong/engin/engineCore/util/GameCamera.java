package co.px.depthsong.engin.engineCore.util;

import co.px.depthsong.engin.ECS.core.ComponentList;
import co.px.depthsong.engin.ECS.core.abstractClasses.EcsEntity;
import co.px.depthsong.engin.ECS.runtime.components.ComponentTransform;
import co.px.depthsong.engin.enginUtils.GeneralUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GameCamera extends OrthographicCamera{
    private static GameCamera instance;
    private float cameraZoom = 0.4f;
    @Getter
    @Setter
    private EcsEntity target;
    private Vector2 virtual_position;



    private GameCamera() {
        this.viewportWidth = GeneralUtils.display_width;
        this.viewportHeight = GeneralUtils.display_height;

        this.virtual_position = new Vector2(0, 0);

    }



    public static GameCamera getInstance() {
        if (instance == null) {
            instance = new GameCamera();
        }
        return instance;
    }

    public void refresh(float deltaTime) {
        // Handle zoom controls
        if (Gdx.input.isKeyPressed(Input.Keys.Q) && cameraZoom < 0.4f) {
            cameraZoom += 0.02f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E) && cameraZoom > 0.28f) {
            cameraZoom -= 0.02f;
        }

        moveCameraToTarget();

        // Apply zoom smoothly
        this.zoom = (float) GeneralUtils.lerp(this.zoom, cameraZoom, 0.7f);
        this.position.set(this.virtual_position, 0);

        // Update matrices — critical before unproject()
        this.update(true);
    }


    private void moveCameraToTarget() {
        if(target == null){
            return;
        }
        ComponentList targetComponents = target.getComponentList();
        ComponentTransform transform = ((ComponentTransform)targetComponents.get(ComponentTransform.class));

        Vector2 targetPosition = new Vector2(transform.getPosition().x, transform.getPosition().y);

        this.virtual_position.set(GeneralUtils.lerp(this.virtual_position, targetPosition, 0.1f));
    }

    public void setViewPort(int width, int height) {
        this.viewportWidth = width;
        this.viewportHeight = height;
//        this.position.set(GeneralUtils.lerp(this.position, virtual_position, 0.1f));
    }

}
