package container;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implémentation FIFO non générique d'une file pour le type Integer
 * utilisant un tableau circulaire
 */
public class IntFIFO implements Queue<Integer> {
    
    private Integer[] array;
    private int front;      // Index du premier élément
    private int rear;       // Index du dernier élément
    private int size;       // Nombre d'éléments dans la file
    private int capacity;   // Capacité maximale du tableau
 
    public IntFIFO(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("La capacité doit être positive");
        }
        this.capacity = capacity;
        this.array = new Integer[capacity];
        this.front = 0;
        this.rear = -1; 
        this.size = 0;
    }
    
    @Override
    public boolean insertElement(Integer e) {
        if (e == null) {
            throw new IllegalArgumentException("Les éléments null ne sont pas autorisés");
        }
        
        // Si la file est pleine, retourner false
        if (size == capacity) {
            System.out.println("La file est pleine, on ne peut plus ajouter d'élément");
            // return false (selon interprétation de l'énoncé)
            resize();
            System.out.println("La file à été redimensionnée: " + capacity);
        }
        
        // Calculer la nouvelle position rear
        rear = (rear + 1) % capacity;
        array[rear] = e;
        size++;
        
        return true;
    }

    private void resize() {
        int newCapacity = capacity * 2; // Doubler la capacité pour éviter les redimensionnements fréquents
        Integer[] newArray = new Integer[newCapacity];
        
        // Copier les éléments dans l'ordre logique (du front au rear)
        /** 
         System.arraycopy(array, 0, newArray, 0, capacity) copie les éléments 
         de 0 à capacity-1, mais dans un tableau circulaire,
         les éléments peuvent être à des positions différentes
         */
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(front + i) % capacity];
        }
        
        array = newArray;
        capacity = newCapacity;
        front = 0;  // Réinitialiser front à 0
        rear = size - 1;  // Rear pointe vers le dernier élément
    }
    
    @Override
    public Integer element() {
        if (isEmpty()) {
            throw new NoSuchElementException("La file est vide");
        }
        return array[front];
    }
    
    @Override
    public Integer popElement() {
        if (isEmpty()) {
            throw new NoSuchElementException("La file est vide");
        }
        
        Integer element = array[front];
        array[front] = null; // Libérer la référence
        front = (front + 1) % capacity;
        size--;
        
        return element;
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
     * Itérateur pour parcourir les éléments de la file
     */
    @Override
    public Iterator<Integer> iterator() {
        return new IntegerQueueIterator();
    }
    
    private class IntegerQueueIterator implements Iterator<Integer> {
        private int currentIndex = 0;
        private int elementsCounted = 0;
        
        @Override
        public boolean hasNext() {
            return elementsCounted < size;
        }
        
        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Aucun élément suivant");
            }
            
            // parcourt circulaire donc on utilise le modulo
            Integer element = array[(front + currentIndex) % capacity];
            currentIndex++;
            elementsCounted++;
            return element;
        }
    }
    
    /**
     * Retourne une représentation en chaîne de la file
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
        
        for (int i = 0; i < this.capacity(); i++) {
            Integer element = array[(front + i) % capacity];
            if (i < this.capacity() - 1) {
                if (i < size) {
                    sb.append(element + ", ");
                } else {
                    sb.append("None, ");
                }
            } else {
                if (i < size) {
                    sb.append(element + "]");
                } else {
                    sb.append("None]");
                }
            }
        }
        return sb.toString();
    }
}
