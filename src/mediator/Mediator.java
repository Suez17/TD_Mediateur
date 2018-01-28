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
    		this.wrapper = new ExcelWrapper();
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
        sentQueryToWrappers(query);
        return getResultFromWrappers();
    }

    public void sentQueryToWrappers(String query) {
        excWrap.getQueryFromMediator(query);
    }

    public Collection<String> getResultFromWrappers() {
        return excWrap.getQueryResult();
    }





	public void readAll() {
		wrapper.readAll();
		
	}
    
    // Methode commentee car l'objet deja initialisé dans  constructeur
//    public void initWrappers() {
//        excWrap = new ExcelWrapper();
//    }
}
