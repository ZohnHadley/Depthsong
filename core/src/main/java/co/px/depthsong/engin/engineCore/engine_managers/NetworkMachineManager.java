package co.px.depthsong.engin.engineCore.engine_managers;

import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumNetworkClientConnectionStates;
import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumNetworkState;
import co.px.depthsong.engin.network.Local.ClientServer;
import co.px.depthsong.engin.network.Local.HostServer;
import co.px.depthsong.engin.network.NetworkMachine;
import co.px.depthsong.engin.network.CustomLogger;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class NetworkMachineManager {

    private static NetworkMachineManager instance;

    private ClientServer clientServer;
    private HostServer hostServer;
    private EventLoopGroup workGroup = new NioEventLoopGroup();


    private EnumNetworkState networkState = EnumNetworkState.OFFLINE;
    private EnumNetworkClientConnectionStates connectionState;


    public static NetworkMachineManager getInstance() {
        if (instance == null) {
            instance = new NetworkMachineManager();
        }
        return instance;
    }

    public void setCurrentConnectedState(EnumNetworkClientConnectionStates state) {
        connectionState = state;
    }

    public void hostServerStart(int port) {
        try {
            this.setHostServer(new HostServer(port));
            Thread.startVirtualThread(this.getHostServer());
        } catch (Throwable throwable) {
            printLogError("Error NetworkMachines (CLIENT SERVER) : \n[cause] \n" + throwable.getCause() + " \n[suppressed] \n" + Arrays.toString(throwable.getSuppressed()) + " \n[message] \n" + throwable.getMessage());
        }
    }

    public void clientServerInit() {
        try {

            this.setClientServer(new ClientServer());

        } catch (Throwable throwable) {
            printLogError("Error NetworkMachines (CLIENT SERVER) : \n[cause] \n" + throwable.getCause() + " \n[suppressed] \n" + Arrays.toString(throwable.getSuppressed()) + " \n[message] \n" + throwable.getMessage());
        }
    }

    public void clientServerConnect(String ip, int port) {

        try {
            this.getClientServer().setConnection(ip, port);
            this.getClientServer().start();
            this.setConnectionState(EnumNetworkClientConnectionStates.CONNECTED);

        } catch (Throwable throwable) {
            printLogError("Error NetworkMachines (CLIENT SERVER) : \n[cause] \n" + throwable.getCause() + " \n[suppressed] \n" + Arrays.toString(throwable.getSuppressed()) + " \n[message] \n" + throwable.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (getClientServer() != null) {
                getClientServer().close();
            }
            if (getHostServer() != null) {
                getHostServer().close();
            }

            setCurrentConnectedState(EnumNetworkClientConnectionStates.DISCONNECTED);

        } catch (Throwable throwable) {
            printLogError("Error NetworkMachines (CLIENT SERVER) : \n[cause] \n" + throwable.getCause() + " \n[suppressed] \n" + Arrays.toString(throwable.getSuppressed()) + " \n[message] \n" + throwable.getMessage());
        }
    }

    private void printLogError(String message) {
        CustomLogger.err(message);
    }
    public void dispose() {
    }
}
