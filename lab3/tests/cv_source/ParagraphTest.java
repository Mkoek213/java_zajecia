// ParagraphTest.java
package cv_source;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class ParagraphTest {

    @Test
    public void testSetContent() {
        Paragraph paragraph = new Paragraph();
        String content = "This is a test paragraph.";
        paragraph.setContent(content);
        assertEquals(content, paragraph.content);
    }

    @Test
    public void testWriteHTML() throws UnsupportedEncodingException {
        String content = "This is a test paragraph.";
        Paragraph paragraph = new Paragraph(content);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        paragraph.writeHTML(ps);

        String result = os.toString("UTF-8");
        assertEquals("<p>This is a test paragraph.</p>\n", result);
    }
}