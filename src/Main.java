import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        Mediator M = new Mediator();
        System.out.println(M.getResult("SELECT * FROM Enseignant"));
    }
}
