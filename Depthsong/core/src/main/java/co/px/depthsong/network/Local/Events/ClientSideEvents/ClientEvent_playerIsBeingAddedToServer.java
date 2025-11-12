package co.px.depthsong.network.Local.Events.ClientSideEvents;

import co.px.depthsong.ECS.entityContext.EntityContext;
import co.px.depthsong.network.Local.ClientServer;
import co.px.depthsong.network.Local.Model.NetworkMessage;
import co.px.depthsong.network.Local.Model.PlayerObj;
import io.netty.channel.ChannelHandlerContext;

public class ClientEvent_playerIsBeingAddedToServer {

    public ClientEvent_playerIsBeingAddedToServer() {
    }

    public void sendPlayerObjectToServer(ChannelHandlerContext context, EntityContext entMng) {


        //prepare the player Object
        PlayerObj thisPlayer = new PlayerObj(
            entMng.getPlayer().getName(),
            "mage",
            (int) entMng.getPlayer().getTransform().getPosition().x,
            (int) entMng.getPlayer().getTransform().getPosition().y,
            false
        );

        thisPlayer.setLocalChannelAddress("N/A");

        ClientServer.clientServerGameMaster.setCurrent_player(thisPlayer);

        //add the player to a 'paquet' then send it
        NetworkMessage message = new NetworkMessage(NetworkMessage.MessageType.PLAYER_OBJECT, thisPlayer);
        context.channel().writeAndFlush(message);
    }
}
