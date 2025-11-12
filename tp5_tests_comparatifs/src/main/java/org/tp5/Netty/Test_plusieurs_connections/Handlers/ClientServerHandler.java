package org.tp5.Netty.Test_plusieurs_connections.Handlers;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;

public class ClientServerHandler extends ChannelHandlerAdapter {

    private Channel channel;

    @Override
    public void channelActive(ChannelHandlerContext context) {
//        System.out.println(PrintColors.ANSI_YELLOW+"(client) active"+ PrintColors.ANSI_RESET);
        channel = context.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        channel = context.channel();
        System.out.println("ClientServerHandler first read Message");

        ByteBuf message_from_server = (ByteBuf) msg;
        String message = message_from_server.toString();

        System.out.println("ClientServerHandler first Message: " + message);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }

    public InetSocketAddress getLocalChannel() {
        return (InetSocketAddress) channel.localAddress();
    }
}
