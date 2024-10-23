package cv_source;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Section {
    Section(String title){
        this.title = title;
    }

    String title;
    List<Paragraph> paragraps = new ArrayList<>() ;

    void setTitle(String title){
        this.title = title;
    }
    void addParagraph(String paragraphText){
        Paragraph p = new Paragraph();
        p.setContent(paragraphText);
        paragraps.add(p);
    }
    void addParagraph(Paragraph p){
        paragraps.add(p);
    }
    void writeHTML(PrintStream out){
        out.printf("<h1>%s</h1>\n",title);
        for(Paragraph p : paragraps){
            p.writeHTML(out);
        }
    }
}
