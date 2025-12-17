package co.px.depthsong.network.Local.Handlers.ClientHandlers;

import co.px.depthsong.ECS.entityContext.EntityContext;
import co.px.depthsong.layers.models.entities.ClientPlayer;
import co.px.depthsong.network.Local.Model.CurrentTurnTimeObject;
import co.px.depthsong.network.Local.Model.NetworkMessage;
import co.px.depthsong.network.Local.Model.PlayerObj;
import co.px.depthsong.layers.managers.GameManager;

import co.px.depthsong.layers.managers.NetworkManager;
import co.px.depthsong.network.Local.ClientServer;
import co.px.depthsong.network.Local.Events.ClientSideEvents.ClientEvent_ServerRespondedToAddingPlayer;
import co.px.depthsong.network.Local.Events.ClientSideEvents.ClientEvent_playerIsBeingAddedToServer;
import co.px.depthsong.network.Local.Model.GameMasters.ClientServerGameMaster;
import co.px.depthsong.network.PrintColors;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.ScheduledFuture;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class LocalClientServerHandler extends ChannelHandlerAdapter {

    private ClientServerGameMaster clientServerGameMaster = ClientServer.clientServerGameMaster;

    private final GameManager gameManager = GameManager.getInstance();

    private Channel channel;

    private final boolean isDebugging = true;


    private PlayerObj currentPlayer = null;


    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        print(false, "connected");
        channel = context.channel();
        gameManager.getNetworkManager().setCurrentConnectedState(NetworkManager.connection_states.CONNECTED);


    }

    @Override
    public void channelActive(ChannelHandlerContext context) {
        print(false, "active");


        //localAddress = getLocalChannel().getAddress().toString() + ":" + getLocalChannel().getPort();

        channel = context.channel();
        gameManager.getNetworkManager().setCurrentConnectedState(NetworkManager.connection_states.CONNECTED);
        if (gameManager.getNetworkManager().getConnectionState() == NetworkManager.connection_states.OFFLINE) {
            context.close();
        }

    }

    ScheduledFuture scheduledFuture_update_player_on_server;

    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {


        if (message instanceof NetworkMessage) {
            NetworkMessage networkMessage = (NetworkMessage) message;
            print(false, "message received : " + networkMessage.getContent());

            if (networkMessage.getContent() instanceof PlayerObj) {
                PlayerObj player = (PlayerObj) networkMessage.getContent();


                if (!clientServerGameMaster.getCurrentPlayerWasIdentifiedByServer()
                    && player.getLocalChannelAddress().equals(getLocalChannel().getAddress() + ":" + getLocalChannel().getPort())) {
                    currentPlayer = player;
                    clientServerGameMaster.setCurrentPlayerWasIdentifiedByServer(true);
                    System.out.println(PrintColors.ANSI_YELLOW + "***you joined server***" + PrintColors.ANSI_RESET);

                } else if (currentPlayer != null && clientServerGameMaster.getCurrentPlayerWasIdentifiedByServer()
                    && !player.getLocalChannelAddress().equals(getLocalChannel().getAddress() + ":" + getLocalChannel().getPort()))
                {
                    ClientServer.clientServerGameMaster.addPlayer(player);
                    print(false, player.getClientServer_id() + " " + player.getUsername() + " joined server");
                }


                scheduledFuture_update_player_on_server = context.executor().scheduleWithFixedDelay(() -> {
//                    if (gameManager.isNetworked() && Player.updatePlayerOnServer && clientServerGameMaster.getCurrentPlayerWasIdentifiedByServer()) {
                    if (gameManager.isNetworked() && clientServerGameMaster.getCurrentPlayerWasIdentifiedByServer()) {

                        ClientPlayer entity_Client_player = (ClientPlayer) gameManager.getEntityContext().getPlayer();
                        currentPlayer.setX((int) entity_Client_player.getTransform().getPosition().x);
                        currentPlayer.setY((int) entity_Client_player.getTransform().getPosition().y);

                        context.writeAndFlush(new NetworkMessage(NetworkMessage.MessageType.PLAYER_OBJECT, currentPlayer));
//                        Player.updatePlayerOnServer = false;
                    }
                }, 0, 500,   TimeUnit.MILLISECONDS);


            }

            if (networkMessage.getContent() instanceof CurrentTurnTimeObject) {
                CurrentTurnTimeObject currentTurnTimeObject = (CurrentTurnTimeObject) networkMessage.getContent();
                print(false, "time left for turn : " + (currentTurnTimeObject.getSeconds()));
            }
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object event) {

        EntityContext entMng = gameManager.getEntityContext();

        print(false, "user event triggered " + event.getClass().getName());

        //adding player to server
        if (event instanceof ClientEvent_playerIsBeingAddedToServer) {
            System.out.println("sending player object to server ...");
            new ClientEvent_playerIsBeingAddedToServer().sendPlayerObjectToServer(context, entMng);

        }

        if (event instanceof ClientEvent_ServerRespondedToAddingPlayer) {


        }

        if (event instanceof IdleStateEvent) {

            if (((IdleStateEvent) event).state() == IdleState.READER_IDLE) {
                print(false, "timeout : nothing received from server (disconnected)");
                gameManager.getNetworkManager().setCurrentConnectedState(NetworkManager.connection_states.OFFLINE);
                context.close();
            }
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        print(false, "disconnected");
        gameManager.getNetworkManager().setCurrentConnectedState(NetworkManager.connection_states.OFFLINE);
        context.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        print(true, "exception caught");
        gameManager.getNetworkManager().setCurrentConnectedState(NetworkManager.connection_states.OFFLINE);
        cause.printStackTrace();
        context.close();
    }

    public InetSocketAddress getLocalChannel() {
        return (InetSocketAddress) channel.localAddress();
    }

    public Channel getChannel() {
        return channel;
    }


    private void print(boolean isError, String message) {
        if (!isDebugging) {
            return;
        }
        if (!isError) {
            System.out.println(PrintColors.ANSI_BLUE + "(client) : " + message + PrintColors.ANSI_RESET);
        } else {
            System.err.println("(client) err : " + message);
        }
    }
}
