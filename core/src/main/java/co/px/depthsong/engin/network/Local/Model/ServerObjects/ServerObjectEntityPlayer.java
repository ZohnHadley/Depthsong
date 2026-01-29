package co.px.depthsong.engin.network.Local.Model.ServerObjects;
import co.px.depthsong.engin.enginUtils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")

@NoArgsConstructor
@Getter
@Setter
public class ServerObjectEntityPlayer extends ServerObject {

    private @JsonProperty("clientServerID") Long clientServerID = -1L;

    public ServerObjectEntityPlayer(String localChannelAddress, Long serverID){
        this.clientServerID = serverID;
    }

}
