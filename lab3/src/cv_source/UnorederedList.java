package cv_source;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// UnorderedList przechowuje elementy ListItem i wypisuje listę w znacznikach <ul>... </ul>
public class UnorederedList {
    List<ListItem> items = new ArrayList<>();

    // Method to add a ListItem to the list
    public void addItem(String content) {
        items.add(new ListItem(content));
    }

    // Method to print the list in HTML format
    public void writeHTML(PrintStream out) {
        out.println("<ul>");
        for (ListItem item : items) {
            item.writeHTML(out);  // Call the writeHTML method of ListItem
        }
        out.println("</ul>");
    }
}
