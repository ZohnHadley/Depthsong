package co.px.depthsong.engin.engineCore.engine_managers;

import co.px.depthsong.engin.ECS.core.EntityContext;
import co.px.depthsong.engin.ECS.runtime.systems.RenderingSystem;
import co.px.depthsong.engin.ECS.runtime.systems.BaseScriptSystem;
import co.px.depthsong.engin.engineCore.util.GameCamera;
import co.px.depthsong.engin.engineCore.util.VirtualMouse;
import co.px.depthsong.engin.network.Local.HostServer;
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

    private BaseScriptSystem baseScriptSystem;
    private RenderingSystem renderingSystem;

    private NetworkManager networkManager;
    private GameCamera gameCamera;
    private VirtualMouse virtualMouse;
//    private ScreenManager screenManager;

    private boolean isInGame = false;
    private boolean isPlayerCreated = false;



    private GameManager() {
        entityContext = EntityContext.getInstance();
        levelManager = GameLevelManager.getInstance();

        baseScriptSystem = BaseScriptSystem.getInstance();

        renderingSystem = RenderingSystem.getInstance();

        gameCamera = GameCamera.getInstance();
        virtualMouse = VirtualMouse.getInstance();
//        screenManager = ScreenManager.getInstance();
        networkManager = NetworkManager.getInstance();
        try{
            networkManager.setHostServer(new HostServer(4444));
//            networkManager.getHostServer().start();
            networkManager.setHostServerThread(Thread.startVirtualThread(networkManager.getHostServer()));

        }catch (Exception e){
            e.printStackTrace();
        }
//        screenManager.setCurrentScreen(GameScreensList.inGameScreen);
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    public void start(){
        baseScriptSystem.start();
    }

    public void update(float deltaTime) {
        getGameCamera().refresh(deltaTime);
        getVirtualMouse().update(deltaTime);

//        screenManager.getCurrentScreen().update();
        baseScriptSystem.update(deltaTime);
        renderingSystem.render();
    }

}
