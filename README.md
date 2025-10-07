# Implémentation de Files (Queues)

Ce projet implémente **3 types de files** (queues) en Java, toutes conformes à l'interface `Queue<E>` :

1. **IntFIFO** : File FIFO non-générique (First In, First Out)
2. **IntPriorityQueue** : File de priorité non-générique (tas binaire max-heap)
3. **GenPriorityQueue** : File de priorité générique (tas binaire max-heap)

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
// Insertion 
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

// Suppression 
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
- **Type erasure** : Utilise `E[]` avec casting sécurisé
- **Même algorithme** que IntPriorityQueue mais générique

### **Gestion des types optimisée :**
```java
public class GenPriorityQueue<E extends Comparable<E>> {
    private E[] heap; 
    
    // HeapifyUp générique 
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
       
            E current = heap[index];
            E parent = heap[parentIndex];
            
            // Si la propriété de tas est respectée, arrêter
            if (current.compareTo(parent) <= 0) {
                break;
            }
            
            swap(index, parentIndex);
            index = parentIndex;
        }
    }
    
    // HeapifyDown générique 
    private void heapifyDown() {
        int index = 0;
        while (true) {
            int biggest = index;
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            
            // Vérifier l'enfant gauche
            if (leftChild < size) {
                E left = heap[leftChild];
                E currentBiggest = heap[biggest];
                if (left.compareTo(currentBiggest) > 0) {
                    biggest = leftChild;
                }
            }
            
            // Vérifier l'enfant droit
            if (rightChild < size) {
                E right = heap[rightChild];
                E currentBiggest = heap[biggest];
                if (right.compareTo(currentBiggest) > 0) {
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

## 5. Tests unitaires JUnit 5

### **Structure des tests :**
```
test/container/
├── TestIntFIFO.java          (21 tests)
├── TestIntPriorityQueue.java (21 tests)
└── TestGenPriorityQueue.java (20 tests)
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