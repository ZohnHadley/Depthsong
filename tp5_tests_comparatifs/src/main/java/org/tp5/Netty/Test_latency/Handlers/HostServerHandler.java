package org.tp5.Netty.Test_latency.Handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.tp5.Netty.Test_latency.HostServer;
import org.tp5.Netty.Model.ClientInfo;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;

public class HostServerHandler extends ChannelHandlerAdapter {

    private ClientInfo clientInfo;
    private ByteBuf buffer;
    private int buffer_size = 32;


    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        InetSocketAddress address = (InetSocketAddress) context.channel().remoteAddress();
        String ip = address.getAddress().toString();
        int port = address.getPort();
        clientInfo = new ClientInfo(ip, port);
        HostServer.list_clientInfo.add(clientInfo);
        HostServer.clientCounter.incrementAndGet();

    }


    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        System.out.println(PrintColors.ANSI_GREEN + "(server) message reçu" + PrintColors.ANSI_RESET);
        if (msg != null) {
            //String message = msg.toString();
            //System.out.println(PrintColors.ANSI_GREEN + "(server) message reçu "+ message.getBytes().length + PrintColors.ANSI_RESET);
            context.writeAndFlush(msg);
        }
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
