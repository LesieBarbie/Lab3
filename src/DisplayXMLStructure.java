import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.InputStream;

public class DisplayXMLStructure {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    System.out.println("Start Element: " + qName);
                    for (int i = 0; i < attributes.getLength(); i++) {
                        System.out.println("Attribute: " + attributes.getQName(i) + " = " + attributes.getValue(i));
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    String content = new String(ch, start, length).trim();
                    if (!content.isEmpty()) {
                        System.out.println("Characters: " + content);
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    System.out.println("End Element: " + qName);
                }
            };

            InputStream xmlInput = new FileInputStream("Popular_Baby_Names_NY.xml");
            saxParser.parse(xmlInput, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
