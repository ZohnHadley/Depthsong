package zgames.tests.engin_classes.rendering.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.loaders.glb.GLBAssetLoader;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.loaders.gltf.GLTFAssetLoader;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

import java.io.File;

public class CustomModelLoader {
    private AssetManager assetManager;
    private final Array<SceneAsset> list_models;
    private boolean loading;
    String models_asset_path = "assets/models/";
    //Creating a File object for directory
    File assets_path = new File(models_asset_path);
    //List of all files and directories
    String models_path_contents[] = assets_path.list();
    private String src_current_model_being_loaded;


    public CustomModelLoader() {

        this.list_models = new Array<SceneAsset>();
        assetManager = new AssetManager();
        assetManager.setLoader(SceneAsset.class, ".gltf", new GLTFAssetLoader());
        assetManager.setLoader(SceneAsset.class, ".glb", new GLBAssetLoader());

    }

    public Array<SceneAsset> getListModels() {
        return list_models;
    }


    public void loadModels() {

        try {

            for (String model_folder : models_path_contents) {
                File model_folder_files = new File(models_asset_path + model_folder);
                String[] model_folder_files_names = model_folder_files.list();

                //check if directory is empty
                if (model_folder_files_names != null && model_folder_files_names.length == 0) {
                    Gdx.app.log("ModelLoader", "Skipping empty folder: " + model_folder);
                    continue;
                }
                Gdx.app.log("ModelLoader", "opening folder: " + models_asset_path + model_folder);


                for (String model_file : model_folder_files_names) {
                    if (!(model_file.contains(".gltf") || model_file.contains(".glb"))) {
                        //Gdx.app.log("ModelLoader", "Skipping file: " + model_file);
                        continue;
                    }
                    Gdx.app.log("ModelLoader", "Loading model: " + model_file);

                    //TODO: figure out if i should load models synchronously or asynchronously

                    src_current_model_being_loaded = models_asset_path + model_folder + "/" + model_file;
                    FileHandle fileHandle = Gdx.files.internal(src_current_model_being_loaded);

                    String fileName = fileHandle.name();
                    SceneAsset model = null;

                    switch (model_file.substring(model_file.lastIndexOf("."))) {
                        //TODO: test if fltf imports correctly
                        case ".gltf":
                            GLTFLoader gltfLoader = new GLTFLoader();

                            assetManager.load(src_current_model_being_loaded, SceneAsset.class); //gltfLoader.load(fileHandle, true);
                            assetManager.finishLoading();

                            break;
                        case ".glb":
                            GLBLoader glbLoader = new GLBLoader();

                            assetManager.load(src_current_model_being_loaded, SceneAsset.class); //glbLoader.load(fileHandle, true);
                            assetManager.finishLoading();

                            break;
                        default:
                            Gdx.app.log("ModelLoader", "Skipping file: " + model_file);
                            continue;
                    }
                    model = assetManager.get(src_current_model_being_loaded, SceneAsset.class);
                    Gdx.app.log("ModelLoader", " model max bones:" + model.maxBones);

                    list_models.add(model);
                    assetManager.update();
                    //Model model_instance = model.scene.model;
                }
                loading = true;
            }
        } catch (Exception e) {
            Gdx.app.log("ModelLoader", "Error loading model: " + src_current_model_being_loaded);
            e.printStackTrace();

        }
    }

    public void doneLoading() {
    }

    public void dispose() {
        assetManager.dispose();
    }
}
