package org.tp5.Netty.Test_latency.Handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.ScheduledFuture;
import org.tp5.Netty.Test_latency.Netty_TestLatency;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class ClientServerHandler extends ChannelHandlerAdapter {


    private static final long TICK_RATE_MILLIS = 1000;
    private Channel channel;

    private ScheduledFuture future_multi_message_par_tick;

    private int message_startTime = 0;
    private int timeElapsedSinceMessageSent = 0;
    private int currentWriteCount = 0;

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
    public void channelActive(ChannelHandlerContext context) throws Exception {

        future_multi_message_par_tick = context.channel().eventLoop().scheduleAtFixedRate(() -> {
            if (context.channel().isActive()) {
                if (currentWriteCount >= Netty_TestLatency.numberOfTimesToWrite) {
                    System.out.println("nombre de pmessages envoyé: " + currentWriteCount);
                    context.close();
                    return;
                }
                message_startTime = (int) System.currentTimeMillis();
                ByteBuf buff = (ByteBuf) context.channel().alloc().buffer(Netty_TestLatency.packetSize);
                buff.writeBytes(new byte[Netty_TestLatency.packetSize]);
                ChannelFuture future = context.writeAndFlush(buff);
                future.addListener(f -> {
                    if (!f.isSuccess()) {
                        System.out.println(PrintColors.ANSI_PURPLE + "(client) message envoyé au server" + PrintColors.ANSI_RESET);
                    }
                });
                currentWriteCount++;
            }
        }, 0, TICK_RATE_MILLIS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        timeElapsedSinceMessageSent = (int) System.currentTimeMillis() - message_startTime;
        Netty_TestLatency.list_packet_times.add(timeElapsedSinceMessageSent);
        String serverIp = ((InetSocketAddress) context.channel().remoteAddress()).getAddress().getHostAddress();
        System.out.println("replique du server " + serverIp + " : bytes=" + Netty_TestLatency.packetSize + " temps=" + timeElapsedSinceMessageSent + " ms");
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        System.out.println("(client) déconnecté");
        int average_packet_time = Netty_TestLatency.list_packet_times.stream().mapToInt(Integer::intValue).sum() / Netty_TestLatency.list_packet_times.size();
        int max_packet_time = Netty_TestLatency.list_packet_times.stream().mapToInt(Integer::intValue).max().getAsInt();
        int min_packet_time = Netty_TestLatency.list_packet_times.stream().mapToInt(Integer::intValue).min().getAsInt();
        System.out.println("Minimum= " + min_packet_time + "ms  Maximum= " + max_packet_time + "ms   temps moyen= " + average_packet_time + "ms ");
        future_multi_message_par_tick.cancel(true);
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
