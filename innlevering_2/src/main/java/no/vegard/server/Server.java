package no.vegard.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

/**
 * This class creates a server that runs on localhost.
 * It also creates a thread that, in turn, is assigned
 * to the client(s).
 *
 * @author Vegard Ingebrigtsen
 * @version 1.0
 * @since 04.12.2016
 */
public class Server {

    /**
     * Class constructor.
     */
    QuestionGenerator questions;

    /**
     * Creates a server running on localhost.
     * Threads for the connecting client(s) are also created
     * so that several clients can communicate with the server simultaneously.
     */
    public void createServer(){
        questions = new QuestionGenerator();
        ServerSocket server;
        try {
            server = new ServerSocket(9090, 0, InetAddress.getByName("localhost"));
        } catch (UnknownHostException e) {
            System.out.println("Kunne ikke starte server på " + e.getMessage() + ". Ukjent host");
            return;
        } catch (IOException e){
            System.out.println("Kunne ikke starte server");
            return;
        }

        while(true) {
            ClientThread client;
            try {
                client = new ClientThread(server.accept(), questions);
            } catch (IOException e) {
                continue; // If a client can't connect, the server goes forward with the quiz.
            }
            new Thread(client).start();
        }
    }
}
