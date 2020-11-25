package DbUtil;

import java.sql.SQLException;

public class CloseConnection extends OpenConnection{

    public CloseConnection() {
        super();
    }
    public void close(){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if(ps!=null){
            try {
                rs.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
        }
        if(con!=null){
            try {
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
