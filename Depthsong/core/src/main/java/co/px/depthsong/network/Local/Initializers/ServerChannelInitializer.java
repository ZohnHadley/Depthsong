package co.px.depthsong.network.Local.Initializers;

import co.px.depthsong.network.Local.Handlers.ServerHandlers.LocalHostServerHandler;
import co.px.depthsong.network.Local.Handlers.ServerHandlers.HostServerEventHandler;
import co.px.depthsong.network.Local.decoder_incoder.CustomDecoder;
import co.px.depthsong.network.Local.decoder_incoder.CustomEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.*;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    //private final LocalHostServerHandler hostServerHandler;
    //private final HostServerEventHandler hostServerEventHandler;


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();


        pipeline.addLast(new CustomEncoder());
        pipeline.addLast(new CustomDecoder());
        //main handler
        pipeline.addLast(new HostServerEventHandler());
        pipeline.addLast(new LocalHostServerHandler());
        pipeline.addLast(new DelimiterBasedFrameDecoder(512, Delimiters.lineDelimiter()));

    }
}
