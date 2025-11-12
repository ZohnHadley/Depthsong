package org.example.Test_envoy_objet_complexe.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class NetworkMessage {
    public enum MessageType {
        PLAYER_OBJECT,
        CURRENT_TURN_TIME_OBJECT,
        STRING,
        INT,
        FLOAT,
        DOUBLE,
        BYTE,
        CHAR,
        BOOLEAN
    }

    private @JsonProperty("type") MessageType type;

    private @JsonProperty("content") Object content;

    public NetworkMessage() {
    }

    public NetworkMessage(MessageType type, Object content) {
        this.type = type;
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }
    public Object getContent() {
        return content;
    }

    @JsonIgnore
    public int getContentByteSize() {
        return content.toString().getBytes().length;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    private static ObjectMapper mapper = new ObjectMapper();

    public String toJson() {
        //converts the object to a json string first
        try {
            ObjectWriter ow = mapper.writer();
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
