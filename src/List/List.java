package List;
import Iterable.Iterable;
/**
 * Interface List parametrizada por tipo y extiende de la interface Iterable.
 * @author Grupo 4
 */
public interface List<E> extends Iterable<E>{

    boolean addFirst(E element);

    boolean addLast(E element);

    boolean removeFirst();

    boolean removeLast();

    E getFirst();

    E getLast();

    boolean insert(int index, E element);

    boolean contains(E element);

    E get(int index);

    int indexOf(E element);

    boolean isEmpty();

    E remove(int index);

    boolean remove(E element);

    E set(int index, E element);

    int size();
}
