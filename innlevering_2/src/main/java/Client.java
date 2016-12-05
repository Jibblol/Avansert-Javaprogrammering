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

        int number, temp;

        Scanner sc = new Scanner(System.in);
        Socket serverConnection = new Socket("127.0.0.1", 9090);
        Scanner sc1 = new Scanner(serverConnection.getInputStream());
        System.out.println("Enter any number to get it multiplied by 2");
        number = sc.nextInt();
        PrintStream p = new PrintStream(serverConnection.getOutputStream());
        p.println(number);
        temp = sc1.nextInt();
        System.out.println(temp);

    }
}

