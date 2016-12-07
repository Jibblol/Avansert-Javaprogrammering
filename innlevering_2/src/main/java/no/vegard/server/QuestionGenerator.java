package no.vegard.server;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.util.Pair;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Jibb on 07.12.2016.
 */
public class QuestionGenerator {
    MysqlDataSource ds;
    Connection con;

    public QuestionGenerator() {
        this.connect();
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void connect() {
        ds = new MysqlDataSource();
        ds.setDatabaseName("innlevering2");
        ds.setServerName("localhost");
        ds.setUser("Vegard");
        ds.setPassword("something");
        try {
            con = ds.getConnection();
        } catch (SQLException e) {
            // error while connecting to database
            e.printStackTrace();
        }
    }

}
