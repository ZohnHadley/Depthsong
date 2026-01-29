package co.px.depthsong.engin.network.Local.Handlers.ServerHandlers;

import co.px.depthsong.engin.network.Local.Events.ClientSideEvents.EventPlayerEstablishConnection;
import co.px.depthsong.engin.network.Local.Events.ServerSideEvents.EventRespondToClientEstablishingConnection;
import co.px.depthsong.engin.network.Local.Model.GameMasters.HostServerMaster;
import co.px.depthsong.engin.network.ServerUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;


public class HostServerEventHandler extends ChannelHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object event) {

        //adding player to server
        if (event instanceof EventRespondToClientEstablishingConnection) {
            EventRespondToClientEstablishingConnection action = ((EventRespondToClientEstablishingConnection)event);
            action.validate(context);
        }

    }


}
