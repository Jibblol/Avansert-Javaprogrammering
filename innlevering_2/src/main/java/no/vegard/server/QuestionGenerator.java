package no.vegard.server;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.util.Pair;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class connects to the database and collects the data it contains.
 * It then generates questions to be communicated to the client(s).
 *
 * @author Vegard Ingebrigtsen
 * @version 1.0
 * @since 04.12.2016
 */
public class QuestionGenerator {
    MysqlDataSource ds;
    Connection con;

    public QuestionGenerator() {
        this.connect();
    }

    /**
     * Collects data from the database and
     * creates questions to be communicated to the client(s).
     *
     * @return a pair of strings where one is the question and the other is the answer to said question.
     */
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
            System.out.println("Kunne ikke generere spørsmål");
        }
        return null;
    }

    /**
     * Connects to the database.
     */
    private void connect() {
        ds = new MysqlDataSource();
        ds.setDatabaseName("innlevering2");
        ds.setServerName("localhost");
        ds.setUser("vegard");
        ds.setPassword("something");
        try {
            con = ds.getConnection();
        } catch (SQLException e) {
            // error while connecting to database
            e.printStackTrace();
        }
    }

}
