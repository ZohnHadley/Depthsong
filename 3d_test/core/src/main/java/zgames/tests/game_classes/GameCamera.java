package zgames.tests.game_classes;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import zgames.tests.engin_classes.EngineEditorCamera;

public class GameCamera extends PerspectiveCamera {
    private static EngineEditorCamera instance;
    private CameraInputController camController;


    public GameCamera() {
        super();
    }
}
