import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        Mediator M = new Mediator();
        //System.out.println("Result of the query : " + "\n" + M.getResult("SELECT Count(*) FROM Etudiant GROUP BY ID"));
        //System.out.println("Number of students : " + "\n" + M.getResult("SELECT Count(*) FROM Etudiant WHERE Provenance <> 'France' GROUP BY ID"));
        System.out.println("Result of the query : " + "\n" + M.getResult("SELECT Libelle, Type FROM Cours"));
    }
}
