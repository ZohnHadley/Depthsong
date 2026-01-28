package co.px.depthsong.engin.network.Local;


import co.px.depthsong.engin.network.NetworkMachine;
import co.px.depthsong.engin.network.ServerUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ScheduledFuture;
import co.px.depthsong.engin.network.PrintColors;
import co.px.depthsong.engin.network.Local.Initializers.ServerChannelInitializer;

public class HostServer extends NetworkMachine {



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

    @Override
    public void start() throws Exception {
        ServerUtil.log("host server started");

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
                    ServerUtil.log("host server started on port "  + " " + port);
                } else {
                    ServerUtil.err("host server failed to start on port  " + port);
//                    close();
                }
            });

            channel_future.channel().closeFuture().sync();
        } finally {
            close();
            ServerUtil.err("host server CLOSED");
        }
    }

    @Override
    public boolean isRunning() {
        return serverRunning;
    }

    @Override
    public void close() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }


    //RUN when assigned a thread
    @Override
    public void run() {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
