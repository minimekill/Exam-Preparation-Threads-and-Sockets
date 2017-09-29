package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Observer extends Thread {

    private Socket clientSocket;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    private TurnstileCounter turnstile;

    public Observer(Socket clientSocket, TurnstileCounter turnstile) {
        this.clientSocket = clientSocket;
        this.turnstile = turnstile;
    }

    @Override
    public void run() {
        try {

            fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            String message;
            while (true) {
                try {
                    toClient.println(getCount());
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ClientPocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getCount() {
        return turnstile.getCount();
    }
}
