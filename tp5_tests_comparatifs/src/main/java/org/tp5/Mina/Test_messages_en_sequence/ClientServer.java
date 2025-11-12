package org.tp5.Mina.Test_messages_en_sequence;


import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.tp5.Mina.Test_messages_en_sequence.Handlers.MinaClientHandler;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;


//TCP/IP based
public class ClientServer implements Runnable {
    private final int PORT;
    private final String SERVER_IP;

    private MinaClientHandler CLIENT_HANDLER;
    private IoConnector connector;

    private final int SECONDS_BEFOR_TIMEOUT;


    public ClientServer(String server_address, int port, int seconds_befor_timeout) {
        SERVER_IP = server_address;
        PORT = port;
        CLIENT_HANDLER = new MinaClientHandler();
        SECONDS_BEFOR_TIMEOUT = seconds_befor_timeout;
    }

    public void start() {

        try {
            connector = new NioSocketConnector();
            //connector.getFilterChain().addLast("logger", new LoggingFilter());
            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(StandardCharsets.UTF_8)));
            connector.setHandler(CLIENT_HANDLER);
            connector.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, SECONDS_BEFOR_TIMEOUT);
            connector.getSessionConfig().setReadBufferSize(2048);

            ConnectFuture future = connector.connect(new InetSocketAddress(SERVER_IP, PORT));
            future.awaitUninterruptibly();

            /*IoSession session = future.getSession();
            session.getCloseFuture().awaitUninterruptibly();
            connector.dispose();*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InetSocketAddress getLocalChannel() {
        return CLIENT_HANDLER.getLocalChannel();
    }

    @Override
    public void run() {
        start();
    }

    public void close() throws InterruptedException {
        connector.dispose();
        Thread.sleep(100);
    }

}
