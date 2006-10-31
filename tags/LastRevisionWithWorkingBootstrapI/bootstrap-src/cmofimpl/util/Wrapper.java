package cmofimpl.util;

public interface Wrapper<E,O> {
    public E getE(O forO);   
    public O getO(E forE);
}
