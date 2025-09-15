# TD Software Engineering - Implémentation de Files (Queues)

Ce projet implémente **3 types de files** (queues) en Java, toutes conformes à l'interface `Queue<E>` :

1. **IntFIFO** : File FIFO non-générique (First In, First Out)
2. **IntPriorityQueue** : File de priorité non-générique (tas binaire)
3. **GenPriorityQueue** : File de priorité générique (tas binaire)

## 🔧 1. Interface Queue<E> - Le contrat commun

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

## 🔄 2. IntFIFO - File FIFO (Tableau circulaire)

### **Principe de fonctionnement :**
- **FIFO** : Premier arrivé, premier servi
- **Structure** : Tableau circulaire avec `front` et `rear`
- **Redimensionnement** : Double la capacité quand nécessaire

### **Algorithme clé :**
```java
// Insertion
rear = (rear + 1) % capacity;
array[rear] = element;

// Suppression
Integer element = array[front];
front = (front + 1) % capacity;
```

### **Avantages :**
- ✅ Insertion/suppression en O(1)
- ✅ Utilisation optimale de l'espace mémoire
- ✅ Redimensionnement automatique

## ⚡ 3. IntPriorityQueue - File de priorité (Tas binaire)

### **Principe de fonctionnement :**
- **Priorité** : L'élément le plus petit est toujours en premier
- **Structure** : Tas binaire (heap) avec propriété min-heap
- **Redimensionnement** : Double la capacité quand nécessaire

### **Algorithme clé :**
```java
// Insertion - heapifyUp
private void heapifyUp(int index) {
    while (index > 0) {
        int parent = (index - 1) / 2;
        if (heap[index].compareTo(heap[parent]) >= 0) break;
        swap(index, parent);
        index = parent;
    }
}

// Suppression - heapifyDown
private void heapifyDown(int index) {
    while (true) {
        int smallest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        // ... logique de comparaison
    }
}
```

### **Avantages :**
- ✅ Insertion en O(log n)
- ✅ Suppression en O(log n)
- ✅ Consultation du minimum en O(1)

## 🎯 4. GenPriorityQueue - File générique (Tas binaire)

### **Principe de fonctionnement :**
- **Généricité** : `<E extends Comparable<E>>` pour tout type comparable
- **Type erasure** : Utilise `Object[]` avec casting sécurisé
- **Même algorithme** que IntPriorityQueue mais générique

### **Gestion des types :**
```java
@SuppressWarnings("unchecked")
public class GenPriorityQueue<E extends Comparable<E>> {
    private Object[] heap; // Tableau d'Object pour éviter les problèmes de type erasure
    
    // Casting sécurisé
    @SuppressWarnings("unchecked")
    private E getElement(int index) {
        return (E) heap[index];
    }
}
```

### **Exemples d'utilisation :**
```java
GenPriorityQueue<Integer> intQueue = new GenPriorityQueue<>(10);
GenPriorityQueue<String> stringQueue = new GenPriorityQueue<>(10);
GenPriorityQueue<Person> personQueue = new GenPriorityQueue<>(10);
```

## 🧪 5. Tests unitaires JUnit 5

### **Structure des tests :**
```
test/container/
├── TestIntFIFO.java          (13 tests)
├── TestIntPriorityQueue.java (15 tests)
└── TestGenPriorityQueue.java (15 tests)
```

### **Couverture des tests :**
- ✅ **Création** : Files vides, capacités invalides
- ✅ **Insertion** : Éléments normaux, null, redimensionnement
- ✅ **Suppression** : Files vides, files pleines, ordre
- ✅ **Consultation** : Éléments, files vides
- ✅ **Itérateurs** : Parcours des éléments
- ✅ **Redimensionnement** : Performance et intégrité
- ✅ **Généricité** : Différents types (Integer, String, Double, Character)

## ⚙️ 6. Configuration Maven

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
target/           # Fichiers compilés (générés)
```

## 🚀 Utilisation

### **Compilation et tests :**
```bash
mvn test
```