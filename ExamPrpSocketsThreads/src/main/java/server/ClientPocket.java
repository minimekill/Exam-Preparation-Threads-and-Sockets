package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientPocket extends Thread {

    private Socket clientSocket;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    private BlockingQueue<String> messages;
    private TurnstileCounter counter;

    public ClientPocket(Socket clientSocket, TurnstileCounter counter) {
        this.clientSocket = clientSocket;
        this.counter = counter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                toClient.println("Welcome to Drayzins TurnstileServer");
                System.out.println("Server notice: client thread is done");

                String message;
                while (!clientSocket.isClosed()) {
                    toClient.println("wirst while loop");
                    while ((message = fromClient.readLine()) == null) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    toClient.println("before switch");
                    System.out.println(message);
                    toClient.println(message);

                    switch (message) {
                        case "count":
                            toClient.println("counted");
                            count();
                            break;
                        case "getcount":
                            toClient.println(getCount());
                            break;
                        default:
                            break;

                    }

                }
            } catch (IOException ex) {
                Logger.getLogger(ClientPocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getCount() {
        return counter.getCount();
    }

    public void count() {
        counter.count();
    }

}
