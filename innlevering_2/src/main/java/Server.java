import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.util.Pair;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Jibb on 05.12.2016.
 */
public class Server {

    MysqlDataSource ds;
    Connection con;

    public void createServer() throws IOException, SQLException {

        connect();

        String answer;
        Pair question;

        ServerSocket server = new ServerSocket(9090, 0, InetAddress.getByName("localhost"));
        Socket clientConnection = server.accept();
        Scanner sc = new Scanner(clientConnection.getInputStream());

        while(true){
            if(clientConnection.isConnected()){
                questionLoop(clientConnection, sc);
            }
        }

    }

    private void questionLoop(Socket clientConnection, Scanner sc) throws IOException {
        Pair question;
        String answer;
        try {
            question = createQuestion();
            PrintStream p = new PrintStream(clientConnection.getOutputStream());
            p.println(question.getKey());

            answer = sc.nextLine();
            System.out.println(answer);

            p.println(question.getValue().equals(answer));
        }
        catch(NoSuchElementException e){

        }
    }


    public Pair<String, String> createQuestion(){
        String select = "SELECT artist, album, låtnavn, utgivelsesår FROM quizdata ORDER BY RAND () LIMIT 1";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(select);

            while(rs.next()){
                String artist = rs.getString("Artist");
                String album = rs.getString("Album");
                String låtnavn = rs.getString("Låtnavn");
                int utgivelsesår = rs.getInt("Utgivelsesår");

                return new Pair<String, String>("Hvem ga ut låten " + låtnavn + " i " + utgivelsesår + "?", artist);

//                System.out.println("Artist: " + artist);
//                System.out.println("Album: " + album);
//                System.out.println("Låtnavn: " + låtnavn);
//                System.out.println("Utgivelsesår: " + utgivelsesår + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void connect() throws SQLException {
        ds = new MysqlDataSource();
        ds.setDatabaseName("innlevering2");
        ds.setServerName("localhost");
        ds.setUser("Vegard");
        ds.setPassword("something");
        con = ds.getConnection();
    }

}
