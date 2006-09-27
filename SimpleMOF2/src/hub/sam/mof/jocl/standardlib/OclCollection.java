package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;

public class OclCollection<T> extends OclAny<ReflectiveCollection<T>> {
    protected OclCollection(Object value) {
        super(value);
    }

    public OclInteger size() {
        return null;
    }

    public OclBoolean includes(OclAny<T> object) {
        return null;
    }

    public OclBoolean excludes(OclAny<T> object) {
        return null;
    }

    public OclInteger count(OclAny<T> object) {
        return null;
    }

    public OclBoolean includesAll(OclCollection<T> c2) {
        return null;
    }

    public OclBoolean excludesAll(OclCollection<T> c2) {
        return null;
    }

    public OclBoolean isEmpty() {
        return null;
    }

    public OclBoolean notEmpty() {
        return null;
    }

    public OclBoolean exists(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclBoolean forAll(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclBoolean forAll(OclAny<T>[] iterators, OclBoolean body) {
        return null;
    }

    public OclBoolean isUnique(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclAny<T> any(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclBoolean one(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public <E> OclCollection<E> collect(OclAny<T> iterator, OclAny<E> body) {
        return null;
    }
}
