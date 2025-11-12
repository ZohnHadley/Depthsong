package org.tp5.Mina.Test_messages_en_sequence.Handlers;

import io.netty.util.concurrent.ScheduledFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.tp5.Mina.Model.ClientInfo;
import org.tp5.Mina.Test_envoy_objet_complexe.HostServer;
import org.tp5.Mina.Test_messages_en_sequence.Mina_TestMessagesEnSequence;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;
import java.sql.Time;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MinaServerHandler extends IoHandlerAdapter {

    private static final long TICK_RATE_MILLIS = Mina_TestMessagesEnSequence.serverWriteTickRate;
    private static final long DELAY_TICK_RATE_MILLIS = 1000;


    //selon mes recherche Mina n'a pas de fonctionnalité pour envoyé des message en séqunce, mais il s'intègre bien avec ScheduledExecutorService
    private final ScheduledExecutorService future_multi_message_par_tick = Executors.newScheduledThreadPool(1);
    private final ScheduledExecutorService future_delayed_message_by_second = Executors.newScheduledThreadPool(1);

    private ClientInfo clientInfo;

    @Override
    public void sessionOpened(IoSession session) {
        InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
        String ip = address.getAddress().toString();
        int port = address.getPort();
        clientInfo = new ClientInfo(ip, port);
        HostServer.list_clientInfo.add(clientInfo);
        HostServer.clientCounter.incrementAndGet();


        future_multi_message_par_tick.scheduleAtFixedRate(() -> {
            if (session.isConnected()) {
                WriteFuture future = session.write("bonjour je suis server");
                future.addListener(new IoFutureListener<IoFuture>() {
                    @Override
                    public void operationComplete(IoFuture future) {
                    }
                });
            }
        }, 0, TICK_RATE_MILLIS, TimeUnit.MILLISECONDS); // Envoi toutes les 1 seconde

        future_delayed_message_by_second.scheduleWithFixedDelay(() -> {
            if (session.isConnected()) {
                WriteFuture future = session.write(PrintColors.ANSI_BLUE + new Time(System.currentTimeMillis()) + PrintColors.ANSI_RESET );
                future.addListener(new IoFutureListener<IoFuture>() {
                    @Override
                    public void operationComplete(IoFuture future) {

                    }
                });
            }
        }, 0, DELAY_TICK_RATE_MILLIS, TimeUnit.MILLISECONDS); // Envoi toutes les 1 seconde
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        System.out.println(PrintColors.ANSI_GREEN + "(server) message reçu" + PrintColors.ANSI_RESET);
        if (message != null) {
            session.write(message);

        }
    }

    @Override
    public void sessionClosed(IoSession session) {
        System.out.println(PrintColors.ANSI_CYAN + "(server) client déconnecté" + PrintColors.ANSI_RESET);

        HostServer.list_clientInfo.remove(clientInfo);
        HostServer.clientCounter.decrementAndGet();
    }


    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println(PrintColors.ANSI_CYAN + "(server) exception attrapé" + PrintColors.ANSI_RESET);
        cause.printStackTrace();
        session.closeNow();
    }
}