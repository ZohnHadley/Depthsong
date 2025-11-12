package org.tp5.Netty.Test_plusieurs_connections;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.tp5.Netty.Test_plusieurs_connections.Handlers.ClientServerHandler;

import java.net.InetSocketAddress;

public class ClientServer implements Runnable {

    protected String HOST_SERVER_IP;
    protected int HOST_SERVER_PORT;

    private EventLoopGroup WORK_GROUP;
    private Bootstrap CLIENT_BOOTSTRAP;
    private ClientServerHandler CLIENT_HANDLER;

    public ClientServer(String hostServerIp, int hostServerPort) {
        HOST_SERVER_IP = hostServerIp;
        HOST_SERVER_PORT = hostServerPort;

    }

    public void start() throws Exception {
        try {
            WORK_GROUP = new NioEventLoopGroup();
            CLIENT_HANDLER = new ClientServerHandler();
            CLIENT_BOOTSTRAP = new Bootstrap();
            CLIENT_BOOTSTRAP.group(WORK_GROUP);
            CLIENT_BOOTSTRAP.channel(NioSocketChannel.class);
            CLIENT_BOOTSTRAP.handler(CLIENT_HANDLER);
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
