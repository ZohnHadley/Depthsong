package zgames.tests.engin_classes.rendering.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import zgames.tests.engin_classes.Interfaces.InterfaceSceneInstance;
;
//more controle over the scene
public class CustomSceneInstance implements InterfaceSceneInstance {
    private static CustomSceneInstance instance;

    private Camera camera;
    private CustomSceneManager sceneManager;

    private CustomSceneInstance() {
        sceneManager = new CustomSceneManager();
    }

    public static CustomSceneInstance getInstance() {
        if (instance == null) {
            instance = new CustomSceneInstance();
        }
        return instance;
    }

    public Camera getCamera() {
        return camera;
    }

    public CustomSceneManager getSceneManager() {
        return sceneManager;
    }

    public void setCamera(Camera camera){
        this.camera = camera;
    }

    public void create(){
        if (camera == null) {
            camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            camera.position.set(0f, 0f, 0f);
            camera.lookAt(0f, 0f, 0f);
            camera.near = 0.1f;
            camera.far = 300f;
            camera.update();
        }
        sceneManager.setCamera(camera);

        sceneManager.getEnvironment().set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        sceneManager.getEnvironment().add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
    }

    public void render(float deltaTime){
        sceneManager.update(deltaTime);
        sceneManager.render();
    }

    public void dispose() {
        sceneManager.dispose();
    }
}
