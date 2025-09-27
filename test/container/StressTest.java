package container;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de stress pour identifier les problèmes de performance
 */
public class StressTest {
    
    @Test
    @Timeout(30) // 30 secondes maximum pour tous les tests de stress
    public void test_stressPerformance() {
        System.out.println("=== Tests de stress ===");
        
        // Test 1: Beaucoup d'éléments
        System.out.println("\n--- Test 1: 10000 éléments ---");
        long start = System.currentTimeMillis();
        
        IntPriorityQueue queue1 = new IntPriorityQueue(100);
        for (int i = 10000; i >= 1; i--) {
            queue1.insertElement(i);
        }
        
        long insertTime = System.currentTimeMillis() - start;
        System.out.println("Insertion de 10000 éléments: " + insertTime + "ms");
        
        // Test 2: Pop de tous les éléments
        start = System.currentTimeMillis();
        for (int i = 10000; i >= 1; i--) {
            Integer popped = queue1.popElement();
            assertEquals(i, popped, "Ordre de priorité incorrect à l'index " + i);
        }
        
        long popTime = System.currentTimeMillis() - start;
        System.out.println("Suppression de 10000 éléments: " + popTime + "ms");
        
        // Vérifier que la queue est vide
        assertTrue(queue1.isEmpty(), "La queue devrait être vide après tous les pops");
        assertEquals(0, queue1.size(), "La taille devrait être 0");
        
        // Test 3: Redimensionnements fréquents
        System.out.println("\n--- Test 2: Redimensionnements fréquents ---");
        start = System.currentTimeMillis();
        
        IntPriorityQueue queue2 = new IntPriorityQueue(1);
        for (int i = 1; i <= 1000; i++) {
            queue2.insertElement(i);
        }
        
        long resizeTime = System.currentTimeMillis() - start;
        System.out.println("1000 insertions avec redimensionnements: " + resizeTime + "ms");
        
        assertEquals(1000, queue2.size(), "Taille incorrecte après redimensionnements");
        assertEquals(1000, queue2.element(), "Le plus grand élément devrait être 1000");
        
        // Test 4: Éléments égaux (cas problématique)
        System.out.println("\n--- Test 3: Éléments égaux ---");
        start = System.currentTimeMillis();
        
        IntPriorityQueue queue3 = new IntPriorityQueue(100);
        for (int i = 0; i < 1000; i++) {
            queue3.insertElement(42); // Tous égaux
        }
        
        long equalTime = System.currentTimeMillis() - start;
        System.out.println("1000 éléments égaux: " + equalTime + "ms");
        
        assertEquals(1000, queue3.size(), "Taille incorrecte pour éléments égaux");
        assertEquals(42, queue3.element(), "Le maximum devrait être 42");
        
        // Test 5: Ordre décroissant (worst case)
        System.out.println("\n--- Test 4: Ordre décroissant (worst case) ---");
        start = System.currentTimeMillis();
        
        IntPriorityQueue queue4 = new IntPriorityQueue(100);
        for (int i = 1000; i >= 1; i--) {
            queue4.insertElement(i); // Ordre décroissant
        }
        
        long worstCaseTime = System.currentTimeMillis() - start;
        System.out.println("1000 éléments en ordre décroissant: " + worstCaseTime + "ms");
        
        assertEquals(1000, queue4.size(), "Taille incorrecte pour ordre décroissant");
        assertEquals(1000, queue4.element(), "Le plus grand devrait être 1000");
        
        // Test 6: Test avec GenPriorityQueue
        System.out.println("\n--- Test 5: GenPriorityQueue avec entiers ---");
        start = System.currentTimeMillis();
        
        GenPriorityQueue<Integer> genQueue = new GenPriorityQueue<>(100);
        for (int i = 5000; i >= 1; i--) {
            genQueue.insertElement(i);
        }
        
        long genInsertTime = System.currentTimeMillis() - start;
        System.out.println("Insertion 5000 entiers génériques: " + genInsertTime + "ms");
        
        assertEquals(5000, genQueue.size(), "Taille incorrecte pour GenPriorityQueue");
        assertEquals(5000, genQueue.element(), "Le plus grand devrait être 5000");
        
        // Test 7: Test avec IntFIFO
        System.out.println("\n--- Test 6: IntFIFO ---");
        start = System.currentTimeMillis();
        
        IntFIFO fifoQueue = new IntFIFO(100);
        for (int i = 1; i <= 5000; i++) {
            fifoQueue.insertElement(i);
        }
        
        long fifoInsertTime = System.currentTimeMillis() - start;
        System.out.println("Insertion 5000 éléments FIFO: " + fifoInsertTime + "ms");
        
        assertEquals(5000, fifoQueue.size(), "Taille incorrecte pour IntFIFO");
        assertEquals(1, fifoQueue.element(), "Le premier élément devrait être 1 (FIFO)");
        
        System.out.println("\n=== Résumé des performances ===");
        System.out.println("Insertion 10000 éléments (IntPriorityQueue): " + insertTime + "ms");
        System.out.println("Suppression 10000 éléments (IntPriorityQueue): " + popTime + "ms");
        System.out.println("Redimensionnements fréquents: " + resizeTime + "ms");
        System.out.println("Éléments égaux: " + equalTime + "ms");
        System.out.println("Ordre décroissant (worst case): " + worstCaseTime + "ms");
        System.out.println("GenPriorityQueue 5000 entiers: " + genInsertTime + "ms");
        System.out.println("IntFIFO 5000 éléments: " + fifoInsertTime + "ms");
        
        // Vérifications de performance
        assertTrue(insertTime < 5000, "Insertion trop lente: " + insertTime + "ms");
        assertTrue(popTime < 5000, "Suppression trop lente: " + popTime + "ms");
        assertTrue(resizeTime < 1000, "Redimensionnement trop lent: " + resizeTime + "ms");
        assertTrue(genInsertTime < 5000, "GenPriorityQueue trop lent: " + genInsertTime + "ms");
        
            System.out.println("✅ Performance acceptable");
        
        // Vérifications finales
        System.out.println("\n=== Vérifications finales ===");
        System.out.println("✓ IntPriorityQueue: " + queue1.size() + " éléments restants");
        System.out.println("✓ GenPriorityQueue: " + genQueue.size() + " éléments");
        System.out.println("✓ IntFIFO: " + fifoQueue.size() + " éléments");
    }
    
