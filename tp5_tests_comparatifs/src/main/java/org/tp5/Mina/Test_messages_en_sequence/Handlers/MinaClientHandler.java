package org.tp5.Mina.Test_messages_en_sequence.Handlers;

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

    //selon mes recherche Mina n'a pas de fonctionnalité pour envoyé des message en séqunce, mais il s'intègre bien avec ScheduledExecutorService
    private IoSession current_session;



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


    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("(client) message reçue du server : " + message);
    }

    @Override
    public void sessionClosed(IoSession session) {
        System.out.println("(client) déconnecté");
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
