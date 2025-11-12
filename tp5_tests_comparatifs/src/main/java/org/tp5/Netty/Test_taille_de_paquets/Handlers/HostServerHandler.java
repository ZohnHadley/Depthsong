package org.tp5.Netty.Test_taille_de_paquets.Handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.tp5.Netty.Test_taille_de_paquets.HostServer;
import org.tp5.Netty.Model.ClientInfo;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class HostServerHandler extends ChannelHandlerAdapter {
    private ClientInfo clientInfo;

    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        System.out.println(PrintColors.ANSI_CYAN + "(server) client connecté" + PrintColors.ANSI_RESET);
        InetSocketAddress address = (InetSocketAddress) context.channel().remoteAddress();
        String ip = address.getAddress().toString();
        int port = address.getPort();
        clientInfo = new ClientInfo(ip, port);
        HostServer.list_clientInfo.add(clientInfo);
        HostServer.clientCounter.incrementAndGet();
        context.flush();
    }


    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {


        System.out.println(PrintColors.ANSI_CYAN + "(Server) message reçue du client : " + PrintColors.ANSI_PURPLE + msg + PrintColors.ANSI_CYAN + " = " + msg.toString().getBytes().length + " Bytes" + PrintColors.ANSI_RESET);
        context.close();

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) {

        System.out.println(PrintColors.ANSI_CYAN + "(server) client déconnecté" + PrintColors.ANSI_RESET);


        HostServer.list_clientInfo.remove(clientInfo);
        HostServer.clientCounter.decrementAndGet();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        System.out.println(PrintColors.ANSI_RED + "exceptionCaught" + PrintColors.ANSI_RESET);
        cause.printStackTrace();
        context.close();
    }

}
