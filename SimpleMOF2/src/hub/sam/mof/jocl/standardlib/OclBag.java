package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;

public class OclBag<T> extends OclCollection<T> {

    protected OclBag(ReflectiveCollection value) {
        super(value);
    }

    public OclBag(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    private static final int OCL_EQUALS = 1;

    public OclBoolean oclEquals(OclBag<T> bag) {
        return null;
    }

    public OclBoolean oclEqualsEval(OclBag<T> bag) {
        return null;
    }

    private static final int UNION_SET = 2;

    public OclBag<T> union(OclSet<T> s) {
        return null;
    }

    public OclBag<T> unionEval(OclSet<T> s) {
        return null;
    }

    private static final int UNION_BAG = 3;

    public OclBag<T> union(OclBag<T> b) {
        return null;
    }

    public OclBag<T> unionEval(OclBag<T> b) {
        return null;
    }

    private static final int INTERSECTION_BAG = 4;

    public OclBag<T> intersection(OclBag<T> b) {
        return null;
    }

    public OclBag<T> intersectionEval(OclBag<T> b) {
        return null;
    }

    private static final int INTERSECTION_SET = 5;

    public OclSet<T> intersection(OclSet<T> s) {
        return null;
    }

    public OclSet<T> intersectionEval(OclSet<T> s) {
        return null;
    }

    private static final int INCLUDING = 6;

    public OclBag<T> including(OclAny<T> object) {
        return null;
    }

    public OclBag<T> includingEval(OclAny<T> object) {
        return null;
    }

    private static final int COUNT = 7;

    @Override
    public OclInteger count(OclAny<T> object) {
        return null;
    }

    @Override
    public OclInteger countEval(OclAny<T> object) {
        return null;
    }

    private static final int FLATTEN = 8;

    public OclBag flatten() {
        return null;
    }

    public OclBag flattenEval() {
        return null;
    }

    private static final int AS_SEQUENCE = 9;

    public OclSequence<T> asSequence() {
        return null;
    }

    public OclSequence<T> asSequenceEval() {
        return null;
    }

    private static final int AS_SET = 10;

    public OclSet<T> asSet() {
        return null;
    }

    public OclSet<T> asSetEval() {
        return null;
    }

    private static final int SELECT = 11;

    public OclBag<T> select(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclBag<T> selectEval(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    private static final int REJECT = 13;

    public OclBag<T> reject(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclBag<T> rejectEval(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    private static final int SORTED_BY = 14;

    public OclSequence<T> sortedBy(OclAny<T> iterator, OclInteger body) {
        return null;
    }

    public OclSequence<T> sortedByEval(OclAny<T> iterator, OclInteger body) {
        return null;
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case OCL_EQUALS:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case UNION_SET:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case UNION_BAG:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INTERSECTION_BAG:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INTERSECTION_SET:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INCLUDING:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case COUNT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case FLATTEN:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_SEQUENCE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_SET:
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
