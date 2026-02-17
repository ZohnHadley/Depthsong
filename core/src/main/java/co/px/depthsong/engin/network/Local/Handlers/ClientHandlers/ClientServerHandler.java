package co.px.depthsong.engin.network.Local.Handlers.ClientHandlers;

import co.px.depthsong.engin.network.CustomLogger;
import co.px.depthsong.engin.network.Local.Events.ClientSideEvents.EventPlayerEstablishConnection;
import co.px.depthsong.engin.network.Local.Model.GameMasters.ClientServerManager;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

public class ClientServerHandler extends ChannelHandlerAdapter {
    ClientServerManager csm = ClientServerManager.getInstance();

    @Override
    public void connect(
        ChannelHandlerContext context,
        SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        promise.addListener(
            future -> {
                context.fireUserEventTriggered(new EventPlayerEstablishConnection(context, promise));
            });
        context.connect(remoteAddress, localAddress, promise);
        CustomLogger.log("client channel", "connected");
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
