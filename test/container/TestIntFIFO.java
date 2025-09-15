package container;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour IntFIFO utilisant JUnit 5
 */
public class TestIntFIFO {
    
    private IntFIFO queue;
    
    @BeforeEach
    public void setUp() {
        queue = new IntFIFO(3);
    }
    
    @Test
    public void test_emptyCreation() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertEquals(3, queue.capacity());
    }
    
    @Test
    public void test_insertElement() {
        assertTrue(queue.insertElement(10));
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(10, queue.element());
    }
    
    @Test
    public void test_insertMultipleElements() {
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        
        assertEquals(3, queue.size());
        assertFalse(queue.isEmpty());
        assertEquals(10, queue.element());
    }
    
    @Test
    public void test_popElement() {
        queue.insertElement(10);
        queue.insertElement(20);
        
        assertEquals(10, queue.popElement());
        assertEquals(1, queue.size());
        assertEquals(20, queue.element());
    }
    
    @Test
    public void test_popAllElements() {
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        
        assertEquals(10, queue.popElement());
        assertEquals(20, queue.popElement());
        assertEquals(30, queue.popElement());
        assertTrue(queue.isEmpty());
    }
    
    @Test
    public void test_resize() {
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        
        // Déclencher le redimensionnement
        queue.insertElement(40);
        
        assertEquals(4, queue.size());
        assertTrue(queue.capacity() > 3);
        assertEquals(10, queue.element());
    }
    
    @Test
    public void test_circularBehavior() {
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        queue.popElement(); // Retire 10
        queue.insertElement(40); // Ajoute 40
        
        assertEquals(3, queue.size());
        assertEquals(20, queue.element());
    }
    
    @Test
    public void test_insertNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            queue.insertElement(null);
        });
    }
    
    @Test
    public void test_elementOnEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> {
            queue.element();
        });
    }
    
    @Test
    public void test_popElementOnEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> {
            queue.popElement();
        });
    }
    
    @Test
    public void test_invalidCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            new IntFIFO(0);
        });
    }
    
    @Test
    public void test_iterator() {
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        
        int count = 0;
        for (Integer element : queue) {
            count++;
            assertNotNull(element);
        }
        assertEquals(3, count);
    }
    
    @Test
    public void test_toString() {
        queue.insertElement(10);
        queue.insertElement(20);
        
        String result = queue.toString();
        assertTrue(result.contains("10"));
        assertTrue(result.contains("20"));
        assertTrue(result.startsWith("["));
        assertTrue(result.endsWith("]"));
    }
}
