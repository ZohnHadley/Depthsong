package co.px.depthsong.engin.engineCore.engine_managers;

import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumNetworkClientConnectionStates;
import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumNetworkState;
import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumNetworkTitle;
import co.px.depthsong.engin.network.Local.ClientServer;
import co.px.depthsong.engin.network.Local.HostServer;
import co.px.depthsong.engin.network.NetworkMachine;
import co.px.depthsong.engin.network.ServerUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NetworkMachineManager {
    private static NetworkMachineManager instance;

    private EnumNetworkTitle currentNetworkTitle;
    private NetworkMachine clientServer;
    private Thread clientServerThread;

    private NetworkMachine hostServer;
    private Thread hostServerThread;

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

    public void startHostServer(int port){
        this.setHostServer(new HostServer(port));
        this.setHostServerThread(Thread.startVirtualThread(this.hostServer));
    }

    public void startClientServer(String serverIP, int port){
        this.clientServer = new ClientServer(serverIP, port);
        this.clientServerThread = Thread.startVirtualThread(this.clientServer);
    }

    public void disconnect(){
        try {
            getClientServer().close();
            clientServerThread.interrupt();
            setClientServer(null);
            if (getHostServer() != null) {

                getHostServer().close();
                hostServerThread.interrupt();
                setHostServer(null);
            }

            setCurrentConnectedState(EnumNetworkClientConnectionStates.DISCONNECTED);

        } catch (Exception e) {
            printLogError("Error closing network server");
        }
    }

    private void printLogError(String message) {
        ServerUtil.err(message);
    }

    private void printLog(String message) {
        ServerUtil.log(message);
    }
}
