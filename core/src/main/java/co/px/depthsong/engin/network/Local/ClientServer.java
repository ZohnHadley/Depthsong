package co.px.depthsong.engin.network.Local;

import co.px.depthsong.engin.engineCore.engine_managers.GameManager;
import co.px.depthsong.engin.network.Local.Initializers.ClientChannelInitializer;
import co.px.depthsong.engin.network.Local.Model.GameMasters.ClientServerGameMaster;
import co.px.depthsong.engin.network.NetworkMachine;
import co.px.depthsong.engin.network.ServerUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientServer extends NetworkMachine {

    private final GameManager gameManager;

    {
        gameManager = GameManager.getInstance();
    }

    public static final ClientServerGameMaster clientServerGameMaster = ClientServerGameMaster.getInstance();

    protected String HOST_SERVER_IP;
    protected int HOST_SERVER_PORT;

    private EventLoopGroup WORK_GROUP;
    private Bootstrap CLIENT_BOOTSTRAP;

    private ClientChannelInitializer CLIENT_INITIALIZER;

    private final int SECONDS_BEFORE_TIMEOUT;

    private boolean clientServerRunning = false;

    public ClientServer(String hostServerIp, int hostServerPort, int sec_before_timeout) {
        HOST_SERVER_IP = hostServerIp;
        HOST_SERVER_PORT = hostServerPort;
        SECONDS_BEFORE_TIMEOUT = sec_before_timeout;

        WORK_GROUP = new NioEventLoopGroup();
        CLIENT_INITIALIZER = new ClientChannelInitializer(SECONDS_BEFORE_TIMEOUT);

        CLIENT_BOOTSTRAP = new Bootstrap();
    }

    public ClientServer(String hostServerIp, int hostServerPort) {
        HOST_SERVER_IP = hostServerIp;
        HOST_SERVER_PORT = hostServerPort;
        SECONDS_BEFORE_TIMEOUT = 45;

        WORK_GROUP = new NioEventLoopGroup();
        CLIENT_INITIALIZER = new ClientChannelInitializer(SECONDS_BEFORE_TIMEOUT);

        CLIENT_BOOTSTRAP = new Bootstrap();

    }

    @Override
    public void start() throws Exception {
        try {
            ServerUtil.log("client server started");

            CLIENT_BOOTSTRAP.group(WORK_GROUP);
            CLIENT_BOOTSTRAP.channel(NioSocketChannel.class);
            CLIENT_BOOTSTRAP.handler(CLIENT_INITIALIZER);
            CLIENT_BOOTSTRAP.option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channel_future = CLIENT_BOOTSTRAP.connect(HOST_SERVER_IP, HOST_SERVER_PORT).sync();
            //listen when server start

            channel_future = channel_future.addListener(future -> {
                if (future.isSuccess()) {
                    ServerUtil.log("client server has started");
                } else {
                    ServerUtil.err("client server has failed to start");
                    //TODO : remove if retry isn't working on client server IDK
                    close();
                }
            });

            channel_future.channel().closeFuture().sync();
        } finally {
            close();
        }
    }

    @Override
    public boolean isRunning() {
        return clientServerRunning;
    }

    @Override
    public void close() throws InterruptedException {
        WORK_GROUP.shutdownGracefully();
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
