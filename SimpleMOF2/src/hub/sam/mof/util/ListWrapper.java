package hub.sam.mof.util;

import cmof.common.ReflectiveSequence;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Collection;
import java.util.NoSuchElementException;

public class ListWrapper extends CollectionWrapper implements List {
    
    private final ReflectiveSequence reflectiveSequence;

    public ListWrapper(ReflectiveSequence reflectiveSequence) {
        super(reflectiveSequence);
        this.reflectiveSequence = reflectiveSequence;
    }
    
    public void add(int index, Object element) {
        reflectiveSequence.add(index, element);
    }
    
    public boolean addAll(int index, Collection elements) {
        return reflectiveSequence.addAll(index, elements);
    }
    
    public Object get(int index) {
        return reflectiveSequence.get(index);
    }
    
    public Object set(int index, Object element) {
        return reflectiveSequence.set(index, element);
    }
    
    public Object remove(int index) {
        return reflectiveSequence.remove(index);
    }
    
    public List subList(int fromIndex, int toIndex) {
        return new ListWrapper(reflectiveSequence.subList(fromIndex, toIndex));
    }
    
    public int indexOf(Object specificObject) {
        int i=0;
        for(Object o: reflectiveSequence) {
            if (o.equals(specificObject)) return i;
            i++;
        }
        return -1;
    }

    public int lastIndexOf(Object specificObject) {
        int i=0;
        int last=-1;
        for(Object o: reflectiveSequence) {
            if (o.equals(specificObject)) last=i;
            i++;
        }
        return last;
    }
    
    private class Itr implements Iterator {
        protected int cursor = 0;
        protected int lastRet = -1;
        
        public boolean hasNext() {
            return cursor != size();
        }
        
        public Object next() {
            Object next = get(cursor);
            lastRet = cursor++;
            return next;
        }
        
        public void remove() {
            if (lastRet == -1)
                throw new IllegalStateException();

            ListWrapper.this.remove(lastRet);
            if (lastRet < cursor)
                cursor--;
            lastRet = -1;
        }
    }
    
    private class ListItr extends Itr implements ListIterator {
        
        public ListItr(int index) {
            cursor = index;
        }
        
        public boolean hasPrevious() {
            return cursor != 0;
        }        
        
        public Object previous() {
            int i = cursor - 1;
            Object previous = get(i);
            lastRet = cursor = i;
            return previous;
        }
        
        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor-1;
        }

        public void set(Object o) {
            if (lastRet == -1)
            throw new IllegalStateException();

            ListWrapper.this.set(lastRet, o);
        }
        
        public void add(Object o) {
            ListWrapper.this.add(cursor++, o);
            lastRet = -1;
        }
        
    }
    
    public ListIterator listIterator() {
        return listIterator(0);
    }
    
    public ListIterator listIterator(int index) {
        if (index<0 || index>size()) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }

        return new ListItr(index);
    }
    
}
