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

public class MyVector<E> implements java.util.List<E> {
    private final Vector<E> v = new Vector<E>();
    public MyVector() {
        
    }
    public int size() {
        return v.size();
    }
    public boolean isEmpty() {
        return v.isEmpty();
    }
    public boolean contains(Object o) {
        return v.contains(o);
    }
    public Iterator<E> iterator() {
        return v.iterator();
    }
    public Object[] toArray() {
        return v.toArray();
    }
    public <T> T[] toArray(T[] a) {
        return v.toArray(a);
    }
    public boolean add(E o) {
        return v.add(o);
    }
    public boolean remove(Object o) {
        return v.remove(o);
    }
    public boolean containsAll(Collection<?> c) {
        return v.containsAll(c);
    }
    public boolean addAll(Collection<? extends E> c) {
        return v.addAll(c);
    }
    public boolean addAll(int index, Collection<? extends E> c) {
        return v.addAll(index, c);
    }
    public boolean removeAll(Collection<?> c) {
        return v.removeAll(c);
    }
    public boolean retainAll(Collection<?> c) {
        return v.retainAll(c);
    }
    public void clear() {
        v.clear();
    }
    public E get(int index) {
        return v.get(index);
    }
    public E set(int index, E element) {
        int m = v.size();
        E result = v.set(index,element);
        if (m > v.size()) {
            System.out.println("###");
        }        
        return v.set(index,element);
    }
    public void add(int index, E element) {
        v.add(index,element);
    }
    public E remove(int index) {
        return v.remove(index);
    }
    public int indexOf(Object o) {
        return v.indexOf(o);
    }
    public int lastIndexOf(Object o) {
        return v.lastIndexOf(o);
    }
    
    class MyListIterator<E> implements ListIterator<E> {
        ListIterator<E> b;
        MyListIterator(ListIterator<E> base) {
            this.b = base;
        }
        public boolean hasNext() {
            return b.hasNext();
        }
        public E next() {
            return b.next();
        }
        public boolean hasPrevious() {
            return b.hasPrevious();
        }
        public E previous() {
            return b.previous();
        }
        public int nextIndex() {
            return b.nextIndex();
        }
        public int previousIndex() {
            return b.previousIndex();
        }
        public void remove() {
            b.remove();
        }
        public void set(E o) {
            if (o == null) {
                System.out.println("####");
            }
            b.set(o);
        }
        public void add(E o) {
            b.add(o);
        }
    }
    public ListIterator<E> listIterator() {
        return new MyListIterator(v.listIterator());
    }
    public ListIterator<E> listIterator(int index) {
        return new MyListIterator(v.listIterator(index));
    }
    public List<E> subList(int fromIndex, int toIndex) {
        return v.subList(fromIndex, toIndex);
    }       
}
