package mediator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

    	/* Exemple requetes qui marchent sur XML :
    	 * 
    	 * String globalQuery = "SELECT COUNT(*) FROM Cours WHERE Type != 'CM'"
    	 * String globalQuery = "SELECT COUNT(*) FROM Cours WHERE Type = 'TD'"
    	 * String globalQuery = "SELECT COUNT(*) FROM Etudiants WHERE Provenance != 'France'"
    	 * String globalQuery = "SELECT COUNT(*) FROM Etudiants WHERE Provenance = 'Etats unis'"
    	 * */
    	
    	Mediator mediator = new Mediator( );
    	String globalQuery = "SELECT COUNT(*) FROM Cours WHERE Type = 'TD'";
    	ArrayList<String> result = (ArrayList) mediator.getResultFromWrappers( globalQuery );
    	
    	System.out.println("Main - result : " + result);
    }
}
