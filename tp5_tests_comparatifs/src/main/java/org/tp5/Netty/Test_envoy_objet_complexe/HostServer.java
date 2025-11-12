package org.tp5.Netty.Test_envoy_objet_complexe;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.tp5.Netty.Test_envoy_objet_complexe.Handlers.HostServerHandler;
import org.tp5.Netty.Test_envoy_objet_complexe.Initializers.ServerChannelInitializer;
import org.tp5.Netty.Model.ClientInfo;
import org.tp5.PrintColors;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class HostServer implements Runnable {

    public static ArrayList<ClientInfo> list_clientInfo = new ArrayList<ClientInfo>();

    public static AtomicInteger clientCounter = new AtomicInteger(0);

    private final HostServerHandler hostServerHandler;

    private final int port;

    public HostServer(int port) {
        this.port = port;
        hostServerHandler = new HostServerHandler();
    }

    public void start() throws Exception {
        System.out.println(PrintColors.ANSI_CYAN+"(netty server) est commencé"+PrintColors.ANSI_RESET);

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootStrap = new ServerBootstrap();
            bootStrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChannelInitializer(hostServerHandler))
                    .option(ChannelOption.SO_BACKLOG, 128)
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
