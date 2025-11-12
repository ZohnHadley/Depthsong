package zgames.tests.engin_classes.rendering.gltf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.scene.SceneSkybox;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;
import zgames.tests.engin_classes.Interfaces.InterfaceSceneInstance;

public class GLTFSceneInstance implements InterfaceSceneInstance {

    private static GLTFSceneInstance instance;

    private Camera camera;

    //
    private SceneManager sceneManager;

    //environment
    private Cubemap diffuseCubemap;
    private Cubemap environmentCubemap;
    private Cubemap specularCubemap;

    private Texture brdfLUT;


    private SceneSkybox skybox;
    private DirectionalLightEx light;

    private Boolean hasGrid = true;
    private Boolean hasLight = false;


    private GLTFSceneInstance() {
        sceneManager = new SceneManager();
    }

    public static GLTFSceneInstance getInstance() {
        if (instance == null) {
            instance = new GLTFSceneInstance();
        }
        return instance;
    }


    public SceneManager getGLTFSceneManager() {
        return sceneManager;
    }

    //setters
    //TODO figure out to update/change sybox and grid (adter the scene has been created)
    protected void setHasGrid(Boolean hasGrid) {
        this.hasGrid = hasGrid;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    //functions

    public void create() {
        if (this.camera == null) {

            return;
        }
        Scene newScene = new Scene(new Model());
        sceneManager.addScene(newScene);
        if (hasGrid) {
            ModelBuilder modelBuilder = new ModelBuilder();
            Model grid = modelBuilder.createLineGrid(100, 100, 1, 1, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position);
            Scene grid_instance = new Scene(grid);
            grid_instance.modelInstance.transform.translate(0, 0, 0);

            sceneManager.addScene(grid_instance);
        }

        sceneManager.setCamera(camera);

        if (hasLight) {
            // setup light
            light = new DirectionalLightEx();
            light.direction.set(1, -10, 1).nor();
            light.color.set(Color.WHITE);
            sceneManager.environment.add(light);

            // setup quick IBL (image based lighting)
            IBLBuilder iblBuilder = IBLBuilder.createOutdoor(light);
            environmentCubemap = iblBuilder.buildEnvMap(1024);
            diffuseCubemap = iblBuilder.buildIrradianceMap(256);
            specularCubemap = iblBuilder.buildRadianceMap(10);
            iblBuilder.dispose();
            // This texture is provided by the library, no need to have it in your assets.
            brdfLUT = new Texture(Gdx.files.classpath("net/mgsx/gltf/shaders/brdfLUT.png"));

            sceneManager.setAmbientLight(1f);
            sceneManager.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, brdfLUT));
            sceneManager.environment.set(PBRCubemapAttribute.createSpecularEnv(specularCubemap));
            sceneManager.environment.set(PBRCubemapAttribute.createDiffuseEnv(diffuseCubemap));
            // setup skybox
            skybox = new SceneSkybox(environmentCubemap);
            sceneManager.setSkyBox(skybox);
        }
    }


    public void render(float deltaTime) {

        sceneManager.update(deltaTime);
        sceneManager.render();

    }

    public void dispose() {
        sceneManager.dispose();
        if (hasLight) {
            environmentCubemap.dispose();
            diffuseCubemap.dispose();
            specularCubemap.dispose();
            brdfLUT.dispose();
            skybox.dispose();
        }
    }
}
