package mediator;
import java.util.Collection;

import wrapper.ExcelWrapper;
import wrapper.OracleWrapper;
import wrapper.Wrapper;
import wrapper.XMLWrapper;

/**
 * Created by mikouyou on 21/11/2017.
 */
public class Mediator {
    private static ExcelWrapper excWrap;
    private static Wrapper wrapper;
    
    

    public Mediator(WrapperType type) {
    	if( type == WrapperType.EXCEL ){
    		excWrap = new ExcelWrapper();
    	}
    	else if( type == WrapperType.XML ){
    		this.wrapper = new XMLWrapper();
    	}
    	else if( type == WrapperType.ORACLE ){
    		this.wrapper = new OracleWrapper();
    	}
	}

    
    
    
	public Collection<String> getResult(String query) {
//        initWrappers();
		if(excWrap != null){
			sentQueryToWrappers(query);
			return getResultFromWrapper();
		}
		return null;
	}
	
	public Object getSingleResult(String query) {
		// le resultat peut etre soit un nombre d'etudiants (entier), soit une note (double)
		Object o = wrapper.getSingleResult( query );
		return o;
	}

    public void sentQueryToWrappers(String query) {
        excWrap.getQueryFromMediator(query);
    }

    public Collection<String> getResultFromWrapper() {
        return excWrap.getQueryResult();
    }





	public void readAll() {
		wrapper.readAll();
	}
    
    // Methode commentee car l'objet deja initialise dans le constructeur
//    public void initWrappers() {
//        excWrap = new ExcelWrapper();
//    }
}
