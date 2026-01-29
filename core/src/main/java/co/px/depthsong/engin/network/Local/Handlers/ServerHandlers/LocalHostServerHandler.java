package co.px.depthsong.engin.network.Local.Handlers.ServerHandlers;

import co.px.depthsong.engin.network.Local.Model.NetworkMessage;
import co.px.depthsong.engin.network.Local.Model.ServerObject;
import co.px.depthsong.engin.network.Local.Model.ServerTracker.ClientServerConnectionContext;
import co.px.depthsong.engin.network.ServerUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import co.px.depthsong.engin.network.Local.Model.ServerEntityPlayer;
import co.px.depthsong.engin.network.Local.Model.GameMasters.HostServerMaster;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.ScheduledFuture;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

//(fixed) a probleme https://stackoverflow.com/questions/23788582/channelhandler-is-not-a-sharable-handler

public class LocalHostServerHandler extends ChannelHandlerAdapter {

    private HostServerMaster hostServerMaster = HostServerMaster.getInstance();
    private ClientServerConnectionContext clientServerConnectionContext;


    private ServerObject receivedPlayer;

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
        ServerUtil.log(localAddress + "");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        InetSocketAddress connection_info = (InetSocketAddress) context.channel().remoteAddress();
        hostServerMaster.setIpAddress(connection_info.toString());
        hostServerMaster.setPort(connection_info.getPort());


        clientServerConnectionContext = new ClientServerConnectionContext();
        clientServerConnectionContext.setCurrentChannel(context.channel());
        clientServerConnectionContext.setCurrentContext(context);
        clientServerConnectionContext.setLocalChannelAddress(context.channel().localAddress().toString() + ":" + ((InetSocketAddress) context.channel().localAddress()).getPort());
        clientServerConnectionContext.setCurrentPlayer(null);


        if (clientServerConnectionContext.getCurrentChannel() != null) {
            ServerUtil.log("server", "client registered");
        }
        context.flush();
    }


    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {

        clientServerConnectionContext.setCurrentChannel(context.channel());
        clientServerConnectionContext.setCurrentContext(context);
        clientServerConnectionContext.setLocalChannelAddress(hostServerMaster.getIpAddress() + ":" + hostServerMaster.getPort()); // very important for decoder to know where to send the message


        hostServerMaster.getClientsServerConnectionContexts().add(clientServerConnectionContext);

        ServerUtil.log("server", "actively connecting");
        context.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ServerUtil.log("server", "message reu");

    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {
        ServerUtil.log("server", "message reçu");

        //        trigger_send_new_player_to_connected_players(context);
        //        trigger_send_connected_players_to_new_player(context);

        //when a player joins the server
//        if (message instanceof ServerEntityPlayer) {
//            receivedPlayer = (ServerEntityPlayer) message;
//
//            clientServerConnectionContext.setChannelReceivedPlayerObj(true);
//
////            ServerUtil.log("server", "receiving player " + receivedPlayer);
////
////            if (clientServerConnectionContext.isFinishedConnectingPlayer()) {
////                //update the player from list with new object information
////                hostServerMaster.updatePlayer(receivedPlayer);
////                //send the player to all other clients
//////                    hostServerMaster.sendToAllChannelsExcept(networkMessage, serverConnectionContext.getCurrentChannel());
////            }
//
//        }

        context.flush();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object message) {
        ServerUtil.log("server", "event triggered " + message.getClass().getName());


        if (((ServerEntityPlayer) receivedPlayer).getClientServerID() != -1L) {
            clientServerConnectionContext.setCurrentPlayer((ServerEntityPlayer) receivedPlayer);
            ServerUtil.log("recieved player " + receivedPlayer);
            context.write(receivedPlayer);
        }
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        ServerUtil.err("server", "client disconnected");
        clientServerConnectionContext.getCurrentChannel().close();
        HostServerMaster.clientCounter.decrementAndGet();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        ServerUtil.err("server", cause.getMessage());
        cause.printStackTrace();
        clientServerConnectionContext.getCurrentChannel().close();
    }

    public ChannelHandlerContext getContext() {
        return clientServerConnectionContext.getCurrentContext();

    }



    private void trigger_send_new_player_to_connected_players(ChannelHandlerContext context) {
//        ClientServerConnectionContext scc = hostServerMaster.getClientsServerConnectionContexts().getLast();
//
//        scheduledFuture_send_new_player_to_connected_players = context.executor().scheduleAtFixedRate(() -> {
//            if (scc.getCurrentPlayer() != null
//                && send_new_player_to_connected_players == false
//            ) {
//                ServerUtil.log("server", "sending new player to connected players");
//                send_new_player_to_connected_players = true;
//                hostServerMaster.sendAllPlayersToChannel(scc.getCurrentChannel());
//
//                scheduledFuture_send_new_player_to_connected_players.cancel(true);
//            }
//        }, 250, 250, TimeUnit.MILLISECONDS);
//    }
//
//    private void trigger_send_connected_players_to_new_player(ChannelHandlerContext context) {
//        ClientServerConnectionContext scc = hostServerMaster.getClientsServerConnectionContexts().getLast();
//
//        scheduledFuture_send_connected_players_to_new_player = context.executor().scheduleAtFixedRate(() -> {
//            if (scc.getCurrentPlayer() != null
//                && send_new_player_to_connected_players
//            ) {
//                ServerUtil.log("server", "sending connected players to new player");
//
//                NetworkMessage networkMessage = new NetworkMessage(NetworkMessage.MessageType.PLAYER_OBJECT, scc.getCurrentPlayer());
//                hostServerMaster.sendToAllChannelsExcept(networkMessage, scc.getCurrentChannel());
//
//                scc.setConnectedOnServer(true);
//
//                scheduledFuture_send_connected_players_to_new_player.cancel(false);
//            }
//        }, 250, 250, TimeUnit.MILLISECONDS);
    }
}
