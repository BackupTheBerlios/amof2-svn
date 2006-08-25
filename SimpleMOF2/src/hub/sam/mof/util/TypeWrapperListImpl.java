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

import hub.sam.mof.reflection.ObjectImpl;
import hub.sam.mof.reflection.client.impl.ClientObjectImpl;
import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;

public class TypeWrapperListImpl<E> extends TypeWrapperSetImpl<E> implements ReflectiveSequence<E> {

    private final ReflectiveSequence untypedList;

    public ReflectiveCollection getUnypedSet() {
    	return untypedList;
    }

    public TypeWrapperListImpl(ReflectiveSequence untypedList) {
        super(untypedList);
        this.untypedList = untypedList;
    }

    public TypeWrapperListImpl(ReflectiveSequence untypedList, ObjectImpl objectImpl, String propertyName) {
        super(untypedList,objectImpl,propertyName);
        this.untypedList = untypedList;
    }

    public TypeWrapperListImpl(ReflectiveSequence untypedList, ClientObjectImpl clientObjectImpl, String propertyName) {
        super(untypedList);
        this.untypedList = untypedList;
    }

    @SuppressWarnings("unchecked")
	public E get(int index) {
        return (E)untypedList.get(index);
    }

    public void set(int index, Object element) {
        if (objectImpl != null && objectImpl.hasListeners() && (index >= size() ||
               !get(index).equals(element))) {
            objectImpl.firePropertyChange(propertyName, null, null);
        }
        untypedList.set(index, element);
    }

    public void add(int index, Object element) {
   		if (objectImpl != null && objectImpl.hasListeners()) {
   			objectImpl.firePropertyChange(propertyName, null, null);
   		}
        untypedList.add(index, element);
    }

    @SuppressWarnings("unchecked")
	public void addAll(int index, Iterable<? extends Object> elements) {
   		if (objectImpl != null && objectImpl.hasListeners()) {
   			objectImpl.firePropertyChange(propertyName, null, null);
   		}
        untypedList.addAll(index, elements);
    }

    public void remove(int index) {
   		if (untypedList.size() > 0 && objectImpl != null && objectImpl.hasListeners()) {
   			objectImpl.firePropertyChange(propertyName, null, null);
   		}
        untypedList.remove(index);
    }

    @SuppressWarnings("unchecked")
	public ReflectiveSequence<E> subList(int from, int to) {
        return (ReflectiveSequence<E>)untypedList.subList(from, to);
    }

    @Override
	public String toString() {
        return untypedList.toString();
    }
}
