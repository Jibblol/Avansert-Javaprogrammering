import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 * Created by Jibb on 05.12.2016.
 */
public class Server {

    QuestionGenerator questions;

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
