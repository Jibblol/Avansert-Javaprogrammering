package no.vegard.server;

import javafx.util.Pair;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Jibb on 07.12.2016.
 */
public class ClientThread implements Runnable {
    private Socket clientConnection;
    private Scanner scanner;
    private PrintStream pr;

    private boolean isAlive = true;

    private QuestionGenerator questions;

    public ClientThread(Socket clientConnection, QuestionGenerator questions) throws IOException {
        System.out.println("Created new client thread");

        this.clientConnection = clientConnection;
        this.scanner = new Scanner(this.clientConnection.getInputStream());
        this.pr = new PrintStream(this.clientConnection.getOutputStream());
        this.questions = questions;
    }

    public void run() throws NoSuchElementException {
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
