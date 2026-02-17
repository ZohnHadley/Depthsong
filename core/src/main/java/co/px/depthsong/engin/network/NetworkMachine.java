package co.px.depthsong.engin.network;

import io.netty.channel.ChannelFuture;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NetworkMachine {


    private ChannelFuture futureChannel;

    public NetworkMachine(){
    }

    public ChannelFuture start() {
        return null;
    }

    public void close() throws Exception {

    }
}
