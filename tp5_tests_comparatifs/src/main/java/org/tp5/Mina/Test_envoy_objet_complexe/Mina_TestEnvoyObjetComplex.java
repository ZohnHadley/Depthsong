package org.tp5.Mina.Test_envoy_objet_complexe;
 

public class Mina_TestEnvoyObjetComplex {
    public static void main(String[] args) {
        /////////config
        String serverHost = "localhost";
        int serverPort = 8080;
        //////////////

        HostServer hostServer = new HostServer(serverPort);
        Thread server_thread = new Thread(hostServer);
        server_thread.start();


        ClientServer client = new ClientServer(serverHost, serverPort);
        Thread client_thread = new Thread(client);
        client_thread.start();
    }
}
