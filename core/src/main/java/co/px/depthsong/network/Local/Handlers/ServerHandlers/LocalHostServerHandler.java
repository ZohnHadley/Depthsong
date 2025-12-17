package co.px.depthsong.network.Local.Handlers.ServerHandlers;

import co.px.depthsong.network.Local.Events.ServerSideEvents.ServerEvent_receivingPlayer;
import co.px.depthsong.network.Local.Model.ServerTracker.ServerConnectionContext;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import co.px.depthsong.network.PrintColors;
import co.px.depthsong.network.Local.Model.NetworkMessage;
import co.px.depthsong.network.Local.Model.PlayerObj;
import co.px.depthsong.network.Local.Model.GameMasters.ServerGameMaster;

import java.net.InetSocketAddress;

//(fixed) a probleme https://stackoverflow.com/questions/23788582/channelhandler-is-not-a-sharable-handler

public class LocalHostServerHandler extends ChannelHandlerAdapter {


    private ServerGameMaster serverGameMaster = ServerGameMaster.getInstance();
    private final ServerConnectionContext serverConnectionContext = new ServerConnectionContext();
    private InetSocketAddress address;

    private boolean isDebugging = true;

    private PlayerObj recievedPlayer;

    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        serverConnectionContext.setCurrentChannel(context.channel());
        serverConnectionContext.setCurrentContext(context);
        serverConnectionContext.setCurrentPlayer(null);

        address = (InetSocketAddress) context.channel().remoteAddress();

        if (serverConnectionContext.getCurrentChannel() != null) {

            print(false, "client registered");
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

        print(false, "client connected");

    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {
        print(false, "message reçu");

        if (message instanceof NetworkMessage) {
            NetworkMessage networkMessage = (NetworkMessage) message;

            if (networkMessage.getContent() == null) {
                return;
            }

            //when a player joins the server
            if (networkMessage.getContent() instanceof PlayerObj) {
                recievedPlayer = (PlayerObj) networkMessage.getContent();
                serverConnectionContext.setChannelRecievedPlayerObj(true);

                print(false, "receiving player " + recievedPlayer);

                if (serverConnectionContext.isFinishedConnectingPlayer()) {
                    //update the player from list with new object information
                    serverGameMaster.updatePlayer(recievedPlayer);
                    //send the player to all other clients
                    serverGameMaster.sendToAllChannelsExcept(networkMessage, serverConnectionContext.getCurrentChannel());
                }

            }
        }

    }


    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object event) {
        print(false, "event triggered " + event.getClass().getName());

        if (event instanceof ServerEvent_receivingPlayer) {
            if (!recievedPlayer.getHasServerID()) {
                serverConnectionContext.setCurrentPlayer(recievedPlayer);
                new ServerEvent_receivingPlayer().confirmToClientThatPlayerHasBeenReceived(serverConnectionContext, context);
            }
        }
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        print(true, "client disconnected");
        serverConnectionContext.getCurrentChannel().close();
        ServerGameMaster.clientCounter.decrementAndGet();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        print(true, cause.getMessage());
        cause.printStackTrace();
        serverConnectionContext.getCurrentChannel().close();
    }

    public ChannelHandlerContext getContext() {
        return serverConnectionContext.getCurrentContext();

    }


    private void print(boolean isError, String message) {
        if (!isDebugging) {
            return;
        }
        if (!isError) {
            System.out.println(PrintColors.ANSI_YELLOW + "(server) : " + message + PrintColors.ANSI_RESET);
        } else {
            System.err.println("(server) err : " + message);
        }
    }
}
