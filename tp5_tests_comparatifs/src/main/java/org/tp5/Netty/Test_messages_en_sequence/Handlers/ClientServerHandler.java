package org.tp5.Netty.Test_messages_en_sequence.Handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.net.InetSocketAddress;

public class ClientServerHandler extends ChannelHandlerAdapter {

    private Channel channel;
    long startTime = -1;


    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        if (startTime < 0) {
            startTime = System.currentTimeMillis();
        }
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

    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        System.out.println("(client) message reçue du server : " + msg);
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

    /*public void println(String message) {
        if (startTime < 0) {
            System.err.format("[SERVER IS DOWN] %s%n", message);
        } else {
            System.err.format("[UPTIME: %5ds] %s%n", (System.currentTimeMillis() - startTime) / 1000, message);
        }
    }*/
}
