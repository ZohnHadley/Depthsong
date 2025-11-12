package org.example.Test_envoy_objet_complexe.Handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.example.PrintColors;
import org.example.Test_envoy_objet_complexe.HostServer;
import org.example.Test_envoy_objet_complexe.Model.CurrentTurnTimeObject;
import org.example.Test_envoy_objet_complexe.Model.NetworkMessage;
import org.example.Test_envoy_objet_complexe.Model.PlayerObj;
import org.example.Test_envoy_objet_complexe.Model.ServerGameMaster;
import org.example.Test_envoy_objet_complexe.Model.ServerTracker.ClientInfo;
import org.example.Test_envoy_objet_complexe.Netty_TestEnvoyObjetComplex;

import java.net.InetSocketAddress;

public class ClientServerHandler extends ChannelHandlerAdapter {

    private Channel channel;

    private ServerGameMaster gm = HostServer.serverGameMaster;

    private String userName;

    public ClientServerHandler(String userName) {
        super();
        this.userName = userName;

    }

    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        System.out.println("(client) connecté");

        channel = context.channel();
        if (channel != null) {
            HostServer.list_channels.add(channel);
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext context) {
        System.out.println("(client) actif");
        channel = context.channel();
        if (!HostServer.list_channels.contains(channel)) {
            HostServer.list_channels.add(channel);
        }

        PlayerObj thisPlayer = new PlayerObj(1L, userName, 0, 0);
        for (ClientInfo clientInfo : gm.getList_clientInfo()) {
            if (clientInfo.getChannel().equals(channel)) {
                clientInfo.setPlayerObj(thisPlayer);
            }
        }

        context.writeAndFlush(new NetworkMessage(NetworkMessage.MessageType.PLAYER_OBJECT,thisPlayer));
    }


    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {
        if (message instanceof NetworkMessage networkMessage) {
            if(networkMessage.getContent() instanceof PlayerObj){
                PlayerObj playerObj = (PlayerObj) networkMessage.getContent();
                System.out.println("PlayerObj received : " + playerObj.getUsername());
            }

            if(networkMessage.getContent() instanceof CurrentTurnTimeObject){
                CurrentTurnTimeObject currentTurnTimeObject = (CurrentTurnTimeObject) networkMessage.getContent();
                System.out.println("Turn time : " + currentTurnTimeObject.getSeconds());
            }
        }
    }




    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object evt) {
        if (!(evt instanceof IdleStateEvent e)) {
            System.out.println("userEventTriggered");
            return;
        }
        if (e.state() == IdleState.READER_IDLE) {
            System.out.println("(client) timeout : rien reçue du server (déconnecté)");
            context.close();
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        System.out.println("(client) déconnecté");
        HostServer.list_channels.remove(channel);
        context.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        HostServer.list_channels.remove(channel);
        cause.printStackTrace();
        context.close();
    }

    public InetSocketAddress getLocalChannel() {
        return (InetSocketAddress) channel.localAddress();
    }
    public Channel getChannel() {
        return  channel;
    }

}
