package cmofimpl.util;

import java.util.Iterator;

import cmof.common.ReflectiveCollection;

public class TypeWrapperSetImpl<E> implements ReflectiveCollection<E> {
    
    private final ReflectiveCollection untypedSet;
    
    public TypeWrapperSetImpl(ReflectiveCollection untypedSet) {
        this.untypedSet = untypedSet;
    }    

    public boolean add(Object element) {
        return untypedSet.add(element);
    }

    public boolean contains(Object element) {
        return untypedSet.contains(element);
    }

    public boolean remove(Object element) {
        return untypedSet.remove(element);
    }

    public boolean addAll(Iterable<? extends Object> elements) {
        return untypedSet.addAll(elements);
    }

    public boolean containsAll(Iterable<? extends Object> elements) {
        return untypedSet.containsAll(elements);
    }

    public boolean removeAll(Iterable<? extends Object> elements) {
        return untypedSet.removeAll(elements);
    }

    public int size() {
        return untypedSet.size();
    }

    public Iterator<E> iterator() {
        return (Iterator<E>)untypedSet.iterator();
    } 
 
}
