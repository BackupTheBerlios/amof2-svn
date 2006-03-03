package cmofimpl.util;

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
