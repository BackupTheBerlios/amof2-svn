package cmofimpl.util;

import java.util.*;

public class ListImpl<E> implements cmof.common.ReflectiveSequence<E> {
    protected List<E> values = new Vector<E>();
    
    public ListImpl() {}
    
    public ListImpl(Iterable<? extends E> copy) {
        for (E elem: copy) {
            values.add(elem);
        }
    }

    
    public static <E> cmof.common.ReflectiveSequence<E> asList(Iterable<E> values) {
        ListImpl<E> result = new ListImpl<E>();
        for (E value: values) {
            result.values.add(value);
        }
        return result;
    }
    
    public static <E> cmof.common.ReflectiveSequence<E> asList(E[] values) {
        ListImpl<E> result = new ListImpl<E>();
        for (E value: values) {
            result.values.add(value);
        }
        return result;
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

    public E get(int index) {
        return values.get(index);
    }

    public void set(int index, Object element) {
        values.set(index, (E)element);
    }

    public void add(int index, Object element) {
        values.add(index, (E)element);
    }

    public void addAll(int index, Iterable<? extends Object> elements) {
        for (Object object: elements) {
            add(index++, object);
        }
    }

    public void remove(int index) {
        values.remove(index);
    }

    public int size() {
        return values.size();        
    }
    
    public cmof.common.ReflectiveSequence<E> subList(int from, int to) {
        ListImpl<E> newList = new ListImpl<E>();
        newList.values = values.subList(from, to);
        return newList;
    }
    
    public String toString() {
        return values.toString();
    }
}