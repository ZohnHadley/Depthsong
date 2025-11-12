package org.example.Test_envoy_objet_complexe.Model;

import org.example.Test_envoy_objet_complexe.Model.ServerTracker.ClientInfo;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerGameMaster {

    private static ServerGameMaster instance;

    private final int secondsPerTurn = 30;

    private ArrayList<ClientInfo> list_clientInfo;
    private AtomicInteger clientCounter;

    private ClientInfo clientOfCurrentTurn;

    private ServerGameMaster() {
        clientOfCurrentTurn = null;
        list_clientInfo = new ArrayList<ClientInfo>();
        clientCounter = new AtomicInteger(0);
    }

    public static ServerGameMaster getInstance() {
        if (instance == null) {
            instance = new ServerGameMaster();
        }
        return instance;
    }

    public ClientInfo getClientOfCurrentTurn() {
        return clientOfCurrentTurn;
    }

    public int getSecondsPerTurn() {
        return secondsPerTurn;
    }


    public ArrayList<ClientInfo> getList_clientInfo() {
        return list_clientInfo;
    }

    public AtomicInteger getClientCounter() {
        return clientCounter;
    }

    public void setClientOfCurrentTurn(ClientInfo clientOfCurrentTurn) {
        this.clientOfCurrentTurn = clientOfCurrentTurn;
    }

    public void setNextClientTurn() {
        if (clientCounter.get() <= 1) {
            System.err.println("no other client to switch to");
            //setClientOfCurrentTurn(null);
            return;
        }

        int index = list_clientInfo.indexOf(clientOfCurrentTurn);
        if (index == list_clientInfo.size() - 1) {
            clientOfCurrentTurn = list_clientInfo.getFirst();
        } else {
            clientOfCurrentTurn = list_clientInfo.get(index + 1);
        }
        System.out.println("next client turn: " + clientOfCurrentTurn.getChannel().remoteAddress());
    }
}
