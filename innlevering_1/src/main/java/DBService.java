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
 * The DBService class reads text-files
 * and splits them up where necessary.
 * It then executes SQLs to create a table
 * in a database, and then it inserts the data
 * from the chosen text-file into the table.
 *
 * @author Vegard Ingebrigtsen
 * @version 1.0
 * @since 28.11.2016
 */

public class DBService {

    MysqlDataSource ds;
    Connection con;

    public DBService(){

    }

    /**
     * Reads from the specified text file and splits
     * the lines into readable data corresponding to the
     * columns in the table.
     *
     * @param fileName name of the file which the method reads from
     * @param tableName name of the table in the database
     */
    public void copyFile(String fileName, String tableName){
        BufferedReader reader = null;

        try{
            reader = new BufferedReader(new FileReader(fileName));

            // read column names
            String line = reader.readLine();
            String[] fieldNames = line.split("/");
            System.out.println(Arrays.toString(fieldNames));

            // read data types
            line = reader.readLine();
            String[] fieldTypes = line.split("/");
            System.out.println(Arrays.toString(fieldTypes));

            // read data size
            line = reader.readLine();
            String[] fieldSize = line.split("/");
            System.out.println(Arrays.toString(fieldSize));

            createTable(fieldNames, fieldTypes, fieldSize, tableName);

            // read fourth line
            line = reader.readLine();
            String[] data = line.split("/");
            System.out.println(Arrays.toString(data));

            insertTable(data, tableName);

            // read fifth line
            line = reader.readLine();
            data = line.split("/");
            System.out.println(Arrays.toString(data));

            insertTable(data, tableName);

            // read sixth line
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

    /**
     * Creates a query consisting of the array of values
     * to be inserted into the table in the database.
     * It then runs the query, and the data is inserted into the table.
     *
     * @param data array of values to be inserted
     * @param tableName name of the table in the database
     */
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

    /**
     * A query is made so a table in the database can be created.
     * The query consists of the field names, data types and sizes,
     * aswell as the name of the table we intend to use.
     *
     * @param fieldNames name of the fields in the table
     * @param fieldTypes describes the data types in the different fields in the database
     * @param fieldSize size of the fields data type
     * @param tableName name of the table
     */
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

    /**
     * Gets all the data in the table 'tableName'
     * and writes it to console.
     *
     * @param tableName name of the table in the database
     */
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

    /**
     * Connects to the database.
     *
     * @throws SQLException
     */
    public void connect() throws SQLException{
        ds = new MysqlDataSource();
        ds.setDatabaseName("innlevering1");
        ds.setServerName("localhost");
        ds.setUser("vegard");
        ds.setPassword("something");
        con = ds.getConnection();
    }

}
