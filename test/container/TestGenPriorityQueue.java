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
        System.out.println("=== Test: Création d'une GenPriorityQueue vide ===");
        assertTrue(intQueue.isEmpty());
        assertEquals(0, intQueue.size());
        assertEquals(3, intQueue.capacity());
        System.out.println("✓ Queue: " + intQueue.toString());
    }
    
    @Test
    public void test_insertInteger() {
        System.out.println("=== Test: Insertion d'un entier dans la GenPriorityQueue ===");
        assertTrue(intQueue.insertElement(10));
        assertFalse(intQueue.isEmpty());
        assertEquals(1, intQueue.size());
        assertEquals(10, intQueue.element());
        System.out.println("✓ Queue: " + intQueue.toString());
    }
    
    @Test
    public void test_insertString() {
        System.out.println("=== Test: Insertion d'une chaîne dans la GenPriorityQueue ===");
        assertTrue(stringQueue.insertElement("Alice"));
        assertFalse(stringQueue.isEmpty());
        assertEquals(1, stringQueue.size());
        assertEquals("Alice", stringQueue.element());
        System.out.println("✓ Queue: " + stringQueue.toString());
    }
    
    @Test
    public void test_priorityOrderInteger() {
        System.out.println("=== Test: Ordre de priorité avec des entiers ===");
        intQueue.insertElement(30);
        intQueue.insertElement(10);
        intQueue.insertElement(20);
        
        assertEquals(3, intQueue.size());
        assertEquals(10, intQueue.element()); // Le plus petit en premier
        System.out.println("Initial: " + intQueue.toString());
        
        assertEquals(10, intQueue.popElement());
        System.out.println("Après pop 10: " + intQueue.toString());
        assertEquals(20, intQueue.popElement());
        System.out.println("Après pop 20: " + intQueue.toString());
        assertEquals(30, intQueue.popElement());
        System.out.println("✓ Final: " + intQueue.toString());
    }
    
    @Test
    public void test_priorityOrderString() {
        System.out.println("=== Test: Ordre de priorité avec des chaînes ===");
        stringQueue.insertElement("Charlie");
        stringQueue.insertElement("Alice");
        stringQueue.insertElement("Bob");
        
        assertEquals(3, stringQueue.size());
        assertEquals("Alice", stringQueue.element()); // Ordre alphabétique
        System.out.println("Initial: " + stringQueue.toString());
        
        assertEquals("Alice", stringQueue.popElement());
        System.out.println("Après pop Alice: " + stringQueue.toString());
        assertEquals("Bob", stringQueue.popElement());
        System.out.println("Après pop Bob: " + stringQueue.toString());
        assertEquals("Charlie", stringQueue.popElement());
        System.out.println("✓ Final: " + stringQueue.toString());
    }
    
    @Test
    public void test_resize() {
        System.out.println("=== Test: Redimensionnement de la GenPriorityQueue ===");
        intQueue.insertElement(10);
        intQueue.insertElement(20);
        intQueue.insertElement(30);
        System.out.println("Avant resize: " + intQueue.toString() + " (cap: " + intQueue.capacity() + ")");
        
        intQueue.insertElement(5);
        assertEquals(4, intQueue.size());
        assertTrue(intQueue.capacity() > 3);
        assertEquals(5, intQueue.element()); // Le plus petit après redimensionnement
        System.out.println("✓ Après resize: " + intQueue.toString() + " (cap: " + intQueue.capacity() + ")");
    }
    
    @Test
    public void test_insertNull() {
        System.out.println("=== Test: Insertion d'éléments null dans la GenPriorityQueue ===");
        assertThrows(IllegalArgumentException.class, () -> {
            intQueue.insertElement(null);
        });
        System.out.println("✓ Exception IllegalArgumentException levée (entier)");
        
        assertThrows(IllegalArgumentException.class, () -> {
            stringQueue.insertElement(null);
        });
        System.out.println("✓ Exception IllegalArgumentException levée (chaîne)");
    }
    
    @Test
    public void test_elementOnEmptyQueue() {
        System.out.println("=== Test: Accès à l'élément d'une GenPriorityQueue vide ===");
        assertThrows(NoSuchElementException.class, () -> {
            intQueue.element();
        });
        System.out.println("✓ Exception NoSuchElementException levée (entier)");
        
        assertThrows(NoSuchElementException.class, () -> {
            stringQueue.element();
        });
        System.out.println("✓ Exception NoSuchElementException levée (chaîne)");
    }
    
    @Test
    public void test_popElementOnEmptyQueue() {
        System.out.println("=== Test: Suppression d'un élément d'une GenPriorityQueue vide ===");
        assertThrows(NoSuchElementException.class, () -> {
            intQueue.popElement();
        });
        System.out.println("✓ Exception NoSuchElementException levée (entier)");
        
        assertThrows(NoSuchElementException.class, () -> {
            stringQueue.popElement();
        });
        System.out.println("✓ Exception NoSuchElementException levée (chaîne)");
    }
    
    @Test
    public void test_invalidCapacity() {
        System.out.println("=== Test: Création avec une capacité invalide ===");
        assertThrows(IllegalArgumentException.class, () -> {
            new GenPriorityQueue<Integer>(0);
        });
        System.out.println("✓ Exception IllegalArgumentException levée (capacité 0)");
        
        assertThrows(IllegalArgumentException.class, () -> {
            new GenPriorityQueue<String>(-1);
        });
        System.out.println("✓ Exception IllegalArgumentException levée (capacité négative)");
    }
    
    @Test
    public void test_iterator() {
        System.out.println("=== Test: Itération sur la GenPriorityQueue ===");
        intQueue.insertElement(30);
        intQueue.insertElement(10);
        intQueue.insertElement(20);
        System.out.println("Queue: " + intQueue.toString());
        
        int count = 0;
        for (Integer element : intQueue) {
            count++;
            assertNotNull(element);
        }
        assertEquals(3, count);
        System.out.println("✓ Itération: " + count + " éléments parcourus");
    }
    
    @Test
    public void test_toString() {
        System.out.println("=== Test: Représentation string de la GenPriorityQueue ===");
        intQueue.insertElement(10);
        intQueue.insertElement(20);
        
        String result = intQueue.toString();
        System.out.println("toString(): " + result);
        assertTrue(result.contains("10"));
        assertTrue(result.contains("20"));
        assertTrue(result.startsWith("["));
        assertTrue(result.endsWith("]"));
        System.out.println("✓ Format correct");
    }
    
    @Test
    public void test_differentTypes() {
        System.out.println("=== Test: GenPriorityQueue avec différents types ===");
        GenPriorityQueue<Double> doubleQueue = new GenPriorityQueue<>(2);
        GenPriorityQueue<Character> charQueue = new GenPriorityQueue<>(2);
        
        doubleQueue.insertElement(3.14);
        doubleQueue.insertElement(2.71);
        assertEquals(2.71, doubleQueue.element());
        System.out.println("Double queue: " + doubleQueue.toString());
        
        charQueue.insertElement('Z');
        charQueue.insertElement('A');
        assertEquals('A', charQueue.element());
        System.out.println("Character queue: " + charQueue.toString());
        System.out.println("✓ Types génériques fonctionnent");
    }
    
    @Test
    public void test_duplicateElements() {
        System.out.println("=== Test: Éléments dupliqués dans la GenPriorityQueue ===");
        intQueue.insertElement(10);
        intQueue.insertElement(10);
        intQueue.insertElement(10);
        
        assertEquals(3, intQueue.size());
        assertEquals(10, intQueue.element());
        System.out.println("Initial: " + intQueue.toString());
        
        // Tous les éléments sont identiques
        for (int i = 0; i < 3; i++) {
            assertEquals(10, intQueue.popElement());
        }
        System.out.println("✓ Final: " + intQueue.toString());
    }
    
    @Test
    public void test_singleElement() {
        System.out.println("\n=== Test: Gestion d'un seul élément dans la GenPriorityQueue ===");
        intQueue.insertElement(42);
        assertEquals(1, intQueue.size());
        assertEquals(42, intQueue.element());
        System.out.println("Après insert: " + intQueue.toString());
        
        assertEquals(42, intQueue.popElement());
        assertTrue(intQueue.isEmpty());
        System.out.println("✓ Après pop: " + intQueue.toString());
    }
    
    @Test
    public void test_iteratorEdgeCases() {
        System.out.println("\n=== Test: Cas limites de l'itérateur ===");
        // Test itérateur sur queue vide
        int count = 0;
        for (Integer element : intQueue) {
            count++;
        }
        assertEquals(0, count);
        System.out.println("✓ Itérateur sur queue vide: " + count + " éléments");
        
        // Test itérateur avec un seul élément
        intQueue.insertElement(42);
        count = 0;
        for (Integer element : intQueue) {
            count++;
            assertNotNull(element);
        }
        assertEquals(1, count);
        System.out.println("✓ Itérateur sur un élément: " + count + " éléments");
    }
    
    @Test
    public void test_iteratorException() {
        System.out.println("\n=== Test: Exception de l'itérateur ===");
        intQueue.insertElement(10);
        intQueue.insertElement(20);
        
        var iterator = intQueue.iterator();
        // Consommer tous les éléments
        iterator.next();
        iterator.next();
        
        // Tenter d'accéder à un élément suivant inexistant
        assertThrows(NoSuchElementException.class, () -> {
            iterator.next();
        });
        System.out.println("✓ Exception NoSuchElementException levée pour itérateur");
    }
    
    @Test
    public void test_heapIfyUpEdgeCases() {
        System.out.println("\n=== Test: Cas limites de heapIfyUp ===");
        // Test avec des éléments égaux (branche compareTo >= 0)
        intQueue.insertElement(10);
        intQueue.insertElement(10);
        intQueue.insertElement(10);
        
        assertEquals(3, intQueue.size());
        assertEquals(10, intQueue.element());
        System.out.println("✓ Éléments égaux: " + intQueue.toString());
        
        // Test avec des éléments déjà dans le bon ordre
        GenPriorityQueue<Integer> orderedQueue = new GenPriorityQueue<>(3);
        orderedQueue.insertElement(5);
        orderedQueue.insertElement(10);
        orderedQueue.insertElement(15);
        
        assertEquals(5, orderedQueue.element());
        System.out.println("✓ Ordre déjà correct: " + orderedQueue.toString());
    }
    
    @Test
    public void test_heapIfyDownEdgeCases() {
        System.out.println("\n=== Test: Cas limites de heapIfyDown ===");
        // Test avec un seul élément (branche size == 1 dans popElement)
        intQueue.insertElement(42);
        assertEquals(42, intQueue.popElement());
        assertTrue(intQueue.isEmpty());
        System.out.println("✓ Pop d'un seul élément: " + intQueue.toString());
        
        // Test avec deux éléments pour couvrir les branches de heapIfyDown
        intQueue.insertElement(20);
        intQueue.insertElement(10);
        assertEquals(10, intQueue.popElement());
        assertEquals(20, intQueue.popElement());
        System.out.println("✓ Pop de deux éléments: " + intQueue.toString());
    }
    
    @Test
    public void test_complexHeapOperations() {
        System.out.println("\n=== Test: Opérations complexes du tas ===");
        // Test avec plusieurs redimensionnements
        GenPriorityQueue<Integer> smallQueue = new GenPriorityQueue<>(2);
        smallQueue.insertElement(50);
        smallQueue.insertElement(30);
        smallQueue.insertElement(20); // Déclenche resize
        smallQueue.insertElement(10); // Déclenche resize
        smallQueue.insertElement(5);  // Déclenche resize
        
        assertEquals(5, smallQueue.element());
        System.out.println("Après multiples redimensionnements: " + smallQueue.toString());
        
        // Vérifier que tous les éléments sont dans le bon ordre
        assertEquals(5, smallQueue.popElement());
        assertEquals(10, smallQueue.popElement());
        assertEquals(20, smallQueue.popElement());
        assertEquals(30, smallQueue.popElement());
        assertEquals(50, smallQueue.popElement());
        System.out.println("✓ Ordre de priorité maintenu après redimensionnements");
    }
    
    @Test
    public void test_toStringEdgeCases() {
        System.out.println("\n=== Test: Cas limites de toString ===");
        // Test toString sur queue vide
        String emptyResult = intQueue.toString();
        assertTrue(emptyResult.contains("None"));
        assertTrue(emptyResult.startsWith("["));
        assertTrue(emptyResult.endsWith("]"));
        System.out.println("Queue vide: " + emptyResult);
        
        // Test toString avec un seul élément
        intQueue.insertElement(42);
        String singleResult = intQueue.toString();
        assertTrue(singleResult.contains("42"));
        assertTrue(singleResult.contains("None"));
        System.out.println("Un élément: " + singleResult);
        
        // Test toString avec capacité pleine
        intQueue.insertElement(10);
        intQueue.insertElement(20);
        String fullResult = intQueue.toString();
        assertTrue(fullResult.contains("10"));
        assertTrue(fullResult.contains("20"));
        assertTrue(fullResult.contains("42"));
        System.out.println("Capacité pleine: " + fullResult);
    }
    
    @Test
    public void test_comparisonEdgeCases() {
        System.out.println("\n=== Test: Cas limites de comparaison ===");
        // Test avec des chaînes vides
        stringQueue.insertElement("");
        stringQueue.insertElement("a");
        stringQueue.insertElement("z");
        
        assertEquals("", stringQueue.element());
        System.out.println("Chaînes avec chaîne vide: " + stringQueue.toString());
        
        // Test avec des caractères spéciaux
        GenPriorityQueue<Character> charQueue = new GenPriorityQueue<>(3);
        charQueue.insertElement('z');
        charQueue.insertElement('a');
        charQueue.insertElement('0');
        
        assertEquals('0', charQueue.element());
        System.out.println("Caractères spéciaux: " + charQueue.toString());
    }
    
    @Test
    public void test_negativeCapacity() {
        System.out.println("\n=== Test: Capacités négatives ===");
        // Test avec différentes valeurs négatives
        assertThrows(IllegalArgumentException.class, () -> {
            new GenPriorityQueue<Integer>(-5);
        });
        System.out.println("✓ Exception pour capacité -5");
        
        assertThrows(IllegalArgumentException.class, () -> {
            new GenPriorityQueue<String>(-100);
        });
        System.out.println("✓ Exception pour capacité -100");
    }
    
    @Test
    public void test_largeOperations() {
        System.out.println("\n=== Test: Opérations sur grande échelle ===");
        GenPriorityQueue<Integer> largeQueue = new GenPriorityQueue<>(5);
        
        // Insérer dans l'ordre décroissant pour tester heapIfyUp
        largeQueue.insertElement(100);
        largeQueue.insertElement(90);
        largeQueue.insertElement(80);
        largeQueue.insertElement(70);
        largeQueue.insertElement(60);
        largeQueue.insertElement(50); // Déclenche resize
        
        assertEquals(50, largeQueue.element());
        System.out.println("Ordre décroissant: " + largeQueue.toString());
        
        // Insérer dans l'ordre croissant pour tester d'autres branches
        GenPriorityQueue<Integer> ascendingQueue = new GenPriorityQueue<>(5);
        ascendingQueue.insertElement(10);
        ascendingQueue.insertElement(20);
        ascendingQueue.insertElement(30);
        ascendingQueue.insertElement(40);
        ascendingQueue.insertElement(50);
        ascendingQueue.insertElement(60); // Déclenche resize
        
        assertEquals(10, ascendingQueue.element());
        System.out.println("Ordre croissant: " + ascendingQueue.toString());
    }
}
