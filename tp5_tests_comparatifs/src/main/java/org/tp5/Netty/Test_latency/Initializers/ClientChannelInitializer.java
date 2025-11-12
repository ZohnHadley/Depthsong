package org.tp5.Netty.Test_latency.Initializers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.tp5.Netty.Test_latency.Handlers.ClientServerHandler;

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
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new IdleStateHandler(sec_before_timeout, 0, 0));
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        //main handler
        pipeline.addLast(clientServerHandler);

    }
}
