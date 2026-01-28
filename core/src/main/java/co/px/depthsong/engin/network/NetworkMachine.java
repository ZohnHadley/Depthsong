package co.px.depthsong.engin.network;

public interface NetworkMachine extends Runnable {

    void start() throws Exception;

    void close() throws Exception;

    boolean isRunning();
}
