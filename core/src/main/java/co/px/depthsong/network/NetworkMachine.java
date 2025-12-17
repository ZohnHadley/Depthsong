package co.px.depthsong.network;

public interface NetworkMachine extends Runnable {

    public void start() throws Exception;

    public void close() throws Exception;

    boolean isRunning();
}
