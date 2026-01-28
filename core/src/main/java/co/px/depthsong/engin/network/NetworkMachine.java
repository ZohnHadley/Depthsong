package co.px.depthsong.engin.network;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class NetworkMachine implements Runnable {

    public String ip_address = "";

    public void start() throws Exception {}

    public void close() throws Exception {}

    public boolean isRunning() {return false;}
}
