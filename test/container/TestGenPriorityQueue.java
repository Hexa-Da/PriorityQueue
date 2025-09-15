package container;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour GenPriorityQueue utilisant JUnit 5
 */
public class TestGenPriorityQueue {
    
    private GenPriorityQueue<Integer> intQueue;
    private GenPriorityQueue<String> stringQueue;
    
    @BeforeEach
    public void setUp() {
        intQueue = new GenPriorityQueue<>(3);
        stringQueue = new GenPriorityQueue<>(3);
    }
    
    @Test
    public void test_emptyCreation() {
        assertTrue(intQueue.isEmpty());
        assertEquals(0, intQueue.size());
        assertEquals(3, intQueue.capacity());
    }
    
    @Test
    public void test_insertInteger() {
        assertTrue(intQueue.insertElement(10));
        assertFalse(intQueue.isEmpty());
        assertEquals(1, intQueue.size());
        assertEquals(10, intQueue.element());
    }
    
    @Test
    public void test_insertString() {
        assertTrue(stringQueue.insertElement("Alice"));
        assertFalse(stringQueue.isEmpty());
        assertEquals(1, stringQueue.size());
        assertEquals("Alice", stringQueue.element());
    }
    
    @Test
    public void test_priorityOrderInteger() {
        intQueue.insertElement(30);
        intQueue.insertElement(10);
        intQueue.insertElement(20);
        
        assertEquals(3, intQueue.size());
        assertEquals(10, intQueue.element()); // Le plus petit en premier
        
        assertEquals(10, intQueue.popElement());
        assertEquals(20, intQueue.popElement());
        assertEquals(30, intQueue.popElement());
    }
    
    @Test
    public void test_priorityOrderString() {
        stringQueue.insertElement("Charlie");
        stringQueue.insertElement("Alice");
        stringQueue.insertElement("Bob");
        
        assertEquals(3, stringQueue.size());
        assertEquals("Alice", stringQueue.element()); // Ordre alphabétique
        
        assertEquals("Alice", stringQueue.popElement());
        assertEquals("Bob", stringQueue.popElement());
        assertEquals("Charlie", stringQueue.popElement());
    }
    
    @Test
    public void test_resize() {
        intQueue.insertElement(10);
        intQueue.insertElement(20);
        intQueue.insertElement(30);
        
        // Déclencher le redimensionnement
        intQueue.insertElement(5);
        
        assertEquals(4, intQueue.size());
        assertTrue(intQueue.capacity() > 3);
        assertEquals(5, intQueue.element()); // Le plus petit après redimensionnement
    }
    
    @Test
    public void test_insertNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            intQueue.insertElement(null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            stringQueue.insertElement(null);
        });
    }
    
    @Test
    public void test_elementOnEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> {
            intQueue.element();
        });
        
        assertThrows(NoSuchElementException.class, () -> {
            stringQueue.element();
        });
    }
    
    @Test
    public void test_popElementOnEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> {
            intQueue.popElement();
        });
        
        assertThrows(NoSuchElementException.class, () -> {
            stringQueue.popElement();
        });
    }
    
    @Test
    public void test_invalidCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GenPriorityQueue<Integer>(0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new GenPriorityQueue<String>(-1);
        });
    }
    
    @Test
    public void test_iterator() {
        intQueue.insertElement(30);
        intQueue.insertElement(10);
        intQueue.insertElement(20);
        
        int count = 0;
        for (Integer element : intQueue) {
            count++;
            assertNotNull(element);
        }
        assertEquals(3, count);
    }
    
    @Test
    public void test_toString() {
        intQueue.insertElement(10);
        intQueue.insertElement(20);
        
        String result = intQueue.toString();
        assertTrue(result.contains("10"));
        assertTrue(result.contains("20"));
        assertTrue(result.startsWith("["));
        assertTrue(result.endsWith("]"));
    }
    
    @Test
    public void test_differentTypes() {
        GenPriorityQueue<Double> doubleQueue = new GenPriorityQueue<>(2);
        GenPriorityQueue<Character> charQueue = new GenPriorityQueue<>(2);
        
        doubleQueue.insertElement(3.14);
        doubleQueue.insertElement(2.71);
        assertEquals(2.71, doubleQueue.element());
        
        charQueue.insertElement('Z');
        charQueue.insertElement('A');
        assertEquals('A', charQueue.element());
    }
    
    @Test
    public void test_duplicateElements() {
        intQueue.insertElement(10);
        intQueue.insertElement(10);
        intQueue.insertElement(10);
        
        assertEquals(3, intQueue.size());
        assertEquals(10, intQueue.element());
        
        // Tous les éléments sont identiques
        for (int i = 0; i < 3; i++) {
            assertEquals(10, intQueue.popElement());
        }
    }
    
    @Test
    public void test_singleElement() {
        intQueue.insertElement(42);
        assertEquals(1, intQueue.size());
        assertEquals(42, intQueue.element());
        assertEquals(42, intQueue.popElement());
        assertTrue(intQueue.isEmpty());
    }
}
