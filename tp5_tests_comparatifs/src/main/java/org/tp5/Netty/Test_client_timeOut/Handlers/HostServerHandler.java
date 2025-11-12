package org.tp5.Netty.Test_client_timeOut.Handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.tp5.Netty.Test_client_timeOut.HostServer;
import org.tp5.Netty.Model.ClientInfo;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class HostServerHandler extends ChannelHandlerAdapter {
    private ClientInfo clientInfo;
    private long conneciton_start_time;
    int currentTime = 0;

    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        conneciton_start_time = System.currentTimeMillis();
        System.out.println(PrintColors.ANSI_CYAN + "(server) client conneciton_start_time: " + (0) + PrintColors.ANSI_RESET);

        //add client to list
        InetSocketAddress address = (InetSocketAddress) context.channel().remoteAddress();
        String ip = address.getAddress().toString();
        int port = address.getPort();
        clientInfo = new ClientInfo(ip, port);
        HostServer.list_clientInfo.add(clientInfo);
        HostServer.clientCounter.incrementAndGet();
        context.flush();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object evt) {
        System.out.println(PrintColors.ANSI_CYAN + "(server) time event triggered" + PrintColors.ANSI_RESET);

        if (!(evt instanceof IdleStateEvent e)) {
            System.out.println(PrintColors.ANSI_CYAN + "(server) non idle event triggered" + PrintColors.ANSI_RESET);
            return;
        }

        if (e.state() == IdleState.WRITER_IDLE) {
            currentTime = (int) (System.currentTimeMillis() - conneciton_start_time) / 1000;
            System.out.println(PrintColors.ANSI_CYAN + "(server) message evoyer apres [" + currentTime + "s]" + PrintColors.ANSI_RESET);
            //change state
            ByteBuf text_message = context.alloc().buffer(1024);
            text_message.writeBytes(("je suis le server bro! [" + currentTime + "s]").getBytes());
            ChannelFuture event_future = context.writeAndFlush(text_message);
            event_future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println(PrintColors.ANSI_CYAN + "(server) operationComplete success : message envoyer après delay" + PrintColors.ANSI_RESET);
                    } else {
                        System.out.println(PrintColors.ANSI_CYAN + "(server) operationComplete failure : message pas envoyer" + PrintColors.ANSI_RESET);
                        channelFuture.cause().printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {

        System.out.println(PrintColors.ANSI_CYAN + "(Server) message reçue du client : " + msg.toString() + PrintColors.ANSI_RESET);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        currentTime = (int) (System.currentTimeMillis() - conneciton_start_time) / 1000;
        System.out.println(PrintColors.ANSI_CYAN + "(server) client déconnecté : temps écoulé avant déconection [" + currentTime + "s]" + PrintColors.ANSI_RESET);


        HostServer.list_clientInfo.remove(clientInfo);
        ArrayList<ClientInfo> temp_list_clientInfo = new ArrayList<>();
        HostServer.clientCounter.decrementAndGet();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }

}
