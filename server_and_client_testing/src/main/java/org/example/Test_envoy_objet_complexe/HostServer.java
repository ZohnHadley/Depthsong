package org.example.Test_envoy_objet_complexe;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ScheduledFuture;
import org.example.PrintColors;
import org.example.Test_envoy_objet_complexe.Handlers.HostServerHandler;
import org.example.Test_envoy_objet_complexe.Initializers.ServerChannelInitializer;
import org.example.Test_envoy_objet_complexe.Model.ServerGameMaster;
import org.example.Test_envoy_objet_complexe.Model.ServerTracker.ClientInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HostServer implements Runnable {

    public static ArrayList<Channel> list_channels = new ArrayList<Channel>();
    public static ServerGameMaster serverGameMaster = ServerGameMaster.getInstance();


    public static ScheduledFuture future_timer;


    /////vars for server config
    private ServerChannelInitializer serverChannelInitializer;
    private final HostServerHandler hostServerHandler;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private final int port;

    public HostServer(int port) {
        this.port = port;
        hostServerHandler = new HostServerHandler();
        serverChannelInitializer = new ServerChannelInitializer(hostServerHandler);
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
    }

    public void start() throws Exception {
        System.out.println(PrintColors.ANSI_CYAN + "(netty server) est commencé" + PrintColors.ANSI_RESET);

        try {
            ServerBootstrap bootStrap = new ServerBootstrap();
            bootStrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(serverChannelInitializer)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = bootStrap.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            close();
        }
    }

    public void close() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
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
