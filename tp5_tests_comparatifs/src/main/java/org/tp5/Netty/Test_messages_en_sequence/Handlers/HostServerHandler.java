package org.tp5.Netty.Test_messages_en_sequence.Handlers;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;
import org.tp5.Netty.Test_messages_en_sequence.HostServer;
import org.tp5.Netty.Test_messages_en_sequence.Netty_TestMessagesEnSequence;
import org.tp5.Netty.Model.ClientInfo;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;
import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class HostServerHandler extends ChannelHandlerAdapter {
    private static final long TICK_RATE_MILLIS = Netty_TestMessagesEnSequence.serverWriteTickRate;
    private static final long DELAY_TICK_RATE_MILLIS = 1000;
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

    }

    private ScheduledFuture future_multi_message_par_tick;
    private ScheduledFuture future_delayed_message_by_second;

    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        System.out.println(PrintColors.ANSI_CYAN + "(server) channelActive" + PrintColors.ANSI_RESET);
        future_multi_message_par_tick = context.channel().eventLoop().scheduleAtFixedRate(() -> {
            if (context.channel().isActive()) {
                //send current hour:min:sec time
                context.writeAndFlush("bonjour je suis server" );
            }
        }, 0, TICK_RATE_MILLIS, TimeUnit.MILLISECONDS);


        future_delayed_message_by_second = context.channel().eventLoop().scheduleWithFixedDelay(() -> {
            if (context.channel().isActive()) {
                //send current hour:min:sec time
                context.writeAndFlush(PrintColors.ANSI_BLUE + new Time(System.currentTimeMillis()) + PrintColors.ANSI_RESET );
            }
        }, 0, DELAY_TICK_RATE_MILLIS, TimeUnit.MILLISECONDS);
    }


    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {


        System.out.println(PrintColors.ANSI_CYAN + "(Server) message reçue du client : " + msg + PrintColors.ANSI_RESET);
        context.close();

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) {

        System.out.println(PrintColors.ANSI_CYAN + "(server) client déconnecté" + PrintColors.ANSI_RESET);
        future_multi_message_par_tick.cancel(true);
        future_delayed_message_by_second.cancel(true);

        HostServer.list_clientInfo.remove(clientInfo);
        ArrayList<ClientInfo> temp_list_clientInfo = new ArrayList<>();
        HostServer.clientCounter.decrementAndGet();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        System.out.println(PrintColors.ANSI_RED + "exceptionCaught" + PrintColors.ANSI_RESET);
        cause.printStackTrace();
        context.close();
    }

}
