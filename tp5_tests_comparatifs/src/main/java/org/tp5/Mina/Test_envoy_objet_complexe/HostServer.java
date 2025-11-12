package org.tp5.Mina.Test_envoy_objet_complexe;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.tp5.Mina.Test_envoy_objet_complexe.CodecFactory.UnixCodecFactory;
import org.tp5.Mina.Test_envoy_objet_complexe.Handlers.MinaServerHandler;
import org.tp5.Mina.Model.ClientInfo;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class HostServer implements Runnable {

    public static ArrayList<ClientInfo> list_clientInfo = new ArrayList<ClientInfo>();

    public static AtomicInteger clientCounter = new AtomicInteger(0);

    private final int PORT;

    private MinaServerHandler serverHandler;
    private IoAcceptor acceptor;

    public HostServer(int port) {
        System.out.println(PrintColors.ANSI_CYAN + "(mina server) est commencé" + PrintColors.ANSI_RESET);
        PORT = port;
        serverHandler = new MinaServerHandler();
    }

    public void start() {
        try {
            // (selon documentation NioSocketAcceptor=Tcp)
            acceptor = new NioSocketAcceptor(); // used to recieve incoming connections

            //acceptor.getFilterChain().addLast("logger", new LoggingFilter()); //This filter will log all information such as newly created sessions, messages received, messages sent, session closed
            //acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(StandardCharsets.UTF_8)));//This filter will translate binary or protocol specific data into message object and vice versa.
            acceptor.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new UnixCodecFactory(false)));

            acceptor.setHandler(serverHandler);

            acceptor.getSessionConfig().setReadBufferSize(2048);
            //acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);


            acceptor.bind(new InetSocketAddress(PORT));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        start();
    }

    public void close() throws InterruptedException {
        acceptor.dispose();
        Thread.sleep(100);
    }
}
