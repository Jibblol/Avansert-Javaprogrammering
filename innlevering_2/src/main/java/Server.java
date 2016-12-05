import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Jibb on 05.12.2016.
 */
public class Server {

    public static void main(String[] args) throws IOException {

        int number, temp;

        ServerSocket server = new ServerSocket(9090, 0, InetAddress.getByName("localhost"));
        Socket clientConnection = server.accept();
        Scanner sc = new Scanner(clientConnection.getInputStream());
        number = sc.nextInt();

        temp = number*2;

        PrintStream p = new PrintStream(clientConnection.getOutputStream());
        p.println(temp);
    }

}
