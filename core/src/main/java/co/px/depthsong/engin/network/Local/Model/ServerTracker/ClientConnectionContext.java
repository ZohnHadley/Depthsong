package co.px.depthsong.engin.network.Local.Model.ServerTracker;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.*;

import java.net.SocketAddress;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientConnectionContext {
    private String remoteAddress;
    private String localAddress;
    private Boolean isConnected;

    @Builder
    public ClientConnectionContext(String remoteAddress, String localAddress){
        this.remoteAddress = remoteAddress;
        this.localAddress = localAddress;
        this.isConnected = false;
    }
}
