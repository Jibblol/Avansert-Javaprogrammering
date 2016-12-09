package no.vegard.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class creates a new client and connects to the server.
 *
 * Created by Jibb on 05.12.2016.
 */
public class Client {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner sc1;
        PrintStream p;
        try {
            Socket serverConnection = new Socket("127.0.0.1", 9090);
            sc1 = new Scanner(serverConnection.getInputStream());
            p = new PrintStream(serverConnection.getOutputStream());
        } catch(IOException e){
            // Could not connect to server..
            System.out.println("Feil: Kunne ikke koble til serveren");
            return;
        }
        while(true){
            try {
                System.out.println(sc1.nextLine());
                String input = sc.nextLine();
                // If the user types "exit" the client disconnects and the thread dies
                if (input.equals("exit")) {
                    return;
                }
                p.println(input);
                System.out.println(sc1.nextLine());
            } catch (NoSuchElementException e) {
                // server has died. abort.
                System.out.println("Feil: Kunne ikke koble til serveren");
                return;
            }
        }
    }
}

