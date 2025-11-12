package org.tp5.Mina.Test_plusieurs_connections.Handlers;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.tp5.Mina.Test_plusieurs_connections.HostServer;
import org.tp5.Mina.Model.ClientInfo;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;


public class ServerHandler extends IoHandlerAdapter {

    private ClientInfo clientInfo;

    @Override
    public void sessionOpened(IoSession session) {
        //get ip and port of client

        InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();

        String ip = address.getAddress().toString();
        int port = address.getPort();
        //add client to list
        clientInfo = new ClientInfo(ip, port);
        HostServer.list_clientInfo.add(clientInfo);
        HostServer.clientCounter.incrementAndGet();
    }


    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String str = message.toString();
        if (str.trim().equalsIgnoreCase("quit")) {
            session.close();
            return;
        }
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("IDLE " + session.getIdleCount(status));
    }

    @Override
    public void sessionClosed(IoSession session) {
        System.out.println(PrintColors.ANSI_CYAN + "(server) client déconnecté" + PrintColors.ANSI_RESET);
        HostServer.list_clientInfo.remove(clientInfo);
        HostServer.clientCounter.decrementAndGet();
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println(PrintColors.ANSI_CYAN + "(server) exception" + PrintColors.ANSI_RESET);

        cause.printStackTrace();
    }

}
