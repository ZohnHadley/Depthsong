package co.px.depthsong.engin.network.Local.decoder_incoder;

import co.px.depthsong.engin.enginUtils.JsonUtil;
import co.px.depthsong.engin.network.Local.Model.ServerObjects.ServerObject;
import co.px.depthsong.engin.network.Local.Model.ServerObjects.ServerObjectClientConnectionContext;
import co.px.depthsong.engin.network.ServerUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.Objects;

public class NioCustomDecoder extends ByteToMessageDecoder {
    JsonUtil jsonUtil = JsonUtil.getInstance();

    //calls the decode() method with an internally maintained cumulative buffer (CUMULATIVE BUFFER IS BUILT IN)
    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) throws Exception {
        try {

            // 1️⃣ Wait for length field
            if (in.readableBytes() < 4) {
                return;
            }

            in.markReaderIndex();
            int length = in.readInt();

            // 2️⃣ Wait for full payload
            if (in.readableBytes() < length) {
                in.resetReaderIndex();
                return;
            }

            // 3️⃣ Read JSON bytes only
            byte[] jsonBytes = new byte[length];
            in.readBytes(jsonBytes);

            String json = new String(jsonBytes);

            ServerObject obj =
                JsonUtil.getInstance()
                    .getObjectMapper()
                    .treeToValue( jsonUtil.fromJson(json), ServerObject.class);

            if (obj instanceof ServerObjectClientConnectionContext) {
                ServerUtil.log(obj.toString());
            }

            // 4️⃣ Deserialize later once stable
//             out.add(jsonUtil.fromJson(json, jsonUtil.fromJson(json).get("type")));
        } catch (Exception e) {
            ServerUtil.err("NioCustomDecoder", e.getMessage());
        }

    }


}
