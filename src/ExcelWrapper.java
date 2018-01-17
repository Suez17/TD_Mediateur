/**
 * Created by mikouyou on 21/11/2017.
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ExcelWrapper {
    public static Connection conn;
    public static HashMap<String, String> correspondenceTab;
    public static String query;
    public static Collection<String> result;

    public ExcelWrapper() {
        super();
    }

    //Tableau de correspondance
    public void initCorrespondenceTab(String excelSheet) {
        correspondenceTab = new HashMap<String, String>();
        //Table Etudiant
        correspondenceTab.put("Etudiant", "(SELECT [" + excelSheet + "$].ID, [" + excelSheet + "$].Nom, [" + excelSheet + "$].Prenom, [" + excelSheet + "$].Provenance, " +
                "[" + excelSheet + "$].FormationPrecedente, [" + excelSheet + "$].NiveauInsertion " +
                "FROM [" + excelSheet + "$] WHERE [" + excelSheet + "$].Statut = 'etudiant')");
        //Table Enseignant
        correspondenceTab.put("Enseignant", "(SELECT [" + excelSheet + "$].ID, [" + excelSheet + "$].Nom, [" + excelSheet + "$].Prenom " +
                "FROM [" + excelSheet + "$] WHERE [" + excelSheet + "$].Statut = 'enseignant')");
        //Table Cours
        correspondenceTab.put("Cours", "(SELECT [" + excelSheet + "$].ID_Cours, [" + excelSheet + "$].Libelle_Cours, [" + excelSheet + "$].Type_Cours, [" + excelSheet + "$].Niveau_Cours " +
                "FROM [" + excelSheet + "$])");
        //Table Inscription
        correspondenceTab.put("Inscription", "(SELECT [" + excelSheet + "$].ID, [" + excelSheet + "$].ID_Cours, [" + excelSheet + "$].Note " +
                "FROM [" + excelSheet + "$])");
        //Table Enseigne
        correspondenceTab.put("Enseigne", "(SELECT [" + excelSheet + "$].ID, [" + excelSheet + "$].ID_Cours " +
                "FROM [" + excelSheet + "$])");
        //Tous les champs
        correspondenceTab.put("ID_Etudiant", "[" + excelSheet + "$].ID");
        correspondenceTab.put("ID_Enseignant", "[" + excelSheet + "$].ID");
        correspondenceTab.put("Nom", "[" + excelSheet + "$].Nom");
        correspondenceTab.put("Prenom", "[" + excelSheet + "$].Prenom");
        correspondenceTab.put("Provenance", "[" + excelSheet + "$].Provenance");
        correspondenceTab.put("FormationPrecedente", "[" + excelSheet + "$].FormationPrecedente");
        correspondenceTab.put("NiveauInsertion", "[" + excelSheet + "$].NiveauInsertion");
        correspondenceTab.put("ID_Cours", "[" + excelSheet + "$].ID_Cours");
        correspondenceTab.put("Libelle", "[" + excelSheet + "$].Libelle_Cours");
        correspondenceTab.put("Type", "[" + excelSheet + "$].Type_Cours");
        correspondenceTab.put("Niveau", "[" + excelSheet + "$].Niveau_Cours");
        correspondenceTab.put("Note", "[" + excelSheet + "$].Note");
    }

    //Connexion à Excel
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

    //Déconnexion d'Excel
    public void disonnection() {
        try {
            this.conn.close();
            System.out.println("Disonnection to excel file : OK");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Récupérer la requête envoyée par le médiateur
    public void getQueryFromMediator(String query) {
        this.query = query;
    }

    //Convertir la requête du médiateur pour la rendre compatible avec la source
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

    //Exécuter la requête convertie dans Excel
    public void excuteQueryInExcel(String query) {
        this.connection();

        //Pour chaque feuille dans Excel on converti la requête principale avec le bon tableau de correspondance et on fait une union des requêtes (ex : 3 feuilles -> union de 3 requêtes)
        String finalQuery = "";
        String[] excelSheets = {"2006", "2007"};
        for (int i = 0; i < excelSheets.length; i++) {
            initCorrespondenceTab(excelSheets[i]);
            finalQuery += convertQueryFromTemplate(query);
            if (i != excelSheets.length - 1) {
                finalQuery += "UNION ";
            }
        }

        result = new ArrayList<String>();
        System.out.println(finalQuery);
        try {
            Statement statement = conn.createStatement();
            ResultSet resultQuery = statement.executeQuery(finalQuery);
            String getResult;
            while (resultQuery.next()) {
                getResult = "";
                //resultQuery.getMetaData().getColumnCount() permet de récupèrer le nombre de colonnes retournées par ResultSet
                for (int i = 1; i <= resultQuery.getMetaData().getColumnCount(); i++) {
                    getResult += resultQuery.getString(i) + " ";
                }
                result.add(getResult + "\n");
            }
            resultQuery.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disonnection();
    }

    //Récupérer le résultat de la requête et l'envoyer au médiateur
    public Collection<String> getQueryResult() {
        excuteQueryInExcel(query);
        return this.result;
    }
}