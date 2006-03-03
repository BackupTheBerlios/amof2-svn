package cmof.common;


public interface ReflectiveSequence<E> extends ReflectiveCollection<E> {

    public E get(int index);
    
    public void set(int index, Object element);
    
    public void add(int index, Object element);
    
    public void addAll(int index, Iterable<? extends Object> elements);
    
    public void remove(int index);
    
    public ReflectiveSequence<E> subList(int from, int to);    
}
