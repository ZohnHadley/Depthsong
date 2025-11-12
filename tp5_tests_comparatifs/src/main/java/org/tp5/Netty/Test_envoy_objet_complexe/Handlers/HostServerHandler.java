package org.tp5.Netty.Test_envoy_objet_complexe.Handlers;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.tp5.Netty.Test_envoy_objet_complexe.HostServer;
import org.tp5.Netty.Model.ClientInfo;
import org.tp5.Netty.Model.UnixTime;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;

public class HostServerHandler extends ChannelHandlerAdapter {

    private ClientInfo clientInfo;

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
    public void channelActive(ChannelHandlerContext context) throws Exception {
        System.out.println(PrintColors.ANSI_CYAN + "(server) actif" + PrintColors.ANSI_RESET);
        ChannelFuture futureChannel = context.writeAndFlush(new UnixTime());
        futureChannel.addListener(
                new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) {
                        if (future.isSuccess()) {
                            System.out.println(PrintColors.ANSI_GREEN + "(server) message envoyé" + PrintColors.ANSI_RESET);
                        } else {
                            System.out.println(PrintColors.ANSI_RED + "(server) message non envoyé" + PrintColors.ANSI_RESET);
                        }
                    }
                }
        );
    }


    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        if (msg != null) {
            //String message = msg.toString();
            //System.out.println(PrintColors.ANSI_GREEN + "(server) message reçu "+ message.getBytes().length + PrintColors.ANSI_RESET);
            context.writeAndFlush(msg);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        System.out.println(PrintColors.ANSI_CYAN + "(server) déconnexion client" + PrintColors.ANSI_RESET);

        HostServer.list_clientInfo.remove(clientInfo);
        HostServer.clientCounter.decrementAndGet();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        System.out.println(PrintColors.ANSI_RED + "(server) exception" + PrintColors.ANSI_RESET);
        cause.printStackTrace();
        context.close();
    }

}
