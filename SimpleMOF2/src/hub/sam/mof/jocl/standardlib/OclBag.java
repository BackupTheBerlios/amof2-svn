package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;

public class OclBag<OT,ET> extends OclCollection<OT,ET> {

    protected OclBag(ReflectiveCollection value) {
        super(value);
    }

    public OclBag(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    private static final int OCL_EQUALS = 1;

    public OclBoolean oclEquals(OclBag<OT,ET> bag) {
        return null;
    }

    public OclBoolean oclEqualsEval(OclBag<OT,ET> bag) {
        return null;
    }

    private static final int UNION_SET = 2;

    public OclBag<OT,ET> union(OclSet<OT,ET> s) {
        return null;
    }

    public OclBag<OT,ET> unionEval(OclSet<OT,ET> s) {
        return null;
    }

    private static final int UNION_BAG = 3;

    public OclBag<OT,ET> union(OclBag<OT,ET> b) {
        return null;
    }

    public OclBag<OT,ET> unionEval(OclBag<OT,ET> b) {
        return null;
    }

    private static final int INTERSECTION_BAG = 4;

    public OclBag<OT,ET> intersection(OclBag<OT,ET> b) {
        return null;
    }

    public OclBag<OT,ET> intersectionEval(OclBag<OT,ET> b) {
        return null;
    }

    private static final int INTERSECTION_SET = 5;

    public OclSet<OT,ET> intersection(OclSet<OT,ET> s) {
        return null;
    }

    public OclSet<OT,ET> intersectionEval(OclSet<OT,ET> s) {
        return null;
    }

    private static final int INCLUDING = 6;

    public OclBag<OT,ET> including(OT object) {
        return null;
    }

    public OclBag<OT,ET> includingEval(OT object) {
        return null;
    }

    private static final int COUNT = 7;

    @Override
    public OclInteger count(OT object) {
        return null;
    }

    @Override
    public OclInteger countEval(OT object) {
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

    public OclSequence<OT,ET> asSequence() {
        return null;
    }

    public OclSequence<OT,ET> asSequenceEval() {
        return null;
    }

    private static final int AS_SET = 10;

    public OclSet<OT,ET> asSet() {
        return null;
    }

    public OclSet<OT,ET> asSetEval() {
        return null;
    }

    private static final int SELECT = 11;

    public OclBag<OT,ET> select(OT iterator, OclBoolean body) {
        return null;
    }

    public OclBag<OT,ET> selectEval(OT iterator, OclBoolean body) {
        return null;
    }

    private static final int REJECT = 13;

    public OclBag<OT,ET> reject(OT iterator, OclBoolean body) {
        return null;
    }

    public OclBag<OT,ET> rejectEval(OT iterator, OclBoolean body) {
        return null;
    }

    private static final int SORTED_BY = 14;

    public OclSequence<OT,ET> sortedBy(OT iterator, OclInteger body) {
        return null;
    }

    public OclSequence<OT,ET> sortedByEval(OT iterator, OclInteger body) {
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
