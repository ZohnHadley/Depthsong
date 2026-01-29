package co.px.depthsong.engin.network.Local.Model.ServerObjects;

import co.px.depthsong.engin.enginUtils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")

@JsonSubTypes({
    @JsonSubTypes.Type(value = ServerObjectEntityPlayer.class, name = "ServerObjectEntityPlayer"),
    @JsonSubTypes.Type(value = ServerObjectClientConnectionContext.class, name = "ServerObjectClientConnectionContext"),
})

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ServerObject {

    private String jsonData = "";

    public  static JsonNode toNodeTree(ServerObject serverObject){
        return  JsonUtil.getInstance().toJsonNode(serverObject);
    }

    public static ServerObject fromNode(JsonNode node) {
        try {
            return JsonUtil.getInstance()
                .getObjectMapper()
                .treeToValue(node, ServerObject.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerObject toServerObject(Object object) {
        return null;
    }


}
