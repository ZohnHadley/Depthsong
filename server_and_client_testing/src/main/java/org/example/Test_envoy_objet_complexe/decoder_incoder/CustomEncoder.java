package org.example.Test_envoy_objet_complexe.decoder_incoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.example.Test_envoy_objet_complexe.Model.NetworkMessage;
import org.example.Test_envoy_objet_complexe.Model.PlayerObj;

public class CustomEncoder extends ChannelOutboundHandlerAdapter {


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        NetworkMessage message = (NetworkMessage) msg;
        String json = message.toJson();
        ByteBuf encoded = ctx.alloc().buffer(json.getBytes().length);

        encoded.writeBytes(json.getBytes());
        ctx.write(encoded, promise);
    }
}
