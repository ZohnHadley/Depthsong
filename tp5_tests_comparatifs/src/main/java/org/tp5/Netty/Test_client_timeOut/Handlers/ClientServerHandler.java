package org.tp5.Netty.Test_client_timeOut.Handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;

public class ClientServerHandler extends ChannelHandlerAdapter {


    private Channel channel;


    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        System.out.println("(client) connecté");
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
    public void channelActive(ChannelHandlerContext context) {
        // System.out.println("ClientServerHandler Active");
        channel = context.channel();
        ByteBuf text_message = context.alloc().buffer(1024);
        text_message.writeBytes("hi".getBytes());
        context.writeAndFlush(text_message);
        System.out.println("(client) message envoyé");

    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        System.out.println(PrintColors.ANSI_GREEN + "(client) message reçue du server : " + PrintColors.ANSI_RESET + msg);
        context.close();
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        System.out.println("(client) déconnecté");
        context.close();
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
