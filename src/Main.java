import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        Mediator M = new Mediator();
        System.out.println("Result of the query : " + "\n" + M.getResult("SELECT Nom, Prenom, Provenance FROM Etudiant WHERE ID_Etudiant = 1"));
    }
}
