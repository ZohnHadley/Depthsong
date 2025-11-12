package org.example.Test_envoy_objet_complexe.decoder_incoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.example.Test_envoy_objet_complexe.Model.CurrentTurnTimeObject;
import org.example.Test_envoy_objet_complexe.Model.NetworkMessage;
import org.example.Test_envoy_objet_complexe.Model.PlayerObj;

import java.util.LinkedHashMap;
import java.util.List;

public class CustomDecoder extends ByteToMessageDecoder {


    //calls the decode() method with an internally maintained cumulative buffer (CUMULATIVE BUFFER IS BUILT IN)
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws JsonProcessingException {


        //TODO fix message decoding
        //get bytes from in
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        //create a new PlayerObj from the bytes

        String data = new String(bytes);
        NetworkMessage message = new ObjectMapper().readValue(data, NetworkMessage.class);
        decodeContent(message);

        out.add(message);
    }


    private void decodeContent(NetworkMessage message) {

        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) (message).getContent();

        if ((message).getType() == NetworkMessage.MessageType.PLAYER_OBJECT) {
            PlayerObj playerObj = new PlayerObj();
            playerObj.setClientServer_id(Long.valueOf(map.get("clientServer_id").toString()));
            playerObj.setUsername((String) map.get("username"));
            playerObj.setX((int) map.get("x"));
            playerObj.setY((int) map.get("y"));
            message.setContent(playerObj);
        }

        if ((message).getType() == NetworkMessage.MessageType.CURRENT_TURN_TIME_OBJECT) {
            CurrentTurnTimeObject currentTurnTimeObject = new CurrentTurnTimeObject();
            currentTurnTimeObject.setSeconds((int) map.get("seconds"));
            message.setContent(currentTurnTimeObject);
        }

        if ((message).getType() == NetworkMessage.MessageType.STRING) {
            message.setContent((String) message.getContent());
        }

        if ((message).getType() == NetworkMessage.MessageType.INT) {
            message.setContent((int) message.getContent());
        }

        if ((message).getType() == NetworkMessage.MessageType.FLOAT) {
            message.setContent((float) message.getContent());
        }

        if ((message).getType() == NetworkMessage.MessageType.DOUBLE) {
            message.setContent((double) message.getContent());
        }

        if ((message).getType() == NetworkMessage.MessageType.BYTE) {
            message.setContent((byte) message.getContent());
        }
    }
}
