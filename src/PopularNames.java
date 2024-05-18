import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

public class PopularNames {
    static class BabyName {
        String name;
        String gender;
        int count;
        int rank;

        BabyName(String name, String gender, int count, int rank) {
            this.name = name;
            this.gender = gender;
            this.count = count;
            this.rank = rank;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BabyName babyName = (BabyName) o;
            return name.equalsIgnoreCase(babyName.name) && gender.equalsIgnoreCase(babyName.gender);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name.toLowerCase(), gender.toLowerCase());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ethnicity: ");
        String targetEthnicity = scanner.nextLine().trim().toUpperCase(); // Задана етнічна група
        int numberOfNames = 10; // Кількість імен

        try {
            File inputFile = new File("Popular_Baby_Names_NY.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("row");
            Set<BabyName> babyNamesSet = new HashSet<>();
            List<BabyName> babyNamesList = new ArrayList<>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String ethnicity = element.getElementsByTagName("ethcty").item(0).getTextContent();
                    if (ethnicity.equalsIgnoreCase(targetEthnicity)) {
                        String name = element.getElementsByTagName("nm").item(0).getTextContent();
                        String gender = element.getElementsByTagName("gndr").item(0).getTextContent();
                        int count = Integer.parseInt(element.getElementsByTagName("cnt").item(0).getTextContent());
                        int rank = Integer.parseInt(element.getElementsByTagName("rnk").item(0).getTextContent());
                        BabyName babyName = new BabyName(name, gender, count, rank);
                        if (babyNamesSet.add(babyName)) {
                            babyNamesList.add(babyName);
                        }
                    }
                }
            }

            babyNamesList.sort((a, b) -> {
                int rankComparison = Integer.compare(a.rank, b.rank);
                if (rankComparison != 0) {
                    return rankComparison;
                }
                return Integer.compare(b.count, a.count);
            });

            if (babyNamesList.size() > numberOfNames) {
                babyNamesList = babyNamesList.subList(0, numberOfNames);
            }

            // Створення нового XML файлу з відсортованою інформацією
            Document newDoc = dBuilder.newDocument();
            Element rootElement = newDoc.createElement("popularNames");
            newDoc.appendChild(rootElement);

            for (BabyName babyName : babyNamesList) {
                Element nameElement = newDoc.createElement("name");
                nameElement.setAttribute("name", babyName.name);
                nameElement.setAttribute("gender", babyName.gender);
                nameElement.setAttribute("count", String.valueOf(babyName.count));
                nameElement.setAttribute("rank", String.valueOf(babyName.rank));
                rootElement.appendChild(nameElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(newDoc);
            StreamResult result = new StreamResult(new File("Sorted_Popular_Baby_Names.xml"));
            transformer.transform(source, result);

            System.out.println("Popular Names for ethnicity: " + targetEthnicity);
            System.out.println(String.format("%-15s %-10s %-10s %-10s", "Name", "Gender", "Count", "Rank"));
            for (BabyName babyName : babyNamesList) {
                System.out.println(String.format("%-15s %-10s %-10d %-10d", babyName.name, babyName.gender, babyName.count, babyName.rank));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
