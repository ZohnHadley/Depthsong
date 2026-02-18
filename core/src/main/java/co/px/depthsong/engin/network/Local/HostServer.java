package co.px.depthsong.engin.network.Local;


import co.px.depthsong.engin.network.Local.Model.Managers.HostServerMaster;
import co.px.depthsong.engin.network.NetworkMachine;
import co.px.depthsong.engin.network.CustomLogger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ScheduledFuture;
import co.px.depthsong.engin.network.Local.Initializers.ServerChannelInitializer;
import lombok.Getter;

@Getter
public class HostServer extends NetworkMachine implements Runnable {
    //Is a thread
    public static ScheduledFuture future_timer;

    private HostServerMaster hostServerMaster = HostServerMaster.getInstance();
    private ServerBootstrap bootStrap;

    /// //vars for server config
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private final int port;

    public HostServer(int port) {
        this.port = port;
        bootStrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

    }

    @Override
    public ChannelFuture start() {

        try {

            bootStrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            setFutureChannel(bootStrap.bind(port).sync());

            //listen when server start
            getFutureChannel().addListener(future -> {
                if (future.isSuccess()) {
                    CustomLogger.log("host server started");
                } else {
                    CustomLogger.err("host server failed to start on port  " + port);
                }
            });

            return getFutureChannel();
        } catch (Exception e) {
            CustomLogger.err("host server (start) " + e.getMessage());
        }
        return null;
    }

    @Override
    public void close() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    @Override
    public void run() {
        try {
            start();
        } catch (Exception e) {
            CustomLogger.err("host server (run) " + e.getMessage());
            close();
        }
    }
}
