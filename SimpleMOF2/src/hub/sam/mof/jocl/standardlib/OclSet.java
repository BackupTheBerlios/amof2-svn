package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;

public class OclSet<T> extends OclCollection<T> {

    public OclSet(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    public OclSet(ReflectiveCollection object) {
        super(object);
    }

    private static final int UNION_SET = 1;

    public OclSet<T> union(OclSet<T> s) {
        return null;
    }

    public OclSet<T> unionEval(OclSet<T> s) {
        return null;
    }

    private static final int UNION_BAG = 2;

    public OclBag<T> union(OclBag<T> s) {
        return null;
    }

    public OclBag<T> unionEval(OclBag<T> s) {
        return null;
    }

    private static final int INTERSECTION_SET = 3;

    public OclSet<T> intersection(OclSet<T> s) {
        return null;
    }

    public OclSet<T> intersectionEval(OclSet<T> s) {
        return null;
    }

    private static final int INTERSECTION_BAG = 4;

    public OclSet<T> intersection(OclBag<T> s) {
        return null;
    }

    public OclSet<T> intersectionEval(OclBag<T> s) {
        return null;
    }

    private static final int SUB = 5;

    public OclSet<T> sub(OclSet<T> s) {
        return null;
    }

    public OclSet<T> subEval(OclSet<T> s) {
        return null;
    }

    private static final int INCLUDING = 6;

    public OclSet<T> including(OclAny<T> object) {
        return null;
    }

    public OclSet<T> includingEval(OclAny<T> object) {
        return null;
    }

    private static final int EXCLUDING = 7;

    public OclSet<T> excluding(OclAny<T> object) {
        return null;
    }

    public OclSet<T> excludingEval(OclAny<T> object) {
        return null;
    }

    private static final int SYMMETRIC_DIFFERENCE = 8;

    public OclSet<T> symmetricDifference(OclSet<T> s) {
        return null;
    }

    public OclSet<T> symmetricDifferenceEval(OclSet<T> s) {
        return null;
    }

    private static final int FLATTEN = 9;

    public OclSet<T> flatten() {
        return null;
    }

    public OclSet<T> flattenEval() {
        return null;
    }

    private static final int AS_SET = 10;

    public OclSet<T> asSet() {
        return null;
    }

    public OclSet<T> asSetEval() {
        return null;
    }

    private static final int AS_SEQUENCE = 11;

    public OclSequence<T> asSequence() {
        return null;
    }

    public OclSequence<T> asSequenceEval() {
        return null;
    }

    private static final int AS_BAG = 12;

    public OclBag<T> asBag() {
        return null;
    }

    public OclBag<T> asBagEval() {
        return null;
    }

    private static final int SELECT = 13;

    public OclSet<T> select(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclSet<T> selectEval(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    private static final int REJECT = 14;

    public OclSet<T> reject(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclSet<T> rejectEval(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    private static final int SORTED_BY = 15;

    public OclSequence<T> sortedBy(OclAny<T> iterator, OclInteger body) {
        return null;
    }

    public OclSequence<T> sortedByEval(OclAny<T> iterator, OclInteger body) {
        return null;
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case UNION_SET:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case UNION_BAG:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INTERSECTION_SET:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INTERSECTION_BAG:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case SUB:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INCLUDING:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case EXCLUDING:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case SYMMETRIC_DIFFERENCE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case FLATTEN:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_SET:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_SEQUENCE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_BAG:
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

