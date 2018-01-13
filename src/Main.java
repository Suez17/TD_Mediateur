import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Mediator M = new Mediator();
        M.sentQueryToWrappers("SELECT * FROM Etudiant");
        System.out.println(M.getResultFromWrappers());
    }
}
