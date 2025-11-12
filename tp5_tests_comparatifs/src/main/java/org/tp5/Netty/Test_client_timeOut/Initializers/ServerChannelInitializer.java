package org.tp5.Netty.Test_client_timeOut.Initializers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.tp5.Netty.Test_client_timeOut.Handlers.HostServerHandler;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private int time_send_delay;

    public ServerChannelInitializer(int time_send_delay) {
        this.time_send_delay = time_send_delay;
    }
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(new IdleStateHandler(0, time_send_delay, 0));
        //main handler
        pipeline.addLast(new HostServerHandler());
    }
}
