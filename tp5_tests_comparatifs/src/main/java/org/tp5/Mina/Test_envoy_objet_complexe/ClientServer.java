package org.tp5.Mina.Test_envoy_objet_complexe;


import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.tp5.Mina.Test_envoy_objet_complexe.CodecFactory.UnixCodecFactory;
import org.tp5.Mina.Test_envoy_objet_complexe.Handlers.MinaClientHandler;

import java.net.InetSocketAddress;

public class ClientServer implements Runnable {
    private final int PORT;
    private final String SERVER_IP;

    private final MinaClientHandler CLIENT_HANDLER;
    private IoConnector connector;

    public ClientServer(String server_address, int port) {
        SERVER_IP = server_address;
        PORT = port;
        CLIENT_HANDLER = new MinaClientHandler();
    }

    public void start() {

        try {
            connector = new NioSocketConnector();
            //connector.getFilterChain().addLast("logger", new LoggingFilter());
            connector.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new UnixCodecFactory(true)));
            //connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));//This filter will translate binary or protocol specific data into message object and vice versa.

            connector.setHandler(CLIENT_HANDLER);

            ConnectFuture future = connector.connect(new java.net.InetSocketAddress(SERVER_IP, PORT));
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
