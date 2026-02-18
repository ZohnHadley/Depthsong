package co.px.depthsong.engin.network.Local.decoder_incoder;

import co.px.depthsong.engin.enginUtils.JsonUtil;
import co.px.depthsong.engin.network.CustomLogger;
import co.px.depthsong.engin.network.Local.Model.ServerObjects.ServerObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import static co.px.depthsong.engin.network.PrintColors.ANSI_CYAN;
import static co.px.depthsong.engin.network.PrintColors.ANSI_PURPLE;

public class NioCustomEncoder extends MessageToByteEncoder<ServerObject> {

    JsonUtil jsonUtil = JsonUtil.getInstance();

    @Override
    public void encode(ChannelHandlerContext context, ServerObject serverObject, ByteBuf out) throws Exception {

        byte[] jsonBytes = jsonUtil.toJson(serverObject).getBytes();
        CustomLogger.log(ANSI_PURPLE,"NioCustomEncoder", "" + serverObject);
        out.writeInt(jsonBytes.length); // length prefix
        out.writeBytes(jsonBytes);      // payload
    }
}
