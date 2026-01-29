package co.px.depthsong.engin.network.Local.Initializers;

import co.px.depthsong.engin.network.Local.Handlers.ServerHandlers.LocalHostServerHandler;
import co.px.depthsong.engin.network.Local.decoder_incoder.NioCustomDecoder;
import co.px.depthsong.engin.network.Local.decoder_incoder.NioCustomEncoder;
import co.px.depthsong.engin.network.ServerUtil;
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

        pipeline.addLast(new NioCustomEncoder());
        pipeline.addLast(new NioCustomDecoder());
        //main handler
        pipeline.addLast(new LocalHostServerHandler());
        pipeline.addLast(new DelimiterBasedFrameDecoder(512, Delimiters.lineDelimiter()));

    }
}
