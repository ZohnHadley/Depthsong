package org.tp5.Mina.Test_client_timeOut;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.tp5.Mina.Test_client_timeOut.Handlers.MinaServerHandler;
import org.tp5.Mina.Model.ClientInfo;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


//TCP/IP based
public class HostServer implements Runnable {

    public static ArrayList<ClientInfo> list_clientInfo = new ArrayList<ClientInfo>();

    public static AtomicInteger clientCounter = new AtomicInteger(0);

    private final int PORT;
    private final int MESSAGE_DELAY_TIME;


    private MinaServerHandler serverHandler;
    private IoAcceptor acceptor;

    public HostServer(int port, int messageDelayTime) {
        System.out.println(PrintColors.ANSI_CYAN + "(mina server) est commencé" + PrintColors.ANSI_RESET);
        PORT = port;
        serverHandler = new MinaServerHandler();
        this.MESSAGE_DELAY_TIME = messageDelayTime;
    }

    public void start() {
        try {
            // (selon documentation NioSocketAcceptor=Tcp)
            acceptor = new NioSocketAcceptor();

            //acceptor.getFilterChain().addLast("logger", new LoggingFilter());
            acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(StandardCharsets.UTF_8)));

            acceptor.setHandler(serverHandler);

            acceptor.getSessionConfig().setReadBufferSize(2048);
            acceptor.getSessionConfig().setIdleTime(IdleStatus.WRITER_IDLE, MESSAGE_DELAY_TIME);

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
