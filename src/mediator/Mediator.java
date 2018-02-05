package mediator;
import java.util.ArrayList;
import java.util.Collection;

import wrapper.ExcelWrapper;
import wrapper.OracleWrapper;
import wrapper.XMLWrapper;

/**
 * Created by mikouyou on 21/11/2017.
 */
public class Mediator {
    private ExcelWrapper excWrap;
    private OracleWrapper oracleWrap;
    private XMLWrapper xmlWrap;

    public Mediator() {
        this.excWrap = new ExcelWrapper();
        this.oracleWrap = new OracleWrapper();
        this.xmlWrap = new XMLWrapper();
    }

    public Collection<String> getResultFromWrappers(String query) {
        sendQueryToWrappers(query);
        Collection<String> resultat = new ArrayList<>();
        resultat.addAll(excWrap.getQueryResult());
        //resultat.addAll(oracleWrap.getQueryResult());
        resultat.addAll(xmlWrap.getQueryResult());
        return resultat;
	}


    public void sendQueryToWrappers(String query) {
		oracleWrap.getQueryFromMediator(query);
    	excWrap.getQueryFromMediator(query);
    	xmlWrap.getQueryFromMediator(query);
    }




/*
	public void readAll() {
		wrapper.readAll();
	}
   */
    // Methode commentee car l'objet deja initialise dans le constructeur
//    public void initWrappers() {
//        excWrap = new ExcelWrapper();
//    }
}
