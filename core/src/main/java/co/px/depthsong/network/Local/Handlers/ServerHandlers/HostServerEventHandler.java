package co.px.depthsong.network.Local.Handlers.ServerHandlers;

import co.px.depthsong.network.Local.Events.ServerSideEvents.ServerEvent_receivingPlayer;
import co.px.depthsong.network.Local.Model.GameMasters.ServerGameMaster;
import co.px.depthsong.network.Local.Model.NetworkMessage;
import co.px.depthsong.network.Local.Model.ServerTracker.ServerConnectionContext;
import co.px.depthsong.network.PrintColors;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;


public class HostServerEventHandler extends ChannelHandlerAdapter {

    //VARIABLES
    private ServerGameMaster serverGameMaster;
    private boolean isDebugging = true;

    //SCHEDULED FUTURES FOR HANDLING EVENTS
    private ScheduledFuture scheduledFuture_receiving_a_player;
    private ScheduledFuture scheduledFuture_send_new_player_to_connected_players;
    private ScheduledFuture scheduledFuture_send_connected_players_to_new_player;

    //CONSTRUCTOR
    public HostServerEventHandler() {
        serverGameMaster = ServerGameMaster.getInstance();
    }

    //OVERRIDES
    @Override
    public void channelReadComplete(ChannelHandlerContext context) {
        trigger_receiving_player(context);
        trigger_send_new_player_to_connected_players(context);
        trigger_send_connected_players_to_new_player(context);
    }


    //METHODS
    private void trigger_receiving_player(ChannelHandlerContext context) {
        ServerConnectionContext scc = serverGameMaster.getChannel_context_list().getLast();
        scheduledFuture_receiving_a_player = context.executor().scheduleAtFixedRate(() -> {
            //print(false, "waiting for player to join ...");
            //print(false, ""+serverGameMaster.getChannel_context_list().size());
            /*print(false, "" + (scc.getCurrentClientAddressInformation() != null));
            print(false, "" + (scc.isChannelRecievedPlayerObj()));
            print(false, "" + (scc.getCurrentPlayer() == null));*/

            if (scc.isChannelRecievedPlayerObj()
                && scc.getCurrentPlayer() == null
            ) {

                print(false, "stopped waiting for player to join (state changed)");

                context.fireUserEventTriggered(new ServerEvent_receivingPlayer());

                scheduledFuture_receiving_a_player.cancel(false);
            }

        }, 0, 250, TimeUnit.MILLISECONDS);
    }

    private boolean send_new_player_to_connected_players = false;

    private void trigger_send_new_player_to_connected_players(ChannelHandlerContext context) {
        ServerConnectionContext scc = serverGameMaster.getChannel_context_list().getLast();

        scheduledFuture_send_new_player_to_connected_players = context.executor().scheduleAtFixedRate(() -> {
            if (scc.getCurrentPlayer() != null
                && send_new_player_to_connected_players == false
            ) {
                print(false, "sending new player to connected players");
                send_new_player_to_connected_players = true;
                serverGameMaster.sendAllPlayersToChannel(scc.getCurrentChannel());

                scheduledFuture_send_new_player_to_connected_players.cancel(true);
            }
        }, 250, 250, TimeUnit.MILLISECONDS);
    }

    private void trigger_send_connected_players_to_new_player(ChannelHandlerContext context) {
        ServerConnectionContext scc = serverGameMaster.getChannel_context_list().getLast();

        scheduledFuture_send_connected_players_to_new_player = context.executor().scheduleAtFixedRate(() -> {
            if (scc.getCurrentPlayer() != null
                && send_new_player_to_connected_players
            ) {
                print(false, "sending connected players to new player");

                NetworkMessage networkMessage = new NetworkMessage(NetworkMessage.MessageType.PLAYER_OBJECT, scc.getCurrentPlayer());
                serverGameMaster.sendToAllChannelsExcept(networkMessage, scc.getCurrentChannel());

                scc.setFinishedConnectingPlayer(true);

                scheduledFuture_send_connected_players_to_new_player.cancel(false);
            }
        }, 250, 250, TimeUnit.MILLISECONDS);
    }


    private void print(boolean isError, String message) {
        if (!isDebugging) {
            return;
        }
        if (!isError) {
            System.out.println(PrintColors.ANSI_CYAN + "(" + PrintColors.ANSI_GREEN + "*" + PrintColors.ANSI_CYAN + "server event manager) : " + message + PrintColors.ANSI_RESET);
        } else {
            System.err.println("(server event manager) err : " + message);
        }
    }
}
