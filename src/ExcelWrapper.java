/**
 * Created by mikouyou on 21/11/2017.
 */
import java.sql.*;
import java.util.HashMap;

public class ExcelWrapper {
    private Connection conn;
    //On déclare la table de correspondance
    private HashMap<String, String> correspondenceTab;
    String query, result;

    public ExcelWrapper() {
        super();
    }

    public void initCorrespondenceTab() {
        correspondenceTab = new HashMap<String, String>();
        correspondenceTab.put("ID_Etudiant", "ID");
        correspondenceTab.put("Etudiant", "[" + 2006 + "$], [" + 2007 + "$] WHERE [" + 2006 + "$].Statut = 'etudiant' " +
                "AND [" + 2007 + "$].Statut = 'etudiant'");
        correspondenceTab.put("Enseignant", "[" + 2006 + "$], [" + 2007 + "$] WHERE [" + 2006 + "$].Statut = 'enseignant' " +
                "AND [" + 2007 + "$].Statut = 'enseignant'");
        correspondenceTab.put("WHERE", "AND");
        //correspondenceTab.put("SELECT count(*) FROM Etudiant WHERE Provenance <> 'France'", "SELECT count(*) FROM 2006, 2007 WHERE Statut = 'Etudiant' AND Provenance <> 'France'");
    }

    public void connection() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            //On met la source de données ODBC créée en 32 bits (le jdk 7 doit être 32 bits aussi)
            String excelUrl = "jdbc:odbc:Connexion ODBC sous Excel";
            this.conn = DriverManager.getConnection(excelUrl);
            System.out.println("Connection to excel file : OK");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void disonnection() {
        try {
            this.conn.close();
            System.out.println("Disonnection to excel file : OK");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void getQueryFromMediator(String query) {
        this.query = query;
    }

    public String convertQueryFromTemplate(String query) {
        String[] splitQuery = query.split(" ");
        String queryConverted = "";
        for (int i = 0; i < splitQuery.length; i++) {
            if (correspondenceTab.get(splitQuery[i]) != null) {
                splitQuery[i] = correspondenceTab.get(splitQuery[i]);
            }
            queryConverted += splitQuery[i] + " ";
        }
        return queryConverted;
    }

    public void excuteQueryInExcel(String query) {
        this.connection();
        initCorrespondenceTab();
        query = convertQueryFromTemplate(query);
        //System.out.println(query);
        try {
            Statement statement = conn.createStatement();
            ResultSet resultQuery = statement.executeQuery(query);
            String getResult;
            while (resultQuery.next()) {
                getResult = "";
                for (int i = 1; i <= 11; i++) {
                    getResult += resultQuery.getString(i) + " ";
                }
                result = getResult;
            }
            resultQuery.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disonnection();
    }

    public String getQueryResult() {
        excuteQueryInExcel(query);
        return this.result;
    }

    /*public void sendResultToMediator(String result) {

    }*/
}