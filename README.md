# TD Software Engineering - Implémentation de Files (Queues)

Ce projet implémente **3 types de files** (queues) en Java, toutes conformes à l'interface `Queue<E>` :

1. **IntFIFO** : File FIFO non-générique (First In, First Out)
2. **IntPriorityQueue** : File de priorité non-générique (tas binaire max-heap)
3. **GenPriorityQueue** : File de priorité générique (tas binaire max-heap)

## Fonctionnalités et Optimisations
- **Tests de stress** : Validation avec 10 000+ éléments
- **Timeouts optimisés** : Tests rapides et fiables
- **Gestion d'erreurs** : Exceptions appropriées (NoSuchElementException, IllegalArgumentException)
- **Cas limites couverts** : Éléments égaux, ordres décroissants, capacités extrêmes

## 1. Interface Queue<E> - Le contrat commun

```java
public interface Queue<E> extends Iterable<E>
```

**Fonctionnalités :**
- **Généricité** : `<E>` permet de typer les éléments
- **Iterable** : Permet l'utilisation dans les boucles `for-each`
- **Méthodes principales** :
  - `insertElement(E e)` : Ajouter un élément
  - `element()` : Consulter le premier élément (sans le supprimer)
  - `popElement()` : Retirer et retourner le premier élément
  - `isEmpty()` : Vérifier si la file est vide
  - `size()` : Obtenir le nombre d'éléments

## 2. IntFIFO - File FIFO (Tableau circulaire)

### **Principe de fonctionnement :**
- **FIFO** : Premier arrivé, premier servi
- **Structure** : Tableau circulaire avec `front` et `rear`
- **Redimensionnement** : Augmentation de la capacité quand nécessaire 

### **Algorithme clé :**
```java
// Insertion
rear = (rear + 1) % capacity;
array[rear] = element;

// Suppression
Integer element = array[front];
front = (front + 1) % capacity;

// Redimensionnement
private void resize() {
    int newCapacity = capacity + 1;
    Integer[] newArray = new Integer[newCapacity];
    // ... copie des éléments
}
```

### **Avantages :**
- Insertion/suppression en O(1)
- Utilisation optimale de l'espace mémoire
- Comportement circulaire efficace

## 3. IntPriorityQueue - File de priorité (Tas binaire)

### **Principe de fonctionnement :**
- **Priorité** : L'élément le plus grand est toujours en premier
- **Structure** : Tas binaire (heap) avec propriété max-heap
- **Redimensionnement** : Augmentation de la capacité quand nécessaire


### **Algorithme clé optimisé :**
```java
// Insertion - heapifyUp simplifié et efficace
private void heapifyUp(int index) {
    while (index > 0) {
        int parentIndex = (index - 1) / 2;
        
        // Si la propriété de tas est respectée, arrêter
        if (heap[index] <= heap[parentIndex]) {
            break;
        }
        
        swap(index, parentIndex);
        index = parentIndex;
    }
}

// Suppression - heapifyDown simplifié et efficace
private void heapifyDown() {
    int index = 0;
    while (true) {
        int biggest = index;
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        
        // Vérifier l'enfant gauche
        if (leftChild < size && heap[leftChild] > heap[biggest]) {
            biggest = leftChild;
        }
        
        // Vérifier l'enfant droit
        if (rightChild < size && heap[rightChild] > heap[biggest]) {
            biggest = rightChild;
        }
        
        // Si aucun échange n'est nécessaire, arrêter
        if (biggest == index) {
            break; // La propriété de tas est respectée !
        }
        
        swap(index, biggest);
        index = biggest;
    }
}
```

### **Avantages :**
- Insertion en O(log n)
- Suppression en O(log n)
- Consultation du maximum en O(1)

## 4. GenPriorityQueue - File générique (Tas binaire)

### **Principe de fonctionnement :**
- **Généricité** : `<E extends Comparable<E>>` pour tout type comparable
- **Type erasure** : Utilise `Object[]` avec casting sécurisé
- **Même algorithme** que IntPriorityQueue mais générique

