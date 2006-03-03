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

package hub.sam.mof.util;

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
 
    public String toString() {
        return untypedSet.toString();
    }
}
