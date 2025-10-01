package container;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Tests unitaires pour IntPriorityQueue (File de priorité - Max-Heap)
 * Le plus GRAND élément est toujours en premier
 */
public class TestIntPriorityQueue {
    
    private IntPriorityQueue queue;
    
    @BeforeEach
    public void setUp() {
        queue = new IntPriorityQueue(3);
    }
    
    // === Tests basiques ===
    
    @Test
    public void test_newQueueIsEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }
    
    @Test
    public void test_insertOneElement() {
        queue.insertElement(10);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(10, queue.element());
    }
    
    @Test
    public void test_insertAndPopOneElement() {
        queue.insertElement(10);
        assertEquals(10, queue.popElement());
        assertTrue(queue.isEmpty());
    }
    
    // === Tests de priorité (Max-Heap: plus grand en premier) ===
    
    @Test
    public void test_maxHeapProperty() {
        queue.insertElement(10);
        queue.insertElement(30);
        queue.insertElement(20);
        
        assertEquals(30, queue.element()); // Le plus grand
        assertEquals(30, queue.popElement());
        assertEquals(20, queue.popElement());
        assertEquals(10, queue.popElement());
        assertTrue(queue.isEmpty());
    }
    
    @Test
    public void test_insertInIncreasingOrder() {
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        
        assertEquals(30, queue.popElement()); // Plus grand
        assertEquals(20, queue.popElement());
        assertEquals(10, queue.popElement()); // Plus petit
    }
    
    @Test
    public void test_insertInDecreasingOrder() {
        queue.insertElement(30);
        queue.insertElement(20);
        queue.insertElement(10);
        
        assertEquals(30, queue.popElement()); // Plus grand
        assertEquals(20, queue.popElement());
        assertEquals(10, queue.popElement()); // Plus petit
    }
    
    @Test
    public void test_complexHeapOperations() {
        // Test pour couvrir toutes les branches de heapifyUp et heapifyDown
        queue.insertElement(50);
        queue.insertElement(30);
        queue.insertElement(20);
        queue.insertElement(15);
        queue.insertElement(10);
        queue.insertElement(8);
        queue.insertElement(16);
        
        assertEquals(50, queue.popElement());
        assertEquals(30, queue.popElement());
        assertEquals(20, queue.popElement());
        assertEquals(16, queue.popElement());
        assertEquals(15, queue.popElement());
    }
    
    @Test
    public void test_heapifyWithMultipleLevels() {
        // Test pour couvrir les cas avec enfant gauche et droit
        for (int i = 1; i <= 7; i++) {
            queue.insertElement(i);
        }
        assertEquals(7, queue.element());
        queue.popElement();
        assertEquals(6, queue.element());
    }
    
    // === Tests de redimensionnement ===
    
    @Test
    public void test_autoGrowth() {
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        queue.insertElement(5); // Dépasse la capacité initiale (3)
        
        assertEquals(4, queue.size());
        assertEquals(30, queue.element()); // Le plus grand reste à la racine
    }
    
    @Test
    public void test_capacity() {
        assertEquals(3, queue.capacity());
        queue.insertElement(1);
        queue.insertElement(2);
        queue.insertElement(3);
        queue.insertElement(4); // Déclenche resize
        assertTrue(queue.capacity() > 3);
    }
    
    // === Tests avec éléments égaux ===
    
    @Test
    public void test_duplicateElements() {
        queue.insertElement(10);
        queue.insertElement(10);
        queue.insertElement(10);
        
        assertEquals(3, queue.size());
        assertEquals(10, queue.popElement());
        assertEquals(10, queue.popElement());
        assertEquals(10, queue.popElement());
    }
    
    // === Tests de l'itérateur ===
    
    @Test
    public void test_iteratorOnEmptyQueue() {
        Iterator<Integer> it = queue.iterator();
        assertFalse(it.hasNext());
    }
    
    @Test
    public void test_iteratorOnSingleElement() {
        queue.insertElement(42);
        Iterator<Integer> it = queue.iterator();
        
        assertTrue(it.hasNext());
        assertEquals(42, it.next());
        assertFalse(it.hasNext());
    }
    
    @Test
    public void test_iteratorOnMultipleElements() {
        queue.insertElement(30);
        queue.insertElement(10);
        queue.insertElement(20);
        
        Iterator<Integer> it = queue.iterator();
        int count = 0;
        while (it.hasNext()) {
            Integer val = it.next();
            assertNotNull(val);
            count++;
        }
        assertEquals(3, count);
    }
    
    @Test
    public void test_iteratorThrowsExceptionWhenEmpty() {
        Iterator<Integer> it = queue.iterator();
        assertThrows(NoSuchElementException.class, () -> it.next());
    }
    
    // === Test toString ===
    
    @Test
    public void test_toString() {
        queue.insertElement(10);
        queue.insertElement(20);
        String str = queue.toString();
        assertNotNull(str);
        assertTrue(str.contains("20")); // Le plus grand doit être présent
    }
    
    @Test
    public void test_toStringOnEmptyQueue() {
        String str = queue.toString();
        assertNotNull(str);
        assertTrue(str.contains("None"));
    }
    
    // === Tests d'erreurs ===
    
    @Test
    public void test_elementOnEmptyQueue() {
        assertThrows(Exception.class, () -> queue.element());
    }
    
    @Test
    public void test_popOnEmptyQueue() {
        assertThrows(Exception.class, () -> queue.popElement());
    }
    
    @Test
    public void test_insertNull() {
        assertThrows(Exception.class, () -> queue.insertElement(null));
    }
    
    @Test
    public void test_invalidCapacity() {
        assertThrows(Exception.class, () -> new IntPriorityQueue(0));
        assertThrows(Exception.class, () -> new IntPriorityQueue(-1));
    }
}
