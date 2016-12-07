import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

/**
 * Created by Jibb on 15.10.2016.
 */



public class DBService {

    MysqlDataSource ds;
    Connection con;

    public DBService(){

    }

    public void copyFile(String fileName, String tableName){
        BufferedReader reader = null;

        try{
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            String[] fieldNames = line.split("/");
            System.out.println(Arrays.toString(fieldNames));

            line = reader.readLine();
            String[] fieldTypes = line.split("/");
            System.out.println(Arrays.toString(fieldTypes));

            line = reader.readLine();
            String[] fieldSize = line.split("/");
            System.out.println(Arrays.toString(fieldSize));

            createTable(fieldNames, fieldTypes, fieldSize, tableName);

            line = reader.readLine();
            String[] data = line.split("/");
            System.out.println(Arrays.toString(data));

            insertTable(data, tableName);

            line = reader.readLine();
            data = line.split("/");
            System.out.println(Arrays.toString(data));

            insertTable(data, tableName);

            line = reader.readLine();
            data = line.split("/");
            System.out.println(Arrays.toString(data));

            insertTable(data, tableName);

        }
        catch(FileNotFoundException e){

        }
        catch(IOException e){

        }

    }

    private void insertTable(String[] data, String tableName) {
        String insertString =
                "INSERT INTO " + tableName + "\n" +
                "VALUES(";

        for(int i = 0; i<data.length; i++){
            insertString += ("'" + data[i] + "'");

            if(i<data.length-1){
                insertString += (",");
            }

        }

        insertString += (");");

        System.out.println(insertString);

        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(insertString);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String[] fieldNames, String[] fieldTypes, String[] fieldSize, String tableName) {
        String createString =
                "CREATE table " + tableName + "(\n";

        for(int i = 0; i<fieldNames.length; i++){
            createString += (fieldNames[i] + " " );

            if(fieldTypes[i].equals("STRING")) {
                createString += ("VARCHAR");
            }
            else{
                createString += (fieldTypes[i]);
            }

            createString += ("(" + fieldSize[i] + ") " + "NOT NULL");

            if(i<fieldNames.length-1){
                createString += (",");
            }
            createString += ("\n");
        }

        createString += (");");

        System.out.println(createString);

        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(createString);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void showTable(String tableName){
        String select = "SELECT * FROM " + tableName;

        Statement stmt = null;
        try {
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(select);

            while(rs.next()){
                String name = rs.getString("Navn");
                String address = rs.getString("Adresse");
                int age = rs.getInt("Alder");
                int height = rs.getInt("Høyde");

                System.out.println("Navn: " + name);
                System.out.println("Adresse: " + address);
                System.out.println("Alder: " + age);
                System.out.println("Høyde: " + height + "\n");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void connect() throws SQLException{
        ds = new MysqlDataSource();
        ds.setDatabaseName("innlevering1");
        ds.setServerName("localhost");
        ds.setUser("Vegard");
        ds.setPassword("something");
        con = ds.getConnection();
    }

}
