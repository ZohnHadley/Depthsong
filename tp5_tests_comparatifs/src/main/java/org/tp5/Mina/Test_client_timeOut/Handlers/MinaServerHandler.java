package org.tp5.Mina.Test_client_timeOut.Handlers;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.FilterEvent;
import org.tp5.Mina.Test_envoy_objet_complexe.HostServer;
import org.tp5.Mina.Model.ClientInfo;
import org.tp5.Mina.Model.UnixTime;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;

public class MinaServerHandler extends IoHandlerAdapter {

    private ClientInfo clientInfo;
    private long conneciton_start_time;
    int currentTime = 0;

    @Override
    public void sessionOpened(IoSession session) {
        conneciton_start_time = System.currentTimeMillis();
        System.out.println(PrintColors.ANSI_CYAN + "(server) actif" + PrintColors.ANSI_RESET);

        InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();

        String ip = address.getAddress().toString();
        int port = address.getPort();
        //ajout client au list
        clientInfo = new ClientInfo(ip, port);
        HostServer.list_clientInfo.add(clientInfo);
        HostServer.clientCounter.incrementAndGet();

    }

    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println(PrintColors.ANSI_GREEN + "(server) message envoyé" + PrintColors.ANSI_RESET);
    }

    @Override
    public void sessionClosed(IoSession session) {
        currentTime = (int) (System.currentTimeMillis() - conneciton_start_time) / 1000;
        System.out.println(PrintColors.ANSI_CYAN + "(server) client déconnecté : temps écoulé avant déconection [" + currentTime + "s]" + PrintColors.ANSI_RESET);

        HostServer.list_clientInfo.remove(clientInfo);
        HostServer.clientCounter.decrementAndGet();
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        if (status == IdleStatus.WRITER_IDLE){
            currentTime = (int) (System.currentTimeMillis() - conneciton_start_time) / 1000;
            System.out.println(PrintColors.ANSI_CYAN + "(server) message evoyer apres [" + currentTime + "s]" + PrintColors.ANSI_RESET);
            session.write("je suis le server bro! [" + currentTime + "s]");
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println(PrintColors.ANSI_CYAN + "(server) exception attrapé" + PrintColors.ANSI_RESET);
        cause.printStackTrace();
    }
}