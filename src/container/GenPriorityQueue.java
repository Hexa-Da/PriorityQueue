package container;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implémentation générique d'une file de priorité utilisant un tas (heap) binaire
 * Les éléments doivent implémenter l'interface Comparable<E>
 */
@SuppressWarnings("unchecked") // Pour éviter les warnings de type erasure
public class GenPriorityQueue<E extends Comparable<E>> implements Queue<E> {
    
    private E[] heap; // Tableau typé E[] au lieu d'Object[]
    private int size;
    private int capacity;
    
    public GenPriorityQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("La capacité doit être positive");
        }
        this.capacity = capacity;
        this.size = 0;
        this.heap = (E[]) new Comparable[capacity]; // Cast une seule fois ici
    }
    
    public boolean insertElement(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Les éléments null ne sont pas autorisés");
        }
        
        if (size == capacity) {
            resize();
        }
        
        // Ajouter l'élément à la fin du tas
        heap[size] = element;
        size++;
        
        // Réorganiser le tas pour maintenir la propriété
        heapifyUp(size - 1);
        
        return true;
    }
    
    private void resize() {
        int newCapacity = capacity + 1;
        E[] newHeap = (E[]) new Comparable[newCapacity];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
        capacity = newCapacity;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            
            // pas besoin de cast
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

    private void swap(int i, int j) {
        E temp = heap[i]; 
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @Override
    public E element() {
        if (isEmpty()) {
            throw new NoSuchElementException("La file est vide");
        }
        return heap[0]; 
    }
    
    @Override
    public E popElement() {
        if (isEmpty()) {
            throw new NoSuchElementException("La file est vide");
        }
        
        E maxElement = heap[0]; 
        
        if (size == 1) {
            heap[0] = null;
            size = 0;
        } else {
            heap[0] = heap[size - 1]; 
            heap[size - 1] = null;
            size--;
            heapifyDown();
        }
        
        return maxElement;
    }

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
                // On compare les éléments avec compareTo
                if (left.compareTo(currentBiggest) > 0) {
                    biggest = leftChild;
                }
            }
            
            // Vérifier l'enfant droit
            if (rightChild < size) {
                E right = heap[rightChild];
                E currentBiggest = heap[biggest];
                // On compare les éléments avec compareTo
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
     * Itérateur pour la file de priorité générique
     */
    @Override
    public Iterator<E> iterator() {
        return new GenericPriorityQueueIterator();
    }
    
    private class GenericPriorityQueueIterator implements Iterator<E> {
        private int currentIndex = 0;
        
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }
        
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Aucun élément suivant");
            }
            
            E element = heap[currentIndex]; 
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

        if (isEmpty()) {
            sb.append("None, ".repeat(Math.max(0, this.capacity() - 1)));
            sb.append("None]");
            return sb.toString();
        }
        
        // Afficher les éléments dans l'ordre du tas (heap)
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(heap[i]);
        }
        
        // Afficher les emplacements vides restants
        sb.append(", None".repeat(Math.max(0, capacity - size)));
        
        sb.append("]");
        return sb.toString();
    }
}
