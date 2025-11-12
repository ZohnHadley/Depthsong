package co.px.depthsong.network.Local.Model.ServerTracker;

import co.px.depthsong.network.Local.Model.PlayerObj;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class ServerConnectionContext {


    private PlayerObj currentPlayer = null;

    private ChannelHandlerContext currentContext;
    private Channel currentChannel;

    private boolean channelRecievedPlayerObj = false;
    private String localChannelAddress = "N/A";

    private boolean finishedConnectingPlayer = false;

    //CONSTRUCTORS
    public ServerConnectionContext() {
    }

    public ServerConnectionContext(PlayerObj currentPlayer) {

        this.currentPlayer = currentPlayer;
    }

    public ServerConnectionContext(PlayerObj currentPlayer, ChannelHandlerContext currentContext, Channel currentChannel) {

        this.currentPlayer = currentPlayer;
        this.currentContext = currentContext;
        this.currentChannel = currentChannel;
    }


    //GETTERS


    public PlayerObj getCurrentPlayer() {
        return currentPlayer;
    }

    public ChannelHandlerContext getCurrentContext() {
        return currentContext;
    }

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public boolean isChannelRecievedPlayerObj() {
        return channelRecievedPlayerObj;
    }

    public String getLocalChannelAddress() {
        return localChannelAddress;
    }

    public boolean isFinishedConnectingPlayer() {
        return finishedConnectingPlayer;
    }


    //SETTERS

    public void setCurrentPlayer(PlayerObj currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setCurrentContext(ChannelHandlerContext currentContext) {
        this.currentContext = currentContext;
    }

    public void setCurrentChannel(Channel currentChannel) {
        this.currentChannel = currentChannel;
    }



    public void setChannelRecievedPlayerObj(boolean channelRecievedPlayerObj) {
        this.channelRecievedPlayerObj = channelRecievedPlayerObj;
    }

    public void setLocalChannelAddress(String localChannelAddress) {
        this.localChannelAddress = localChannelAddress;
    }

    public void setFinishedConnectingPlayer(boolean finishedConnectingPlayer) {
        this.finishedConnectingPlayer = finishedConnectingPlayer;
    }
}
