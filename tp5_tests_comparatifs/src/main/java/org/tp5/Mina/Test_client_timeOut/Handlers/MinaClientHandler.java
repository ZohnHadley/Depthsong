package org.tp5.Mina.Test_client_timeOut.Handlers;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;

public class MinaClientHandler extends IoHandlerAdapter {

    private IoSession current_session;

    @Override
    public void sessionCreated(IoSession session) {
        System.out.println("(client) connecté");
    }

    @Override
    public void sessionOpened(IoSession session) {
        System.out.println("(client) actif");
        current_session = session;
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println(PrintColors.ANSI_GREEN + "(client) message reçue" + PrintColors.ANSI_RESET);
        session.closeNow();
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        if (status == IdleStatus.READER_IDLE) {
            System.out.println("(client) timeout : rien reçue du server (déconnecté)");
            session.closeNow();
        }
    }

    @Override
    public void sessionClosed(IoSession session) {
        System.out.println("(client) déconnecté");
    }

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println("(client) exception attrapé");
        cause.printStackTrace();
    }


    public InetSocketAddress getLocalChannel() {
        return (InetSocketAddress) current_session.getLocalAddress();
    }

}
