package container;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Timeout;
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
    @Timeout(1) // 1 seconde maximum
    public void test_emptyCreation() {
        System.out.println("=== Test: Création d'une queue vide ===");
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertEquals(3, queue.capacity());
        System.out.println("✓ Queue: " + queue.toString());
    }
    
    @Test
    @Timeout(1) // 1 seconde maximum
    public void test_insertElement() {
        System.out.println("=== Test: Insertion d'un élément ===");
        assertTrue(queue.insertElement(10));
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(10, queue.element());
        System.out.println("✓ Queue: " + queue.toString());
    }
    
    @Test
    public void test_insertMultipleElements() {
        System.out.println("=== Test: Insertion de plusieurs éléments ===");
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        
        assertEquals(3, queue.size());
        assertFalse(queue.isEmpty());
        assertEquals(10, queue.element());
        System.out.println("✓ Queue: " + queue.toString());
    }
    
    @Test
    @Timeout(1) // 1 seconde maximum
    public void test_popElement() {
        System.out.println("=== Test: Suppression d'un élément ===");
        queue.insertElement(10);
        queue.insertElement(20);
        System.out.println("Avant pop: " + queue.toString());
        
        assertEquals(10, queue.popElement());
        assertEquals(1, queue.size());
        assertEquals(20, queue.element());
        System.out.println("✓ Après pop: " + queue.toString());
    }
    
    @Test
    public void test_popAllElements() {
        System.out.println("=== Test: Suppression de tous les éléments ===");
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        System.out.println("Initial: " + queue.toString());
        
        assertEquals(10, queue.popElement());
        System.out.println("Après pop 10: " + queue);
        assertEquals(20, queue.popElement());
        System.out.println("Après pop 20: " + queue.toString());
        assertEquals(30, queue.popElement());
        assertTrue(queue.isEmpty());
        System.out.println("✓ Final: " + queue.toString());
    }
    
    @Test
    @Timeout(2) // 2 secondes pour le redimensionnement
    public void test_resize() {
        System.out.println("=== Test: Redimensionnement de la queue ===");
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        System.out.println("Avant resize: " + queue.toString() + " (cap: " + queue.capacity() + ")");
        
        queue.insertElement(40);
        assertEquals(4, queue.size());
        assertTrue(queue.capacity() > 3);
        assertEquals(10, queue.element());
        System.out.println("✓ Après resize: " + queue.toString() + " (cap: " + queue.capacity() + ")");
    }
    
    @Test
    public void test_circularBehavior() {
        System.out.println("=== Test: Comportement circulaire de la queue ===");
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        System.out.println("Initial: " + queue.toString());
        
        queue.popElement(); // Retire 10
        System.out.println("Après pop: " + queue.toString());
        
        queue.insertElement(40); // Ajoute 40
        assertEquals(3, queue.size());
        assertEquals(20, queue.element());
        System.out.println("✓ Après insert 40: " + queue.toString());
    }
    
    @Test
    public void test_insertNull() {
        System.out.println("=== Test: Insertion d'un élément null ===");
        assertThrows(IllegalArgumentException.class, () -> {
            queue.insertElement(null);
        });
        System.out.println("✓ Exception IllegalArgumentException levée");
    }
    
    @Test
    public void test_elementOnEmptyQueue() {
        System.out.println("=== Test: Accès à l'élément d'une queue vide ===");
        assertThrows(NoSuchElementException.class, () -> {
            queue.element();
        });
        System.out.println("✓ Exception NoSuchElementException levée");
    }
    
    @Test
    public void test_popElementOnEmptyQueue() {
        System.out.println("=== Test: Suppression d'un élément d'une queue vide ===");
        assertThrows(NoSuchElementException.class, () -> {
            queue.popElement();
        });
        System.out.println("✓ Exception NoSuchElementException levée");
    }
    
    @Test
    public void test_invalidCapacity() {
        System.out.println("=== Test: Création avec une capacité invalide ===");
        assertThrows(IllegalArgumentException.class, () -> {
            new IntFIFO(0);
        });
        System.out.println("✓ Exception IllegalArgumentException levée");
    }
    
    @Test
    public void test_iterator() {
        System.out.println("=== Test: Itération sur la queue ===");
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        System.out.println("Queue: " + queue.toString());
        
        int count = 0;
        for (Integer element : queue) {
            count++;
            assertNotNull(element);
        }
        assertEquals(3, count);
        System.out.println("✓ Itération: " + count + " éléments parcourus");
    }
    
    @Test
    public void test_toString() {
        System.out.println("\n=== Test: Représentation string de la queue ===");
        queue.insertElement(10);
        queue.insertElement(20);
        
        String result = queue.toString();
        System.out.println("toString(): " + result);
        assertTrue(result.contains("10"));
        assertTrue(result.contains("20"));
        assertTrue(result.startsWith("["));
        assertTrue(result.endsWith("]"));
        System.out.println("✓ Format correct");
    }
    
    @Test
    public void test_edgeCases() {
        System.out.println("\n=== Test: Cas limites supplémentaires ===");
        // Test avec des valeurs limites
        queue.insertElement(Integer.MAX_VALUE);
        queue.insertElement(Integer.MIN_VALUE);
        queue.insertElement(0);
        
        assertEquals(3, queue.size());
        assertEquals(Integer.MAX_VALUE, queue.element()); // FIFO: premier inséré = premier sorti
        System.out.println("✓ Valeurs limites: " + queue.toString());
        
        // Test redimensionnement multiple
        queue.insertElement(1); // Déclenche resize
        queue.insertElement(2); // Déclenche resize
        queue.insertElement(3); // Déclenche resize
        
        assertEquals(6, queue.size());
        assertTrue(queue.capacity() > 3);
        System.out.println("✓ Multiples redimensionnements: " + queue.toString());
    }
    
    @Test
    public void test_iteratorEdgeCases() {
        System.out.println("\n=== Test: Cas limites de l'itérateur ===");
        // Test itérateur sur queue vide
        int count = 0;
        for (Integer element : queue) {
            count++;
        }
        assertEquals(0, count);
        System.out.println("✓ Itérateur sur queue vide: " + count + " éléments");
        
        // Test itérateur avec un seul élément
        queue.insertElement(42);
        count = 0;
        for (Integer element : queue) {
            count++;
            assertNotNull(element);
        }
        assertEquals(1, count);
        System.out.println("✓ Itérateur sur un élément: " + count + " éléments");
    }
}
