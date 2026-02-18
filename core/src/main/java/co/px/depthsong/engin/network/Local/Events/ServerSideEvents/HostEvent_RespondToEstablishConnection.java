package co.px.depthsong.engin.network.Local.Events.ServerSideEvents;

import co.px.depthsong.engin.network.Local.Model.Managers.HostServerMaster;
import co.px.depthsong.engin.network.Local.Model.ServerObjects.ServerObjectClientConnectionContext;
import co.px.depthsong.engin.network.Local.Model.ServerTracker.ClientConnectionContext;
import io.netty.channel.ChannelHandlerContext;

public class HostEvent_RespondToEstablishConnection {
    private HostServerMaster hsm = HostServerMaster.getInstance();
    private final ChannelHandlerContext context;
    private final ClientConnectionContext message;

    public HostEvent_RespondToEstablishConnection(ChannelHandlerContext context, ClientConnectionContext message) {
        this.context = context;
        this.message = message;

    }

    public void respond() {
            this.message.setIsConnected(true);
            hsm.getClientsServerConnectionContexts().add(this.message);
            context.writeAndFlush(ServerObjectClientConnectionContext.toServerObject(this.message));
    }
}
