package DbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OpenConnection {
    public static Connection con;
    public static PreparedStatement ps;
    public static ResultSet rs;

    public OpenConnection() {
        if(con==null){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(OpenConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            String dbUrl="jdbc:mysql://localhost:3306/crawlData?useSSL=false";
            try {
                con= DriverManager.getConnection(dbUrl, "root", "242999");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
