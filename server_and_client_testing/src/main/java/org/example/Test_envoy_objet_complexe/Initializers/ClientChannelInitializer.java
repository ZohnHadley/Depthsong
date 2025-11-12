package org.example.Test_envoy_objet_complexe.Initializers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.example.Test_envoy_objet_complexe.Handlers.ClientServerHandler;
import org.example.Test_envoy_objet_complexe.decoder_incoder.CustomDecoder;
import org.example.Test_envoy_objet_complexe.decoder_incoder.CustomEncoder;

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

        pipeline.addLast(new CustomDecoder());
        pipeline.addLast(new CustomEncoder());

        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        //pipeline.addLast(new IdleStateHandler(sec_before_timeout, 0, 0));
        //main handler
        pipeline.addLast(clientServerHandler);

    }
}
