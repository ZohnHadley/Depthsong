package co.px.depthsong.engin.network.Local.Handlers.ServerHandlers;

import co.px.depthsong.engin.network.Local.Model.GameMasters.HostServerMaster;
import co.px.depthsong.engin.network.Local.Model.NetworkMessage;
import co.px.depthsong.engin.network.Local.Model.ServerTracker.ClientServerConnectionContext;
import co.px.depthsong.engin.network.ServerUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;


public class HostServerEventHandler extends ChannelHandlerAdapter {

    //VARIABLES
    private HostServerMaster hostServerMaster;

    //SCHEDULED FUTURES FOR HANDLING EVENTS
    private ScheduledFuture sch_receivingPlayer;
    private ScheduledFuture scheduledFuture_send_new_player_to_connected_players;
    private ScheduledFuture scheduledFuture_send_connected_players_to_new_player;


    //CONSTRUCTOR
    public HostServerEventHandler() {
        hostServerMaster = HostServerMaster.getInstance();
    }

    //OVERRIDES
    @Override
    public void channelRead(ChannelHandlerContext context, Object message) throws Exception {
        trigger_receiving_player(context, message);
//        trigger_send_new_player_to_connected_players(context);
//        trigger_send_connected_players_to_new_player(context);
    }


    //METHODS
    private void trigger_receiving_player(ChannelHandlerContext context, Object message) {
        ServerUtil.log(message.toString());
        sch_receivingPlayer = context.executor().scheduleAtFixedRate(() -> {
            //ServerUtil.log("server", "waiting for player to join ...");
            //ServerUtil.log("server", ""+serverGameMaster.getChannel_context_list().size());
            /*ServerUtil.log("server", "" + (scc.getCurrentClientAddressInformation() != null));
            ServerUtil.log("server", "" + (scc.isChannelReceivedPlayerObj()));
            ServerUtil.log("server", "" + (scc.getCurrentPlayer() == null));*/


                sch_receivingPlayer.cancel(false);

        }, 0, 250, TimeUnit.MILLISECONDS);
    }




}
