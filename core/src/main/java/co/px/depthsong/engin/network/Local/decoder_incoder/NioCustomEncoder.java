package co.px.depthsong.engin.network.Local.decoder_incoder;

import co.px.depthsong.engin.enginUtils.JsonUtil;
import co.px.depthsong.engin.network.Local.Model.ServerObjects.ServerObject;
import co.px.depthsong.engin.network.Local.Model.ServerObjects.ServerObjectClientConnectionContext;
import co.px.depthsong.engin.network.PrintColors;
import co.px.depthsong.engin.network.ServerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.Arrays;

public class NioCustomEncoder extends MessageToByteEncoder<ServerObject> {

    JsonUtil jsonUtil = JsonUtil.getInstance();

    @Override
    public void encode(ChannelHandlerContext context, ServerObject serverObject, ByteBuf out) throws Exception {

        ServerUtil.log(serverObject.toString());
        byte[] jsonBytes = jsonUtil.toJson(serverObject).getBytes();
        out.writeInt(jsonBytes.length); // length prefix
        out.writeBytes(jsonBytes);      // payload
    }
}
