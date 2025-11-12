package org.tp5.Netty.Test_plusieurs_connections;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.tp5.Netty.Test_plusieurs_connections.Handlers.HostServerHandler;
import org.tp5.Netty.Model.ClientInfo;
import org.tp5.PrintColors;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class HostServer implements Runnable {

    public static ArrayList<ClientInfo> list_clientInfo = new ArrayList<ClientInfo>();

    public static AtomicInteger clientCounter = new AtomicInteger(0);

    private final int port;

    public HostServer(int port) {
        System.out.println(PrintColors.ANSI_CYAN+"(netty server) est commencé"+PrintColors.ANSI_RESET);
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port");
        }

        this.port = port;
    }

    public void start() throws Exception {
        System.out.println(PrintColors.ANSI_CYAN+"(server) est commencé"+PrintColors.ANSI_RESET);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootStrap = new ServerBootstrap();
            bootStrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) {
                            channel.pipeline().addLast(
                                    new HostServerHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = bootStrap.bind(port).sync();
            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
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
}
