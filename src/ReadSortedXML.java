import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ReadSortedXML {
    public static void main(String[] args) {
        try {
            File inputFile = new File("Sorted_Popular_Baby_Names.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("name");

            System.out.println("Popular Names from Sorted XML:");
            System.out.println(String.format("%-15s %-10s %-10s %-10s", "Name", "Gender", "Count", "Rank"));
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String name = element.getAttribute("name");
                String gender = element.getAttribute("gender");
                int count = Integer.parseInt(element.getAttribute("count"));
                int rank = Integer.parseInt(element.getAttribute("rank"));
                System.out.println(String.format("%-15s %-10s %-10d %-10d", name, gender, count, rank));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
