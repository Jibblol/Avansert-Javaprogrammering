import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Jibb on 05.12.2016.
 */
public class Client {

    public static void main(String[] args) throws IOException {

        String answer, question;

        Scanner sc = new Scanner(System.in);
        Socket serverConnection = new Socket("127.0.0.1", 9090);
        Scanner sc1 = new Scanner(serverConnection.getInputStream());
        PrintStream p = new PrintStream(serverConnection.getOutputStream());

        while(true){
            questionLoop(sc, sc1, p);
        }

    }

    private static void questionLoop(Scanner sc, Scanner sc1, PrintStream p) {
        String question;
        String answer;
        question = sc1.nextLine();
        System.out.println(question);
        answer = sc.nextLine();
        p.println(answer);
        System.out.println(sc1.nextLine());
    }
}

