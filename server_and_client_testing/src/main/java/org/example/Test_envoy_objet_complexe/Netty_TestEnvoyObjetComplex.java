package org.example.Test_envoy_objet_complexe;


import java.util.Scanner;

public class Netty_TestEnvoyObjetComplex {
    public static String newUserName;

    public static void main(String[] args) {
        /////////config
        String serverHost = "localhost";
        int serverPort = 8080;
        //////////////

        HostServer hostServer = new HostServer(serverPort);
        Thread server_thread = new Thread(hostServer);
        server_thread.start();

    }
}
