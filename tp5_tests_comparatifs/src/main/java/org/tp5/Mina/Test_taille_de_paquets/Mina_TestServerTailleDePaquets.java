package org.tp5.Mina.Test_taille_de_paquets;

import org.tp5.Netty.Test_taille_de_paquets.ClientServer;
import org.tp5.Netty.Test_taille_de_paquets.HostServer;
import org.tp5.PrintColors;

import java.util.Scanner;

public class Mina_TestServerTailleDePaquets {

    public static String message = "ABCDEFGHIJKLMNOPQRSTUVWXYZ,1234567890";
    public static int numberOfBytes;

    public static void main(String[] args) throws InterruptedException {
        /////////config
        String serverHost = "localhost";
        int serverPort = 8080;
        Scanner sc = new Scanner(System.in);
        //////////////
        System.out.println();
        System.out.println(PrintColors.ANSI_BLUE + "voicit un message qu'un client veut envoyer au server \"" + PrintColors.ANSI_PURPLE + message + PrintColors.ANSI_BLUE + "\" qui est de " + message.getBytes().length + " Bytes" + PrintColors.ANSI_RESET);
        System.out.println();
        System.out.print(PrintColors.ANSI_BLUE + "vous pouvez changer la taille du paquets qui contient se message (enBytes) \n+ ce n'est pas obliger d'être le même taille que le message elle même : " + PrintColors.ANSI_RESET);
        numberOfBytes = sc.nextInt();
        numberOfBytes = numberOfBytes < 1 ? 1 : numberOfBytes ;
        HostServer server = new HostServer(serverPort); //delay le reply au client server
        Thread server_thread = new Thread(server);
        server_thread.start();

        //////////create client server
        ClientServer client = new ClientServer(serverHost, serverPort, 30);
        Thread client_thread = new Thread(client);
        client_thread.start();
        System.out.println("Client started");


    }
}
