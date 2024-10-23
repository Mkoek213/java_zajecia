package cv_source;

import java.io.PrintStream;

// Atrybut content to treść akapitu.
public class Paragraph {
    String content;

    public Paragraph() {
        // Default constructor
    }

    public Paragraph(String content) {
        this.content = content;
    }

    // Metoda setContent() zmienia treść
    public void setContent(String content) {
        this.content = content;
    }

    // Metoda writeHTML() umieszcza treść pomiędzy znacznikami <p>…</p>
    public void writeHTML(PrintStream out) {
        out.println("<p>" + content + "</p>");
    }
}
