package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MysqlCon {

    public Connection con;
    private static MysqlCon single_instance = null;

    public MysqlCon() {
        try{
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=   ");
            Statement s = (Statement) con.createStatement();
            int result = s.executeUpdate("USE testdb");
        }
        catch ( Exception e) {                e.printStackTrace();            }
        System.out.println("Connection created and connected to testdb");
    }

    public static Connection getConnection(){
        Connection con = null;
        try{
         con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=   ");
        Statement s = (Statement) con.createStatement();
        int result = s.executeUpdate("USE testdb");
    }
        catch ( Exception e) {                e.printStackTrace();            }
    return con;
    }
}

