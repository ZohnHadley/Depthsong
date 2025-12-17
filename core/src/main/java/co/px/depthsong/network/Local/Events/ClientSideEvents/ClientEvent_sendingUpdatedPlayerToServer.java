package co.px.depthsong.network.Local.Events.ClientSideEvents;

import co.px.depthsong.network.Local.Model.PlayerObj;
import io.netty.channel.ChannelHandlerContext;

public class ClientEvent_sendingUpdatedPlayerToServer {

    public ClientEvent_sendingUpdatedPlayerToServer() {
    }

    public void sendUpdatedPlayerToServer(ChannelHandlerContext context, PlayerObj player) {
        context.writeAndFlush(player);
    }
}