    @Test
    @Timeout(10) // 10 secondes pour les tests de concurrence
    public void test_concurrentOperations() {
        System.out.println("\n=== Test de concurrence simulée ===");
        
        IntPriorityQueue queue = new IntPriorityQueue(1000);
        
        // Insertions rapides
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            queue.insertElement(i);
        }
        long insertTime = System.currentTimeMillis() - start;
        
        // Pops rapides
        start = System.currentTimeMillis();
        for (int i = 999; i >= 0; i--) {
            Integer popped = queue.popElement();
            assertEquals(i, popped, "Ordre incorrect en concurrence");
        }
        long popTime = System.currentTimeMillis() - start;
        
        assertTrue(queue.isEmpty(), "Queue devrait être vide");
        assertTrue(insertTime < 1000, "Insertions trop lentes: " + insertTime + "ms");
        assertTrue(popTime < 1000, "Pops trop lents: " + popTime + "ms");
        
        System.out.println("✓ Insertions concurrentes: " + insertTime + "ms");
        System.out.println("✓ Pops concurrents: " + popTime + "ms");
    }
    
    @Test
    @Timeout(15) // 15 secondes pour les tests de mémoire
    public void test_memoryStress() {
        System.out.println("\n=== Test de stress mémoire ===");
        
        // Test avec beaucoup d'allocations/désallocations
        for (int round = 0; round < 10; round++) {
            IntPriorityQueue queue = new IntPriorityQueue(100);
            
            // Insérer beaucoup d'éléments
            for (int i = 0; i < 500; i++) {
                queue.insertElement(i);
            }
            
            // Vérifier l'état
            assertEquals(500, queue.size(), "Taille incorrecte au round " + round);
            
            // Pop la moitié
            for (int i = 499; i >= 250; i--) {
                Integer popped = queue.popElement();
                assertEquals(i, popped, "Ordre incorrect au round " + round);
            }
            
            assertEquals(250, queue.size(), "Taille incorrecte après pops au round " + round);
            
            // Insérer de nouveaux éléments
            for (int i = 500; i < 750; i++) {
                queue.insertElement(i);
            }
            
            assertEquals(500, queue.size(), "Taille finale incorrecte au round " + round);
        }
        
        System.out.println("✓ 10 rounds de stress mémoire réussis");
    }
}
