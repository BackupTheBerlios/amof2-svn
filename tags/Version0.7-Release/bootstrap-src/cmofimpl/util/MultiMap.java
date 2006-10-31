package cmofimpl.util;

import java.util.HashMap;

import cmof.common.ReflectiveCollection;

public class MultiMap<E, V> {

    protected HashMap<E, ReflectiveCollection<V>> values = new HashMap<E, ReflectiveCollection<V>>();
    
    public void put(E key, V value) {
        ReflectiveCollection<V> set = values.get(key);
        if (set == null) {
            set = new SetImpl<V>();
            values.put(key, set);
        }
        set.add(value);
    }
    
    public void putAll(E key, Iterable<V> value) {
        ReflectiveCollection<V> set = values.get(key);
        if (set == null) {
            set = new SetImpl<V>();
            values.put(key, set);
        }
        set.addAll(value);
    }
    
    public ReflectiveCollection<V> get(E key) {
        ReflectiveCollection<V> result = values.get(key);
        if (result == null) {
            result = new SetImpl<V>();
            values.put(key, result);
        } 
        return result;
    }    
}
