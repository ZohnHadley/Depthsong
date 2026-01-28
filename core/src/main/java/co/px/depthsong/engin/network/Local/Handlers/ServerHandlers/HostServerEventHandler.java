package co.px.depthsong.engin.network.Local.Handlers.ServerHandlers;

import co.px.depthsong.engin.network.Local.Events.ServerSideEvents.ServerEvent_receivingPlayer;
import co.px.depthsong.engin.network.Local.Model.GameMasters.ServerGameMaster;
import co.px.depthsong.engin.network.Local.Model.NetworkMessage;
import co.px.depthsong.engin.network.Local.Model.ServerTracker.ServerConnectionContext;
import co.px.depthsong.engin.network.PrintColors;
import co.px.depthsong.engin.network.ServerUtil;
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
            //ServerUtil.log("server", "waiting for player to join ...");
            //ServerUtil.log("server", ""+serverGameMaster.getChannel_context_list().size());
            /*ServerUtil.log("server", "" + (scc.getCurrentClientAddressInformation() != null));
            ServerUtil.log("server", "" + (scc.isChannelRecievedPlayerObj()));
            ServerUtil.log("server", "" + (scc.getCurrentPlayer() == null));*/

            if (scc.isChannelRecievedPlayerObj()
                && scc.getCurrentPlayer() == null
            ) {

                ServerUtil.log("server", "stopped waiting for player to join (state changed)");

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
                ServerUtil.log("server", "sending new player to connected players");
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
                ServerUtil.log("server", "sending connected players to new player");

                NetworkMessage networkMessage = new NetworkMessage(NetworkMessage.MessageType.PLAYER_OBJECT, scc.getCurrentPlayer());
                serverGameMaster.sendToAllChannelsExcept(networkMessage, scc.getCurrentChannel());

                scc.setFinishedConnectingPlayer(true);

                scheduledFuture_send_connected_players_to_new_player.cancel(false);
            }
        }, 250, 250, TimeUnit.MILLISECONDS);
    }

}
