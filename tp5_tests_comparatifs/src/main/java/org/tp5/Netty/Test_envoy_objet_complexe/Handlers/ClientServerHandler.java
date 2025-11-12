package org.tp5.Netty.Test_envoy_objet_complexe.Handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.tp5.Netty.Model.UnixTime;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;

public class ClientServerHandler extends ChannelHandlerAdapter {


    private static final long TICK_RATE_MILLIS = 1000;
    private Channel channel;


    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        System.out.println("(client) connecté");
    }

    @Override
    public void channelActive(ChannelHandlerContext context) {
        System.out.println("(client) actif");

    }


    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        System.out.println(PrintColors.ANSI_GREEN + "(client) message reçu" + PrintColors.ANSI_RESET);
        UnixTime m = (UnixTime) msg;
        System.out.println(m);
        context.close();
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        System.out.println("(client) déconnecté");
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
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }

    public InetSocketAddress getLocalChannel() {
        return (InetSocketAddress) channel.localAddress();
    }

}
