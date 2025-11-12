package org.tp5.Mina.Test_latency.Handlers;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.tp5.Mina.Model.ClientInfo;
import org.tp5.Mina.Test_envoy_objet_complexe.HostServer;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;

public class MinaServerHandler extends IoHandlerAdapter {

    private ClientInfo clientInfo;

    @Override
    public void sessionOpened(IoSession session) {
        InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
        String ip = address.getAddress().toString();
        int port = address.getPort();
        clientInfo = new ClientInfo(ip, port);
        HostServer.list_clientInfo.add(clientInfo);
        HostServer.clientCounter.incrementAndGet();

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