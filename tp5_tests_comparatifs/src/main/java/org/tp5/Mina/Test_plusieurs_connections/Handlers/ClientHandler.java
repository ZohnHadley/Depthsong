package org.tp5.Mina.Test_plusieurs_connections.Handlers;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.net.InetSocketAddress;

public class ClientHandler extends IoHandlerAdapter {

    private IoSession current_session;

    @Override
    public void sessionOpened(IoSession session) {
        current_session = session;
    }


    @Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception {
        cause.printStackTrace();
    }


    public InetSocketAddress getLocalChannel() {
        return (InetSocketAddress) current_session.getLocalAddress();
    }
}
