package org.tp5.Netty.Test_client_timeOut;

import org.tp5.PrintColors;

import java.util.Scanner;

public class Netty_TestServerClientTimeOut {

    /*
     * Offre les foncitonnalités de gérer les timeout lorsque un paquet est en retard ou est perdu
     *
     *
     * */

    public static void main(String[] args) throws InterruptedException {
        /////////config
        String serverHost = "localhost";
        int serverPort = 8080;

        /////////
        System.out.println();

        System.out.println("Test de pert de paquets / timeout : ");
        Scanner sc = new Scanner(System.in);
        /////////create server
        System.out.print(PrintColors.ANSI_BLUE + "Entré le nombre de seconds que le client devrait attendre pour recevoir un message avant de se déconnecter : " + PrintColors.ANSI_RESET);
        int temp_client_timeout = sc.nextInt();
        System.out.print(PrintColors.ANSI_BLUE + "Entré le nombre de seconds que le server devrait attendre avant d'envoyer un message au client : " + PrintColors.ANSI_RESET);
        int temp_de_envoy_de_message_du_server = sc.nextInt();

        HostServer server = new HostServer(serverPort, temp_de_envoy_de_message_du_server); //delay le reply au client server
        Thread server_thread = new Thread(server);
        server_thread.start();

        //////////create client server
        ClientServer client = new ClientServer(serverHost, serverPort, temp_client_timeout);
        Thread client_thread = new Thread(client);
        client_thread.start();
        System.out.println("Client started");
    }
}
