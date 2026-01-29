package co.px.depthsong.engin.network.Local.Events.ClientSideEvents;

import co.px.depthsong.engin.network.Local.ClientServer;
import co.px.depthsong.engin.network.Local.Model.GameMasters.ClientServerManager;
import co.px.depthsong.engin.network.Local.Model.ServerObjects.ServerObjectClientConnectionContext;
import co.px.depthsong.engin.network.Local.Model.ServerTracker.ClientConnectionContext;
import co.px.depthsong.engin.network.ServerUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.AccessLevel;
import lombok.Getter;

import java.net.SocketAddress;

@Getter
public class EventPlayerEstablishConnection {

    @Getter(AccessLevel.NONE)
    private ClientServerManager csm = ClientServerManager.getInstance();
    ChannelHandlerContext context;
    ChannelPromise promise;

    public EventPlayerEstablishConnection(ChannelHandlerContext context, ChannelPromise promise) {
        try {

            if (promise.isSuccess()) {
                this.promise = promise;
                this.context = context;
                csm.setClientConnectionContext(new ClientConnectionContext(context.channel().remoteAddress().toString(), context.channel().localAddress().toString()));
            }

        } catch (Exception e) {
            ServerUtil.err("EventPlayerEstablishConnection", e.getMessage());
        }
    }

    public void sendClientContextDataToServer() {
        try {
            if (promise.isDone()) {
               context.writeAndFlush(ServerObjectClientConnectionContext.toServerObject(csm.getClientConnectionContext()));
            }
        } catch (Exception e) {
            ServerUtil.err(e.getMessage());
        }
    }

}
