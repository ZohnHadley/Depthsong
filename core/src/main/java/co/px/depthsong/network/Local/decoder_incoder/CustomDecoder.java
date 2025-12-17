package co.px.depthsong.network.Local.decoder_incoder;

import co.px.depthsong.network.Local.Model.CurrentTurnTimeObject;
import co.px.depthsong.network.Local.Model.NetworkMessage;
import co.px.depthsong.network.Local.Model.PlayerObj;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CustomDecoder extends ByteToMessageDecoder {


    //calls the decode() method with an internally maintained cumulative buffer (CUMULATIVE BUFFER IS BUILT IN)
    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 4) {
            return; // Not enough data to process
        }

        byte[] byteArray = new byte[in.readableBytes()];
        in.readBytes(byteArray);
        String data = new String(byteArray ) ;


        try {
            JsonNode nodeTree = NetworkMessage.mapper.readTree(data);
            System.out.println("nodeTree: " + nodeTree);
            if (nodeTree == null || !nodeTree.has("type") || !nodeTree.has("content")) {
                System.err.println("Invalid message structure");
                return;
            }
            decodeContent(context, nodeTree, out);

        } catch (JsonProcessingException e) {
            System.err.println("Failed to decode message: " + e.getMessage());
            context.close(); // Optionally close the connection on error
        }
    }

    private final Map<String, Function<JsonNode, NetworkMessage>> messageHandlers = Map.of(
        "PLAYER_OBJECT", this::decodePlayerObject,
        "CURRENT_TURN_TIME_OBJECT", this::decodeTurnTimeObject
    );



    private NetworkMessage decodePlayerObject(JsonNode content) {
        PlayerObj playerObj = new PlayerObj();
        playerObj.setClientServer_id(content.get("clientServer_id").asInt());
        playerObj.setUsername(content.get("username").asText());
        playerObj.setX(content.get("x").asInt());
        playerObj.setY(content.get("y").asInt());
        playerObj.setSpriteKey(content.get("spriteKey").asText());
        if (content.has("localChannelAddress")) {
            playerObj.setLocalChannelAddress(content.get("localChannelAddress").asText());
        }
        playerObj.setHasServerID(content.get("hasServerID").asBoolean());

        return new NetworkMessage(NetworkMessage.MessageType.PLAYER_OBJECT, playerObj);
    }

    private NetworkMessage decodeTurnTimeObject(JsonNode content) {
        CurrentTurnTimeObject turnTime = new CurrentTurnTimeObject(content.get("seconds").asInt());
        return new NetworkMessage(NetworkMessage.MessageType.CURRENT_TURN_TIME_OBJECT, turnTime);
    }

    private void decodeContent(ChannelHandlerContext context, JsonNode nodeTree, List<Object> out) {
        String type = nodeTree.get("type").asText();
        JsonNode content = nodeTree.get("content");


        Function<JsonNode, NetworkMessage> handler = messageHandlers.get(type);
        if (handler != null) {
            out.add(handler.apply(content));


        } else {
            System.err.println("Unknown message type: " + type);
            System.out.println("content: " + content);
        }
    }



}
