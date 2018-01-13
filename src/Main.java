import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Mediator M = new Mediator();
        System.out.println(M.getResult("SELECT * FROM Etudiant"));
    }
}
