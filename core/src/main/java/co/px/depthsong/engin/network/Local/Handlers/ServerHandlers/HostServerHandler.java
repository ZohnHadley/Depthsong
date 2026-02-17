package co.px.depthsong.engin.network.Local.Handlers.ServerHandlers;

import co.px.depthsong.engin.network.Local.Events.ServerSideEvents.EventRespondToClientEstablishingConnection;
import co.px.depthsong.engin.network.CustomLogger;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;


public class HostServerHandler extends ChannelHandlerAdapter {

    @Override
    public void connect(
        ChannelHandlerContext context,
        SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
    }

    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        CustomLogger.log("server channel", "active");
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {
        CustomLogger.log("server channel", message.toString());
        context.fireUserEventTriggered(new EventRespondToClientEstablishingConnection(context, message));
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
