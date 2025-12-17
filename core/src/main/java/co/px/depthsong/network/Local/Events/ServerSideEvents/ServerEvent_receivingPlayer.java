package co.px.depthsong.network.Local.Events.ServerSideEvents;

import co.px.depthsong.network.Local.Model.GameMasters.ServerGameMaster;
import co.px.depthsong.network.Local.Model.NetworkMessage;
import co.px.depthsong.network.Local.Model.PlayerObj;
import co.px.depthsong.network.Local.Model.ServerTracker.ServerConnectionContext;
import io.netty.channel.ChannelHandlerContext;

public class ServerEvent_receivingPlayer {

    public ServerEvent_receivingPlayer() {
    }

    public void confirmToClientThatPlayerHasBeenReceived(ServerConnectionContext serverConnectionContext, ChannelHandlerContext context) {
        //Identify player when first time joining the server

        PlayerObj player = new PlayerObj();
        player.setUsername(serverConnectionContext.getCurrentPlayer().getUsername());
        player.setClientServer_id(ServerGameMaster.clientCounter.getAndIncrement());
        player.setLocalChannelAddress(serverConnectionContext.getLocalChannelAddress());
        player.setHasServerID(true);
        player.setX(serverConnectionContext.getCurrentPlayer().getX());
        player.setY(serverConnectionContext.getCurrentPlayer().getY());
        player.setSpriteKey(serverConnectionContext.getCurrentPlayer().getSpriteKey());
        serverConnectionContext.setCurrentPlayer(player);
        System.out.println(player);

        //Send message to client that player has been added to server
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.setType(NetworkMessage.MessageType.PLAYER_OBJECT);
        networkMessage.setContent(player);
        System.out.println("sending back: "+networkMessage.toJson());
        System.out.println(serverConnectionContext.getCurrentContext() == null);
        serverConnectionContext.getCurrentContext().writeAndFlush(networkMessage);

    }
}
