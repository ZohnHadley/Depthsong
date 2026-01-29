package co.px.depthsong.engin.engineCore.engine_managers;

import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumNetworkClientConnectionStates;
import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumNetworkState;
import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumNetworkTitle;
import co.px.depthsong.engin.network.Local.ClientServer;
import co.px.depthsong.engin.network.Local.HostServer;
import co.px.depthsong.engin.network.NetworkMachine;
import co.px.depthsong.engin.network.ServerUtil;
import com.badlogic.gdx.Gdx;
import io.netty.channel.ChannelFuture;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NetworkMachineManager implements Runnable {
    private String IPADDRESS = "192.168.0.104";
    private  int PORT = 1234;

    private static NetworkMachineManager instance;

    private EnumNetworkTitle currentNetworkTitle;
    private NetworkMachine clientServer;

    private NetworkMachine hostServer;

    private EnumNetworkState networkState = EnumNetworkState.OFFLINE;

    private EnumNetworkClientConnectionStates connectionState;


    public static NetworkMachineManager getInstance() {
        if (instance == null) {
            instance = new NetworkMachineManager();
        }
        return instance;
    }

    public void setCurrentConnectedState(EnumNetworkClientConnectionStates state) {
        connectionState = state;
    }

    public void startHostServer(int port){
        if (this.getHostServer() != null && this.getClientServer() != null){
            return;
        }
        try{
            this.setHostServer(new HostServer(port));
            this.getHostServer().start().sync();

            if(this.getHostServer().getIsRunning()){
                this.setClientServer(new ClientServer(((HostServer)this.getHostServer()).getHOST_SERVER_MASTER().getIpAddress(), port));
                this.getClientServer().start().sync();
                if(this.getClientServer().getIsRunning()){
                    this.setConnectionState(EnumNetworkClientConnectionStates.CONNECTED);
                    this.setCurrentNetworkTitle(EnumNetworkTitle.HOST);
                }

            }

        }catch (Exception e){
            printLogError("Error NetworkMachines : " + e.getMessage());
        }
    }

    public void startClientServer(String ip, int port){
        if (this.getClientServer() != null){
            return;
        }

        try{
                this.setClientServer(new ClientServer(ip, port));
                this.getClientServer().start().sync();
                if(this.getClientServer().getIsRunning()){
                    this.setConnectionState(EnumNetworkClientConnectionStates.CONNECTED);
                    this.setCurrentNetworkTitle(EnumNetworkTitle.CLIENT);
                }

        }catch (Exception e){
            printLogError("Error NetworkMachines : " + e.getMessage());
        }
    }

    public void disconnect(){
        try {
            if (getClientServer() != null){
                getClientServer().close();
            }
            if (getHostServer() != null) {
                getHostServer().close();
            }

            setCurrentConnectedState(EnumNetworkClientConnectionStates.DISCONNECTED);

        } catch (Exception e) {
            printLogError("Error closing network server : " + e);
        }
    }

    private void printLogError(String message) {
        ServerUtil.err(message);
    }

    private void printLog(String message) {
        ServerUtil.log(message);
    }

    @Override
    public void run() {

        startHostServer(PORT);
    }

    public void dispose(){
    }
}
