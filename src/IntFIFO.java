import java.util.Iterator;
import java.util.NoSuchElementException;
import container.Queue;

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
            return false;
        }
        
        // Calculer la nouvelle position rear
        rear = (rear + 1) % capacity;
        array[rear] = e;
        size++;
        
        return true;
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
    
    @Override
    public Iterator<Integer> iterator() {
        return new IntegerQueueIterator();
    }
    
    /**
     * Itérateur pour parcourir les éléments de la file
     */
    private class IntegerQueueIterator implements Iterator<Integer> {
        private int currentIndex = 0;
        private int elementsProcessed = 0;
        
        @Override
        public boolean hasNext() {
            return elementsProcessed < size;
        }
        
        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Aucun élément suivant");
            }
            
            Integer element = array[(front + currentIndex) % capacity];
            currentIndex++;
            elementsProcessed++;
            return element;
        }
    }
    
    /**
     * Retourne une représentation en chaîne de la file
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(array[(front + i) % capacity]);
        }
        
        sb.append("]");
        return sb.toString();
    }
}
