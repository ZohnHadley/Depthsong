package org.tp5.Netty.Test_taille_de_paquets.Handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.tp5.Netty.Test_taille_de_paquets.Netty_TestServerTailleDePaquets;

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
        text_message.writeBytes(Netty_TestServerTailleDePaquets.message.getBytes());
        context.writeAndFlush(text_message);
        System.out.println("(client) message envoyé");
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        System.out.println("(client) message reçue du server : " + msg);
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

    /*public void println(String message) {
        if (startTime < 0) {
            System.err.format("[SERVER IS DOWN] %s%n", message);
        } else {
            System.err.format("[UPTIME: %5ds] %s%n", (System.currentTimeMillis() - startTime) / 1000, message);
        }
    }*/
}
