package wrapper;

// Classe abstraite qui represente tous les Adapteurs, 
// et qui contient les methodes/attributs qui leur sont communs
public abstract class Wrapper {

	
	// Rappel : Les methodes abstraites DOIVENT etre (re)definis dans 
	// les classes filles (=> XMLWrapper, ...)
	public abstract void readAll(); //recupere tout le contenu du fichier source (ideal pour un premier test)
	
	

}
