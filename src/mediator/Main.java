package mediator;

import java.sql.SQLException;

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
    	
    	Mediator mediator = new Mediator( WrapperType.XML );
    	String globalQuery = "SELECT COUNT(*) FROM STUDENT WHERE COUNTRY != 'France'";
//    	mediator.readAll();
    	int nb = (Integer) mediator.getSingleResult( globalQuery );
    	
    	System.out.println("nb : " + nb);
    	
        
    	
    	
    	
    	
        /****************** CONNEXION ORACLE *******************/
        /******************************************************/
//    	System.out.println("Hello ORACLE SECTION!");
    	
    }
}
