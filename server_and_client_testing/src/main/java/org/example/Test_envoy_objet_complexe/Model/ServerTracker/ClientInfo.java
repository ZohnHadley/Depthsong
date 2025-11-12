package org.example.Test_envoy_objet_complexe.Model.ServerTracker;

import io.netty.channel.Channel;
import org.example.Test_envoy_objet_complexe.Model.PlayerObj;

public class ClientInfo {

    protected String clientName = null;


    protected final String IP;
    protected final int PORT;
    protected Channel channel = null;
    protected boolean isConnectedToServer = false;


    protected boolean isItThisClientTurn = false;
    protected PlayerObj playerObj = null;


    //////CONSTRUCTORS
    public ClientInfo(String name, String _IP, int _PORT) {
        this.clientName = name;
        this.IP = _IP;
        this.PORT = _PORT;
    }


    public ClientInfo(String IP, int PORT) {
        this.clientName = "unknown";
        this.IP = IP;
        this.PORT = PORT;
    }

    public ClientInfo(String name, String _IP, int _PORT, Channel channel) {
        this.clientName = name;
        this.IP = _IP;
        this.PORT = _PORT;
        this.channel = channel;
    }

    public ClientInfo(String IP, int PORT, Channel channel) {
        this.clientName = "unknown";
        this.IP = IP;
        this.PORT = PORT;
        this.channel = channel;
    }
    ////////////////


    ///////GETTERS
    public String getClientName() {
        return this.clientName;
    }

    public String getIP() {
        return this.IP;
    }

    public int getPORT() {
        return this.PORT;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public boolean isItThisClientTurn() {
        return this.isItThisClientTurn;
    }

    public PlayerObj getPlayerObj() {
        return this.playerObj;
    }
    //////////


    ///////SETTERS
    public boolean isConnectedToServer() {
        return this.isConnectedToServer;
    }

    public void setConnectedToServer(boolean isConnected) {
        this.isConnectedToServer = isConnected;
    }

    public void setItThisClientTurn(boolean isItThisClientTurn) {
        this.isItThisClientTurn = isItThisClientTurn;
    }

    public void setPlayerObj(PlayerObj playerObj) {
        this.playerObj = playerObj;
    }
    //////////
}
