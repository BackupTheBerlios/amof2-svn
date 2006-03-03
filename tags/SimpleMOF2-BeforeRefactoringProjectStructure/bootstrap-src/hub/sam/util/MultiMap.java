/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
*/

package hub.sam.util;

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
    
    public void putAll(E key, Iterable<? extends V> value) {
        Collection<V> set = values.get(key);
        if (set == null) {
            set = new HashSet<V>();
            values.put(key, set);
        }
        for (V v: value) {
            set.add(v);    
        }        
    }
    
    public void removeValue(V value) {
        for (Collection<V> values: this.values.values()) {
            values.remove(value);
        }
    }
    
    public void removeKey(E key) {
        values.get(key).clear();
        values.remove(key);
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
