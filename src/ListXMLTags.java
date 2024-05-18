import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class ListXMLTags {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            Set<String> tags = new HashSet<>();

            DefaultHandler handler = new DefaultHandler() {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    tags.add(qName);
                }
            };

            InputStream xmlInput = new FileInputStream("Popular_Baby_Names_NY.xml");
            saxParser.parse(xmlInput, handler);

            System.out.println("Tags found in the document: " + tags);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
