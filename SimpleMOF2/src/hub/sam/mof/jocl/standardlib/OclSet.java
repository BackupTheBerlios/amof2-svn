package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;

public class OclSet<T> extends OclCollection<T> {

    public OclSet(ReflectiveCollection object) {
        super(object);
    }

    public OclSet<T> union(OclSet<T> s) {
        return null;
    }

    public OclBag<T> union(OclBag<T> s) {
        return null;
    }

    public OclSet<T> intersection(OclSet<T> s) {
        return null;
    }

    public OclSet<T> intersection(OclBag<T> s) {
        return null;
    }

    public OclSet<T> sub(OclSet<T> s) {
        return null;
    }

    public OclSet<T> including(OclAny<T> object) {
        return null;
    }

    public OclSet<T> excluding(OclAny<T> object) {
        return null;
    }

    public OclSet<T> symmetricDifference(OclSet<T> s) {
        return null;
    }

    public OclSet<T> flatten() {
        return null;
    }

    public OclSet<T> asSet() {
        return null;
    }

    public OclSequence<T> asSequence() {
        return null;
    }

    public OclBag<T> asBag() {
        return null;
    }

    public OclSet<T> select(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclSet<T> reject(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclSequence<T> sortedBy(OclAny<T> iterator, OclInteger body) {
        return null;
    }
}

