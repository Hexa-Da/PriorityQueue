package container;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implémentation générique d'une file de priorité utilisant un tas (heap) binaire
 * Les éléments doivent implémenter l'interface Comparable<E>
 */
@SuppressWarnings("unchecked") // Pour éviter les warnings de type erasure
public class GenPriorityQueue<E extends Comparable<E>> implements Queue<E> {
    
    private Object[] heap; // Tableau d'Object pour éviter les problèmes de type erasure
    private int size;
    private int capacity;
    
    public GenPriorityQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("La capacité doit être positive");
        }
        this.capacity = capacity;
        this.size = 0;
        this.heap = new Object[capacity];
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
        Object[] newHeap = new Object[newCapacity];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
        capacity = newCapacity;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            
            // Vérifier que les éléments ne sont pas null avant comparaison
            if (heap[index] != null && heap[parentIndex] != null) {
                // Dans IntPriorityQueue, on compare directement les Integer avec <
                // Ici on doit caster les Object en E et utiliser compareTo car on ne sait pas
                // quel type sera utilisé, on sait juste qu'il implémente Comparable<E>
                E current = (E) heap[index];
                E parent = (E) heap[parentIndex];
                
                if (current.compareTo(parent) >= 0) {
                    break; // La propriété de tas est respectée
                }
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break; // Si un élément est null, arrêter
            }
        }
    }

    private void swap(int i, int j) {
        E temp = (E) heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @Override
    public E element() {
        if (isEmpty()) {
            throw new NoSuchElementException("La file est vide");
        }
        return (E) heap[0]; // Cast vers E
    }
    
    @Override
    public E popElement() {
        if (isEmpty()) {
            throw new NoSuchElementException("La file est vide");
        }
        
        E minElement = (E) heap[0]; 
        
        if (size == 1) {
            heap[0] = null;
            size = 0;
        } else {
            heap[0] = heap[size - 1]; 
            heap[size - 1] = null;
            size--;
            heapifyDown(0);
        }
        
        return minElement;
    }

    private void heapifyDown(int index) {
        while (true) {
            int smallest = index;
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            
            if (leftChild < size && heap[leftChild] != null) {
                E left = (E) heap[leftChild];
                E current = (E) heap[smallest];
                // On compare les éléments avec compareTo
                if (left.compareTo(current) < 0) {
                    smallest = leftChild;
                }
            }
            
            if (rightChild < size && heap[rightChild] != null) {
                E right = (E) heap[rightChild];
                E current = (E) heap[smallest];
                // On compare les éléments avec compareTo
                if (right.compareTo(current) < 0) {
                    smallest = rightChild;
                }
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
            
            E element = (E) heap[currentIndex]; // Cast vers E
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
            for (int i = 0; i < this.capacity() - 1; i++) {
                sb.append("None, ");
            }
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
        for (int i = size; i < capacity; i++) {
            sb.append(", None");
        }
        
        sb.append("]");
        return sb.toString();
    }
}
