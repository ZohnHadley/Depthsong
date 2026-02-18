package co.px.depthsong.engin.network.Local.Handlers.ClientHandlers;

import co.px.depthsong.engin.network.CustomLogger;
import co.px.depthsong.engin.network.Local.Events.ClientSideEvents.ClientEvent_EstablishConnection;
import co.px.depthsong.engin.network.Local.Events.ServerSideEvents.HostEvent_RespondToEstablishConnection;
import co.px.depthsong.engin.network.Local.Model.Managers.ClientServerManager;
import co.px.depthsong.engin.network.Local.Model.ServerObjects.ServerObjectClientConnectionContext;
import co.px.depthsong.engin.network.Local.Model.ServerTracker.ClientConnectionContext;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientServerHandler extends ChannelHandlerAdapter {
    ClientServerManager csm = ClientServerManager.getInstance();


    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        if (context != null && context.channel() != null) {
            ClientConnectionContext connectionContext = new ClientConnectionContext(context.channel().remoteAddress()+"", context.channel().localAddress()+"");
            csm.setClientConnectionContext(connectionContext);
            context.fireUserEventTriggered(new ClientEvent_EstablishConnection(context));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        //check if object is instance of clientconnectioncontext
        if (context != null && context.channel() != null) {
            if (msg instanceof ServerObjectClientConnectionContext) {
               ClientConnectionContext clientConnectionContext = ((ServerObjectClientConnectionContext) msg).toObject();
               if(csm.getClientConnectionContext().getLocalAddress().equalsIgnoreCase(clientConnectionContext.getLocalAddress())
                   && csm.getClientConnectionContext().getRemoteAddress().equalsIgnoreCase(clientConnectionContext.getRemoteAddress())
                   && clientConnectionContext.getIsConnected() == true){
                  csm.setClientConnectionContext(clientConnectionContext);
                  CustomLogger.log("client channel", "client connection established");
               }
            }



            context.fireChannelRead(msg);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        context.close();
        CustomLogger.log("client channel", "channel unregistered");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        context.close();
        CustomLogger.err("client channel", cause.getMessage());
    }


}
