package co.px.depthsong.network.Local.Initializers;

import co.px.depthsong.layers.managers.GameManager;
import co.px.depthsong.network.Local.Handlers.ClientHandlers.ClientServerEventHandler;
import co.px.depthsong.network.Local.Handlers.ClientHandlers.LocalClientServerHandler;
import co.px.depthsong.network.Local.decoder_incoder.CustomDecoder;
import co.px.depthsong.network.Local.decoder_incoder.CustomEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.*;
import io.netty.handler.timeout.IdleStateHandler;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {


    private final int sec_before_timeout;
    private final GameManager gameManager = GameManager.getInstance();

    public ClientChannelInitializer(int sec_before_timeout ) {
        this.sec_before_timeout = sec_before_timeout;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();


        pipeline.addLast(new CustomEncoder());
        pipeline.addLast(new CustomDecoder());
        pipeline.addLast(new IdleStateHandler(0, 0, sec_before_timeout));
        //main handler
        pipeline.addLast(new ClientServerEventHandler());
        pipeline.addLast(new LocalClientServerHandler());
        pipeline.addLast(new DelimiterBasedFrameDecoder(512, Delimiters.lineDelimiter()));
    }
}
