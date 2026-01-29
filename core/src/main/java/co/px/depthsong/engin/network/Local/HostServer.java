package co.px.depthsong.engin.network.Local;


import co.px.depthsong.engin.network.Local.Model.GameMasters.HostServerMaster;
import co.px.depthsong.engin.network.NetworkMachine;
import co.px.depthsong.engin.network.ServerUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ScheduledFuture;
import co.px.depthsong.engin.network.Local.Initializers.ServerChannelInitializer;
import lombok.Getter;

@Getter
public class HostServer extends NetworkMachine {
    public static ScheduledFuture future_timer;

    private HostServerMaster HOST_SERVER_MASTER = HostServerMaster.getInstance();
    private  ServerBootstrap bootStrap;

    /////vars for server config
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
    public ChannelFuture start() throws InterruptedException {

        try {

            bootStrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler( new ServerChannelInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            setChannel_future(bootStrap.bind(port).sync());

            //listen when server start
            getChannel_future().addListener(future -> {
                if (future.isSuccess()) {
                    ServerUtil.log("host server started");
                } else {
                    ServerUtil.err("host server failed to start on port  " + port);
                }
            });

            return getChannel_future();
        }
        catch (Exception e){
            ServerUtil.err("host server (start) " + e.getMessage());
        }
        return null;
    }

    @Override
    public void close(){
        getChannel_future().channel().close();
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
