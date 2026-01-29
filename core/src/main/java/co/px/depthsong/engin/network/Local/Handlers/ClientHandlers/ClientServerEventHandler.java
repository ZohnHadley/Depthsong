package co.px.depthsong.engin.network.Local.Handlers.ClientHandlers;

import co.px.depthsong.engin.engineCore.engine_managers.GameManager;
import co.px.depthsong.engin.network.Local.ClientServer;
import co.px.depthsong.engin.network.Local.Model.GameMasters.ClientServerManager;
import co.px.depthsong.engin.network.PrintColors;
import co.px.depthsong.engin.network.ServerUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ScheduledFuture;
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
    private ClientServerManager clientServerManager = ClientServer.CLIENT_SERVER_MANAGER;



    //SCHEDULED FUTURES FOR HANDLING EVENTS
    private ScheduledFuture scheduledFuture_adding_player_to_server;
    private Future future_adding_player_to_server = scheduledFuture_adding_player_to_server;

    private ScheduledFuture scheduledFuture_check_server_added_player;

    private ScheduledFuture sch_receivingServerResponse;


//    //OVERRIDES
//    @Override
//    public void channelRegistered(ChannelHandlerContext context) {
//        trigger_EstablishingContactWithServer(context);
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext context, Object message) throws Exception {
//        sch_receivingServerResponse(context, message);
//    }

    //METHODS
    private void trigger_EstablishingContactWithServer(ChannelHandlerContext context) {

        try{
            scheduledFuture_adding_player_to_server = context.executor().scheduleAtFixedRate(() -> {
                print("attempting to add player to server ...");

                if (gameManager.getEntityContext().getPlayer() != null && !clientServerManager.isPlayerInstanceSavedOnServer()) {

                    context.write(clientServerManager.getClientServerConnectionContext());

                    scheduledFuture_adding_player_to_server.cancel(false);
                }

            }, 0, 1, java.util.concurrent.TimeUnit.SECONDS);
        } catch (Exception e){
            ServerUtil.err("e client server "+e.getMessage());
        }


    }

    private void sch_receivingServerResponse(ChannelHandlerContext context, Object message) {
        sch_receivingServerResponse = context.executor().scheduleAtFixedRate(() -> {
            ServerUtil.log("DDDD");
            if (clientServerManager.getLocalPlayerInstance() != null && clientServerManager.getClientServerConnectionContext().isConnectedOnServer()
            ) {

                sch_receivingServerResponse.cancel(false);
            }

        }, 0, 1, java.util.concurrent.TimeUnit.SECONDS);

    }

    private void print(String message) {
        ServerUtil.log(PrintColors.ANSI_GREEN, "e client server", message);
    }

}
