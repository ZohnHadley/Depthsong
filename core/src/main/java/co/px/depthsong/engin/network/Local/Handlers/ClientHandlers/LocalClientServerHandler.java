package co.px.depthsong.engin.network.Local.Handlers.ClientHandlers;

import co.px.depthsong.engin.ECS.core.EntityContext;
import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumActivationState;
import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumNetworkClientConnectionStates;
import co.px.depthsong.engin.network.ServerUtil;
import co.px.depthsong.game.models.entities.ClientPlayer;
import co.px.depthsong.engin.network.Local.Model.CurrentTurnTimeObject;
import co.px.depthsong.engin.network.Local.Model.NetworkMessage;
import co.px.depthsong.engin.network.Local.Model.PlayerObj;
import co.px.depthsong.engin.engineCore.engine_managers.GameManager;

import co.px.depthsong.engin.network.Local.ClientServer;
import co.px.depthsong.engin.network.Local.Events.ClientSideEvents.ClientEvent_ServerRespondedToAddingPlayer;
import co.px.depthsong.engin.network.Local.Events.ClientSideEvents.ClientEvent_playerIsBeingAddedToServer;
import co.px.depthsong.engin.network.Local.Model.GameMasters.ClientServerGameMaster;
import co.px.depthsong.engin.network.PrintColors;
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

    private final EnumActivationState isDebugging = EnumActivationState.OFF;


    private PlayerObj currentPlayer = null;


    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        ServerUtil.log("host server","registered channel");
        channel = context.channel();
        gameManager.getNetworkMachineManager().setCurrentConnectedState(EnumNetworkClientConnectionStates.CONNECTED);


    }

    @Override
    public void channelActive(ChannelHandlerContext context) {
        ServerUtil.log("host server","active");


        //localAddress = getLocalChannel().getAddress().toString() + ":" + getLocalChannel().getPort();

        channel = context.channel();
        gameManager.getNetworkMachineManager().setCurrentConnectedState(EnumNetworkClientConnectionStates.CONNECTED);
        if (gameManager.getNetworkMachineManager().getConnectionState() == EnumNetworkClientConnectionStates.DISCONNECTED) {
            context.close();
        }

    }

    ScheduledFuture scheduledFuture_update_player_on_server;

    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {


        if (message instanceof NetworkMessage) {
            NetworkMessage networkMessage = (NetworkMessage) message;
            ServerUtil.log("host server", "message received : " + networkMessage.getContent());

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
                    ServerUtil.log("host server", player.getClientServer_id() + " " + player.getUsername() + " joined server");
                }


                scheduledFuture_update_player_on_server = context.executor().scheduleWithFixedDelay(() -> {
//                    if (gameManager.isNetworked() && Player.updatePlayerOnServer && clientServerGameMaster.getCurrentPlayerWasIdentifiedByServer()) {
                    if (gameManager.getNetworkMachineManager().getConnectionState() == EnumNetworkClientConnectionStates.CONNECTED && clientServerGameMaster.getCurrentPlayerWasIdentifiedByServer()) {

                        ClientPlayer entity_Client_player = (ClientPlayer) gameManager.getEntityContext().getPlayer();
                        currentPlayer.setX((int) entity_Client_player.getComponentTransform().getPosition().x);
                        currentPlayer.setY((int) entity_Client_player.getComponentTransform().getPosition().y);

                        context.writeAndFlush(new NetworkMessage(NetworkMessage.MessageType.PLAYER_OBJECT, currentPlayer));
//                        Player.updatePlayerOnServer = false;
                    }
                }, 0, 500,   TimeUnit.MILLISECONDS);
            }

            if (networkMessage.getContent() instanceof CurrentTurnTimeObject) {
                CurrentTurnTimeObject currentTurnTimeObject = (CurrentTurnTimeObject) networkMessage.getContent();
                ServerUtil.log("time left for turn : " + (currentTurnTimeObject.getSeconds()));
            }
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object event) {

        EntityContext entMng = gameManager.getEntityContext();

        ServerUtil.log("host server", "user event triggered " + event.getClass().getName());

        //adding player to server
        if (event instanceof ClientEvent_playerIsBeingAddedToServer) {
            System.out.println("sending player object to server ...");
            new ClientEvent_playerIsBeingAddedToServer().sendPlayerObjectToServer(context, entMng);

        }

        if (event instanceof ClientEvent_ServerRespondedToAddingPlayer) {


        }

        if (event instanceof IdleStateEvent) {

            if (((IdleStateEvent) event).state() == IdleState.READER_IDLE) {
                ServerUtil.log("host server", "timeout : nothing received from server (disconnected)");
                gameManager.getNetworkMachineManager().setCurrentConnectedState(EnumNetworkClientConnectionStates.CONNECTED);
                context.close();
            }
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        ServerUtil.log("host server", "disconnected");
        gameManager.getNetworkMachineManager().setCurrentConnectedState(EnumNetworkClientConnectionStates.CONNECTED);
        context.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        ServerUtil.err("host server", "exception caught");
        gameManager.getNetworkMachineManager().setCurrentConnectedState(EnumNetworkClientConnectionStates.CONNECTED);
        cause.printStackTrace();
        context.close();
    }

    public InetSocketAddress getLocalChannel() {
        return (InetSocketAddress) channel.localAddress();
    }

    public Channel getChannel() {
        return channel;
    }



}
