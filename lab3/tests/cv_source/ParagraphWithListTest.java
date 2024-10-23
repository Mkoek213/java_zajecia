// ParagraphWithListTest.java
package cv_source;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class ParagraphWithListTest {

    @Test
    public void testAddItemToList() {
        ParagraphWithList paragraphWithList = new ParagraphWithList("Technologies");
        paragraphWithList.addItemToList("Java");
        paragraphWithList.addItemToList("C++");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        paragraphWithList.writeHTML(ps);

        String result = null;
        try {
            result = os.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        assertTrue(result.contains("<li>Java</li>"));
        assertTrue(result.contains("<li>C++</li>"));
    }

    @Test
    public void testWriteHTML() throws UnsupportedEncodingException {
        String content = "Known technologies";
        ParagraphWithList paragraphWithList = new ParagraphWithList(content);
        paragraphWithList.addItemToList("Java");
        paragraphWithList.addItemToList("C++");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        paragraphWithList.writeHTML(ps);

        String result = os.toString("UTF-8");
        assertTrue(result.contains("<p>Known technologies</p>"));
        assertTrue(result.contains("<ul>"));
        assertTrue(result.contains("<li>Java</li>"));
        assertTrue(result.contains("<li>C++</li>"));
        assertTrue(result.contains("</ul>"));
    }
}