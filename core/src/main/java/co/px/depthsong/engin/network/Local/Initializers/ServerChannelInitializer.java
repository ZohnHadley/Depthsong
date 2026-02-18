package co.px.depthsong.engin.network.Local.Initializers;

import co.px.depthsong.engin.network.Local.Handlers.ServerHandlers.HostServerEventHandler;
import co.px.depthsong.engin.network.Local.Handlers.ServerHandlers.HostServerHandler;
import co.px.depthsong.engin.network.Local.decoder_incoder.NioCustomDecoder;
import co.px.depthsong.engin.network.Local.decoder_incoder.NioCustomEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    //private final LocalHostServerHandler hostServerHandler;
    //private final HostServerEventHandler hostServerEventHandler;


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new NioCustomDecoder());
        pipeline.addLast(new NioCustomEncoder());
        //main handler
        pipeline.addLast(new HostServerHandler());
        pipeline.addLast(new HostServerEventHandler());
//        pipeline.addLast(new DelimiterBasedFrameDecoder(512, Delimiters.lineDelimiter()));
    }
}
