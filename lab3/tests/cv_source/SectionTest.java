
// SectionTest.java
package cv_source;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class SectionTest {

    @Test
    public void testSetTitle() {
        Section section = new Section("Initial Title");
        section.setTitle("Updated Title");
        assertEquals("Updated Title", section.title);
    }

    @Test
    public void testAddParagraphWithText() {
        Section section = new Section("Test Section");
        section.addParagraph("This is a test paragraph.");
        assertEquals(1, section.paragraps.size());
        assertEquals("This is a test paragraph.", section.paragraps.get(0).content);
    }

    @Test
    public void testAddParagraphWithObject() {
        Section section = new Section("Test Section");
        Paragraph paragraph = new Paragraph("This is a test paragraph.");
        section.addParagraph(paragraph);
        assertEquals(1, section.paragraps.size());
        assertEquals("This is a test paragraph.", section.paragraps.get(0).content);
    }

    @Test
    public void testWriteHTML() throws UnsupportedEncodingException {
        Section section = new Section("Test Section");
        section.addParagraph("This is a test paragraph.");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        section.writeHTML(ps);

        String result = os.toString("UTF-8");
        assertTrue(result.contains("<h1>Test Section</h1>"));
        assertTrue(result.contains("<p>This is a test paragraph.</p>"));
    }
}