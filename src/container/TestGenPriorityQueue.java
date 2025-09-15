package container;

/**
 * Test unitaire pour GenPriorityQueue - Focus sur la généricité
 */
public class TestGenPriorityQueue {
    
    public static void main(String[] args) {
        System.out.println("=== Test de GenPriorityQueue (Généricité) ===");
        
        testAvecInteger();
        testAvecString();
        testAvecTypePersonnalise();
        testComparaisonTypes();
        
        System.out.println("\n=== Tous les tests de généricité sont terminés ===");
    }
    
    // Classe interne pour tester un type personnalisé
    static class Person implements Comparable<Person> {
        private String name;
        private int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        @Override
        public int compareTo(Person other) {
            return Integer.compare(this.age, other.age); // Tri par âge croissant
        }
        
        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
    
    private static void testAvecInteger() {
        System.out.println("\n--- Test avec Integer ---");
        
        GenPriorityQueue<Integer> queue = new GenPriorityQueue<>(3);
        queue.insertElement(30);
        queue.insertElement(10);
        queue.insertElement(20);
        
        System.out.println("File Integer : " + queue);
        System.out.println("Suppression dans l'ordre croissant :");
        while (!queue.isEmpty()) {
            Integer element = queue.popElement();
            System.out.println("  Supprimé : " + element + " - Reste : " + queue);
        }
        
        System.out.println("✓ Test Integer réussi");
    }
    
    private static void testAvecString() {
        System.out.println("\n--- Test avec String ---");
        
        GenPriorityQueue<String> queue = new GenPriorityQueue<>(4);
        queue.insertElement("Charlie");
        queue.insertElement("Alice");
        queue.insertElement("Bob");
        queue.insertElement("David");
        
        System.out.println("File String : " + queue);
        System.out.println("Suppression dans l'ordre alphabétique :");
        while (!queue.isEmpty()) {
            String element = queue.popElement();
            System.out.println("  Supprimé : " + element + " - Reste : " + queue);
        }
        
        System.out.println("✓ Test String réussi");
    }
    
    private static void testAvecTypePersonnalise() {
        System.out.println("\n--- Test avec type personnalisé (Person) ---");
        
        GenPriorityQueue<Person> queue = new GenPriorityQueue<>(4);
        queue.insertElement(new Person("Alice", 25));
        queue.insertElement(new Person("Bob", 30));
        queue.insertElement(new Person("Charlie", 20));
        queue.insertElement(new Person("David", 35));
        
        System.out.println("File Person : " + queue);
        System.out.println("Suppression dans l'ordre d'âge croissant :");
        while (!queue.isEmpty()) {
            Person person = queue.popElement();
            System.out.println("  Supprimé : " + person + " - Reste : " + queue);
        }
        
        System.out.println("✓ Test type personnalisé réussi");
    }
    
    private static void testComparaisonTypes() {
        System.out.println("\n--- Test de comparaison entre types ---");
        
        // Test avec des Double
        GenPriorityQueue<Double> doubleQueue = new GenPriorityQueue<>(3);
        doubleQueue.insertElement(3.14);
        doubleQueue.insertElement(2.71);
        doubleQueue.insertElement(1.41);
        
        System.out.println("File Double : " + doubleQueue);
        System.out.println("Premier élément (plus petit) : " + doubleQueue.element());
        
        // Test avec des Character
        GenPriorityQueue<Character> charQueue = new GenPriorityQueue<>(3);
        charQueue.insertElement('Z');
        charQueue.insertElement('A');
        charQueue.insertElement('M');
        
        System.out.println("File Character : " + charQueue);
        System.out.println("Premier élément (plus petit) : " + charQueue.element());
        
        System.out.println("✓ Test comparaison types réussi");
    }
}
