package container;

import java.util.NoSuchElementException;

/**
 * Test unitaire pour IntPriorityQueue
 */
public class TestIntPriorityQueue {
    
    public static void main(String[] args) {
        System.out.println("=== Test de IntPriorityQueue ===");
        
        // Test 1: Création et état initial
        testCreation();
        
        // Test 2: Insertion d'éléments
        testInsertion();
        
        // Test 3: Suppression d'éléments (ordre de priorité)
        testSuppression();
        
        // Test 4: Redimensionnement
        testRedimensionnement();
        
        // Test 5: Cas limites
        testCasLimites();
        
        // Test 6: Itérateur
        testIterateur();
        
        // Test 7: Gestion des exceptions
        testExceptions();
        
        System.out.println("\n=== Tous les tests sont terminés ===");
    }
    
    private static void testCreation() {
        System.out.println("\n--- Test 1 : Création et état initial ---");
        
        IntPriorityQueue queue = new IntPriorityQueue(3);

        System.out.println("Création de la file : " + queue);
        assert queue.isEmpty() : "La file devrait être vide";
        assert queue.size() == 0 : "La taille devrait être 0";
        assert queue.capacity() == 3 : "La capacité devrait être 3";
        
        System.out.println("✓ Création réussie");
    }
    
    private static void testInsertion() {
        System.out.println("\n--- Test 2 : Insertion d'éléments ---");
        
        IntPriorityQueue queue = new IntPriorityQueue(3);
        
        // Insertion d'éléments
        assert queue.insertElement(30) : "Insertion de 30 devrait réussir";
        queue.insertElement(30);
        System.out.println("Insertion de 30 : " + queue);
        assert queue.insertElement(10) : "Insertion de 10 devrait réussir";
        queue.insertElement(10);
        System.out.println("Insertion de 10 : " + queue);
        assert queue.insertElement(20) : "Insertion de 20 devrait réussir";
        queue.insertElement(20);
        System.out.println("Insertion de 20 : " + queue);
        
        assert !queue.isEmpty() : "La file ne devrait pas être vide";
        assert queue.size() == 3 : "La taille devrait être 3";
        
        // Vérifier que le plus petit élément est à la racine
        assert queue.element() == 10 : "Le plus petit élément devrait être 10";
        
        System.out.println("✓ Insertions réussies");
    }
    
    private static void testSuppression() {
        System.out.println("\n--- Test 3 : Suppression d'éléments (ordre de priorité) ---");
        
        IntPriorityQueue queue = new IntPriorityQueue(5);
        
        // Insérer des éléments dans un ordre aléatoire
        queue.insertElement(50);
        queue.insertElement(20);
        queue.insertElement(30);
        queue.insertElement(10);
        queue.insertElement(40);
        
        System.out.println("Avant suppression : " + queue);
        
        // Supprimer dans l'ordre de priorité (plus petit d'abord)
        assert queue.popElement() == 10 : "Premier élément supprimé devrait être 10";
        int suppression1 = queue.popElement();
        System.out.println("1er Suppression : " + queue + " return " + suppression1);
        assert queue.popElement() == 20 : "Deuxième élément supprimé devrait être 20";
        int suppression2 =queue.popElement();
        System.out.println("2nd Suppression : " + queue + " return " + suppression2);
        assert queue.popElement() == 30 : "Troisième élément supprimé devrait être 30";
        int suppression3 = queue.popElement();
        System.out.println("3rd Suppression : " + queue + " return " + suppression3);

        assert queue.size() == 2 : "La taille devrait être 2";
        System.out.println("Taille : " + queue.size());
        assert queue.element() == 40 : "Le prochain élément devrait être 40";
        System.out.println("Prochain élément : " + queue.element());
        System.out.println("✓ Suppressions réussies");
    }
    
    private static void testRedimensionnement() {
        System.out.println("\n--- Test 4 : Redimensionnement ---");
        
        IntPriorityQueue queue = new IntPriorityQueue(2);
        
        // Remplir la capacité initiale
        queue.insertElement(10);
        queue.insertElement(20);
        
        System.out.println("Avant redimensionnement : " + queue);
        System.out.println("Capacité : " + queue.capacity());
        
        // Insérer un élément de plus pour déclencher le redimensionnement
        System.out.println("Test d'insertion d'un nouvel élément...");
        queue.insertElement(5);
        
        assert queue.size() == 3 : "La taille devrait être 3";
        assert queue.capacity() == 3 : "La capacité devrait être 3";
        assert queue.element() == 5 : "Le plus petit élément devrait être 5";
        
        System.out.println("Après redimensionnement: " + queue);
        System.out.println("Nouvelle capacité: " + queue.capacity());
        System.out.println("✓ Redimensionnement réussi");
    }
    
    private static void testCasLimites() {
        System.out.println("\n--- Test 5 : Cas limites ---");
        
        IntPriorityQueue queue = new IntPriorityQueue(1);
        System.out.println("Création d'une file de capacité 1 : " + queue);

        // Test avec un seul élément
        System.out.println("Test d'insertion d'un élément...");
        queue.insertElement(42);
        assert queue.element() == 42 : "L'élément unique devrait être 42";
        System.out.println("Insertion de l'élément réussi : " + queue);
        assert queue.popElement() == 42 : "Suppression de l'élément unique";
        System.out.println("Suppression de l'élément réussi : " + queue);
        assert queue.isEmpty() : "La file devrait être vide après suppression";
        
        // Test avec éléments identiques
        System.out.println("Test d'insertion d'éléments identiques...");
        queue.insertElement(5);
        queue.insertElement(5);
        queue.insertElement(5);
        System.out.println("Insertion des éléments identiques réussi : " + queue);
        
        assert queue.size() == 3 : "Devrait pouvoir insérer des éléments identiques";
        assert queue.element() == 5 : "Le plus petit élément devrait être 5";
        
        System.out.println("✓ Cas limites gérés correctement");
    }
    
    private static void testIterateur() {
        System.out.println("\n--- Test 6: Itérateur ---");
        
        IntPriorityQueue queue = new IntPriorityQueue(4);
        queue.insertElement(40);
        queue.insertElement(10);
        queue.insertElement(30);
        queue.insertElement(20);
        
        System.out.println("  Parcours avec itérateur:");
        int count = 0;
        for (Integer element : queue) {
            System.out.println("    Élément " + count + ": " + element);
            count++;
        }
        
        assert count == 4 : "L'itérateur devrait parcourir 4 éléments";
        
        System.out.println("✓ Itérateur fonctionne correctement");
    }
    
    private static void testExceptions() {
        System.out.println("\n--- Test 7: Gestion des exceptions ---");
        
        IntPriorityQueue queue = new IntPriorityQueue(2);
        
        // Test avec élément null
        try {
            queue.insertElement(null);
            assert false : "Devrait lever une exception pour null";
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Exception correcte pour null: " + e.getMessage());
        }
        
        // Test element() sur file vide
        try {
            queue.element();
            assert false : "Devrait lever une exception pour file vide";
        } catch (NoSuchElementException e) {
            System.out.println("✓ Exception correcte pour file vide: " + e.getMessage());
        }
        
        // Test popElement() sur file vide
        try {
            queue.popElement();
            assert false : "Devrait lever une exception pour file vide";
        } catch (NoSuchElementException e) {
            System.out.println("✓ Exception correcte pour file vide: " + e.getMessage());
        }
        
        // Test constructeur avec capacité invalide
        try {
            new IntPriorityQueue(0);
            assert false : "Devrait lever une exception pour capacité 0";
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Exception correcte pour capacité invalide: " + e.getMessage());
        }
    }
}
