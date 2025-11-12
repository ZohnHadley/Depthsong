package org.tp5.Netty.Test_latency;

import org.tp5.PrintColors;

import java.util.ArrayList;
import java.util.Scanner;

public class Netty_TestLatency {

    public static int numberOfTimesToWrite;
    public static int packetSize;
    public static ArrayList<Integer> list_packet_times = new ArrayList<>();


    public static void main(String[] args) throws InterruptedException {
        /////////config
        String serverHost = "localhost";
        int serverPort = 8080;
        Scanner sc = new Scanner(System.in);
        //////////////
        System.out.println(PrintColors.ANSI_BLUE + "Entrer le nombre de fois que vous voulez \"pingger\" le server : " + PrintColors.ANSI_RESET);
        numberOfTimesToWrite = sc.nextInt();
        System.out.println(PrintColors.ANSI_BLUE + "Entrer la taille des packets envoyer (Bytes) : " + PrintColors.ANSI_RESET);
        packetSize = sc.nextInt();


        HostServer server = new HostServer(serverPort);
        Thread server_thread = new Thread(server);
        server_thread.start();

        //////////create client server
        ClientServer client = new ClientServer(serverHost, serverPort, 30);
        Thread client_thread = new Thread(client);
        client_thread.start();
        System.out.println(PrintColors.ANSI_GREEN + "!!! J'ai pas pas implémenté TTL (pas nécessaire pour test je crois)!!!" + PrintColors.ANSI_RESET);

    }
}
