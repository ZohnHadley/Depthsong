package org.tp5.Netty.Test_messages_en_sequence;

import org.tp5.PrintColors;

import java.util.Scanner;

public class Netty_TestMessagesEnSequence {

    public static int serverWriteTickRate = 1;
    public static void main(String[] args) throws InterruptedException {
        /////////config
        String serverHost = "localhost";
        int serverPort = 8080;
        Scanner sc = new Scanner(System.in);
        //////////////
        System.out.println();
        System.out.println(PrintColors.ANSI_BLUE + "Dans se test le server envoy un nombre de message que vous allez spécifier par seconds au clients \n" +
                "+ le server envoyera un autre message pour marquer les seconds" + PrintColors.ANSI_RESET);

        System.out.println();
        System.out.print(PrintColors.ANSI_BLUE + "Choisit le nombre de message que vous voullez que le server envoi par seconds : " + PrintColors.ANSI_RESET);
        System.out.println();

        int nombreDePaquets = sc.nextInt();
        serverWriteTickRate = (int) ((1f / nombreDePaquets) * 1000);
        serverWriteTickRate = serverWriteTickRate < 1 ? 1 : serverWriteTickRate;
        System.out.println(serverWriteTickRate);

        HostServer server = new HostServer(serverPort);
        Thread server_thread = new Thread(server);
        server_thread.start();

        //////////create client server
        ClientServer client = new ClientServer(serverHost, serverPort, 30);
        Thread client_thread = new Thread(client);
        client_thread.start();
        System.out.println("Client started");

    }
}
