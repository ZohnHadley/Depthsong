package co.px.depthsong.engin.network.Local;

import co.px.depthsong.engin.network.Local.Initializers.ClientChannelInitializer;
import co.px.depthsong.engin.network.Local.Model.GameMasters.ClientServerManager;
import co.px.depthsong.engin.network.NetworkMachine;
import co.px.depthsong.engin.network.CustomLogger;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;

import java.util.concurrent.CompletableFuture;

public class ClientServer extends NetworkMachine {

    public static final ClientServerManager clientServerManager = ClientServerManager.getInstance();

    protected String hostServerIp;
    protected int hostServerPort;

    private Bootstrap clientBootStrap;
    private EventLoopGroup workGroup;


    public ClientServer() {

    }

    /**
     * Configure the connection target and prepare Netty objects.
     * This does NOT connect; call start() to actually connect.
     */
    public void setConnection(String hostServerIp, int hostServerPort) {
        this.hostServerIp = hostServerIp;
        this.hostServerPort = hostServerPort;

        if (clientBootStrap == null) {
            clientBootStrap = new Bootstrap();
        }
        if (workGroup == null || workGroup.isShuttingDown() || workGroup.isShutdown()) {
            workGroup = new NioEventLoopGroup();
        }
    }

    @Override
    public ChannelFuture start() {
        int SECONDS_BEFORE_TIMEOUT = 45;
        if (hostServerIp == null || hostServerIp.isBlank()) {
            throw new IllegalStateException("ClientServer hostServerIp is not set. Call setConnection(ip, port) first.");
        }
        if (hostServerPort <= 0) {
            throw new IllegalStateException("ClientServer hostServerPort is not set/invalid. Call setConnection(ip, port) first.");
        }
        if (clientBootStrap == null || workGroup == null) {
            throw new IllegalStateException("ClientServer is not initialized. Call setConnection(ip, port) first.");
        }

        clientBootStrap.group(workGroup)
            .channel(NioSocketChannel.class)
            .handler(new ClientChannelInitializer(SECONDS_BEFORE_TIMEOUT))
            .option(ChannelOption.SO_KEEPALIVE, true);

        // Connect asynchronously; don't .sync() here unless you truly want to block the calling thread.
        ChannelFuture connectFuture = clientBootStrap.connect(hostServerIp, hostServerPort);
        setFutureChannel(connectFuture);

        connectFuture.addListener(future -> {
            if (future.isSuccess()) {
                CustomLogger.log("client server connection success");
            } else {
                CustomLogger.err("client server has failed to connect: " + future.cause());
            }
        });

        return connectFuture;
    }


    @Override
    public void close() {
        // Make close safe to call even if start() never happened or failed.
        try {
            ChannelFuture f = getFutureChannel();
            if (f != null) {
                if (f.channel() != null) {
                    f.channel().close();
                }
                setFutureChannel(null);
            }
        } catch (Throwable t) {
            CustomLogger.err("client server close error: " + t);
        } finally {
            if (workGroup != null) {
                workGroup.shutdownGracefully();
                workGroup = null;
            }
        }
    }


}