### **Gestion des types optimisée :**
```java
@SuppressWarnings("unchecked")
public class GenPriorityQueue<E extends Comparable<E>> {
    private Object[] heap; // Tableau d'Object pour éviter les problèmes de type erasure
    
    // HeapifyUp générique optimisé
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
       
            // Caster et comparer les éléments
            E current = (E) heap[index];
            E parent = (E) heap[parentIndex];
            
            // Si la propriété de tas est respectée, arrêter
            if (current.compareTo(parent) <= 0) {
                break;
            }
            
            swap(index, parentIndex);
            index = parentIndex;
        }
    }
    
    // HeapifyDown générique optimisé
    private void heapifyDown() {
        int index = 0;
        while (true) {
            int biggest = index;
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            
            // Vérifier l'enfant gauche
            if (leftChild < size) {
                E left = (E) heap[leftChild];
                E current = (E) heap[biggest];
                if (left.compareTo(current) > 0) {
                    biggest = leftChild;
                }
            }
            
            // Vérifier l'enfant droit
            if (rightChild < size) {
                E right = (E) heap[rightChild];
                E current = (E) heap[biggest];
                if (right.compareTo(current) > 0) {
                    biggest = rightChild;
                }
            }
            
            // Si aucun échange n'est nécessaire, arrêter
            if (biggest == index) {
                break; // La propriété de tas est respectée !
            }
            
            swap(index, biggest);
            index = biggest;
        }
    }
}
```

### **Exemples d'utilisation :**
```java
// Files typées
GenPriorityQueue<Integer> intQueue = new GenPriorityQueue<>(10);
GenPriorityQueue<String> stringQueue = new GenPriorityQueue<>(10);
GenPriorityQueue<Double> doubleQueue = new GenPriorityQueue<>(10);

// Utilisation
intQueue.insertElement(42);
stringQueue.insertElement("Hello");
doubleQueue.insertElement(3.14);

// Parcours avec itérateur
for (Integer value : intQueue) {
    System.out.println(value);
}
```

## 5. Tests unitaires JUnit 5

### **Structure des tests :**
```
test/container/
├── TestIntFIFO.java          (15 tests)
├── TestIntPriorityQueue.java (18 tests)
├── TestGenPriorityQueue.java (24 tests)
└── StressTest.java           (3 tests de performance)
```

### **Couverture des tests :**
- **Création** : Files vides, capacités invalides, capacités négatives
- **Insertion** : Éléments normaux, null, redimensionnement, ordres variés
- **Suppression** : Files vides, files pleines, ordre de priorité
- **Consultation** : Éléments, files vides, cas limites
- **Itérateurs** : Parcours des éléments, exceptions, cas limites
- **Redimensionnement** : Performance et intégrité, tests de stress
- **Généricité** : Différents types (Integer, String, Double, Character)
- **Sécurité** : Protection contre les boucles infinies, valeurs null
- **Performance** : Tests de stress avec 10 000+ éléments
- **Timeouts** : Tests optimisés (1-30 secondes selon la complexité)

## 6. Configuration Maven

### **Dépendances :**
```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.8.2</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.8.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### **Structure Maven :**
```
src/container/     # Code source principal
test/container/    # Tests unitaires
```

## Utilisation

### **Compilation et tests :**
```bash
# Tous les tests (60 tests au total)
mvn test

# Tests spécifiques
mvn test -Dtest=StressTest              # Tests de performance
mvn test -Dtest=TestIntPriorityQueue    # Tests IntPriorityQueue
mvn test -Dtest=TestGenPriorityQueue    # Tests GenPriorityQueue
mvn test -Dtest=TestIntFIFO             # Tests IntFIFO

# Tests avec rapport de couverture
mvn test jacoco:report
```

### **Résultats de performance :**
```
=== Tests de stress ===
--- Test 1: 10000 éléments ---
Insertion de 10000 éléments: 23ms
Suppression de 10000 éléments: 42ms

--- Test 2: Redimensionnements fréquents ---
1000 insertions avec redimensionnements: 1ms

--- Test 3: Éléments égaux ---
1000 éléments égaux: 0ms

--- Test 4: Ordre décroissant (worst case) ---
1000 éléments en ordre décroissant: 1ms

--- Test 5: GenPriorityQueue avec entiers ---
Insertion 5000 entiers génériques: 16ms

--- Test 6: IntFIFO ---
```

### **Statistiques finales :**
- **Total** : 60 tests
- **Succès** : 60  
- **Échecs** : 0 
- **Erreurs** : 0 
- **Temps d'exécution** : ~20 secondes
- **Couverture de code** : Optimale avec tests de stress
