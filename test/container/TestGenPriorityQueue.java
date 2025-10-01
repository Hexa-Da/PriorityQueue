package container;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Tests unitaires pour GenPriorityQueue (File de priorité générique - Max-Heap)
 * Le plus GRAND élément est toujours en premier
 */
public class TestGenPriorityQueue {
    
    private GenPriorityQueue<Integer> intQueue;
    private GenPriorityQueue<String> stringQueue;
    
    @BeforeEach
    public void setUp() {
        intQueue = new GenPriorityQueue<>(3);
        stringQueue = new GenPriorityQueue<>(3);
    }
    
    // === Tests basiques avec Integer ===
    
    @Test
    public void test_newQueueIsEmpty() {
        assertTrue(intQueue.isEmpty());
        assertEquals(0, intQueue.size());
    }
    
    @Test
    public void test_insertOneInteger() {
        intQueue.insertElement(10);
        assertFalse(intQueue.isEmpty());
        assertEquals(1, intQueue.size());
        assertEquals(10, intQueue.element());
    }
    
    // === Tests de priorité avec Integer (Max-Heap) ===
    
    @Test
    public void test_maxHeapPropertyWithIntegers() {
        intQueue.insertElement(10);
        intQueue.insertElement(30);
        intQueue.insertElement(20);
        
        assertEquals(30, intQueue.element()); // Le plus grand
        assertEquals(30, intQueue.popElement());
        assertEquals(20, intQueue.popElement());
        assertEquals(10, intQueue.popElement());
    }
    
    @Test
    public void test_complexHeapOperations() {
        // Test pour couvrir toutes les branches de heapifyUp et heapifyDown
        intQueue.insertElement(50);
        intQueue.insertElement(30);
        intQueue.insertElement(20);
        intQueue.insertElement(15);
        intQueue.insertElement(10);
        intQueue.insertElement(8);
        intQueue.insertElement(16);
        
        assertEquals(50, intQueue.popElement());
        assertEquals(30, intQueue.popElement());
        assertEquals(20, intQueue.popElement());
        assertEquals(16, intQueue.popElement());
    }
    
    // === Tests avec String ===
    
    @Test
    public void test_maxHeapPropertyWithStrings() {
        stringQueue.insertElement("Alice");
        stringQueue.insertElement("Charlie");
        stringQueue.insertElement("Bob");
        
        // Ordre alphabétique: "Charlie" > "Bob" > "Alice"
        assertEquals("Charlie", stringQueue.element());
        assertEquals("Charlie", stringQueue.popElement());
        assertEquals("Bob", stringQueue.popElement());
        assertEquals("Alice", stringQueue.popElement());
    }
    
    // === Tests de redimensionnement ===
    
    @Test
    public void test_autoGrowth() {
        intQueue.insertElement(10);
        intQueue.insertElement(20);
        intQueue.insertElement(30);
        intQueue.insertElement(5); // Dépasse la capacité initiale (3)
        
        assertEquals(4, intQueue.size());
        assertEquals(30, intQueue.element());
    }
    
    @Test
    public void test_capacity() {
        assertEquals(3, intQueue.capacity());
        intQueue.insertElement(1);
        intQueue.insertElement(2);
        intQueue.insertElement(3);
        intQueue.insertElement(4); // Déclenche resize
        assertTrue(intQueue.capacity() > 3);
    }
    
    // === Tests avec différents types ===
    
    @Test
    public void test_withDoubles() {
        GenPriorityQueue<Double> doubleQueue = new GenPriorityQueue<>(3);
        doubleQueue.insertElement(1.5);
        doubleQueue.insertElement(3.14);
        doubleQueue.insertElement(2.71);
        
        assertEquals(3.14, doubleQueue.element());
        assertEquals(3.14, doubleQueue.popElement());
        assertEquals(2.71, doubleQueue.popElement());
    }
    
    @Test
    public void test_withCharacters() {
        GenPriorityQueue<Character> charQueue = new GenPriorityQueue<>(3);
        charQueue.insertElement('A');
        charQueue.insertElement('Z');
        charQueue.insertElement('M');
        
        assertEquals('Z', charQueue.element()); // 'Z' > 'M' > 'A'
        assertEquals('Z', charQueue.popElement());
        assertEquals('M', charQueue.popElement());
    }
    
    // === Tests de l'itérateur ===
    
    @Test
    public void test_iteratorOnEmptyQueue() {
        Iterator<Integer> it = intQueue.iterator();
        assertFalse(it.hasNext());
    }
    
    @Test
    public void test_iteratorOnSingleElement() {
        intQueue.insertElement(42);
        Iterator<Integer> it = intQueue.iterator();
        
        assertTrue(it.hasNext());
        assertEquals(42, it.next());
        assertFalse(it.hasNext());
    }
    
    @Test
    public void test_iteratorOnMultipleElements() {
        intQueue.insertElement(30);
        intQueue.insertElement(10);
        intQueue.insertElement(20);
        
        Iterator<Integer> it = intQueue.iterator();
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
        Iterator<Integer> it = intQueue.iterator();
        assertThrows(NoSuchElementException.class, () -> it.next());
    }
    
    @Test
    public void test_iteratorWithStrings() {
        stringQueue.insertElement("A");
        stringQueue.insertElement("B");
        
        Iterator<String> it = stringQueue.iterator();
        assertTrue(it.hasNext());
        assertNotNull(it.next());
        assertTrue(it.hasNext());
        assertNotNull(it.next());
        assertFalse(it.hasNext());
    }
    
    // === Test toString ===
    
    @Test
    public void test_toString() {
        intQueue.insertElement(10);
        intQueue.insertElement(20);
        String str = intQueue.toString();
        assertNotNull(str);
        assertTrue(str.contains("20")); // Le plus grand doit être présent
    }
    
    @Test
    public void test_toStringOnEmptyQueue() {
        String str = intQueue.toString();
        assertNotNull(str);
        assertTrue(str.contains("None"));
    }
    
    // === Tests d'erreurs ===
    
    @Test
    public void test_elementOnEmptyQueue() {
        assertThrows(Exception.class, () -> intQueue.element());
    }
    
    @Test
    public void test_popOnEmptyQueue() {
        assertThrows(Exception.class, () -> intQueue.popElement());
    }
    
    @Test
    public void test_insertNull() {
        assertThrows(Exception.class, () -> intQueue.insertElement(null));
    }
    
    @Test
    public void test_invalidCapacity() {
        assertThrows(Exception.class, () -> new GenPriorityQueue<Integer>(0));
        assertThrows(Exception.class, () -> new GenPriorityQueue<String>(-1));
    }
}
