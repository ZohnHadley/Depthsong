package org.tp5.Mina.Test_envoy_objet_complexe.Handlers;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.tp5.Mina.Model.UnixTime;
import org.tp5.Mina.Test_envoy_objet_complexe.HostServer;
import org.tp5.Mina.Model.ClientInfo;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;

public class MinaServerHandler extends IoHandlerAdapter {

    private ClientInfo clientInfo;
    private long startTime = -1;

    @Override
    public void sessionOpened(IoSession session) {
        System.out.println(PrintColors.ANSI_CYAN + "(server) actif" + PrintColors.ANSI_RESET);

        InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();

        String ip = address.getAddress().toString();
        int port = address.getPort();
        //ajout client au list
        clientInfo = new ClientInfo(ip, port);
        HostServer.list_clientInfo.add(clientInfo);
        HostServer.clientCounter.incrementAndGet();

        //envoy temps
        startTime = System.currentTimeMillis();
        UnixTime time = new UnixTime(startTime);
        session.write(time);
    }

    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println(PrintColors.ANSI_GREEN + "(server) message envoyé" + PrintColors.ANSI_RESET);
    }

    @Override
    public void sessionClosed(IoSession session) {
        System.out.println(PrintColors.ANSI_CYAN + "(server) client déconnecté" + PrintColors.ANSI_RESET);

        org.tp5.Mina.Test_plusieurs_connections.HostServer.list_clientInfo.remove(clientInfo);
        org.tp5.Mina.Test_plusieurs_connections.HostServer.clientCounter.decrementAndGet();
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("(server) idle triggered" + session.getIdleCount(status));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println(PrintColors.ANSI_CYAN + "(server) exception attrapé" + PrintColors.ANSI_RESET);
        cause.printStackTrace();
    }
}