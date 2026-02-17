package co.px.depthsong.engin.network.Local.Events.ServerSideEvents;

import co.px.depthsong.engin.network.Local.Model.GameMasters.HostServerMaster;
import co.px.depthsong.engin.network.CustomLogger;
import io.netty.channel.ChannelHandlerContext;

public class EventRespondToClientEstablishingConnection {


    private ChannelHandlerContext context;
    private Object message;

    public EventRespondToClientEstablishingConnection(ChannelHandlerContext context, Object message) {
        this.context = context;
        this.message = message;
    }

    public void respond(){
        HostServerMaster.getInstance().getClientsServerConnectionContexts();
        CustomLogger.log("Recieved somthing");
    }
}
