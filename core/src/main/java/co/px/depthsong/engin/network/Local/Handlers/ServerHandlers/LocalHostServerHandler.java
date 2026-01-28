package co.px.depthsong.engin.network.Local.Handlers.ServerHandlers;

import co.px.depthsong.engin.engineCore.engine_managers.NetworkMachineManager;
import co.px.depthsong.engin.network.Local.Events.ServerSideEvents.ServerEvent_receivingPlayer;
import co.px.depthsong.engin.network.Local.Model.ServerTracker.ServerConnectionContext;
import co.px.depthsong.engin.network.ServerUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import co.px.depthsong.engin.network.Local.Model.NetworkMessage;
import co.px.depthsong.engin.network.Local.Model.PlayerObj;
import co.px.depthsong.engin.network.Local.Model.GameMasters.ServerGameMaster;

import java.net.InetSocketAddress;

//(fixed) a probleme https://stackoverflow.com/questions/23788582/channelhandler-is-not-a-sharable-handler

public class LocalHostServerHandler extends ChannelHandlerAdapter {
    private NetworkMachineManager networkMachineManager = NetworkMachineManager.getInstance();

    private ServerGameMaster serverGameMaster = ServerGameMaster.getInstance();
    private final ServerConnectionContext serverConnectionContext = new ServerConnectionContext();
    private InetSocketAddress address;


    private PlayerObj receivedPlayer;

    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        serverConnectionContext.setCurrentChannel(context.channel());
        serverConnectionContext.setCurrentContext(context);
        serverConnectionContext.setCurrentPlayer(null);

        address = (InetSocketAddress) context.channel().remoteAddress();

         if (serverConnectionContext.getCurrentChannel() != null) {
            ServerUtil.log("server", "client registered");
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        serverConnectionContext.setCurrentChannel(context.channel());
        serverConnectionContext.setCurrentContext(context);

        address = (InetSocketAddress) context.channel().remoteAddress();
        String ip = address.getAddress().toString();
        int port = address.getPort();

        serverConnectionContext.setLocalChannelAddress(ip + ":" + port); // very important for decoder to know where to send the message


        serverGameMaster.getChannel_context_list().add(serverConnectionContext);

        ServerUtil.log("server", "client connected");

    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {
        ServerUtil.log("server", "message reçu");

        if (message instanceof NetworkMessage) {
            NetworkMessage networkMessage = (NetworkMessage) message;

            if (networkMessage.getContent() == null) {
                return;
            }

            //when a player joins the server
            if (networkMessage.getContent() instanceof PlayerObj) {
                receivedPlayer = (PlayerObj) networkMessage.getContent();
                serverConnectionContext.setChannelRecievedPlayerObj(true);

                ServerUtil.log("server", "receiving player " + receivedPlayer);

                if (serverConnectionContext.isFinishedConnectingPlayer()) {
                    //update the player from list with new object information
                    serverGameMaster.updatePlayer(receivedPlayer);
                    //send the player to all other clients
                    serverGameMaster.sendToAllChannelsExcept(networkMessage, serverConnectionContext.getCurrentChannel());
                }

            }
        }

    }


    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object event) {
        ServerUtil.log("server","event triggered " + event.getClass().getName());

        if (event instanceof ServerEvent_receivingPlayer) {
            if (!receivedPlayer.getHasServerID()) {
                serverConnectionContext.setCurrentPlayer(receivedPlayer);
                new ServerEvent_receivingPlayer().confirmToClientThatPlayerHasBeenReceived(serverConnectionContext, context);
            }
        }
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        ServerUtil.err("server", "client disconnected");
        serverConnectionContext.getCurrentChannel().close();
        ServerGameMaster.clientCounter.decrementAndGet();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        ServerUtil.err("server",cause.getMessage());
        cause.printStackTrace();
        serverConnectionContext.getCurrentChannel().close();
    }

    public ChannelHandlerContext getContext() {
        return serverConnectionContext.getCurrentContext();

    }
}
