import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

/**
 * Created by Jibb on 02.11.2016.
 */
public class DBServiceTest {

    static DBService tester;

    @BeforeClass
    public static void testSetup() {

        tester = new DBService();
    }

    @AfterClass
    public static void testCleanup() {
        // Do your cleanup here like close URL connection , releasing resources etc
    }

    @Test
    public void testConnection(){

    }

}