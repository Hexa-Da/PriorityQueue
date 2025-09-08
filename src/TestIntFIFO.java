import container.Queue;

/**
 * Test simple pour IntFIFO
 */
public class TestIntFIFO {
    
    public static void main(String[] args) {
        System.out.println("=== Test de IntFIFO ===");
        
        // Test avec capacité de 3
        IntFIFO queue = new IntFIFO(3);
        
        System.out.println("1. File vide : " + queue.isEmpty() + ", taille : " + queue.size());
        
        // Ajouter des éléments
        System.out.println("2. Ajout de 10 : " + queue.insertElement(10));
        System.out.println("3. Ajout de 20 : " + queue.insertElement(20));
        System.out.println("4. Ajout de 30 : " + queue.insertElement(30));
        System.out.println("5. Ajout de 40 (devrait échouer) : " + queue.insertElement(40));
        
        System.out.println("6. Contenu : " + queue);
        System.out.println("7. Premier élément : " + queue.element());
        
        // Supprimer des éléments (FIFO)
        System.out.println("8. Suppression : " + queue.popElement());
        System.out.println("9. Contenu après suppression : " + queue);
        
        System.out.println("10. Ajout de 40 (maintenant possible) : " + queue.insertElement(40));
        System.out.println("11. Contenu final : " + queue);
        
        System.out.println("\n=== Test terminé ===");
    }
}
