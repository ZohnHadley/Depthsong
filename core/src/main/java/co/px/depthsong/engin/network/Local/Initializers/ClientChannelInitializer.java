package co.px.depthsong.engin.network.Local.Initializers;

import co.px.depthsong.engin.network.Local.Handlers.ClientHandlers.ClientServerEventHandler;
import co.px.depthsong.engin.network.Local.Handlers.ClientHandlers.ClientServerHandler;
import co.px.depthsong.engin.network.Local.decoder_incoder.NioCustomDecoder;
import co.px.depthsong.engin.network.Local.decoder_incoder.NioCustomEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.*;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {


    private int sec_before_timeout;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();


        pipeline.addLast(new NioCustomDecoder());
        pipeline.addLast(new NioCustomEncoder());
        pipeline.addLast(new IdleStateHandler(0, 0, sec_before_timeout));
        //main handler
        pipeline.addLast(new ClientServerHandler());
        pipeline.addLast(new ClientServerEventHandler());
//        pipeline.addLast(new DelimiterBasedFrameDecoder(512, Delimiters.lineDelimiter()));
    }
}
