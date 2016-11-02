import java.sql.SQLException;

/**
 * Created by Jibb on 15.10.2016.
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
