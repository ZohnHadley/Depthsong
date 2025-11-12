package co.px.depthsong.network.Local;

import co.px.depthsong.layers.managers.GameManager;
import co.px.depthsong.network.Local.Initializers.ClientChannelInitializer;
import co.px.depthsong.network.Local.Model.GameMasters.ClientServerGameMaster;
import co.px.depthsong.network.NetworkMachine;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientServer implements NetworkMachine {

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

    public void start() throws Exception {
        try {

            CLIENT_BOOTSTRAP.group(WORK_GROUP);
            CLIENT_BOOTSTRAP.channel(NioSocketChannel.class);
            CLIENT_BOOTSTRAP.handler(CLIENT_INITIALIZER);
            CLIENT_BOOTSTRAP.option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channel_future = CLIENT_BOOTSTRAP.connect(HOST_SERVER_IP, HOST_SERVER_PORT).sync();

            channel_future = channel_future.addListener(future -> {
                if (future.isSuccess()) {
                    clientServerRunning = true;
                } else {
                    clientServerRunning = false;
                }
            });

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


    //close
    public void close() throws InterruptedException {
        WORK_GROUP.shutdownGracefully();
    }

    @Override
    public boolean isRunning() {
        return clientServerRunning;
    }

}
