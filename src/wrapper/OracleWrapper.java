package wrapper;
/**
 * Created by florpiel on 21/11/2017.
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class OracleWrapper {
    public static Connection conn;
    public static HashMap<String, String> correspondenceTab;
    public static String query;
    public static Collection<String> result;
    /* Connexion à la base de données */
    String url = "jdbc:mysql://localhost:3306/mediateur";
    String utilisateur = "root";
    String motDePasse = "root";


    public OracleWrapper() {
        super();
    }

    //Tableau de correspondance
    public void initCorrespondenceTab() {
        correspondenceTab = new HashMap<String, String>();
        //Table Etudiant
        correspondenceTab.put("Etudiant", "etudiant");
        //Table Enseignant
        correspondenceTab.put("Enseignant", "enseignant");
        //Table Cours
        correspondenceTab.put("Cours", "cours");
        //Table Inscription
        correspondenceTab.put("Inscription", "inscription");
        //Table Enseigne
        correspondenceTab.put("Enseigne", "enseigne");
        //Tous les champs
        correspondenceTab.put("ID_Etudiant", "etudiant.ID_Etudiant");
        correspondenceTab.put("Nom", "etudiant.nom");
        correspondenceTab.put("Prenom", "etudiant.prenom");
        correspondenceTab.put("Provenance", "etudiant.provenance");
        correspondenceTab.put("ID_Enseignant", "enseignant.ID_ens");
        correspondenceTab.put("Nom", "enseignant.nom");
        correspondenceTab.put("Prenom", "enseignant.prenom");
        correspondenceTab.put("ID_cours", "cours.NumCours");
        correspondenceTab.put("Libele", "cours.libele");
        correspondenceTab.put("Niveau", "cours.niveau");
        correspondenceTab.put("Type", "cours.type");
        correspondenceTab.put("ID_Etudiant", ".inscription.numEt");
        correspondenceTab.put("ID_Cours", "NumCours");
        correspondenceTab.put("Annee", "cours.annee");
        correspondenceTab.put("Note", "cours.note");
        correspondenceTab.put("ID_Enseignant", "enseigne.NumEns");
        correspondenceTab.put("ID_Cours", "enseigne.NumCours");
        correspondenceTab.put("Annee", "enseigne.Annee");
        correspondenceTab.put("'France'", "'fr'");

    }

    public void connexion()  {
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Erreur de chargement du driver.");
        }

        try {
            this.conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            //this.conn = DriverManager.getConnection("jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:miage", "videlcro", "miage");
//"jdbc:oracle:thin:@172.19.255.3:1521:MIAGE"

        } catch (SQLException ex) {
            System.err.println("Erreur de connexion � la base de donn�es.");
        }
    }

    public void deconnexion() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            System.err.println("Erreur de deconnexion � la base de donn�es.");
        }
    }

    //Récupérer la requête envoyée par le médiateur
    public void getQueryFromMediator(String query) {
        this.query = query;
    }

    //Convertir la requête du médiateur pour la rendre compatible avec la source
    public String convertQueryFromTemplate(String query) {
        String[] splitQuery = query.split(" ");
        String queryConverted = "", currentQueryElement, charAtEndOfElement;
        boolean isElementContainsComma, isElementIsFunction;
        for (int i = 0; i < splitQuery.length; i++) {
            currentQueryElement = splitQuery[i];
            charAtEndOfElement = " ";

            //Si l'élément de la requête contient une virugule, on le retire et on le rajoute à la fin
            isElementContainsComma = currentQueryElement.substring(currentQueryElement.length() - 1).equals(",");
            if (isElementContainsComma) {
                String[] splitQueryElementComma = currentQueryElement.split(",");
                currentQueryElement = splitQueryElementComma[0];
                charAtEndOfElement = ", "; //On remet la virgule qu'on avait retiré
            }

            //Si l'élément de la requête est une fonction, on extrait le champ entre parenthèses pour le comparer au tableau de correspondance et on remet comme avant à la fin
            String functionName = null;
            isElementIsFunction = currentQueryElement.contains("(");
            if (isElementIsFunction) {
                functionName = currentQueryElement.substring(0, currentQueryElement.indexOf("("));
                currentQueryElement = currentQueryElement.substring(currentQueryElement.indexOf("(") + 1, currentQueryElement.indexOf(")"));
            }

            if (correspondenceTab.get(currentQueryElement) != null) { //On compare l'élément par rapport au tableau de correspondance
                currentQueryElement = correspondenceTab.get(currentQueryElement);
            }
            if (functionName != null) { //On remet la fonction comme avant
                currentQueryElement = functionName + "(" + currentQueryElement + ")";
            }
            queryConverted += currentQueryElement + charAtEndOfElement;
        }
        return queryConverted;
    }

    //Exécuter la requête convertie dans Mysql
    public void excuteQueryInOracle(String query) {
        this.connexion();

        String finalQuery = "";
        initCorrespondenceTab();
        finalQuery = convertQueryFromTemplate(query);

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
                    if (!query.contains("COUNT")) {
                        getResult += " : " + resultQuery.getString(i) + " // \n";
                    } else {
                        getResult = resultQuery.getString(i);
                    }
                }
                result.add(getResult);
            }
            resultQuery.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        deconnexion();
    }

    //Récupérer le résultat de la requête et l'envoyer au médiateur
    public Collection<String> getQueryResult() {
        excuteQueryInOracle(query);
        return this.result;
    }

}
