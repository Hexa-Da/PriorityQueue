import container.Queue;

/**
 * Test simple pour IntFIFO
 */
public class TestIntFIFO {
    
    public static void main(String[] args) {
        int capacity = 3;

        System.out.println("=== Test de IntFIFO ===");
        
        // Test avec capacité de 3
        IntFIFO queue = new IntFIFO(capacity);
        
        System.out.println("1. on crée une file vide (capacity : " + capacity + ", size : " + queue.size() + ")");
        System.out.println("2. Contenu de la file avant les ajouts : " + queue);
        
        // Ajouter des éléments
        System.out.println("3. Ajout de 10 : " + queue.insertElement(10));
        System.out.println("4. Ajout de 20 : " + queue.insertElement(20));
        System.out.println("5. Ajout de 30 : " + queue.insertElement(30));
        System.out.println("6. Contenu à cette étape : " + queue);
        System.out.println("7. Ajout de 40 : " + queue.insertElement(40));
        System.out.println("8. Premier élément (via la fonction element()) : " + queue.element());
        
        // Supprimer des éléments (FIFO)
        System.out.println("9. Suppression (via la fonction popElement()) : " + queue.popElement());
        System.out.println("10. Contenu après suppression : " + queue);
        System.out.println("11. Ajout de 40 (maintenant possible) : " + queue.insertElement(40));
        System.out.println("12. Contenu final : " + queue);
        
        System.out.println("\n=== Test terminé ===");
    }
}
