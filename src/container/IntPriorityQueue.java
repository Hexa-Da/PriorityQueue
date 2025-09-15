package container;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implémentation d'une file de priorité non générique pour le type Integer
 * utilisant un tas (heap) binaire
 */
public class IntPriorityQueue implements Queue<Integer> {
    
    private Integer[] heap;  // Tableau représentant le tas
    private int size;        // Nombre d'éléments dans le tas
    private int capacity;    // Capacité maximale du tableau
 
    public IntPriorityQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("La capacité doit être positive");
        }
        this.capacity = capacity;
        this.heap = new Integer[capacity];
        this.size = 0;
    }
    
    @Override
    public boolean insertElement(Integer e) {
        if (e == null) {
            throw new IllegalArgumentException("Les éléments null ne sont pas autorisés");
        }
        
        // Si le tas est plein, redimensionner
        if (size == capacity) {
            System.out.println("La file est pleine, on ne peut plus ajouter d'élément");
            // return false (selon interprétation de l'énoncé)
            resize();
            System.out.println("La file a été redimensionnée (capacity : " + capacity + ")");
        }
        
        // Ajouter l'élément à la fin du tas
        heap[size] = e;
        size++;
        
        // Réorganiser le tas pour maintenir la propriété de tas
        heapifyUp(size - 1);
        
        return true;
    }

    private void resize() {
        int newCapacity = capacity + 1; 
        Integer[] newHeap = new Integer[newCapacity];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
        capacity = newCapacity;
    }
    
    // Méthode pour maintenir la propriété de tas lors de l'ajout
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index] >= heap[parentIndex]) {
                break; // La propriété de tas est respectée
            }
            // Échanger avec le parent
            swap(index, parentIndex);
            index = parentIndex;
        }
    }
    
    // Méthode utilitaire pour échanger deux éléments
    private void swap(int i, int j) {
        Integer temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    @Override
    public Integer element() {
        if (isEmpty()) {
            throw new NoSuchElementException("La file est vide");
        }
        return heap[0]; // Le plus petit élément est toujours à la racine
    }
    
    @Override
    public Integer popElement() {
        if (isEmpty()) {
            throw new NoSuchElementException("La file est vide");
        }
        
        Integer minElement = heap[0]; // Le plus petit élément
        
        if (size == 1) {
            // Cas spécial : un seul élément
            heap[0] = null;
            size = 0;
        } else {
            // Cas général : plusieurs éléments
            heap[0] = heap[size - 1]; // Remplacer par le dernier élément
            heap[size - 1] = null; // Libérer la référence
            size--;
            
            // Réorganiser le tas pour maintenir la propriété
            heapifyDown(0);
        }
        
        return minElement;
    }

    // Méthode pour maintenir la propriété de tas lors de la suppression
    private void heapifyDown(int index) {
        while (true) {
            int smallest = index;
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            
            if (leftChild < size && heap[leftChild] < heap[smallest]) {
                smallest = leftChild;
            }
            
            if (rightChild < size && heap[rightChild] < heap[smallest]) {
                smallest = rightChild;
            }
            
            if (smallest == index) {
                break; // La propriété de tas est respectée
            }
            
            swap(index, smallest);
            index = smallest;
        }
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }
    

    /**
     * Itérateur pour parcourir les éléments de la file de priorité
     */
    @Override
    public Iterator<Integer> iterator() {
        return new IntegerPriorityQueueIterator();
    }
    
    private class IntegerPriorityQueueIterator implements Iterator<Integer> {
        private int currentIndex = 0;
        
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }
        
        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Aucun élément suivant");
            }
            
            Integer element = heap[currentIndex];
            currentIndex++;
            return element;
        }
    }
    
    /**
     * Retourne une représentation en chaîne de la file de priorité
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < this.capacity(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            if (i < size && heap[i] != null) {
                sb.append(heap[i]);
            } else {
                sb.append("None");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
