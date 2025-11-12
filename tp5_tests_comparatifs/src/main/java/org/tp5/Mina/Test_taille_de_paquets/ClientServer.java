package org.tp5.Mina.Test_taille_de_paquets;


import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.tp5.Mina.Test_plusieurs_connections.Handlers.ClientHandler;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


//TCP/IP based
public class ClientServer implements Runnable {
    private final int PORT;
    private final String SERVER_IP;

    private ClientHandler CLIENT_HANDLER;
    private IoConnector connector;

    public ClientServer(String server_address, int port) {
        SERVER_IP = server_address;
        PORT = port;
        CLIENT_HANDLER = new ClientHandler();
    }

    public void start() {

        try {
            connector = new NioSocketConnector();
            //connector.getFilterChain().addLast("logger", new LoggingFilter());
            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(StandardCharsets.UTF_8)));
            connector.setHandler(CLIENT_HANDLER);
            ConnectFuture future = connector.connect(new InetSocketAddress(SERVER_IP, PORT));
            future.awaitUninterruptibly();

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
