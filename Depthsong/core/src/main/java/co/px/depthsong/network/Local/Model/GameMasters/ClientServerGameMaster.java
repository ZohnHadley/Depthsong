package co.px.depthsong.network.Local.Model.GameMasters;

import co.px.depthsong.ECS.abstractClasses.EcsEntity;
import co.px.depthsong.layers.models.entities.OtherPlayer;
import co.px.depthsong.layers.managers.GameManager;
import co.px.depthsong.network.Local.Model.PlayerObj;

import java.util.ArrayList;
import java.util.Objects;


//TODO fix bug when a player dies server crashes
//TODO
public class ClientServerGameMaster {


    private final GameManager gameManager;

    {
        gameManager = GameManager.getInstance();
    }

    private PlayerObj current_player;
    private static ClientServerGameMaster instance;
    private ArrayList<PlayerObj> currentGamePlayers = new ArrayList<PlayerObj>();


    private boolean currentPlayerWasIdentifiedByServer = false;

    private ClientServerGameMaster() {
        currentGamePlayers = new ArrayList<PlayerObj>();
    }


    public static ClientServerGameMaster getInstance() {
        if (instance == null) {
            instance = new ClientServerGameMaster();
        }
        return instance;
    }


    public boolean getCurrentPlayerWasIdentifiedByServer() {
        return currentPlayerWasIdentifiedByServer;
    }

    public void setCurrentPlayerWasIdentifiedByServer(boolean val) {
        this.currentPlayerWasIdentifiedByServer = val;
    }

    public PlayerObj getCurrent_player() {
        return current_player;
    }

    public void setCurrent_player(PlayerObj current_player) {
        this.current_player = current_player;
    }

    public void addPlayer(PlayerObj playerObj) {
        if (isPlayerInGame(playerObj) != -1) {
            currentGamePlayers.get(isPlayerInGame(playerObj)).setX(playerObj.getX());
            currentGamePlayers.get(isPlayerInGame(playerObj)).setY(playerObj.getY());
            currentGamePlayers.get(isPlayerInGame(playerObj)).setSpriteKey(playerObj.getSpriteKey());
            playerObjToLocalOtherPlayer(playerObj);
            return;
        }

        currentGamePlayers.add(playerObj);
        playerObjToLocalOtherPlayer(playerObj);

    }

    public void removePlayer(PlayerObj playerObj) {
        currentGamePlayers.remove(playerObj);

        for (EcsEntity otherPlayer : gameManager.getEntityContext().getAllEntitiesOfType(OtherPlayer.class)) {
            if (Objects.equals(((OtherPlayer)otherPlayer).getServerId(), playerObj.getClientServer_id())) {
                gameManager.getEntityContext().getEntities().remove(otherPlayer);
                return;
            }
        }
    }

    public int isPlayerInGame(PlayerObj playerObj) {
        int index = 0;
        for (PlayerObj player : currentGamePlayers) {
            if (player.getClientServer_id() == playerObj.getClientServer_id()) {
                return index;
            }
            index++;
        }
        return -1;
    }


    //TODO add health points and attack points (at some point)
    public void playerObjToLocalOtherPlayer(PlayerObj playerObj) {
        //check if player is already in game
        for (EcsEntity otherPlayer : gameManager.getEntityContext().getAllEntitiesOfType(OtherPlayer.class)) {
            if (Objects.equals(((OtherPlayer)otherPlayer).getServerId(), playerObj.getClientServer_id())) {
//                otherPlayer.setSpriteKey(playerObj.getSpriteKey());
//                otherPlayer.translate(playerObj.getX(), playerObj.getY());
                return;
            }
        }

//        new OtherPlayer(playerObj.getClientServer_id(), playerObj.getUsername(), 5, 1, playerObj.getX(), playerObj.getY(), playerObj.getSpriteKey());
    }


    public PlayerObj localOtherPlayerToPlayerObj(Long clientServer_id) {
        for (EcsEntity otherPlayer : gameManager.getEntityContext().getAllEntitiesOfType(OtherPlayer.class)) {
            if (Objects.equals(((OtherPlayer)otherPlayer).getServerId(), clientServer_id)) {
                return null;
//                    new PlayerObj(otherPlayer.getServerClientId(), otherPlayer.getTitle(), otherPlayer.getSpriteKey(), (int) otherPlayer.getPosition().getX() / 16, (int) otherPlayer.getPosition().getY() / 16);
            }
        }
        return null;
    }


}
