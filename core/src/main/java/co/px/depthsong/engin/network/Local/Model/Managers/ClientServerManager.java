package co.px.depthsong.engin.network.Local.Model.Managers;

import co.px.depthsong.engin.ECS.core.EntityContext;
import co.px.depthsong.engin.ECS.core.abstractClasses.EcsEntity;
import co.px.depthsong.engin.engineCore.engine_managers.NetworkMachineManager;
import co.px.depthsong.engin.network.Local.Model.ServerTracker.ClientConnectionContext;
import co.px.depthsong.game.models.entities.ClientPlayer;
import co.px.depthsong.game.models.entities.OtherPlayer;
import co.px.depthsong.engin.engineCore.engine_managers.GameManager;
import co.px.depthsong.engin.network.Local.Model.ServerObjects.ServerObjectEntityPlayer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


//TODO fix bug when a player dies server crashes
//TODO
@Getter
@Setter
public class ClientServerManager {

    private final GameManager gameManager = GameManager.getInstance();
    private final EntityContext entityContext = gameManager.getEntityContext();
    private final NetworkMachineManager networkMachineManager = gameManager.getNetworkMachineManager();

    private static ClientServerManager instance;
    private ClientConnectionContext clientConnectionContext;

    @Setter(AccessLevel.NONE)
    private ClientPlayer localPlayerInstance;

    private ClientServerManager() {
        localPlayerInstance = (ClientPlayer) entityContext.getPlayer();
        clientConnectionContext = new ClientConnectionContext();
    }


    public static ClientServerManager getInstance() {
        if (instance == null) {
            instance = new ClientServerManager();
        }
        return instance;
    }

    public boolean isPlayerInstanceSavedOnServer() {
        //TODO : check if player instance is saved on server
        return false;

    }


    //TODO add health points and attack points (at some point)
    public void playerObjToLocalOtherPlayer(ServerObjectEntityPlayer serverObjectEntityPlayer) {
        //check if player is already in game
        for (EcsEntity otherPlayer : gameManager.getEntityContext().getAllEntitiesOfType(OtherPlayer.class)) {
            if (Objects.equals(((OtherPlayer) otherPlayer).getServerId(), serverObjectEntityPlayer.getClientServerID())) {
//                otherPlayer.setSpriteKey(playerObj.getSpriteKey());
//                otherPlayer.translate(playerObj.getX(), playerObj.getY());
                return;
            }
        }

//        new OtherPlayer(playerObj.getClientServer_id(), playerObj.getUsername(), 5, 1, playerObj.getX(), playerObj.getY(), playerObj.getSpriteKey());
    }


    public ServerObjectEntityPlayer localOtherPlayerToPlayerObj(Long clientServerID) {
        for (EcsEntity otherPlayer : gameManager.getEntityContext().getAllEntitiesOfType(OtherPlayer.class)) {
            if (Objects.equals(((OtherPlayer) otherPlayer).getServerId(), clientServerID)) {
                return null;
//                    new PlayerObj(otherPlayer.getServerClientId(), otherPlayer.getTitle(), otherPlayer.getSpriteKey(), (int) otherPlayer.getPosition().getX() / 16, (int) otherPlayer.getPosition().getY() / 16);
            }
        }
        return null;
    }


}
