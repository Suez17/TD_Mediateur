package mediator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        
        /****************** CONNEXION EXCEL *******************/
        /******************************************************/
//    	System.out.println("Hello EXCEL SECTION!");
//        Mediator mediator = new Mediator( WrapperType.EXCEL );
        //System.out.println("Result of the query : " + "\n" + mediator.getResult("SELECT Count(Type), Type FROM Cours GROUP BY Type"));
        //System.out.println("Result of the query : " + "\n" + M.getResult("SELECT Count(*) FROM Etudiant GROUP BY ID"));
        //System.out.println("Number of students : " + "\n" + M.getResult("SELECT Count(*) FROM Etudiant WHERE Provenance <> 'France' GROUP BY ID"));
        
    	
        
        
        /****************** CONNEXION XML *******************/
        /******************************************************/
    	System.out.println("Hello XML SECTION!");
    	
    	Mediator mediator = new Mediator( );
    	String globalQuery = "SELECT COUNT(*) FROM Etudiant WHERE Provenance <> 'France'";
//    	mediator.readAll();
    	ArrayList<String> result = (ArrayList) mediator.getResultFromWrappers( globalQuery );
    	
    	System.out.println("Main - result : " + result);
    	
        
    	
    	
    	
    	
        /****************** CONNEXION ORACLE *******************/
        /******************************************************/
//    	System.out.println("Hello ORACLE SECTION!");
    	
    }
}
