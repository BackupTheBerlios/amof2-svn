package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;

public class OclBag<T> extends OclCollection<T> {
    protected OclBag(ReflectiveCollection value) {
        super(value);
    }

    public OclBoolean oclEquals(OclBag<T> bag) {
        return null;
    }

    public OclBag<T> union(OclSet<T> s) {
        return null;
    }

    public OclBag<T> union(OclBag<T> b) {
        return null;
    }

    public OclBag<T> intersection(OclBag<T> b) {
        return null;
    }

    public OclSet<T> intersection(OclSet<T> s) {
        return null;
    }

    public OclBag<T> including(OclAny<T> object) {
        return null;
    }

    @Override
    public OclInteger count(OclAny<T> object) {
        return null;
    }

    public OclBag flatten() {
        return null;
    }

    public OclSequence<T> asSequence() {
        return null;
    }

    public OclSet<T> asSet() {
        return null;
    }

    public OclBag<T> select(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclBag<T> reject(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclSequence<T> sortedBy(OclAny<T> iterator, OclInteger body) {
        return null;
    }
}
