package org.example.Test_envoy_objet_complexe;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.Test_envoy_objet_complexe.Handlers.ClientServerHandler;
import org.example.Test_envoy_objet_complexe.Initializers.ClientChannelInitializer;
import org.example.Test_envoy_objet_complexe.Model.PlayerObj;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class ClientServer implements Runnable {

    protected String HOST_SERVER_IP;
    protected int HOST_SERVER_PORT;

    private EventLoopGroup WORK_GROUP;
    private Bootstrap CLIENT_BOOTSTRAP;

    private ClientServerHandler CLIENT_HANDLER;
    private ClientChannelInitializer CLIENT_INITIALIZER;

    private final int SECONDS_BEFORE_TIMEOUT;

    private String userName;

    public ClientServer(String hostServerIp, int hostServerPort, int sec_before_timeout) {
        HOST_SERVER_IP = hostServerIp;
        HOST_SERVER_PORT = hostServerPort;
        SECONDS_BEFORE_TIMEOUT = sec_before_timeout;
    }

    public ClientServer(String hostServerIp, int hostServerPort, String userName) {
        HOST_SERVER_IP = hostServerIp;
        HOST_SERVER_PORT = hostServerPort;
        SECONDS_BEFORE_TIMEOUT = 10;
        this.userName = userName;

    }

    public void start() throws Exception {
        try {
            WORK_GROUP = new NioEventLoopGroup();
            CLIENT_HANDLER = new ClientServerHandler(userName);
            CLIENT_INITIALIZER = new ClientChannelInitializer(SECONDS_BEFORE_TIMEOUT, CLIENT_HANDLER);

            CLIENT_BOOTSTRAP = new Bootstrap();
            CLIENT_BOOTSTRAP.group(WORK_GROUP);
            CLIENT_BOOTSTRAP.channel(NioSocketChannel.class);
            CLIENT_BOOTSTRAP.handler(CLIENT_INITIALIZER);
            CLIENT_BOOTSTRAP.option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channel_future = CLIENT_BOOTSTRAP.connect(HOST_SERVER_IP, HOST_SERVER_PORT).sync();
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

    public InetSocketAddress getLocalChannel() {
        return CLIENT_HANDLER.getLocalChannel();
    }

    //close
    public void close() throws InterruptedException {
        WORK_GROUP.shutdownGracefully();
    }

        //TODO for player entity add a empty param constructor (for when after connecting to server the player adds the information after when doing character creation)
    public static void main(String[] args) {
        /////////config
        String serverHost = "localhost";
        int serverPort = 8080;
        //////////////
        Scanner sc = new Scanner(System.in);
        String userName = sc.nextLine();

        ClientServer clientServer = new ClientServer(serverHost, serverPort, userName);
        Thread client_thread = new Thread(clientServer);
        client_thread.start();


    }

}
