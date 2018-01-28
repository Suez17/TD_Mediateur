package mediator;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        
        // La classe WrapperType represente une enumeration.
        Mediator mediator = new Mediator( WrapperType.XML );
        
        mediator.readAll();
        
        
        //System.out.println("Result of the query : " + "\n" + mediator.getResult("SELECT Count(Type), Type FROM Cours GROUP BY Type"));
        //System.out.println("Result of the query : " + "\n" + M.getResult("SELECT Count(*) FROM Etudiant GROUP BY ID"));
        //System.out.println("Number of students : " + "\n" + M.getResult("SELECT Count(*) FROM Etudiant WHERE Provenance <> 'France' GROUP BY ID"));
    }
}
