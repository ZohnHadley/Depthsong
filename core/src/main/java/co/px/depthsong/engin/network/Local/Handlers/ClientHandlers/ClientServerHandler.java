package co.px.depthsong.engin.network.Local.Handlers.ClientHandlers;

import co.px.depthsong.engin.ECS.core.EntityContext;
import co.px.depthsong.engin.enginUtils.JsonUtil;
import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumActivationState;
import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumNetworkClientConnectionStates;
import co.px.depthsong.engin.network.Local.Model.ServerTracker.ClientConnectionContext;
import co.px.depthsong.engin.network.ServerUtil;
import co.px.depthsong.engin.engineCore.engine_managers.GameManager;

import co.px.depthsong.engin.network.Local.ClientServer;
import co.px.depthsong.engin.network.Local.Events.ClientSideEvents.EventPlayerEstablishConnection;
import co.px.depthsong.engin.network.Local.Model.GameMasters.ClientServerManager;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.ScheduledFuture;

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
