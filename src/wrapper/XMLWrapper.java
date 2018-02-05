package wrapper;

import java.io.File;
/**
 * Created by mikouyou on 21/11/2017.
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import static wrapper.ExcelWrapper.query;

public class XMLWrapper {
	private String sourceFilePath;
	private static HashMap<String, String> correspondenceTab;
	private Document doc;
	private String query;

	public XMLWrapper() {
		sourceFilePath = "/source-xml.xml";
		
		correspondenceTab = new HashMap<String, String>();
		correspondenceTab.put("Etudiant", "Etudiant");
		correspondenceTab.put("Provenance", "Provenance");
		
		correspondenceTab.put("Cours", "Cours");
		correspondenceTab.put("Type", "Type");
		correspondenceTab.put("TD", "Travaux diriges");
		correspondenceTab.put("TP", "Travaux pratiques");
		correspondenceTab.put("CM", "Cours Magistral");
		
		

		try {
			URL url = XMLWrapper.class.getResource(sourceFilePath);
//			System.out.println("url : " + url);
			
			File file = new File(url.toURI());
			FileInputStream fis = new FileInputStream(file);
//			System.out.println("fis : " + fis);
			
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			doc = builderFactory.newDocumentBuilder().parse(fis);
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public void readAll() {

		try {
			System.out.println("sourceFilePath : " + sourceFilePath);

			Node node;
			Element e, e_l;

			// liste des etudiants
			System.out.println("doc : " + doc);
			NodeList etudiants = ((Document) doc).getElementsByTagName("Etudiant");
			NodeList l;

			for (int index = 0; index < etudiants.getLength(); index++) {
				// un etudiant
				e = (Element) etudiants.item(index);

				l = e.getElementsByTagName("NumEt");
				e_l = (Element) l.item(0); // un seul noeud NumEt
				System.out.println("num etudiant " + e_l.getTextContent());

				l = e.getElementsByTagName("nom");
				e_l = (Element) l.item(0);
				System.out.println("num etudiant " + e_l.getTextContent());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	
	

	private Integer executeCountQuery(String query) {
		Integer nb = new Integer( 0 );
//			Query example : SELECT COUNT(*) FROM STUDENT WHERE COUNTRY != 'FRANCE'
		String[] splitQuery = query.split(" ");
        
        String section = splitQuery[3];
        String field = splitQuery[5];
        String operation =  splitQuery[6]; // Exemple : "=", "!=", ...
        
        
        String value = query.split("'")[1]; // Enleve les " ' " de la chaine
        
        
		if (section.equalsIgnoreCase("Etudiant") || section.equalsIgnoreCase("Cours") ) {
			return countFunction(section, field, operation, value);

        }else{
        	System.out.println("count function only works on STUDENT table currently.");
        }
        return nb;
	}
	
	
	public Integer countFunction(String xmlSection_name,
			String field, String operation, String value){
		
		Integer count = 0;
		
		String xmlElement = correspondenceTab.get( xmlSection_name );
		String xmlField = correspondenceTab.get( field );
		String xmlValue = correspondenceTab.get( value );
		if(xmlValue != null){
			value = xmlValue;
		}
		
		
//		System.out.println("tableName : " + xmlSection_name);
//		System.out.println("xmlElement : " + xmlElement);
		
		NodeList elementNodeList = doc.getElementsByTagName( xmlElement ); // Exemple : elementNodeList <==> List d'etudiants <==> element xml 'Etudiants'
		
		
		if ( (operation.equals("!=")) || (operation.equals("<>")) ){
			
			for (int index = 0; index < elementNodeList.getLength(); index++) {
				
				Element elementItem = (Element) elementNodeList.item(index); // Exemple : elementItm <==> un etudiant

				NodeList field_nodeList = elementItem.getElementsByTagName( xmlField );
				
				Element field_firstNode = (Element) field_nodeList.item(0);
				String field_firstValue = field_firstNode.getTextContent();
//				System.out.println("Prov value -" + provenance_value + "-");
				
//				System.out.println("value : " + value);
				if( !(field_firstValue.equals(value)) ){
					count++;
					System.out.println("count++... " + count);
				}
			}
			return count;
		} 
		
		else if( operation.equals("=") ){
			
			for (int index = 0; index < elementNodeList.getLength(); index++) {
//				if()
				
				Element elementItem = (Element) elementNodeList.item(index); // Exemple : elementItm <==> un etudiant

				NodeList field_nodeList = elementItem.getElementsByTagName( xmlField );
//				System.out.println("field_nodeList : " + field_nodeList);
				
				Element field_firstNode = (Element) field_nodeList.item(0);
				String field_firstValue = field_firstNode.getTextContent();
				
//				System.out.println("field_firstValue : -" + field_firstValue + "-");
//				System.out.println("value : " + value);
			
				if( field_firstValue.equals(value) ){
					count++;
					System.out.println("count++... " + count);
				}
			}
			return count;
			
		} else {
			System.out.println("XMLWrapper.countFunction() - Unknown operation. Only '=' and '!=' (or '<>') operation are executed for now...");
		}
		
		return count;
	}

	public Collection<String> getQueryResult() {
		Collection<String> resultat  = new ArrayList();

		Integer nb = 0;
		String COUNT_QUERY = "SELECT COUNT(*)";
		String query_type = null;

		System.out.println("query : " + query);
		query_type = query.substring(0, 15);
//		System.out.println("query_type : " + query_type);

		if (query_type.equalsIgnoreCase(COUNT_QUERY)) {
			System.out.println("Matchs count_query! ");

			nb = executeCountQuery(query);
		} else {
			System.out.println("Doesn't match.");
		}

		resultat.add( nb.toString() );
		return resultat;
	}

	public void getQueryFromMediator(String query) {
		this.query = query;
	}
}
