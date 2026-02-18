package co.px.depthsong.engin.network.Local.Handlers.ServerHandlers;

import co.px.depthsong.engin.network.Local.Events.ServerSideEvents.HostEvent_RespondToEstablishConnection;
import co.px.depthsong.engin.network.CustomLogger;
import co.px.depthsong.engin.network.Local.Model.Managers.HostServerMaster;
import co.px.depthsong.engin.network.Local.Model.ServerObjects.ServerObjectClientConnectionContext;
import co.px.depthsong.engin.network.Local.Model.ServerTracker.ClientConnectionContext;
import io.netty.channel.*;


public class HostServerHandler extends ChannelHandlerAdapter {
    private HostServerMaster hsm = HostServerMaster.getInstance();


    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        //check if object is instance of clientconnectioncontext
        if (context != null && context.channel() != null) {
            if (msg instanceof ServerObjectClientConnectionContext) {
                //check if already present in liste
                ClientConnectionContext clientConnectionContext = ((ServerObjectClientConnectionContext) msg).toObject();
                if (hsm.getClientsServerConnectionContexts().contains(clientConnectionContext)) {
                    //then ignore (connnection is already established)
                    CustomLogger.log("host channel", "client already connected");
                    return;
                }
                //then add to list and set as isConnected
                context.fireUserEventTriggered(new HostEvent_RespondToEstablishConnection(context, clientConnectionContext));
                CustomLogger.log("host channel", "client connection established");
            }
            context.fireChannelRead(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context) throws Exception {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        context.close();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        context.close();
    }

}
