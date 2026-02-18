package co.px.depthsong.engin.network.Local.Handlers.ServerHandlers;

import co.px.depthsong.engin.network.Local.Events.ServerSideEvents.HostEvent_RespondToEstablishConnection;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class HostServerEventHandler extends ChannelHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object event) {

        //adding player to server
        if (event instanceof HostEvent_RespondToEstablishConnection) {
            HostEvent_RespondToEstablishConnection action = ((HostEvent_RespondToEstablishConnection)event);
            action.respond();

        }
    }


}
