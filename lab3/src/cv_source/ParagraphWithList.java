package cv_source;

import java.io.PrintStream;

import static java.lang.System.out;

public class ParagraphWithList extends Paragraph {
    UnorederedList list = new UnorederedList();

    public ParagraphWithList() {
        super();  // Call Paragraph constructor
    }

    public ParagraphWithList(String content) {
        super(content);  // Call Paragraph constructor with content
    }

    // Add an item to the list
    public void addItemToList(String content) {
        list.addItem(content);
    }

    // Overriding the writeHTML method to include both the paragraph and the list
    public void writeHTML(PrintStream out) {
        // Write the paragraph
        super.writeHTML(out);
        // Write the unordered list after the paragraph
        list.writeHTML(out);
    }
}
