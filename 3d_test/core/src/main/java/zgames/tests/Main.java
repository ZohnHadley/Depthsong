package zgames.tests;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import org.w3c.dom.Attr;
import zgames.tests.engin_classes.rendering.custom.CustomModelLoader;
import zgames.tests.engin_classes.rendering.custom.CustomSceneInstance;
import zgames.tests.engin_classes.rendering.custom.GameObject;
import zgames.tests.engin_classes.EngineEditorCamera;
import zgames.tests.engin_classes.rendering.custom.ShaderAttributes.DoubleColorAttribute;
import zgames.tests.engin_classes.rendering.custom.ShaderAttributes.NormalColorAttribute;
import zgames.tests.engin_classes.rendering.custom.shaderClasses.CustomShader;
import zgames.tests.ui.screens.editor_screen.Editor_screen;

import java.util.List;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {


    private float time;

    private EngineEditorCamera camera;

    //private Engine_GLTFSceneInstance engineGltfSceneInstance;
    //private GLTFModelLoader engineGLTFModelLoader;

    private CustomSceneInstance sceneInstance;
    private CustomModelLoader customModelLoader;


    private Editor_screen editor_screen;

    private List<Float> vertices;
    private List<Short> indices;
    @Override
    public void create() {

        camera = EngineEditorCamera.getInstance();
        ImGuiRenderer.initImGui();

        //engineGLTFModelLoader = new GLTFModelLoader();
        //engineGLTFModelLoader.loadModels();

        /*engineGltfSceneInstance = Engine_GLTFSceneInstance.getInstance();
        engineGltfSceneInstance.setCamera(camera);
        engineGltfSceneInstance.create();*/
        customModelLoader = new CustomModelLoader();
        customModelLoader.loadModels();
        sceneInstance = CustomSceneInstance.getInstance();
        sceneInstance.setCamera(camera);
        sceneInstance.create();


        Model npcModel = (customModelLoader.getListModels().get(1).scene.model);
        Shader shaderColorDepth = new CustomShader();
        shaderColorDepth.init();
        GameObject gameObject01 = new GameObject(npcModel);

        gameObject01.setModelShader(shaderColorDepth);
        GameObject gameObject02 = new GameObject(npcModel);
        gameObject02.setRotation(new Vector3(30,30,0));
        gameObject02.setPosition(new Vector3(0,3,0));
        //add custom color attribute
        Attribute singleColorAttribute = new NormalColorAttribute(NormalColorAttribute.NormalColor, new Color(1, 1, 1, 1));
        gameObject01.getModelInstance().materials.get(0).set(singleColorAttribute);
        //
        sceneInstance.getSceneManager().addGameObjectInstance(gameObject01);
        sceneInstance.getSceneManager().addGameObjectInstance(gameObject02);

        /*ModelBuilder modelBuilder = new ModelBuilder();
        Model model = modelBuilder.createSphere(2f, 2f, 2f, 10, 10, new Material(ColorAttribute.createDiffuse(Color.GREEN)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        GameObject spehre = new GameObject(new Vector3(0, 0, 0), model, new Vector3(2, 2, 2));
        sceneInstance.getSceneManager().addGameObjectInstance(spehre);*/


        editor_screen = new Editor_screen(sceneInstance);
    }


    @Override
    public void resize(int width, int height) {

        //engineGltfSceneInstance.getGLTFSceneManager().updateViewport(width, height);
        sceneInstance.getSceneManager().updateViewport(width, height);
    }


    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;

        ScreenUtils.clear(0f, 0f, 0f, 1, true);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        sceneInstance.render(deltaTime);


        //engineGltfSceneInstance.render(deltaTime);


        //when in development mode
        /*ImGuiRenderer.startImGui();
        editor_screen.render(time);
        ImGuiRenderer.endImGui();*/
    }

    @Override
    public void dispose() {
        //engineGltfSceneInstance.dispose();
        editor_screen.dispose();
        sceneInstance.dispose();
        ImGuiRenderer.disposeImGui();

    }

}
