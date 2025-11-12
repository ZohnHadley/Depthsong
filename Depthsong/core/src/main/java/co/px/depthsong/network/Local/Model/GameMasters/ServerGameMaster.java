package co.px.depthsong.network.Local.Model.GameMasters;

import co.px.depthsong.network.Local.Model.NetworkMessage;
import co.px.depthsong.network.Local.Model.PlayerObj;
import co.px.depthsong.network.Local.Model.ServerTracker.ClientInfo;
import co.px.depthsong.network.Local.Model.ServerTracker.ServerConnectionContext;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerGameMaster {

    private static ServerGameMaster instance;

    public static AtomicInteger clientCounter = new AtomicInteger(0);


    private ArrayList<ServerConnectionContext> channel_context_list;



    private ServerGameMaster() {
        channel_context_list = new ArrayList<ServerConnectionContext>();
    }

    public static ServerGameMaster getInstance() {
        if (instance == null) {
            instance = new ServerGameMaster();
        }
        return instance;
    }


    public ArrayList<ServerConnectionContext> getChannel_context_list() {
        return channel_context_list;
    }

    private final int secondsPerTurn = 30;
    private ClientInfo clientOfCurrentTurn = null;

    public void sendToAllChannelsExcept(NetworkMessage networkMessagePlayer, Channel currentChannel) {
        for (ServerConnectionContext scm : channel_context_list) {
            if (scm.getCurrentChannel() != currentChannel) {
                scm.getCurrentChannel().writeAndFlush(networkMessagePlayer);
            }
        }
    }

    public void sendAllPlayersToChannel(Channel currentChannel) {
        for (ServerConnectionContext scm : channel_context_list) {
            if (scm.getCurrentChannel() != currentChannel) {
                NetworkMessage networkMessage = new NetworkMessage();
                networkMessage.setType(NetworkMessage.MessageType.PLAYER_OBJECT);
                networkMessage.setContent(scm.getCurrentPlayer());
                currentChannel.writeAndFlush(networkMessage);
            }
        }
    }

    public void updatePlayer(PlayerObj recievedPlayer) {

        if (recievedPlayer == null) {
            System.out.println("recieved player is null");
            return;
        }

        if (channel_context_list.size() == 0) {
            System.out.println("no players connected");
            return;
        }

        for (ServerConnectionContext scm : channel_context_list) {
            if (scm.getCurrentPlayer() == null) {
                continue;
            }
            if (scm.getCurrentPlayer().getClientServer_id() == recievedPlayer.getClientServer_id()) {
                scm.setCurrentPlayer(recievedPlayer);
            }
        }
    }
}
