package container;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;

/**
 * Tests unitaires pour IntFIFO (File FIFO)
 */
public class TestIntFIFO {
    
    private IntFIFO queue;
    
    @BeforeEach
    public void setUp() {
        queue = new IntFIFO(3);
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
    
    // === Tests FIFO (ordre d'insertion) ===
    
    @Test
    public void test_fifoOrder() {
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        
        assertEquals(10, queue.popElement()); // Premier inséré
        assertEquals(20, queue.popElement()); // Deuxième inséré
        assertEquals(30, queue.popElement()); // Troisième inséré
        assertTrue(queue.isEmpty());
    }
    
    // === Tests de redimensionnement ===
    
    @Test
    public void test_autoGrowth() {
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        queue.insertElement(40); // Dépasse la capacité initiale (3)
        
        assertEquals(4, queue.size());
        assertEquals(10, queue.element());
    }
    
    @Test
    public void test_multipleResizes() {
        // Test pour couvrir le cas où rear < front après redimensionnement
        queue.insertElement(1);
        queue.insertElement(2);
        queue.insertElement(3);
        queue.popElement(); // Fait avancer front
        queue.insertElement(4); // Dépasse la capacité, déclenche resize
        queue.insertElement(5); // Encore un resize
        
        assertEquals(4, queue.size());
        assertEquals(2, queue.element());
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
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        
        Iterator<Integer> it = queue.iterator();
        assertTrue(it.hasNext());
        assertEquals(10, it.next());
        assertTrue(it.hasNext());
        assertEquals(20, it.next());
        assertTrue(it.hasNext());
        assertEquals(30, it.next());
        assertFalse(it.hasNext());
    }
    
    @Test
    public void test_iteratorWithCircularBuffer() {
        // Test pour couvrir le cas où rear < front dans l'itérateur
        queue.insertElement(1);
        queue.insertElement(2);
        queue.insertElement(3);
        queue.popElement(); // front avance
        queue.popElement(); // front avance encore
        queue.insertElement(4); // rear dépasse la fin et revient au début
        queue.insertElement(5);
        
        Iterator<Integer> it = queue.iterator();
        assertEquals(3, it.next());
        assertEquals(4, it.next());
        assertEquals(5, it.next());
        assertFalse(it.hasNext());
    }
    
    // === Tests toString (pour 100% de couverture) ===
    
    @Test
    public void test_toStringEmptyQueue() {
        String str = queue.toString();
        assertNotNull(str);
        assertTrue(str.contains("None"));
    }
    
    @Test
    public void test_toStringWithOneElement() {
        queue.insertElement(10);
        String str = queue.toString();
        assertNotNull(str);
        assertTrue(str.contains("10"));
        assertTrue(str.contains("None"));
    }
    
    @Test
    public void test_toStringWithTwoElements() {
        queue.insertElement(10);
        queue.insertElement(20);
        String str = queue.toString();
        assertNotNull(str);
        assertTrue(str.contains("10"));
        assertTrue(str.contains("20"));
        assertTrue(str.contains("None"));
    }
    
    @Test
    public void test_toStringFullQueue() {
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        String str = queue.toString();
        assertNotNull(str);
        assertTrue(str.contains("10"));
        assertTrue(str.contains("20"));
        assertTrue(str.contains("30"));
    }
    
    @Test
    public void test_toStringAfterPopAndInsert() {
        // Test pour couvrir différentes branches de toString
        queue.insertElement(1);
        queue.insertElement(2);
        queue.insertElement(3);
        queue.popElement();
        queue.insertElement(4);
        String str = queue.toString();
        assertNotNull(str);
        assertTrue(str.contains("2"));
        assertTrue(str.contains("3"));
        assertTrue(str.contains("4"));
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
        assertThrows(Exception.class, () -> new IntFIFO(0));
        assertThrows(Exception.class, () -> new IntFIFO(-1));
    }

    @Test
    public void test_iterator_Exception() {
        // Cas 1: itérateur sur file vide -> next() doit lever NoSuchElementException
        Iterator<Integer> itEmpty = queue.iterator();
        assertFalse(itEmpty.hasNext());
        assertThrows(java.util.NoSuchElementException.class, () -> itEmpty.next());

        // Cas 2: itérateur épuisé -> next() doit lever NoSuchElementException (pas de NPE)
        queue.insertElement(99);
        Iterator<Integer> itOne = queue.iterator();
        assertTrue(itOne.hasNext());
        assertEquals(99, itOne.next());
        assertFalse(itOne.hasNext());
        assertThrows(java.util.NoSuchElementException.class, () -> itOne.next());
    }

    @Test
    public void test_iterator_Empty() {
        // Après insertion d'un élément, hasNext() doit être true
        queue.insertElement(1);
        Iterator<Integer> it = queue.iterator();
        assertTrue(it.hasNext());
        assertEquals(1, it.next());
        // Après consommation du seul élément, hasNext() devient false
        assertFalse(it.hasNext());
    }
}
