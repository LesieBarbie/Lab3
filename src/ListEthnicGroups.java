import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class ListEthnicGroups {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            Set<String> ethnicGroups = new HashSet<>();

            DefaultHandler handler = new DefaultHandler() {
                boolean isEthnicity = false;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("ethcty")) {
                        isEthnicity = true;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (isEthnicity) {
                        String ethnicity = new String(ch, start, length).trim();
                        if (!ethnicity.isEmpty()) {
                            ethnicGroups.add(ethnicity);
                        }
                        isEthnicity = false;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("ethcty")) {
                        isEthnicity = false;
                    }
                }
            };

            InputStream xmlInput = new FileInputStream("Popular_Baby_Names_NY.xml");
            saxParser.parse(xmlInput, handler);

            System.out.println("Ethnic groups found in the document:");
            for (String group : ethnicGroups) {
                System.out.println(" - " + group);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
