package co.px.depthsong.network.Local.Handlers.ClientHandlers;

import co.px.depthsong.layers.managers.GameManager;
import co.px.depthsong.network.Local.ClientServer;
import co.px.depthsong.network.Local.Events.ClientSideEvents.ClientEvent_ServerRespondedToAddingPlayer;
import co.px.depthsong.network.Local.Events.ClientSideEvents.ClientEvent_playerIsBeingAddedToServer;
import co.px.depthsong.network.Local.Model.GameMasters.ClientServerGameMaster;
import co.px.depthsong.network.PrintColors;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ScheduledFuture;

public class ClientServerEventHandler extends ChannelHandlerAdapter {
    //VARIABLES
    private ClientServerGameMaster clientServerGameMaster = ClientServer.clientServerGameMaster;
    private final GameManager gameManager = GameManager.getInstance();
    private Channel channel;

    private final boolean isDebugging = true;


    //SCHEDULED FUTURES FOR HANDLING EVENTS
    private ScheduledFuture scheduledFuture_adding_player_to_server;
    Future future_adding_player_to_server = scheduledFuture_adding_player_to_server;
    public static boolean checkIfPlayerWasSentToServer = false;

    private ScheduledFuture scheduledFuture_check_server_added_player;




    //OVERRIDES
    @Override
    public void channelRegistered(ChannelHandlerContext context) {

        trigger_adding_player_to_server(context);
        trigger_checkForServerResponseToPlayerCreation(context);

    }


    //METHODS
    private void trigger_adding_player_to_server(ChannelHandlerContext context) {

        scheduledFuture_adding_player_to_server = context.executor().scheduleAtFixedRate(() -> {
            print(false, "attempting to add player to server ...");

            if (gameManager.isPlayerCreated() && !checkIfPlayerWasSentToServer) {
                context.fireUserEventTriggered(new ClientEvent_playerIsBeingAddedToServer());


                checkIfPlayerWasSentToServer = true;
                scheduledFuture_adding_player_to_server.cancel(false);
            }

        }, 0, 1, java.util.concurrent.TimeUnit.SECONDS);



    }


    private void trigger_checkForServerResponseToPlayerCreation(ChannelHandlerContext context) {
        scheduledFuture_check_server_added_player = context.executor().scheduleAtFixedRate(() -> {
            print(false, "checking for server response ...");
            if (checkIfPlayerWasSentToServer && clientServerGameMaster.getCurrentPlayerWasIdentifiedByServer()) {
                print(false, "server responded to adding player");
                context.fireUserEventTriggered(new ClientEvent_ServerRespondedToAddingPlayer());

                scheduledFuture_check_server_added_player.cancel(false);
            }

        }, 0, 1, java.util.concurrent.TimeUnit.SECONDS);
    }


    //Change the name of this methode to somthing like getLocalAddressData()

    private void print(boolean isError, String message) {
        if (!isDebugging) {
            return;
        }
        if (!isError) {
            System.out.println(PrintColors.ANSI_BLUE + "(" + PrintColors.ANSI_GREEN + "*" + PrintColors.ANSI_BLUE + "client event manager) : " + message + PrintColors.ANSI_RESET);
        } else {
            System.err.println("(client event manager) err : " + message);
        }
    }
}
