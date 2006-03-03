package cmofimpl.util;

import java.util.*;

public class Tree<E> extends MultiMap<E, E>{

    public MultiMap<E, E> collapseTree() {
        MultiMap<E, E> result = new MultiMap<E, E>();
        for (E key: values.keySet()) {
            collectAllValues(key, result.get(key));
        }
        return result;
    }
    
    private void collectAllValues(E key, Collection<E> result) { 
        Collection<E> values = this.values.get(key);
        if (values != null) {
            for (E value: values) {
                result.add(value);
                collectAllValues(value, result);            
            }                
        }
    }
}
