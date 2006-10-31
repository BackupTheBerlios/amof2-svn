package cmof.common;

public interface ReflectiveCollection<E> extends Iterable<E> {
    
    public boolean add(Object element);

    public boolean contains(Object element);
    
    public boolean remove(Object element);
    
    public boolean addAll(Iterable<? extends Object> elements);

    public boolean containsAll(Iterable<? extends Object> elements);
    
    public boolean removeAll(Iterable<? extends Object> elements);
    
    public int size();    
}
