// Import de l'exception NoSuchElementException pour les cas où la file est vide
import java.util.NoSuchElementException;

/**
 * Interface Queue<E> - Définit le contrat pour une structure de données de type "file"
 * 
 * Une file (queue) est une structure de données linéaire qui suit le principe FIFO
 * (First In, First Out) : le premier élément ajouté est le premier à être retiré.
 * 
 * @param <E> Le type générique des éléments stockés dans la file. E est un paramètre
 *           de type qui peut représenter n'importe quel type d'objet Java (Integer,
 *           String, Object personnalisé, etc.). Il permet de créer une file qui peut
 *           stocker n'importe quel type d'élément de manière type-safe.
 */

public interface Queue<E> extends Iterable<E> {
    /**
     * Une interface en Java est un contrat qui définit un ensemble de méthodes abstraites
     * que les classes qui l'implémentent doivent fournir. Elle permet de :
     * 
     * - Définir un comportement commun que plusieurs classes non liées peuvent implémenter
     * - Établir un contrat entre le code client et les classes d'implémentation
     * - Réaliser une forme de polymorphisme sans héritage de classe
     * - Définir des constantes partagées entre les implémentations
     * 
     * Caractéristiques principales :
     * - Toutes les méthodes sont public abstract par défaut
     * - Tous les attributs sont public static final par défaut
     * - Une classe peut implémenter plusieurs interfaces
     * - Une interface peut étendre d'autres interfaces
     * 
     * Exemple d'utilisation de cette interface Queue<E> :
     * public class MaFile<E> implements Queue<E> {
     *     // Doit implémenter toutes les méthodes définies
     * }
     */
    
    /**
     * L'interface Iterable<E> permet de parcourir les éléments de la file
     * de manière séquentielle, du premier au dernier élément, sans modifier
     * la structure de la file. Cela permet notamment d'utiliser la file
     * dans une boucle for-each.
     */
    
    /**
     * Ajoute un élément à la fin de la file
     * 
     * Cette méthode insère l'élément spécifié à la fin de la file. Si la capacité
     * actuelle n'est pas suffisante, la capacité de la file peut être augmentée
     * automatiquement selon l'implémentation.
     * 
     * @param e L'élément à ajouter à la file (ne doit pas être null)
     * @return true si l'élément a été ajouté avec succès, false sinon
     * @throws IllegalArgumentException si l'élément est null (selon l'implémentation)
     */
    boolean insertElement(E e);

    /**
     * Consulte le premier élément de la file SANS le supprimer
     * 
     * Cette méthode permet de "regarder" le premier élément de la file (celui qui
     * sera le prochain à être retiré) sans le supprimer de la structure.
     * 
     * @return Le premier élément de la file (le plus ancien)
     * @throws NoSuchElementException si la file est vide (aucun élément à consulter)
     */
    E element();

    /**
     * Retire et retourne le premier élément de la file
     * 
     * Cette méthode suit le principe FIFO : elle retire le premier élément ajouté
     * (le plus ancien) et le retourne. Après cet appel, l'élément n'est plus dans la file.
     * 
     * @return Le premier élément de la file (celui qui vient d'être retiré)
     * @throws NoSuchElementException si la file est vide (aucun élément à retirer)
     */
    E popElement();

    /**
     * Vérifie si la file est vide
     * 
     * @return true si la file ne contient aucun élément, false sinon
     */
    boolean isEmpty();

    /**
     * Retourne le nombre d'éléments actuellement dans la file
     * 
     * @return Le nombre d'éléments dans la file (0 si la file est vide)
     */
    int size();
}