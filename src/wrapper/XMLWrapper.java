package wrapper;
/**
 * Created by mikouyou on 21/11/2017.
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLWrapper extends Wrapper{
	private String sourceFilePath;
	
	public XMLWrapper(){
		sourceFilePath = "../../sources/source-xml.xml";
	}
	

    public void lire_XML (String path_fichier) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException{

        
        
    }

	@Override
	public void readAll() {
		
		try {
			
			FileInputStream fis = (FileInputStream) XMLWrapper.class.getResourceAsStream(sourceFilePath);
			
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			Document doc;
			doc = builderFactory.newDocumentBuilder().parse( fis );
			Node node ;
			Element e, e_l;
			
			//liste des etudiants
			NodeList etudiants = ((Document) doc).getElementsByTagName("Etudiant");
			NodeList l ;
			
			for (int index =0 ; index < etudiants.getLength(); index++) {
				// un etudiant
				e = (Element) etudiants.item(index);
				
				l =  e.getElementsByTagName("NumEt");
				e_l = (Element) l.item(0); // un seul noeud NumEt
				System.out.println("num etudiant "+e_l.getTextContent());
				
				l =  e.getElementsByTagName("nom");
				e_l = (Element) l.item(0);
				System.out.println("num etudiant "+e_l.getTextContent());
			}
		} catch (SAXException | IOException | ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}

