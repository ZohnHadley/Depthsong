package zgames.tests.engin_classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

public class EngineEditorCamera extends PerspectiveCamera {
    private static EngineEditorCamera instance;
    private CameraInputController camController;

    private EngineEditorCamera() {
        this.fieldOfView = 67;
        this.viewportWidth = Gdx.graphics.getWidth();
        this.viewportHeight = Gdx.graphics.getHeight();
        this.position.set(12, 5, 12);
        this.lookAt(0, 0, 0);
        this.near = 0.1f;
        this.far = 300f;
        this.update();


        camController = new CameraInputController(this);
        Gdx.input.setInputProcessor(camController);
    }

    public static EngineEditorCamera getInstance() {
        if (instance == null) {
            instance = new EngineEditorCamera();
        }
        return instance;
    }


    public CameraInputController getCamController() {
        return camController;
    }

    public void setFieldOfView(float fieldOfView) {
        this.fieldOfView = fieldOfView;
    }

    public void setViewportWidth(float viewportWidth) {
        this.viewportWidth = viewportWidth;
    }

    public void setViewportHeight(float viewportHeight) {
        this.viewportHeight = viewportHeight;
    }

    public void setPosition(Vector3 position) {
        this.position.set(position);
    }

    public void setLookAtTarget(Vector3 lookAtTarget) {
        this.lookAt(lookAtTarget);
    }

    public void setNear(float near) {
        this.near = near;
    }

    public void setFar(float far) {
        this.far = far;
    }

    public CameraInputController getCameraInputController() {
        return camController;
    }
}
