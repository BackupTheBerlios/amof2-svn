package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveSequence;

public class OclSequence<T> extends OclCollection<T> {

    protected OclSequence(ReflectiveSequence value) {
        super(value);
    }

    public OclSequence(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    private static final int COUNT = 1;

    @Override
    public OclInteger count(OclAny<T> object) {
        return null;
    }

    @Override
    public OclInteger countEval(OclAny<T> object) {
        return null;
    }

    private static final int OCL_EQUALS = 2;

    public OclBoolean oclEquals(OclSequence<T> s) {
        return null;
    }

    public OclBoolean oclEqualsEval(OclSequence<T> s) {
        return null;
    }

    private static final int UNION = 3;

    public OclSequence<T> union(OclSequence<T> s) {
        return null;
    }

    public OclSequence<T> unionEval(OclSequence<T> s) {
        return null;
    }

    private static final int FLATTEN = 4;

    public OclSequence flatten() {
        return null;
    }

    public OclSequence flattenEval() {
        return null;
    }

    private static final int APPEND = 5;

    public OclSequence<T> append(OclAny<T> o) {
        return null;
    }

    public OclSequence<T> appendEval(OclAny<T> o) {
        return null;
    }

    private static final int PREPEND = 6;

    public OclSequence<T> prepend(OclAny<T> o) {
        return null;
    }

    public OclSequence<T> prependEval(OclAny<T> o) {
        return null;
    }

    private static final int INSERT_AT = 7;

    public OclSequence<T> insertAt(OclInteger i, OclAny<T> o) {
        return null;
    }

    public OclSequence<T> insertAtEval(OclInteger i, OclAny<T> o) {
        return null;
    }

    private static final int SUBSEQUENCE = 8;

    public OclSequence<T> subsequence(OclInteger lower, OclInteger upper) {
        return null;
    }

    public OclSequence<T> subsequenceEval(OclInteger lower, OclInteger upper) {
        return null;
    }

    private static final int AT = 9;

    public OclAny<T> at(OclInteger i) {
        return null;
    }

    public OclAny<T> atEval(OclInteger i) {
        return null;
    }

    private static final int INDEX_OF = 10;

    public OclInteger indexOf(OclAny<T> o) {
        return null;
    }

    public OclInteger indexOfEval(OclAny<T> o) {
        return null;
    }

    private static final int FIRST = 11;

    public OclAny<T> first() {
        return null;
    }

    public OclAny<T> firstEval() {
        return null;
    }

    private static final int LAST = 12;

    public OclAny<T> last() {
        return null;
    }

    public OclAny<T> lastEval() {
        return null;
    }

    private static final int INCLUDING = 13;

    public OclSequence<T> including(OclAny<T> o) {
        return null;
    }

    public OclSequence<T> includingEval(OclAny<T> o) {
        return null;
    }

    private static final int EXCLUDING = 14;

    public OclSequence<T> excluding(OclAny<T> o) {
        return null;
    }

    public OclSequence<T> excludingEval(OclAny<T> o) {
        return null;
    }

    private static final int AS_BAG = 15;

    public OclBag<T> asBag() {
        return null;
    }

    public OclBag<T> asBagEval() {
        return null;
    }

    private static final int AS_SET = 16;

    public OclSet<T> asSet() {
        return null;
    }

    public OclSet<T> asSetEval() {
        return null;
    }

    private static final int AS_SEQUENCE = 17;

    public OclSequence<T> asSequence() {
        return null;
    }

    public OclSequence<T> asSequenceEval() {
        return null;
    }

    private static final int SELECT = 18;

    public OclSequence<T> select(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclSequence<T> selectEval(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    private static final int REJECT = 19;

    public OclSequence<T> reject(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclSequence<T> rejectEval(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    private static final int SORTED_BY = 20;

    public OclSequence<T> sortedBy(OclAny<T> iterator, OclInteger body) {
        return null;
    }

    public OclSequence<T> sortedByEval(OclAny<T> iterator, OclInteger body) {
        return null;
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case COUNT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case OCL_EQUALS:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case UNION:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case FLATTEN:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case APPEND:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case PREPEND:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INSERT_AT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case SUBSEQUENCE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INDEX_OF:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case FIRST:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case LAST:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INCLUDING:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case EXCLUDING:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_BAG:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_SET:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_SEQUENCE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case SELECT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case REJECT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case SORTED_BY:
                throw new RuntimeException("This part of the OCL library is not implemented");
            default:
                return super.eval(symbolCode, name, children);
        }
    }
}
