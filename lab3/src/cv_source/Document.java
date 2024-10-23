// Document.java
package cv_source;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Document {
    String title;
    Photo photo;
    List<Section> sections = new ArrayList<>();

    public Document(String title) {
        this.title = title;
    }

    public void setPhoto(String url) {
        this.photo = new Photo(url);
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public void writeHTML(PrintStream out) {
        try {
            out = new PrintStream(out, true, "UTF-8");
            out.printf("<!DOCTYPE html>\n<html>\n<head>\n<title>%s</title>\n</head>\n<body>\n", title);
            if (photo != null) {
                photo.writeHTML(out);
            }
            for (Section s : sections) {
                s.writeHTML(out);
            }
            out.print("</body>\n</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}