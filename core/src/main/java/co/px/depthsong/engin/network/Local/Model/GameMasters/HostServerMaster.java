package co.px.depthsong.engin.network.Local.Model.GameMasters;

import co.px.depthsong.engin.network.Local.Model.ServerTracker.ClientConnectionContext;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class HostServerMaster {


    private static HostServerMaster instance;
    private String ipAddress;

    private ArrayList<ClientConnectionContext> clientsServerConnectionContexts;


    private HostServerMaster() {
        clientsServerConnectionContexts = new ArrayList<>();
    }

    public static HostServerMaster getInstance() {
        if (instance == null) {
            instance = new HostServerMaster();
        }
        return instance;
    }

//
//    public void sendToAllChannelsExcept(NetworkMessage networkMessagePlayer, Channel currentChannel) {
//        for (ClientConnectionContext scm : clientsServerConnectionContexts) {
//            if (scm.getCurrentChannel() != currentChannel) {
//                scm.getCurrentChannel().writeAndFlush(networkMessagePlayer);
//            }
//        }
//    }
//
//    public void sendAllPlayersToChannel(Channel currentChannel) {
//        for (ClientConnectionContext scm : clientsServerConnectionContexts) {
//            if (scm.getCurrentChannel() != currentChannel) {
//                NetworkMessage networkMessage = new NetworkMessage();
//                networkMessage.setType(NetworkMessage.MessageType.PLAYER_OBJECT);
//                networkMessage.setContent(scm.getCurrentPlayer());
//                currentChannel.writeAndFlush(networkMessage);
//            }
//        }
//    }
//    public void updatePlayer(ServerEntityPlayer recievedPlayer) {
//
//        if (recievedPlayer == null) {
//            System.out.println("recieved player is null");
//            return;
//        }
//
//        if (clientsServerConnectionContexts.isEmpty()) {
//            System.out.println("no players connected");
//            return;
//        }
//
//        for (ClientConnectionContext scm : clientsServerConnectionContexts) {
//            if (scm.getCurrentPlayer() == null) {
//                continue;
//            }
//            if (scm.getCurrentPlayer().getClientServerID() == recievedPlayer.getClientServerID()) {
//                scm.setCurrentPlayer(recievedPlayer);
//            }
//        }
//    }
}
