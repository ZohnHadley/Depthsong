package org.tp5.Netty.Test_messages_en_sequence;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.tp5.Netty.Test_messages_en_sequence.Handlers.ClientServerHandler;
import org.tp5.Netty.Test_messages_en_sequence.Initializers.ClientChannelInitializer;

import java.net.InetSocketAddress;

public class ClientServer implements Runnable {

    protected String HOST_SERVER_IP;
    protected int HOST_SERVER_PORT;

    private EventLoopGroup WORK_GROUP;
    private Bootstrap CLIENT_BOOTSTRAP;

    private ClientServerHandler CLIENT_HANDLER;
    private ClientChannelInitializer CLIENT_INITIALIZER;


    private final int SECONDS_BEFOR_TIMEOUT;

    public ClientServer(String hostServerIp, int hostServerPort, int sec_before_timeout) {
        HOST_SERVER_IP = hostServerIp;
        HOST_SERVER_PORT = hostServerPort;
        SECONDS_BEFOR_TIMEOUT = sec_before_timeout;
    }

    public ClientServer(String hostServerIp, int hostServerPort) {
        HOST_SERVER_IP = hostServerIp;
        HOST_SERVER_PORT = hostServerPort;
        SECONDS_BEFOR_TIMEOUT = 10;
    }

    public void start() throws Exception {
        try {
            WORK_GROUP = new NioEventLoopGroup();
            CLIENT_HANDLER = new ClientServerHandler();
            CLIENT_INITIALIZER = new ClientChannelInitializer(SECONDS_BEFOR_TIMEOUT, CLIENT_HANDLER);

            CLIENT_BOOTSTRAP = new Bootstrap();
            CLIENT_BOOTSTRAP.group(WORK_GROUP);
            CLIENT_BOOTSTRAP.channel(NioSocketChannel.class);
            CLIENT_BOOTSTRAP.handler(CLIENT_INITIALIZER);
            CLIENT_BOOTSTRAP.option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channel_future = CLIENT_BOOTSTRAP.connect(HOST_SERVER_IP, HOST_SERVER_PORT).sync();
            channel_future.channel().closeFuture().sync();

        } finally {
            close();
        }
    }

    @Override
    public void run() {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InetSocketAddress getLocalChannel() {
        return CLIENT_HANDLER.getLocalChannel();
    }

    //close
    public void close() throws InterruptedException {
        WORK_GROUP.shutdownGracefully();
        Thread.sleep(100);
    }
}
