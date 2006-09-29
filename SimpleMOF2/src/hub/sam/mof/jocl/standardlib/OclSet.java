package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;

public class OclSet<OT,ET> extends OclCollection<OT,ET> {

    public OclSet(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    public OclSet(ReflectiveCollection object) {
        super(object);
    }

    private static final int UNION_SET = 1;

    public OclSet<OT,ET> union(OclSet<OT,ET> s) {
        return null;
    }

    public OclSet<OT,ET> unionEval(OclSet<OT,ET> s) {
        return null;
    }

    private static final int UNION_BAG = 2;

    public OclBag<OT,ET> union(OclBag<OT,ET> s) {
        return null;
    }

    public OclBag<OT,ET> unionEval(OclBag<OT,ET> s) {
        return null;
    }

    private static final int INTERSECTION_SET = 3;

    public OclSet<OT,ET> intersection(OclSet<OT,ET> s) {
        return null;
    }

    public OclSet<OT,ET> intersectionEval(OclSet<OT,ET> s) {
        return null;
    }

    private static final int INTERSECTION_BAG = 4;

    public OclSet<OT,ET> intersection(OclBag<OT,ET> s) {
        return null;
    }

    public OclSet<OT,ET> intersectionEval(OclBag<OT,ET> s) {
        return null;
    }

    private static final int SUB = 5;

    public OclSet<OT,ET> sub(OclSet<OT,ET> s) {
        return null;
    }

    public OclSet<OT,ET> subEval(OclSet<OT,ET> s) {
        return null;
    }

    private static final int INCLUDING = 6;

    public OclSet<OT,ET> including(OT object) {
        return null;
    }

    public OclSet<OT,ET> includingEval(OT object) {
        return null;
    }

    private static final int EXCLUDING = 7;

    public OclSet<OT,ET> excluding(OT object) {
        return null;
    }

    public OclSet<OT,ET> excludingEval(OT object) {
        return null;
    }

    private static final int SYMMETRIC_DIFFERENCE = 8;

    public OclSet<OT,ET> symmetricDifference(OclSet<OT,ET> s) {
        return null;
    }

    public OclSet<OT,ET> symmetricDifferenceEval(OclSet<OT,ET> s) {
        return null;
    }

    private static final int FLATTEN = 9;

    public OclSet<OT,ET> flatten() {
        return null;
    }

    public OclSet<OT,ET> flattenEval() {
        return null;
    }

    private static final int AS_SET = 10;

    public OclSet<OT,ET> asSet() {
        return null;
    }

    public OclSet<OT,ET> asSetEval() {
        return null;
    }

    private static final int AS_SEQUENCE = 11;

    public OclSequence<OT,ET> asSequence() {
        return null;
    }

    public OclSequence<OT,ET> asSequenceEval() {
        return null;
    }

    private static final int AS_BAG = 12;

    public OclBag<OT,ET> asBag() {
        return null;
    }

    public OclBag<OT,ET> asBagEval() {
        return null;
    }

    private static final int SELECT = 13;

    public OclSet<OT,ET> select(OT iterator, OclBoolean body) {
        return null;
    }

    public OclSet<OT,ET> selectEval(OT iterator, OclBoolean body) {
        return null;
    }

    private static final int REJECT = 14;

    public OclSet<OT,ET> reject(OT iterator, OclBoolean body) {
        return null;
    }

    public OclSet<OT,ET> rejectEval(OT iterator, OclBoolean body) {
        return null;
    }

    private static final int SORTED_BY = 15;

    public OclSequence<OT,ET> sortedBy(OT iterator, OclInteger body) {
        return null;
    }

    public OclSequence<OT,ET> sortedByEval(OT iterator, OclInteger body) {
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

