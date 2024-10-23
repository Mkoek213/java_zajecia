package cv_source;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Document cv = new Document("Jana Kowalski - CV");
        cv.setPhoto("https://upload.wikimedia.org/wikipedia/commons/thumb/7/71/Calico_tabby_cat_-_Savannah.jpg/1200px-Calico_tabby_cat_-_Savannah.jpg");
        // Simple paragraph example
        Section sec1 = new Section("Dane osobowe");
        sec1.addParagraph("Imie i nazwisko: Jan Kowalski");
        sec1.addParagraph("Adres: ul. Kolorowa 12");
        sec1.addParagraph("Tel: 123456789");
        cv.addSection(sec1);

        // Paragraph with list example
        Section sec2 = new Section("Umiejetnosci");
        ParagraphWithList skillsParagraph = new ParagraphWithList("Znane technologie");
        skillsParagraph.addItemToList("C");
        skillsParagraph.addItemToList("C++");
        skillsParagraph.addItemToList("Java");
        sec2.addParagraph(skillsParagraph);
        cv.addSection(sec2);

        // Write to JSON
        String json_output = cv.toJson();
        System.out.println(json_output);

        try (FileWriter file = new FileWriter("cv_output.json")) {
            file.write(json_output);
        }
        // Deserialize from JSON
        Document deserializedCv = Document.fromJson(json_output);

        // Write deserialized document to HTML
        deserializedCv.writeHTML(new PrintStream("deserialized_cv.html", "UTF-8"));
    }
}
