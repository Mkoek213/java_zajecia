package cv_source;

import java.io.PrintStream;

// ListItem zawiera wyłącznie tekst i wypisuje go w znacznikach <li>...</li>
public class ListItem {
    String content;

    public ListItem(String content) {
        this.content = content;
    }

    public void writeHTML(PrintStream out) {
        out.println("<li>" + content + "</li>");
    }
}
