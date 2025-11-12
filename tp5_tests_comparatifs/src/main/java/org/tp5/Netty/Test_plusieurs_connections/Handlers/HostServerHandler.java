package org.tp5.Netty.Test_plusieurs_connections.Handlers;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.tp5.Netty.Test_plusieurs_connections.HostServer;
import org.tp5.Netty.Model.ClientInfo;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class HostServerHandler extends ChannelHandlerAdapter {
    private ClientInfo clientInfo;
    private int index;
    @Override
    public void channelRegistered(ChannelHandlerContext context) {
        InetSocketAddress address = (InetSocketAddress) context.channel().remoteAddress();

        String ip = address.getAddress().toString();
        int port = address.getPort();
        //add client to list
        clientInfo = new ClientInfo(ip,port);
        HostServer.list_clientInfo.add(clientInfo);
        HostServer.clientCounter.incrementAndGet();
    }

    @Override
    public void channelActive(ChannelHandlerContext context) {

        //System.out.println(PrintColors.ANSI_CYAN+"HostServerHandler Active"+PrintColors.ANSI_RESET);

        context.write("(server) : Welcome" + (HostServer.list_clientInfo.size()-1));


    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) {
        System.out.println(PrintColors.ANSI_CYAN+"(server) client déconnecté"+PrintColors.ANSI_RESET);

        HostServer.list_clientInfo.remove(clientInfo);
        ArrayList<ClientInfo> temp_list_clientInfo = new ArrayList<>();
        HostServer.clientCounter.decrementAndGet();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }

}
