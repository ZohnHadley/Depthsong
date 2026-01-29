package co.px.depthsong.engin.network;

import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class NetworkMachine {


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean isRunning = false;

    private ChannelFuture channel_future;

    public NetworkMachine(){
    }

    public abstract ChannelFuture start() throws Exception;

    public void close() throws Exception {

    }

    public boolean getIsRunning() {
        try
        {
            if(channel_future.sync() != null){
                isRunning = true;
            }
        }
        catch (Exception e){
            ServerUtil.err(e.getMessage());
            isRunning = false;
        }
        return isRunning;
    }

    public void setIsRunning(boolean val){
        try
        {
            if(channel_future.sync() != null){
                isRunning = val;
            }
        }
        catch (Exception e){
            ServerUtil.err(e.getMessage());
        }
    }
}
