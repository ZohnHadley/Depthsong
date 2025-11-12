package zgames.tests.engin_classes.rendering.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import zgames.tests.engin_classes.rendering.custom.shaderClasses.CustomDefaultShaderConfig;
import zgames.tests.engin_classes.rendering.custom.shaderClasses.CustomShader;
import zgames.tests.engin_classes.rendering.custom.shaderClasses.MyDefaultShader;

public class CustomSceneManager {

    //manages the scene data

    //private Array<SceneAsset> scneneAssets = new Array<SceneAsset>();
    private Array<GameObject> sceneGameObjects = new Array<GameObject>();

    private Environment environment;
    private Camera camera;


    ModelBatch modelBatch;

    protected Stage stage;
    protected Label label;
    protected BitmapFont font;
    protected StringBuilder stringBuilder;

    public CustomSceneManager() {
        environment = new Environment();
        //shader on ly applied t onw model TODO fix this (might get fixe once i figure out proper implementation of shaders in java code)



        DefaultShaderProvider defaultShaderProvider = new DefaultShaderProvider(new CustomDefaultShaderConfig());

        modelBatch = new ModelBatch(defaultShaderProvider);
        //
        stage = new Stage();
        font = new BitmapFont();
        label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
        stage.addActor(label);
        stringBuilder = new StringBuilder();
    }

    public Environment getEnvironment() {
        return environment;
    }


    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void addGameObjectInstance(GameObject instance) {
        sceneGameObjects.add(instance);
    }

    public void removeGameObjectInstance(GameObject instance) {
        sceneGameObjects.removeValue(instance, true);
    }

    public Array<GameObject> getSceneGameObjects() {
        return sceneGameObjects;
    }

    //TODO figure out what to put in update
    public void update(float deltaTime) {

    }

    public void updateViewport(int width, int height) {
        if (camera != null) {
            camera.viewportWidth = width;
            camera.viewportHeight = height;
            camera.update(true);
        }
        stage.getViewport().update(width, height, true);
    }

    protected boolean isVisible(final Camera cam, final GameObject instance) {
        Vector3 position = new Vector3();
        instance.getModelInstance().transform.getTranslation(position);
        position.add(instance.getCenter());
        return cam.frustum.boundsInFrustum(position, instance.getDimensions());
    }

    private int visibleCount;
    public void render() {

        modelBatch.begin(camera);

        //modelBatch.render(sceneGameObjects, environment, customDefaultShader);
        for (GameObject instance : sceneGameObjects) {            //modelBatch.render(instance,environment, customDefaultShader);
            if (isVisible(camera, instance)) {
                modelBatch.render(instance.getModelInstance(), environment, instance.getModelShader());
                visibleCount++;
            }

        }
        modelBatch.end();


        stringBuilder.setLength(0);
        stringBuilder.append(" FPS: ").append(Gdx.graphics.getFramesPerSecond());
        //add color

        //stringBuilder.append(" Visible: ").append(visibleCount);
        label.setText(stringBuilder);
        label.setColor(Color.YELLOW);
        stage.draw();
    }

    public void dispose() {
        modelBatch.dispose();
        sceneGameObjects.clear();
    }

}
