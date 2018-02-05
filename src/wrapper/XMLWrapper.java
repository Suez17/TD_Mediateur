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
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLWrapper extends Wrapper {
	private String sourceFilePath;
	private static HashMap<String, String> correspondenceTab;
	private Document doc;

	public XMLWrapper() {
		sourceFilePath = "/source-xml.xml";
		
		correspondenceTab = new HashMap<String, String>();
		correspondenceTab.put("STUDENT", "Etudiant");
		correspondenceTab.put("COUNTRY", "Provenance");
		
		
		try {
			URL url = XMLWrapper.class.getResource(sourceFilePath);
			System.out.println("url : " + url);
			
			File file = new File(url.toURI());
			FileInputStream fis = new FileInputStream(file);
			System.out.println("fis : " + fis);
			
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			doc = builderFactory.newDocumentBuilder().parse(fis);
			System.out.println("cstrctor - doc : " + doc);
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void lire_XML(String path_fichier)
			throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {

	}

	@Override
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

	// select count(*)
	@Override
	public Object getSingleResult(String query) {
		Integer nb = 0;
		String COUNT_QUERY = "SELECT COUNT(*)";
		String query_type = null;

		System.out.println("query : " + query);
		query_type = query.substring(0, 15);
		System.out.println("query_type : " + query_type);

		if (query_type.equalsIgnoreCase(COUNT_QUERY)) {
			System.out.println("Matchs count_query! ");

			nb = executeCountQuery(query);
		} else {
			System.out.println("Doesn't match.");
		}

		return nb;
	}

	private Integer executeCountQuery(String query) {
		Integer nb = new Integer( 0 );
//			Query example : SELECT COUNT(*) FROM STUDENT WHERE COUNTRY != 'FRANCE'
		String[] splitQuery = query.split(" ");
        String queryConverted = "", currentQueryElement, charAtEndOfElement;
        
        String section = splitQuery[3];
        String field = splitQuery[5];
        String operation =  splitQuery[6]; // Exemple : "=", "!=", ...
        String value = splitQuery[7];
        
        value = value.split("'")[1]; // Enleve les " ' " de la chaine
        
        
		if (section.equalsIgnoreCase("STUDENT")) {
			return countFunction("STUDENT", field, operation, value);

        }else{
        	System.out.println("count function only works on STUDENT table currently.");
        }
        return nb;
	}
	
	
	public Integer countFunction(String tableName,
			String field, String operation, String value){
		
		Integer count = 0;
		
		String xmlElement = correspondenceTab.get( tableName );
		String xmlField = correspondenceTab.get( field );
		
		System.out.println("countFct - doc : " + doc);
		System.out.println("xmlElement : " + xmlElement);
		
		NodeList etudiants = doc.getElementsByTagName( xmlElement );
		
		
		
		if ( operation.equals("!=") ){
			
			for (int index = 0; index < etudiants.getLength(); index++) {
				// un etudiant
				Element etu = (Element) etudiants.item(index);

				NodeList provenance_nodeList = etu.getElementsByTagName("Provenance");
				
				Element provenance_node = (Element) provenance_nodeList.item(0); // le premier noeud Provenance
				String provenance_value = provenance_node.getTextContent();
				System.out.println("Prov value -" + provenance_value + "-");
				
				System.out.println("value : " + value);
				if( provenance_value.equals(value) ){
					System.out.println("count" + count);
					count++;
				}
			}
			return count;
		}
		
		return count;
	}

}
