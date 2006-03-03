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

import java.util.*;
import cmof.common.*;

public class ReadOnlyListWrapper<E,O> implements ReflectiveSequence<E> {

    private final List<? extends O> wrapped;
    private Wrapper<E,O> wrapper;
    protected void setWrapper(Wrapper<E,O> wrapper) {
        this.wrapper = wrapper;
    }
    public ReadOnlyListWrapper(List<? extends O> wrapped, Wrapper<E,O> wrapper) {
        this.wrapped = wrapped;
        this.wrapper = wrapper;
    }
    
    public void add(int index, Object element) {
        throw new IllegalAccessError();
    }
    public void addAll(int index, Iterable<? extends Object> elements) {
        throw new IllegalAccessError();
    }
    public E get(int index) {
        return wrapper.getE(wrapped.get(index));
    }
    public void remove(int index) {
        throw new IllegalAccessError();
    }
    public void set(int index, Object element) {
        throw new IllegalAccessError();
    }
    public ReflectiveSequence<E> subList(int from, int to) {
        return new ReadOnlyListWrapper<E,O>(wrapped.subList(from,to), wrapper);
    }
    public boolean add(Object element) {
        throw new IllegalAccessError();        
    }
    public boolean addAll(Iterable<? extends Object> elements) {
        throw new IllegalAccessError();
    }
    @SuppressWarnings("unchecked")
	public boolean contains(Object element) {
        return wrapped.contains(wrapper.getO((E)element));
    }
    public boolean containsAll(Iterable<? extends Object> elements) {
        throw new IllegalAccessError();
    }
    public boolean remove(Object element) {
        throw new IllegalAccessError();
    }
    public boolean removeAll(Iterable<? extends Object> elements) {
        throw new IllegalAccessError();
    }
    public int size() {
        if (wrapped == null) {
            return 0;
        } else {
            return wrapped.size();
        }
    }
    public Iterator<E> iterator() {
        return new MyIterator(wrapped.iterator());
    }
    
    class MyIterator implements Iterator<E> {
        private final Iterator<? extends O> wrapped;
        public MyIterator(Iterator<? extends O> wrapped) {
            this.wrapped = wrapped;
        }
        public boolean hasNext() {
            return wrapped.hasNext();
        }
        public E next() {
            return wrapper.getE(wrapped.next());
        }
        public void remove() {
            throw new IllegalAccessError();
        }        
    }

    @Override
	public String toString() {
        return wrapped.toString();
    }
}
