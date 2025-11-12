package org.tp5.Netty.Test_envoy_objet_complexe.Initializers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import org.tp5.Netty.Test_envoy_objet_complexe.Handlers.HostServerHandler;
import org.tp5.Netty.Test_envoy_objet_complexe.decoder_incoder.TimeEncoder;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private HostServerHandler hostServerHandler;
    public ServerChannelInitializer( HostServerHandler _hostServerHandler) {
        hostServerHandler = _hostServerHandler;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new TimeEncoder());
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        //main handler
        pipeline.addLast(hostServerHandler);
    }
}
