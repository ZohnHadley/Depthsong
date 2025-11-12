package co.px.depthsong.network.Local;


import co.px.depthsong.network.NetworkMachine;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ScheduledFuture;
import co.px.depthsong.network.PrintColors;
import co.px.depthsong.network.Local.Initializers.ServerChannelInitializer;

public class HostServer implements NetworkMachine {



    public static ScheduledFuture future_timer;


    /////vars for server config
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private final int port;

    private boolean serverRunning = false;

    public HostServer(int port) {
        this.port = port;
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
    }

    public void start() throws Exception {
        System.out.println(PrintColors.ANSI_CYAN + "(netty server) est commencé" + PrintColors.ANSI_RESET);

        try {
            ServerBootstrap bootStrap = new ServerBootstrap();
            bootStrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler( new ServerChannelInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channel_future = bootStrap.bind(port).sync();
            //listen when server start
            channel_future = channel_future.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println(PrintColors.ANSI_GREEN + "Server started on port " + port + PrintColors.ANSI_RESET);
                    serverRunning = true;
                } else {
                    System.out.println(PrintColors.ANSI_RED + "Server failed to start on port " + port + PrintColors.ANSI_RESET);
                    serverRunning = false;
                }
            });

            channel_future.channel().closeFuture().sync();
        } finally {
            close();
        }
    }

    public boolean isRunning() {
        return serverRunning;
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
