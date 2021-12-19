package Multithreading.NetworkChat;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static ArrayList<Client> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException  {
        ServerSocket server = new ServerSocket(1234);
        while(true) {
            System.out.println("Waiting...");
            // ждем клиента из сети
            Socket socket = server.accept();
            System.out.println("Client connected!");
            // создаем клиента на своей стороне
            Client client = new Client(socket);
            clients.add(client);
            new Thread(client).start();
        }
    }

    public static void broadcast(String message, Client sender) {
        if (message.isEmpty()) return;
        for (Client c: clients) {
            if (!c.equals(sender)) {
                PrintStream out = c.getOut();
                if (out != null) {
                    out.print(sender.getName() + ": " + message + System.lineSeparator());
                }
            }
        }
    }


}
