package container;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour IntPriorityQueue utilisant JUnit 5
 */
public class TestIntPriorityQueue {
    
    private IntPriorityQueue queue;
    
    @BeforeEach
    public void setUp() {
        queue = new IntPriorityQueue(3);
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
        queue.insertElement(30);
        queue.insertElement(10);
        queue.insertElement(20);
        
        assertEquals(3, queue.size());
        assertFalse(queue.isEmpty());
        assertEquals(10, queue.element()); // Le plus petit doit être à la racine
    }
    
    @Test
    public void test_popElement() {
        queue.insertElement(30);
        queue.insertElement(10);
        queue.insertElement(20);
        
        assertEquals(10, queue.popElement()); // Le plus petit en premier
        assertEquals(2, queue.size());
        assertEquals(20, queue.element()); // Le nouveau plus petit
    }
    
    @Test
    public void test_popAllElements() {
        queue.insertElement(50);
        queue.insertElement(20);
        queue.insertElement(30);
        queue.insertElement(10);
        queue.insertElement(40);
        
        assertEquals(10, queue.popElement());
        assertEquals(20, queue.popElement());
        assertEquals(30, queue.popElement());
        assertEquals(40, queue.popElement());
        assertEquals(50, queue.popElement());
        assertTrue(queue.isEmpty());
    }
    
    @Test
    public void test_resize() {
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        
        // Déclencher le redimensionnement
        queue.insertElement(5);
        
        assertEquals(4, queue.size());
        assertTrue(queue.capacity() > 3);
        assertEquals(5, queue.element()); // Le plus petit après redimensionnement
    }
    
    @Test
    public void test_priorityOrder() {
        queue.insertElement(100);
        queue.insertElement(50);
        queue.insertElement(75);
        queue.insertElement(25);
        queue.insertElement(10);
        
        assertEquals(10, queue.popElement());
        assertEquals(25, queue.popElement());
        assertEquals(50, queue.popElement());
        assertEquals(75, queue.popElement());
        assertEquals(100, queue.popElement());
    }
    
    @Test
    public void test_duplicateElements() {
        queue.insertElement(10);
        queue.insertElement(10);
        queue.insertElement(10);
        
        assertEquals(3, queue.size());
        assertEquals(10, queue.element());
        
        // Tous les éléments sont identiques, l'ordre de suppression peut varier
        // mais tous doivent être 10
        for (int i = 0; i < 3; i++) {
            assertEquals(10, queue.popElement());
        }
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
            new IntPriorityQueue(0);
        });
    }
    
    @Test
    public void test_iterator() {
        queue.insertElement(30);
        queue.insertElement(10);
        queue.insertElement(20);
        
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
    
    @Test
    public void test_singleElement() {
        queue.insertElement(42);
        assertEquals(1, queue.size());
        assertEquals(42, queue.element());
        assertEquals(42, queue.popElement());
        assertTrue(queue.isEmpty());
    }
}
