package org.tp5.Netty.Test_taille_de_paquets.Initializers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import org.tp5.Netty.Test_taille_de_paquets.Encodage_Decpdage.CustomMessageEncoder;
import org.tp5.Netty.Test_taille_de_paquets.Handlers.ClientServerHandler;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private ClientServerHandler clientServerHandler;
    private final int sec_before_timeout;

    public ClientChannelInitializer(int sec_before_timeout, ClientServerHandler clientServerHandler) {
        this.sec_before_timeout = sec_before_timeout;
        this.clientServerHandler = clientServerHandler;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(new CustomMessageEncoder());
        /*pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new IdleStateHandler(sec_before_timeout, 0, 0));*/
        //main handler
        pipeline.addLast(clientServerHandler);

    }
}
