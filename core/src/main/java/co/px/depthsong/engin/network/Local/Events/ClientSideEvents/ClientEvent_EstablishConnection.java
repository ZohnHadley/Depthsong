package co.px.depthsong.engin.network.Local.Events.ClientSideEvents;

import co.px.depthsong.engin.engineCore.engine_managers.NetworkMachineManager;
import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumNetworkClientConnectionStates;
import co.px.depthsong.engin.network.Local.Model.Managers.ClientServerManager;
import co.px.depthsong.engin.network.Local.Model.ServerObjects.ServerObjectClientConnectionContext;
import co.px.depthsong.engin.network.CustomLogger;
import io.netty.channel.ChannelHandlerContext;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class ClientEvent_EstablishConnection {

    @Getter(AccessLevel.NONE)
    private ClientServerManager csm = ClientServerManager.getInstance();
    private ChannelHandlerContext context;

    public ClientEvent_EstablishConnection(ChannelHandlerContext context) {
        try {
                this.context = context;

        } catch (Exception e) {
            CustomLogger.err("EventPlayerEstablishConnection", e.getCause().getMessage());
        }
    }

    public void sendClientContextDataToServer() {
        try {
                CustomLogger.log("EventPlayerEstablishConnection", "sendClientContextDataToServer");
                context.writeAndFlush(ServerObjectClientConnectionContext.toServerObject(csm.getClientConnectionContext()));

        } catch (Exception e) {
            CustomLogger.err(e.getMessage());
        }
    }

    public void finalised() {
        NetworkMachineManager.getInstance().setConnectionState(EnumNetworkClientConnectionStates.CONNECTED);
    }

}
