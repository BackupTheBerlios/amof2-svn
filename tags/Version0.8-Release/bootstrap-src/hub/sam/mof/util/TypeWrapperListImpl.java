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

import cmof.common.ReflectiveSequence;

public class TypeWrapperListImpl<E> extends TypeWrapperSetImpl<E> implements ReflectiveSequence<E> {
    
    private final ReflectiveSequence untypedList;
    
    public TypeWrapperListImpl(ReflectiveSequence untypedList) {
        super(untypedList);
        this.untypedList = untypedList;
    }    
    
    public E get(int index) {
        return (E)untypedList.get(index);
    }

    public void set(int index, Object element) {
        untypedList.set(index, element);
    }

    public void add(int index, Object element) {
        untypedList.add(index, element);
    }

    public void addAll(int index, Iterable<? extends Object> elements) {
        untypedList.addAll(index, elements);
    }

    public void remove(int index) {
        untypedList.remove(index);
    }

    public ReflectiveSequence<E> subList(int from, int to) {
        return (ReflectiveSequence<E>)untypedList.subList(from, to);
    }
    
    public String toString() {
        return untypedList.toString();
    }
}
