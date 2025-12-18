package co.px.depthsong.layers.engine_managers;

import co.px.depthsong.layers.engine_managers.enums.EnumNetworkClientConnectionStates;
import co.px.depthsong.network.NetworkMachine;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NetworkManager {
    private static NetworkManager instance;

    private NetworkMachine clientServer;
    private Thread clientServerThread;
    private NetworkMachine hostServer;
    private Thread hostServerThread;



    private EnumNetworkClientConnectionStates connectionState;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public void setCurrentConnectedState(EnumNetworkClientConnectionStates state) {
        connectionState = state;
    }

    public void connect(){}

    public void disconnect(){
        try {
            getClientServer().close();
            //clientServerThread.interrupt();
            setClientServer(null);
            if (getHostServer() != null) {

                getHostServer().close();
                //hostServerThread.interrupt();
                setHostServer(null);
            }

            setCurrentConnectedState(EnumNetworkClientConnectionStates.DISCONNECTED);

        } catch (Exception e) {
            printLogError("Error closing network server");
        }
    }

    private void printLogError(String message) {
        System.err.println(message);
    }

    private void printLog(String message) {
        System.out.println(message);
    }
}
