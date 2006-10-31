package cmofimpl.util;

import java.util.*;

public class MultiMap<E, V> {

    protected HashMap<E, Collection<V>> values = new HashMap<E, Collection<V>>();
    
    public void put(E key, V value) {
        Collection<V> set = values.get(key);
        if (set == null) {
            set = new HashSet<V>();
            values.put(key, set);
        }
        set.add(value);
    }
    
    public void putAll(E key, Iterable<V> value) {
        Collection<V> set = values.get(key);
        if (set == null) {
            set = new HashSet<V>();
            values.put(key, set);
        }
        for (V v: value) {
            set.add(v);    
        }        
    }
    
    public Collection<V> get(E key) {
        Collection<V> result = values.get(key);
        if (result == null) {
            result = new HashSet<V>();
            values.put(key, result);
        } 
        return result;
    }    
}
