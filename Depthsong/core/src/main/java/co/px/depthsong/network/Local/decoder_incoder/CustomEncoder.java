package co.px.depthsong.network.Local.decoder_incoder;

import co.px.depthsong.network.Local.Model.NetworkMessage;
import co.px.depthsong.network.Local.Model.PlayerObj;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.InetSocketAddress;

public class CustomEncoder extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext context, Object msg, ChannelPromise promise) {
        if (!(msg instanceof NetworkMessage)) {
            System.err.println("Unsupported message type: " + msg.getClass());
            promise.setFailure(new IllegalArgumentException("Unsupported message type"));
            return;
        }

        try {
            NetworkMessage message = (NetworkMessage) msg;
            encodeContent(context, message, promise);
        } catch (Exception e) {
            System.err.println("Encoding failed: " + e.getMessage());
            promise.setFailure(e);
        }
    }

    private void encodeContent(ChannelHandlerContext context, NetworkMessage message, ChannelPromise promise) {
        InetSocketAddress senderAddressInfo = (InetSocketAddress) context.channel().localAddress();
        String senderAddress = senderAddressInfo.getAddress() + ":" + senderAddressInfo.getPort();

        if (message.getType() == NetworkMessage.MessageType.PLAYER_OBJECT) {
            PlayerObj playerObj = (PlayerObj) message.getContent();
            if ("N/A".equals(playerObj.getLocalChannelAddress())) {
                playerObj.setLocalChannelAddress(senderAddress);
            }
            message.setContent(playerObj);
        }

        String json = message.toJson();
        byte[] jsonBytes = json.getBytes();


        ByteBuf encoded = context.alloc().buffer( jsonBytes.length);
        encoded.writeBytes(jsonBytes);

        // Write and flush to the channel
        context.write(encoded, promise);
    }
}
