package zgames.tests.engin_classes.rendering.gltf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

import java.io.File;

//using Gltf
public class GLTFModelLoader {

    private final Array<SceneAsset> list_models;

    private boolean loading;
    String models_asset_path = "assets/models/";
    //Creating a File object for directory
    File assets_path = new File(models_asset_path);
    //List of all files and directories
    String models_path_contents[] = assets_path.list();
    private String src_current_model_being_loaded;


    public GLTFModelLoader() {

        this.list_models = new Array<SceneAsset>();

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

                            model = gltfLoader.load(fileHandle, true);


                            break;
                        case ".glb":
                            GLBLoader glbLoader = new GLBLoader();

                            model = glbLoader.load(fileHandle, true);


                            break;
                        default:
                            Gdx.app.log("ModelLoader", "Skipping file: " + model_file);
                            continue;
                    }

                    Gdx.app.log("ModelLoader",  " model max bones:" + model.maxBones);

                    list_models.add(model);
                    //Model model_instance = model.scene.model;
                }
                loading = true;
            }
        } catch (Exception e) {
            Gdx.app.log("ModelLoader", "Error loading model: " + src_current_model_being_loaded);
            e.printStackTrace();

        }
    }

}
