package co.px.depthsong.engin.network.Local.Events.ClientSideEvents;

import co.px.depthsong.engin.network.Local.Model.ServerObjects.ServerObjectEntityPlayer;
import io.netty.channel.ChannelHandlerContext;

public class ClientEvent_sendingUpdatedPlayerToServer {

    public ClientEvent_sendingUpdatedPlayerToServer() {
    }

    public void sendUpdatedPlayerToServer(ChannelHandlerContext context, ServerObjectEntityPlayer player) {
        context.write(player);
    }
}
