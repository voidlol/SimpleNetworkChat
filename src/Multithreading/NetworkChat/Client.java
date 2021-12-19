package Multithreading.NetworkChat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client implements Runnable {
    private static int counter = 0;
    private final Socket socket;
    private PrintStream out = null;
    private String name = "Unnamed User ";

    public Client(Socket socket) {
        this.socket = socket;
        this.name += ++counter;
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            Scanner in = new Scanner(is);
            out = new PrintStream(os);
            out.println("Welcome to Chat" + System.lineSeparator() +
                        "Enter you name: ");
            String name = in.nextLine();
            this.name = name.isEmpty() ? this.name : name;
            String input = in.nextLine();
            while (!input.equals("bye")) {
                Server.broadcast(input, this);
                input = in.nextLine();
            }
            socket.close();
            Server.clients.remove(this);
        } catch (NoSuchElementException | IOException e) {
            System.out.println(this.name + " disconnected");
        }
    }

    public PrintStream getOut() {
        return out;
    }

    public String getName() {
        return this.name;
    }
}
