package org.tp5.Netty.Test_taille_de_paquets.Initializers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.tp5.Netty.Test_taille_de_paquets.Handlers.HostServerHandler;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    public ServerChannelInitializer() {
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
       // pipeline.addLast(new IdleStateHandler(0, time_send_delay, 0));
        //main handler
        pipeline.addLast(new HostServerHandler());
    }
}
