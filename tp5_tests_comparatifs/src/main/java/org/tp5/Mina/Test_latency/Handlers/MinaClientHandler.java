package org.tp5.Mina.Test_latency.Handlers;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.tp5.Mina.Test_latency.Mina_TestLatency;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MinaClientHandler extends IoHandlerAdapter {

    private IoSession current_session;

    //selon mes recherche Mina n'a pas de fonctionnalité pour envoyé des message en séqunce, mais il s'intègre bien avec ScheduledExecutorService
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private int message_startTime = 0;
    private int timeElapsedSinceMessageSent = 0;
    private int currentWriteCount = 0;

    @Override
    public void sessionCreated(IoSession session) {
        System.out.println("(client) connecté");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        if (status == IdleStatus.READER_IDLE) {
            CloseFuture future = session.closeNow();
            future.addListener(new IoFutureListener<IoFuture>() {
                @Override
                public void operationComplete(IoFuture future) {
                    if (future.isDone()) {
                        System.out.println("(client) timeout : rien reçue du server (déconnecté)");
                    }
                }
            });
        }
    }

    @Override
    public void sessionOpened(IoSession session) {
        System.out.println("(client) actif");
        current_session = session;
        //session loop a fixed tick rate
        // Planifier l'envoi périodique de messages
        scheduler.scheduleAtFixedRate(() -> {
            if (session.isConnected()) {
                if (currentWriteCount >= Mina_TestLatency.numberOfTimesToWrite) {
                    System.out.println("nombre de pmessages envoyé: " + currentWriteCount);
                    session.closeNow();
                    return;
                }
                message_startTime = (int) System.currentTimeMillis();

                ByteBuffer buffer = ByteBuffer.allocate(Mina_TestLatency.packetSize);
                buffer.put(new byte[Mina_TestLatency.packetSize]);
                WriteFuture future = session.write(buffer);
                future.addListener(new IoFutureListener<IoFuture>() {
                    @Override
                    public void operationComplete(IoFuture future) {
                        if (future.isDone()) {
                            System.out.println(PrintColors.ANSI_PURPLE + "(client) message envoyé au server" + PrintColors.ANSI_RESET);
                        }
                    }
                });
                currentWriteCount++;
            }
        }, 0, 1, TimeUnit.SECONDS); // Envoi toutes les 1 seconde
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        timeElapsedSinceMessageSent = (int) System.currentTimeMillis() - message_startTime;
        Mina_TestLatency.list_packet_times.add(timeElapsedSinceMessageSent);
        String serverIp = getLocalChannel().getAddress().toString();
        System.out.println("replique du server " + serverIp + " : bytes=" + Mina_TestLatency.packetSize + " temps=" + timeElapsedSinceMessageSent + " ms");

    }

    @Override
    public void sessionClosed(IoSession session) {
        System.out.println("(client) déconnecté");
        int average_packet_time = Mina_TestLatency.list_packet_times.stream().mapToInt(Integer::intValue).sum() / Mina_TestLatency.list_packet_times.size();
        int max_packet_time = Mina_TestLatency.list_packet_times.stream().mapToInt(Integer::intValue).max().getAsInt();
        int min_packet_time = Mina_TestLatency.list_packet_times.stream().mapToInt(Integer::intValue).min().getAsInt();
        System.out.println("Minimum= " + min_packet_time + "ms  Maximum= " + max_packet_time + "ms   temps moyen= " + average_packet_time + "ms ");
        scheduler.close();
        session.closeNow();
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println("(client) exception attrapé");
        cause.printStackTrace();
    }


    public InetSocketAddress getLocalChannel() {
        return (InetSocketAddress) current_session.getLocalAddress();
    }

}
