package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveSequence;

public class OclSequence<T> extends OclCollection<T> {

    protected OclSequence(ReflectiveSequence value) {
        super(value);
    }

    @Override
    public OclInteger count(OclAny<T> object) {
        return null;
    }

    public OclBoolean oclEquals(OclSequence<T> s) {
        return null;
    }

    public OclSequence<T> union(OclSequence<T> s) {
        return null;
    }

    public OclSequence flatten() {
        return null;
    }

    public OclSequence<T> append(OclAny<T> o) {
        return null;
    }

    public OclSequence<T> prepend(OclAny<T> o) {
        return null;
    }

    public OclSequence<T> insertAt(OclInteger i, OclAny<T> o) {
        return null;
    }

    public OclSequence<T> subsequence(OclInteger lower, OclInteger upper) {
        return null;
    }

    public OclAny<T> at(OclInteger i) {
        return null;
    }

    public OclInteger indexOf(OclAny<T> o) {
        return null;
    }

    public OclAny<T> first() {
        return null;
    }

    public OclAny<T> last() {
        return null;
    }

    public OclSequence<T> including(OclAny<T> o) {
        return null;
    }

    public OclSequence<T> excluding(OclAny<T> o) {
        return null;
    }

    public OclBag<T> asBag() {
        return null;
    }

    public OclSet<T> asSet() {
        return null;
    }

    public OclSequence<T> asSequence() {
        return null;
    }

    public OclSequence<T> select(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclSequence<T> reject(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclSequence<T> sortedBy(OclAny<T> iterator, OclInteger body) {
        return null;
    }
}
