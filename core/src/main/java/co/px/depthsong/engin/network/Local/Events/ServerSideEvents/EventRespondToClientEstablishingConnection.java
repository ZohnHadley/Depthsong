package co.px.depthsong.engin.network.Local.Events.ServerSideEvents;

import co.px.depthsong.engin.network.Local.Model.ServerTracker.ClientConnectionContext;
import co.px.depthsong.engin.network.ServerUtil;
import io.netty.channel.ChannelHandlerContext;

public class EventRespondToClientEstablishingConnection {

    private final ClientConnectionContext clientConnectionContext;

    public EventRespondToClientEstablishingConnection(ClientConnectionContext clientConnectionContext) {
        this.clientConnectionContext = clientConnectionContext;
    }

    public void validate(ChannelHandlerContext contex){
        ServerUtil.log("Recieved somthing");
    }
}
