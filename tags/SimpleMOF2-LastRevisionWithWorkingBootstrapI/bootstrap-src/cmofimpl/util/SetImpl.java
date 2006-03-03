package cmofimpl.util;

import java.util.*;

public class SetImpl<E> implements cmof.common.ReflectiveCollection<E> {
    private final Collection<E> values;
    
    public SetImpl() {
        this.values = new HashSet<E>();        
    }
    
    public SetImpl(Iterable<? extends E> copy) {
        this();
        for (E elem: copy) {
            values.add(elem);
        }
    }
    
    public SetImpl(Collection<E> values) {
        this.values = values;
    }
    
    public boolean add(Object element) {        
        return values.add((E)element);
    }

    public boolean contains(Object element) {
        return values.contains((E)element);
    }

    public boolean remove(Object element) {
        return values.remove((E)element);
    }

    public boolean addAll(Iterable<? extends Object> elements) {
        boolean result = false;
        for (Object element: elements) {
            result = add(element) || result;
        }
        return result;
    }

    public boolean containsAll(Iterable<? extends Object> elements) {
        boolean result = true;
        for (Object element: elements) {
            result = result && contains(element);
        }
        return result;        
    }

    public boolean removeAll(Iterable<? extends Object> elements) {        
        boolean result = false;
        for (Object element: elements) {
            result = remove(element) || result;
        }
        return result;
    }

    public Iterator<E> iterator() {
        return values.iterator();
    }

    public int size() {
        return values.size();
    }

    public String toString() {
        return values.toString();
    }
    
    public Collection<E> toCollection() {
        Collection<E> result = new Vector<E>();
        for (E o: this) {
            result.add(o);
        }
        return result;
    }
}
