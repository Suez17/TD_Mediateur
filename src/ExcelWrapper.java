/**
 * Created by mikouyou on 21/11/2017.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ExcelWrapper {
    private Connection conn;

    public ExcelWrapper() {
        super();
    }

    public void connexion() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            /*String excelUrl = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=E:/ID/DataSource/source1.xls;" +
                    "DriverID=22;READONLY=false";*/
            String excelUrl = "jdbc:odbc:Connexion ODBC sous Excel";
            this.conn = DriverManager.getConnection(excelUrl);
            System.out.println("Connection to excel file : OK");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deconnexion() {
        try {
            this.conn.close();
            System.out.println("Disonnection to excel file : OK");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}