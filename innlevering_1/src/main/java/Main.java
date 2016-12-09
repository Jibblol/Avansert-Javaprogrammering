import java.sql.SQLException;

/**
 * @author vegard Ingebrigtsen
 * @version 1.0
 * @since 25.11.2016
 */
public class Main {
    public static void main(String[] args) {
        DBService db = new DBService();
        try {
            db.connect();
            db.copyFile("src/main/resources/Donald.txt", "Donald");
            db.showTable("Donald");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
