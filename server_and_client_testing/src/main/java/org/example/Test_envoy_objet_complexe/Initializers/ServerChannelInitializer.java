package org.example.Test_envoy_objet_complexe.Initializers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.example.Test_envoy_objet_complexe.Handlers.HostServerHandler;
import org.example.Test_envoy_objet_complexe.decoder_incoder.CustomDecoder;
import org.example.Test_envoy_objet_complexe.decoder_incoder.CustomEncoder;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final HostServerHandler hostServerHandler;

    public ServerChannelInitializer(HostServerHandler _hostServerHandler) {
        hostServerHandler = _hostServerHandler;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new CustomDecoder());
        pipeline.addLast(new CustomEncoder());

        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        //main handler
        pipeline.addLast(hostServerHandler);
    }
}
