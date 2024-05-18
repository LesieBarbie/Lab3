import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import java.io.IOException;

public class XMLValidator {
    public static void main(String[] args) {
        try {

            String schemaLang = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(schemaLang);
            Schema schema = factory.newSchema(new File("Popular_Baby_Names_NY.xsd"));
            Validator validator = schema.newValidator();


            validator.validate(new StreamSource(new File("Popular_Baby_Names_NY.xml")));

            System.out.println("Validation is successful");
        } catch (SAXException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        }
    }
}
