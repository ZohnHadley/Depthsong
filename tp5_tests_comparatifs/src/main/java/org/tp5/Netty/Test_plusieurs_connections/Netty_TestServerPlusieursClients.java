package org.tp5.Netty.Test_plusieurs_connections;

import org.tp5.PrintColors;

import java.util.ArrayList;
import java.util.Scanner;

public class Netty_TestServerPlusieursClients {

    public static void main(String[] args) throws Exception {
        //https://stackoverflow.com/questions/77704322/how-many-platform-threads-are-used-to-schedule-virtual-threads
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1000000");
        /////////config
        String serverHost = "localhost";
        int serverPort = 8080;
        int maxClients = 1000000;
        /////////create server
        HostServer server = new HostServer(serverPort);
        Thread server_thread = new Thread(server);
        server_thread.start();
        Thread.sleep(1000);
        System.out.println();

        /////////create clients
        System.out.print(PrintColors.ANSI_BLUE + "choisit le nombre de clients à connecter (" + maxClients + " max): " + PrintColors.ANSI_RESET);
        Scanner sc = new Scanner(System.in);

        int nb_clients = Math.min(sc.nextInt(), maxClients);

        ArrayList<ClientServer> clients_to_be = new ArrayList<ClientServer>();
        ArrayList<Thread> client_threads = new ArrayList<Thread>();

        for (int i = 0; i < nb_clients; i++) {
            clients_to_be.add(new ClientServer(serverHost, serverPort));
        }
        System.out.println("temps estimate pour connected tout les clients : " + ((nb_clients * 15) / 1000) + "s");
        for (ClientServer clientServer : clients_to_be) {
            client_threads.add(Thread.startVirtualThread(clientServer));
            Thread.sleep(15);
        }
        System.out.println();
        Thread.sleep(100);

        while (true) {
            if (HostServer.clientCounter.get() <= 0) {
                break;
            }

            for (int i = 0; i < clients_to_be.size(); i++) {
                System.out.println(PrintColors.ANSI_BLUE + "Client [" + i + "] : " + clients_to_be.get(i).getLocalChannel().getAddress() + ":" + clients_to_be.get(i).getLocalChannel().getPort() + PrintColors.ANSI_RESET);
                Thread.sleep(10);
            }

            System.out.println("Nombre de clients connecter : " + HostServer.clientCounter.get() + PrintColors.ANSI_RESET);
            System.out.print(PrintColors.ANSI_BLUE + "- taper l'index du client pour le deconnecter  : ");
            System.out.println("- Ou taper \"-1\" pour quitter" + PrintColors.ANSI_RESET);

            int index = sc.nextInt();

            if (index == -1) {
                break;
            }

            if (index < 0 || index >= clients_to_be.size()) {
                System.out.println(PrintColors.ANSI_RED + "index invalide essayer encore" + PrintColors.ANSI_RESET);
                Thread.sleep(100);
                continue;
            }
            clients_to_be.get(index).close();
            clients_to_be.remove(index);
            client_threads.get(index).join();
        }

        /////////close server
        for (ClientServer client : clients_to_be) {
            client.close();
        }
        System.out.println("Server closed");
        //close programme
        sc.close();
        System.exit(0);
    }
}
