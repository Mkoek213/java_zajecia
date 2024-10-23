// UnorederedListTest.java
package cv_source;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class UnorederedListTest {

    @Test
    public void testAddItem() {
        UnorederedList list = new UnorederedList();
        list.addItem("Item 1");
        list.addItem("Item 2");

        assertEquals(2, list.items.size());
        assertEquals("Item 1", list.items.get(0).content);
        assertEquals("Item 2", list.items.get(1).content);
    }

    @Test
    public void testWriteHTML() throws UnsupportedEncodingException {
        UnorederedList list = new UnorederedList();
        list.addItem("Item 1");
        list.addItem("Item 2");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        list.writeHTML(ps);

        String result = os.toString("UTF-8");
        assertTrue(result.contains("<ul>"));
        assertTrue(result.contains("<li>Item 1</li>"));
        assertTrue(result.contains("<li>Item 2</li>"));
        assertTrue(result.contains("</ul>"));
    }
}