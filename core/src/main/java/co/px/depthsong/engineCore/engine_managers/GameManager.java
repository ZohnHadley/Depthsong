package co.px.depthsong.engineCore.engine_managers;

import co.px.depthsong.ECS.core.EntityContext;
import co.px.depthsong.ECS.runtime.systems.RenderingSystem;
import co.px.depthsong.ECS.runtime.systems.UpdateSystem;
import co.px.depthsong.engineCore.engine_managers.enums.EnumNetworkState;
import co.px.depthsong.engineCore.models.util.GameCamera;
import co.px.depthsong.engineCore.models.util.VirtualMouse;
import com.badlogic.gdx.Gdx;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameManager {

    @Getter(AccessLevel.NONE)
    private static GameManager instance = null;

    private EntityContext entityContext;
    private GameLevelManager levelManager;

    private UpdateSystem updateSystem;
    private RenderingSystem renderingSystem;

    private GameCamera gameCamera;
    private VirtualMouse virtualMouse;
    private ScreenManager screenManager;
    private NetworkManager networkManager;

    private boolean isInGame = false;
    private boolean isPlayerCreated = false;



    private GameManager() {
        entityContext = EntityContext.getInstance();
        levelManager = GameLevelManager.getInstance();

        updateSystem = UpdateSystem.getInstance();

        renderingSystem = RenderingSystem.getInstance();

        gameCamera = GameCamera.getInstance();
        virtualMouse = VirtualMouse.getInstance();
        screenManager = ScreenManager.getInstance();
        networkManager = NetworkManager.getInstance();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    public void update(float deltaTime) {
        getGameCamera().update(deltaTime);
        getVirtualMouse().update(deltaTime);
        updateSystem.update(deltaTime);
        renderingSystem.render();
    }

}
