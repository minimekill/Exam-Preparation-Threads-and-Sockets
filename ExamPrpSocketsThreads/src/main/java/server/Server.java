package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter toClient;
    private BufferedReader fromClient;
    private ExecutorService ex = Executors.newFixedThreadPool(10);
    //private ArrayList<String> chat = new ArrayList();
    private ArrayList<ClientPocket> clients = new ArrayList();
    //private BlockingQueue<String> messages = new LinkedBlockingDeque();
    private ClientPocket cp;
    private ServerSocket socket;
    private TurnstileCounter counter = new TurnstileCounter();

    public void serverStart() {

        try {
            socket = new ServerSocket(80);
            while (true) {
                System.out.println("Waiting for clients");
                clientSocket = socket.accept();
                System.out.println("Connected");

                fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                toClient = new PrintWriter(clientSocket.getOutputStream(), true);

                toClient.println("write turnstile or observer");

                askClient();

            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void askClient() {

        String message;
        try {
            while ((message = fromClient.readLine()) == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (message.equals("turnstile")) {
                cp = new ClientPocket(clientSocket, counter);
                clients.add(cp);
                ex.execute(cp);
            } else if (message.equals("observer")) {
                Observer ob = new Observer(clientSocket, counter);
                ex.execute(ob);
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
