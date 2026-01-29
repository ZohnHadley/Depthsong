package co.px.depthsong.engin.network.Local.Model.ServerTracker;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.SocketAddress;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientConnectionContext {
    private String remoteAddress;
    private String localAddress;
}
