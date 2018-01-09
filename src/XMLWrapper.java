/**
 * Created by mikouyou on 21/11/2017.
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLWrapper {

    public void lire_XML (String path_fichier) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException{

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        org.w3c.dom.Document doc = builderFactory.newDocumentBuilder().parse(new FileInputStream(path_fichier));
        Node node ;
        Element E, E_1;

        //liste des etudiants
        NodeList etudiants = ((org.w3c.dom.Document) doc).getElementsByTagName("Etudiant");
        NodeList L ;

        for (int index =0 ; index < etudiants.getLength(); index++) {
            // un etudiant
            E = (Element) etudiants.item(index);

            L =  E.getElementsByTagName("NumEt");
            E_1 = (Element) L.item(0); // un seul noeud NumEt
            System.out.println("num etudiant "+E_1.getTextContent());

            L =  E.getElementsByTagName("nom");
            E_1 = (Element) L.item(0);
            System.out.println("num etudiant "+E_1.getTextContent());
        }
    }
}

