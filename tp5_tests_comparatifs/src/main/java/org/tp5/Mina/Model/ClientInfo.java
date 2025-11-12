package org.tp5.Mina.Model;

public class ClientInfo {
    protected int connectionTime = -1;
    protected String clientName = null;
    protected final String IP;
    protected final int PORT;

    protected boolean isConnectedToServer = false;

    public ClientInfo(String name, String _IP, int _PORT){
        this.clientName = name;
        this.IP = _IP;
        this.PORT = _PORT;
    }

    public ClientInfo(String IP, int PORT){
        this.clientName = "unknown";
        this.IP = IP;
        this.PORT = PORT;
    }

    public String getClientName(){
        return this.clientName;
    }

    public String getIP(){
        return this.IP;
    }

    public int getPORT(){
        return this.PORT;
    }

    public boolean isConnectedToServer(){
        return this.isConnectedToServer;
    }

    public int getConnectionTime(){
        return this.connectionTime;
    }

    public void setConnectedToServer(boolean isConnected){
        this.isConnectedToServer = isConnected;
    }

    public void setConnectionTime(int connectionTime){
        this.connectionTime = connectionTime;
    }
}
