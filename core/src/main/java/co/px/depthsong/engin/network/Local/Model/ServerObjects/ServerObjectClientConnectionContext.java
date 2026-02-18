package co.px.depthsong.engin.network.Local.Model.ServerObjects;

import co.px.depthsong.engin.enginUtils.JsonUtil;
import co.px.depthsong.engin.network.Local.Model.ServerTracker.ClientConnectionContext;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.*;

import java.net.SocketAddress;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ServerObjectClientConnectionContext extends ServerObject {

    private String remoteAddress;
    private String localAddress;
    private Boolean isConnected;

    public static ServerObjectClientConnectionContext toServerObject(ClientConnectionContext object){
        return new ServerObjectClientConnectionContext(object.getRemoteAddress(), object.getLocalAddress(), object.getIsConnected());
    }

    public ClientConnectionContext toObject(){
        return new ClientConnectionContext(this.remoteAddress, this.localAddress, this.isConnected);
    }
}
