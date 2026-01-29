package co.px.depthsong.engin.network.Local;

import co.px.depthsong.engin.network.Local.Initializers.ClientChannelInitializer;
import co.px.depthsong.engin.network.Local.Model.GameMasters.ClientServerManager;
import co.px.depthsong.engin.network.NetworkMachine;
import co.px.depthsong.engin.network.ServerUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientServer extends NetworkMachine {

    public static final ClientServerManager CLIENT_SERVER_MANAGER = ClientServerManager.getInstance();

    protected String hostServerIp;
    protected int hostServerPort;

    private Bootstrap clientBootStrap;
    private EventLoopGroup workGroup;


    public ClientServer(String hostServerIp, int hostServerPort) {
        this.hostServerIp = hostServerIp;
        this.hostServerPort = hostServerPort;

        clientBootStrap = new Bootstrap();
        workGroup = new NioEventLoopGroup();
    }

    @Override
    public ChannelFuture start() throws Exception {
        int SECONDS_BEFORE_TIMEOUT = 45;

        try {
            ServerUtil.log("client server started");

            clientBootStrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer(SECONDS_BEFORE_TIMEOUT))
                .option(ChannelOption.SO_KEEPALIVE, true);

            setChannel_future(clientBootStrap.connect(hostServerIp, hostServerPort).sync());
            //listen when server start

            getChannel_future().addListener(future -> {
                if (future.isSuccess()) {

                    ServerUtil.log("client server has started");
                } else {
                    ServerUtil.err("client server has failed to start");
                }
            });
            setIsRunning(true);
            return getChannel_future();
        }
        catch (Exception e){
            ServerUtil.err("client server " + e.getMessage());
        }
        return null;
    }


    @Override
    public void close() {
        if(getChannel_future() != null)
            getChannel_future().channel().closeFuture();
        workGroup.shutdownGracefully();
        setIsRunning(false);


    }


}
