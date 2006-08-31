package hub.sam.mof.util;

import java.util.Collection;
import java.util.Iterator;

import cmof.common.ReflectiveCollection;

public class CollectionWrapper implements Collection {
    
    private final ReflectiveCollection reflectiveCollection;
    
    public CollectionWrapper(ReflectiveCollection reflectiveCollection) {
        this.reflectiveCollection = reflectiveCollection;
    }
    
    public boolean contains(Object obj) {
        return reflectiveCollection.contains(obj);
    }
    
    @SuppressWarnings("unchecked")
    public boolean containsAll(Collection c) {
        return reflectiveCollection.containsAll(c);
    }
    
    public boolean remove(Object obj) {
        return reflectiveCollection.remove(obj);
    }
    
    @SuppressWarnings("unchecked")
    public boolean removeAll(Collection c) {
        return reflectiveCollection.removeAll(c);
    }
    
    public boolean add(Object obj) {
        return reflectiveCollection.add(obj);
    }
    
    @SuppressWarnings("unchecked")
    public boolean addAll(Collection c) {
        return reflectiveCollection.addAll(c);
    }
    
    public Iterator iterator() {
        return reflectiveCollection.iterator();
    }
    
    public int size() {
        return reflectiveCollection.size();
    }
    
    public boolean isEmpty() {
        return reflectiveCollection.size() == 0;
    }
    
    public void clear() {
        reflectiveCollection.clear();
    }

    public boolean retainAll(Collection collection) {
        // empty sets
        if (collection.isEmpty() || isEmpty()) return false;
        // equal sets
        if (collection.size() == size() && containsAll(collection)) return false;
        
        // in any other case we will modify the set
        for(Object obj: collection) {
            if (!contains(obj)) {
                remove(obj);
            }
        }
        clear();
        addAll(collection);
        return true;
    }
    
    public Object[] toArray() {
        Object[] objectArray = new Object[size()];
        
        int i=0;
        for(Object object: reflectiveCollection) {
            objectArray[i++] = object;
        }
        
        return objectArray;
    }
    
    @SuppressWarnings("unchecked")
    public Object[] toArray(Object[] a) {
        return toArray();
    }
    
}
