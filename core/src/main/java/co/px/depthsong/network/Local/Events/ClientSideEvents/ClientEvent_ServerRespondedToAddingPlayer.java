package co.px.depthsong.network.Local.Events.ClientSideEvents;

public class ClientEvent_ServerRespondedToAddingPlayer {

    private boolean isPlayerAddedToServer = false;

    public ClientEvent_ServerRespondedToAddingPlayer(){
        isPlayerAddedToServer = true;
    }

    public boolean isPlayerAddedToServer() {
        return isPlayerAddedToServer;
    }

}
