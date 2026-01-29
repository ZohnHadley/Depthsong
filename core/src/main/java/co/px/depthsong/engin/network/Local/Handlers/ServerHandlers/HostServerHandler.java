package co.px.depthsong.engin.network.Local.Handlers.ServerHandlers;

import co.px.depthsong.engin.network.Local.Events.ClientSideEvents.EventPlayerEstablishConnection;
import co.px.depthsong.engin.network.Local.Events.ServerSideEvents.EventRespondToClientEstablishingConnection;
import co.px.depthsong.engin.network.Local.Model.ServerTracker.ClientConnectionContext;
import co.px.depthsong.engin.network.ServerUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;


public class HostServerHandler extends ChannelHandlerAdapter {

    @Override
    public void connect(
        ChannelHandlerContext context,
        SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ServerUtil.log("server", "message reu");
    }

    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        ServerUtil.log("server", "active");
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {
        ServerUtil.log("server", "message reu");
//        context.fireUserEventTriggered(new EventRespondToClientEstablishingConnection(new ClientConnectionContext(context, localAddress, remoteAddress, context.channel())));
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
