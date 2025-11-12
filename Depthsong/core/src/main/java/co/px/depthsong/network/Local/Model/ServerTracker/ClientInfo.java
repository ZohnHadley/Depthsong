package co.px.depthsong.network.Local.Model.ServerTracker;

import io.netty.channel.Channel;

public class ClientInfo {

    protected String clientName;


    protected final String IP;
    protected final int PORT;

    protected final Channel channel;


    protected boolean isItThisClientTurn = false;



    //////CONSTRUCTORS


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

    //////////


    ///////SETTERS

    public void setItThisClientTurn(boolean isItThisClientTurn) {
        this.isItThisClientTurn = isItThisClientTurn;
    }

    //////////
}
