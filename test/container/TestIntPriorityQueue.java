package container;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Timeout;
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
    @Timeout(1) // 1 seconde maximum
    public void test_emptyCreation() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertEquals(3, queue.capacity());
    }
    
    @Test
    @Timeout(1) // 1 seconde maximum
    public void test_insertElement() {
        assertTrue(queue.insertElement(10));
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(10, queue.element());
    }
    
    @Test
    @Timeout(1) // 1 seconde maximum
    public void test_insertMultipleElements() {
        queue.insertElement(30);
        queue.insertElement(10);
        queue.insertElement(20);
        
        assertEquals(3, queue.size());
        assertFalse(queue.isEmpty());
        assertEquals(30, queue.element()); // Le plus grand doit être à la racine
    }
    
    @Test
    @Timeout(1) // 1 seconde maximum
    public void test_popElement() {
        queue.insertElement(30);
        queue.insertElement(10);
        queue.insertElement(20);
        
        assertEquals(30, queue.popElement()); // Le plus grand en premier
        assertEquals(2, queue.size());
        assertEquals(20, queue.element()); // Le nouveau plus grand
    }
    
    @Test
    @Timeout(1) // 1 seconde maximum
    public void test_popAllElements() {
        System.out.println("=== Test: Suppression de tous les éléments de la priority queue ===");
        queue.insertElement(50);
        queue.insertElement(20);
        queue.insertElement(30);
        queue.insertElement(10);
        queue.insertElement(40);
        System.out.println("Initial: " + queue.toString());
        
        assertEquals(50, queue.popElement());
        System.out.println("Après pop 50: " + queue.toString());
        assertEquals(40, queue.popElement());
        System.out.println("Après pop 40: " + queue.toString());
        assertEquals(30, queue.popElement());
        System.out.println("Après pop 30: " + queue.toString());
        assertEquals(20, queue.popElement());
        System.out.println("Après pop 20: " + queue.toString());
        assertEquals(10, queue.popElement());
        assertTrue(queue.isEmpty());
        System.out.println("✓ Final: " + queue.toString());
    }
    
    @Test
    @Timeout(2) // 2 secondes pour le redimensionnement
    public void test_resize() {
        System.out.println("=== Test: Redimensionnement de la priority queue ===");
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);
        System.out.println("Avant resize: " + queue.toString() + " (cap: " + queue.capacity() + ")");
        
        queue.insertElement(5);
        assertEquals(4, queue.size());
        assertTrue(queue.capacity() > 3);
        assertEquals(30, queue.element()); // Le plus grand après redimensionnement
        System.out.println("✓ Après resize: " + queue.toString() + " (cap: " + queue.capacity() + ")");
    }
    
    @Test
    @Timeout(1) // 1 seconde maximum
    public void test_priorityOrder() {
        System.out.println("=== Test: Ordre de priorité de la priority queue ===");
        queue.insertElement(100);
        queue.insertElement(50);
        queue.insertElement(75);
        queue.insertElement(25);
        queue.insertElement(10);
        System.out.println("Initial: " + queue.toString());
        
        assertEquals(100, queue.popElement());
        System.out.println("Après pop 100: " + queue.toString());
        assertEquals(75, queue.popElement());
        System.out.println("Après pop 75: " + queue.toString());
        assertEquals(50, queue.popElement());
        System.out.println("Après pop 50: " + queue.toString());
        assertEquals(25, queue.popElement());
        System.out.println("Après pop 25: " + queue.toString());
        assertEquals(10, queue.popElement());
        System.out.println("✓ Final: " + queue.toString());
    }
    
    @Test
    @Timeout(1) // 1 seconde maximum
    public void test_duplicateElements() {
        System.out.println("=== Test: Éléments dupliqués dans la priority queue ===");
        queue.insertElement(10);
        queue.insertElement(10);
        queue.insertElement(10);
        
        assertEquals(3, queue.size());
        assertEquals(10, queue.element());
        System.out.println("Initial: " + queue.toString());
        
        // Tous les éléments sont identiques, l'ordre de suppression peut varier,
        // mais tous doivent être à 10
        for (int i = 0; i < 3; i++) {
            assertEquals(10, queue.popElement());
        }
        System.out.println("✓ Final: " + queue.toString());
    }
    
    @Test
    @Timeout(1) // 1 seconde maximum
    public void test_insertNull() {
        System.out.println("=== Test: Insertion d'un élément null dans la priority queue ===");
        assertThrows(IllegalArgumentException.class, () -> {
            queue.insertElement(null);
        });
        System.out.println("✓ Exception IllegalArgumentException levée");
    }
    
    @Test
    @Timeout(1) // 1 seconde maximum
    public void test_elementOnEmptyQueue() {
        System.out.println("=== Test: Accès à l'élément d'une priority queue vide ===");
        assertThrows(NoSuchElementException.class, () -> {
            queue.element();
        });
        System.out.println("✓ Exception NoSuchElementException levée");
    }
    
    @Test
    @Timeout(1) // 1 seconde maximum
    public void test_popElementOnEmptyQueue() {
        System.out.println("=== Test: Suppression d'un élément d'une priority queue vide ===");
        assertThrows(NoSuchElementException.class, () -> {
            queue.popElement();
        });
        System.out.println("✓ Exception NoSuchElementException levée");
    }
    
    @Test
    @Timeout(1) // 1 seconde maximum
    public void test_invalidCapacity() {
        System.out.println("=== Test: Création avec une capacité invalide ===");
        assertThrows(IllegalArgumentException.class, () -> {
            new IntPriorityQueue(0);
        });
        System.out.println("✓ Exception IllegalArgumentException levée");
    }
    
    @Test
    @Timeout(2) // 2 secondes pour l'itération
    public void test_iterator() {
        System.out.println("=== Test: Itération sur la priority queue ===");
        queue.insertElement(30);
        queue.insertElement(10);
        queue.insertElement(20);
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
        System.out.println("=== Test: Représentation string de la priority queue ===");
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
    public void test_singleElement() {
        System.out.println("\n=== Test: Gestion d'un seul élément dans la priority queue ===");
        queue.insertElement(42);
        assertEquals(1, queue.size());
        assertEquals(42, queue.element());
        System.out.println("Après insert: " + queue.toString());
        
        assertEquals(42, queue.popElement());
        assertTrue(queue.isEmpty());
        System.out.println("✓ Après pop: " + queue.toString());
    }
    
    @Test
    public void test_heapOperationsEdgeCases() {
        System.out.println("\n=== Test: Cas limites des opérations de tas ===");
        // Test avec des éléments égaux pour couvrir les branches de comparaison
        queue.insertElement(10);
        queue.insertElement(10);
        queue.insertElement(10);
        
        assertEquals(3, queue.size());
        assertEquals(10, queue.element());
        System.out.println("✓ Éléments égaux: " + queue.toString());
        
        // Test avec des éléments déjà dans le bon ordre
        IntPriorityQueue orderedQueue = new IntPriorityQueue(3);
        orderedQueue.insertElement(5);
        orderedQueue.insertElement(10);
        orderedQueue.insertElement(15);
        
        assertEquals(15, orderedQueue.element());
        System.out.println("✓ Ordre déjà correct: " + orderedQueue.toString());
    }
    
    @Test
    public void test_complexHeapScenarios() {
        System.out.println("\n=== Test: Scénarios complexes de tas ===");
        // Test avec plusieurs redimensionnements
        IntPriorityQueue smallQueue = new IntPriorityQueue(2);
        smallQueue.insertElement(50);
        smallQueue.insertElement(30);
        smallQueue.insertElement(20); // Déclenche resize
        smallQueue.insertElement(10); // Déclenche resize
        smallQueue.insertElement(5);  // Déclenche resize
        
        assertEquals(50, smallQueue.element());
        System.out.println("Après multiples redimensionnements: " + smallQueue.toString());
        
        // Vérifier que tous les éléments sont dans le bon ordre
        assertEquals(50, smallQueue.popElement());
        assertEquals(30, smallQueue.popElement());
        assertEquals(20, smallQueue.popElement());
        assertEquals(10, smallQueue.popElement());
        assertEquals(5, smallQueue.popElement());
        System.out.println("✓ Ordre de priorité maintenu après redimensionnements");
    }
    
    @Test
    public void test_edgeValues() {
        System.out.println("\n=== Test: Valeurs limites ===");
        // Test avec des valeurs limites
        queue.insertElement(Integer.MAX_VALUE);
        queue.insertElement(Integer.MIN_VALUE);
        queue.insertElement(0);
        
        assertEquals(3, queue.size());
        assertEquals(Integer.MAX_VALUE, queue.element());
        System.out.println("✓ Valeurs limites: " + queue.toString());
        
        // Test avec des valeurs négatives
        IntPriorityQueue negativeQueue = new IntPriorityQueue(3);
        negativeQueue.insertElement(-10);
        negativeQueue.insertElement(-5);
        negativeQueue.insertElement(-1);
        
        assertEquals(-1, negativeQueue.element());
        System.out.println("✓ Valeurs négatives: " + negativeQueue.toString());
    }
}
