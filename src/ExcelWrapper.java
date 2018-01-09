/**
 * Created by mikouyou on 21/11/2017.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ExcelWrapper {
    private Connection conn;

    public ExcelWrapper() {
        super();
    }

    public void connexion() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            String excelUrl = "jdbc:odbc={Microsoft Excel Driver(*.xls)};DBQ=E:/ID/DataSource/source1.xls;" +
                    "DriverID=22;READONLY=false";
            this.conn = DriverManager.getConnection(excelUrl);
            System.out.println("Connection to excel file : OK");
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