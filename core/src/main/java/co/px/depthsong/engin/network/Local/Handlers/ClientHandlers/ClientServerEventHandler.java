package co.px.depthsong.engin.network.Local.Handlers.ClientHandlers;

import co.px.depthsong.engin.engineCore.engine_managers.GameManager;
import co.px.depthsong.engin.network.Local.ClientServer;
import co.px.depthsong.engin.network.Local.Events.ClientSideEvents.ClientEvent_EstablishConnection;
import co.px.depthsong.engin.network.Local.Model.Managers.ClientServerManager;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientServerEventHandler extends ChannelHandlerAdapter {
    //VARIABLES
    private final GameManager gameManager = GameManager.getInstance();
    private ClientServerManager clientServerManager = ClientServer.clientServerManager;

    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object event) {

        //adding player to server
        if (event instanceof ClientEvent_EstablishConnection) {
            ClientEvent_EstablishConnection action = ((ClientEvent_EstablishConnection) event);
            action.sendClientContextDataToServer();
        }

    }


}
