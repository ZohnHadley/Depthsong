package org.example.Test_envoy_objet_complexe.Handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.concurrent.ScheduledFuture;
import org.example.PrintColors;
import org.example.Test_envoy_objet_complexe.HostServer;
import org.example.Test_envoy_objet_complexe.Model.CurrentTurnTimeObject;
import org.example.Test_envoy_objet_complexe.Model.NetworkMessage;
import org.example.Test_envoy_objet_complexe.Model.PlayerObj;
import org.example.Test_envoy_objet_complexe.Model.ServerGameMaster;
import org.example.Test_envoy_objet_complexe.Model.ServerTracker.ClientInfo;

import java.net.InetSocketAddress;
import java.sql.Time;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;


//TODO this (fixed) a probleme https://stackoverflow.com/questions/23788582/channelhandler-is-not-a-sharable-handler
@ChannelHandler.Sharable
public class HostServerHandler extends ChannelHandlerAdapter {

    private ClientInfo clientInfo;
    private ServerGameMaster gm = HostServer.serverGameMaster;

    //private int messagePerTick = 1;
    //private int TIME_TO_SEND = (int) ((1f / messagePerTick) * 1000);

    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        InetSocketAddress address = (InetSocketAddress) context.channel().remoteAddress();
        String ip = address.getAddress().toString();
        int port = address.getPort();

        clientInfo = new ClientInfo(ip, port, context.channel());
        gm.getList_clientInfo().add(clientInfo);
        gm.getClientCounter().incrementAndGet();

        if (gm.getClientCounter().get() == 1) {
            clientInfo.setItThisClientTurn(true);
            gm.setClientOfCurrentTurn(clientInfo);
        }

    }

    private ScheduledFuture future_debug_timer;
    int sec = 0;

    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {

        //gm.getList_clientInfo().size() > 1 &&
        //update turns
        if (gm.getClientOfCurrentTurn() != null && gm.getClientOfCurrentTurn().getChannel() == context.channel()) {
            updateTurns(context);
        }

    }

    private void updateTurns(ChannelHandlerContext context) {

        future_debug_timer = context.channel().eventLoop().scheduleAtFixedRate(() -> {
            CurrentTurnTimeObject currentTurnTimeObject = new CurrentTurnTimeObject(gm.getSecondsPerTurn() - sec);

            NetworkMessage networkMessage_turnTime = new NetworkMessage(NetworkMessage.MessageType.CURRENT_TURN_TIME_OBJECT, currentTurnTimeObject);
            gm.getClientOfCurrentTurn().getChannel().writeAndFlush(networkMessage_turnTime);

            sec++;
        }, 1, 1, TimeUnit.SECONDS);

        System.out.println(PrintColors.ANSI_CYAN + "(server) actif" + PrintColors.ANSI_RESET);
        HostServer.future_timer = context.channel().eventLoop().scheduleAtFixedRate(() -> {
            if (context.channel().isActive()) {

                gm.setNextClientTurn();
                sec = 0;
            }
        }, 30, 30, TimeUnit.SECONDS);
    }


    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {
        if (message instanceof NetworkMessage networkMessage) {

            //when a player joins the server
            if (networkMessage.getContent() instanceof PlayerObj playerObj) {
                //send to every client except the sender aka the player that just joined
                if (gm.getList_clientInfo().size() > 1) {
                    for (ClientInfo clientInfo : gm.getList_clientInfo()) {
                        if (!clientInfo.equals(this.clientInfo)) {
                            System.out.println("new client : sending to other clients");

                            clientInfo.getChannel().writeAndFlush(networkMessage);
                        }
                    }
                }
            }
        }

        if (message instanceof String) {
            String msg = (String) message;
            System.out.println(PrintColors.ANSI_CYAN + "(server) message reçu" + PrintColors.ANSI_RESET);
            System.out.println(msg);
        }

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        System.out.println(PrintColors.ANSI_CYAN + "(server) déconnexion client" + PrintColors.ANSI_RESET);
        gm.getList_clientInfo().remove(clientInfo);
        gm.getClientCounter().decrementAndGet();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        System.out.println(PrintColors.ANSI_RED + "(server) exception" + PrintColors.ANSI_RESET);
        cause.printStackTrace();
        context.close();
    }

}
