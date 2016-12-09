package no.vegard.server;

import javafx.util.Pair;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class contains everything concerning
 * the threads that are assigned to the connected clients.
 *
 * @author Vegard Ingebrigtsen
 * @version 1.0
 * @since 04.12.2016
 */
public class ClientThread implements Runnable {
    private Socket clientConnection;
    private Scanner scanner;
    private PrintStream pr;

    private boolean isAlive = true;
    /**
     * Class constructor.
     */
    private QuestionGenerator questions;

    /**
     * Creates a new client thread.
     *
     * @param clientConnection the clients connection to the server
     * @param questions the generated questions
     * @throws IOException
     */
    public ClientThread(Socket clientConnection, QuestionGenerator questions) throws IOException {
        System.out.println("Created new client thread");

        this.clientConnection = clientConnection;
        this.scanner = new Scanner(this.clientConnection.getInputStream());
        this.pr = new PrintStream(this.clientConnection.getOutputStream());
        this.questions = questions;
    }

    /**
     * Writes the generated questions out to the clients
     * and checks if the answer corresponds to the
     * data from the database. It then tells the client
     * whether the answer is correct or not.
     */
    public void run() {
        while (isAlive) {
            try {
                Pair<String, String> question = questions.createQuestion();
                pr.println(question.getKey());
                String answer = scanner.nextLine();
                if (answer.equals(question.getValue())) {
                    pr.println("Riktig");
                } else {
                    pr.println("Galt");
                }
            } catch (NoSuchElementException e) {
                // client has disconnected
                System.out.println("Killing thread");
                isAlive = false;
            }
        }
    }
}
