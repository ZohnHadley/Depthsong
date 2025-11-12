package org.tp5.Netty.Test_envoy_objet_complexe.decoder_incoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.tp5.Netty.Model.UnixTime;

import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder {
    //calls the decode() method with an internally maintained cumulative buffer (CUMULATIVE BUFFER IS BUILT IN)
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 4) {
            return;
        }
        out.add(new UnixTime(in.readUnsignedInt()));
        /*out.writeInt((int)msg.value()); //simplefied version */
    }
}
