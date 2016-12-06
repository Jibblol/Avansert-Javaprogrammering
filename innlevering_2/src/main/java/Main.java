import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Jibb on 05.12.2016.
 */
public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        Server server = new Server();
        try {
            server.createServer();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        server.connect();


//        Client client = new Client();
//        client.createClient();

    }
}
