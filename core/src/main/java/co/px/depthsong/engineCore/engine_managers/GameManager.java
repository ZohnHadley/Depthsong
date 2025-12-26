package co.px.depthsong.engineCore.engine_managers;

import co.px.depthsong.ECS.core.EntityContext;
import co.px.depthsong.ECS.systems.RenderingSystem;
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

    //    @Getter(AccessLevel.NONE)
    private NetworkManager networkManager;
    private RenderingSystem renderingSystem;

    private ScreenManager screenManager;
    //    private LevelManager levelManager;
    private EntityContext entityContext;
    private GameCamera gameCamera;
    private VirtualMouse virtualMouse;

    private EnumNetworkState networkState = EnumNetworkState.Offline;
    private boolean isInGame = false;
    private boolean isPlayerCreated = false;

    @Getter(AccessLevel.NONE)
    private static GameManager instance = null;

    private GameManager() {
        entityContext = EntityContext.getInstance();
        networkManager = NetworkManager.getInstance();
        renderingSystem = RenderingSystem.getInstance();
//        levelManager = LevelManager.getInstance();
        screenManager = ScreenManager.getInstance();
        gameCamera = GameCamera.getInstance();
        virtualMouse = VirtualMouse.getInstance();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            Gdx.app.log("GameManager", "instantiated game master");
            instance = new GameManager();
        }

        return instance;
    }

    public void update(float deltaTime) {
        getGameCamera().update(deltaTime);
        getVirtualMouse().update(deltaTime);
//        isPlayerCreated = entityContext.getPlayer() != null;


//        if (isNetworked && getNetworkSystem().getConnectionState() == NetworkSystem.connection_states.OFFLINE) {
//            isNetworked = false;
//            getNetworkSystem().setCurrentConnectedState(NetworkSystem.connection_states.OFFLINE);
//            try {
//                if (getNetworkSystem().getHostServer() != null)
//                    getNetworkSystem().getHostServer().close();
//                getNetworkSystem().getClientServer().close();
//            } catch (Exception e) {
//                Gdx.app.log("Main", "error closing host server or client server");
//            }
//
//            getScreenManager().setCurrentScreen(Global_Screens.mainMenu);
//        }
//
//        if(getNetworkSystem().getConnectionState() == NetworkSystem.connection_states.CONNECTED){
//            isNetworked = true;
//        }


//        getLevelManager().getCurrentLevel().update(deltaTime);
//        getEntityContext().update(deltaTime);

        renderingSystem.render();
    }

}
