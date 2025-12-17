package co.px.depthsong.layers.managers;

import co.px.depthsong.network.NetworkMachine;

public class NetworkManager {
    private static NetworkManager instance;
    public static enum connection_states {
        CONNECTED,
        OFFLINE
    }

    private NetworkMachine clientServer;
    private Thread clientServerThread;
    private NetworkMachine hostServer;
    private Thread hostServerThread;



    private connection_states connection_state;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }



    public NetworkMachine getClientServer() {
        return clientServer;
    }

    public Thread getClientServerThread() {
        return clientServerThread;
    }

    public NetworkMachine getHostServer() {
        return hostServer;
    }

    public Thread getHostServerThread() {
        return hostServerThread;
    }

    public connection_states getConnectionState() {
        return connection_state;
    }


    public void setClientServer(NetworkMachine clientServer) {
        this.clientServer = clientServer;
    }

    public void setClientServerThread(Thread clientServerThread) {
        this.clientServerThread = clientServerThread;
    }

    public void setHostServer(NetworkMachine hostServer) {
        this.hostServer = hostServer;
    }

    public void setHostServerThread(Thread hostServerThread) {
        this.hostServerThread = hostServerThread;
    }
    public void setCurrentConnectedState(connection_states state) {
        connection_state = state;
    }

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

            setCurrentConnectedState(NetworkManager.connection_states.OFFLINE);

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
