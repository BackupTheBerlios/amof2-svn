package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveSequence;

public class OclSequence<OT,ET> extends OclCollection<OT,ET> {

    protected OclSequence(ReflectiveSequence value) {
        super(value);
    }

    public OclSequence(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    private static final int COUNT = 1;

    @Override
    public OclInteger count(OT object) {
        return null;
    }

    @Override
    public OclInteger countEval(OT object) {
        return null;
    }

    private static final int OCL_EQUALS = 2;

    public OclBoolean oclEquals(OclSequence<OT,ET> s) {
        return null;
    }

    public OclBoolean oclEqualsEval(OclSequence<OT,ET> s) {
        return null;
    }

    private static final int UNION = 3;

    public OclSequence<OT,ET> union(OclSequence<OT,ET> s) {
        return null;
    }

    public OclSequence<OT,ET> unionEval(OclSequence<OT,ET> s) {
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

    public OclSequence<OT,ET> append(OT o) {
        return null;
    }

    public OclSequence<OT,ET> appendEval(OT o) {
        return null;
    }

    private static final int PREPEND = 6;

    public OclSequence<OT,ET> prepend(OT o) {
        return null;
    }

    public OclSequence<OT,ET> prependEval(OT o) {
        return null;
    }

    private static final int INSERT_AT = 7;

    public OclSequence<OT,ET> insertAt(OclInteger i, OT o) {
        return null;
    }

    public OclSequence<OT,ET> insertAtEval(OclInteger i, OT o) {
        return null;
    }

    private static final int SUBSEQUENCE = 8;

    public OclSequence<OT,ET> subsequence(OclInteger lower, OclInteger upper) {
        return null;
    }

    public OclSequence<OT,ET> subsequenceEval(OclInteger lower, OclInteger upper) {
        return null;
    }

    private static final int AT = 9;

    public OT at(OclInteger i) {
        return null;
    }

    public OT atEval(OclInteger i) {
        return null;
    }

    private static final int INDEX_OF = 10;

    public OclInteger indexOf(OT o) {
        return null;
    }

    public OclInteger indexOfEval(OT o) {
        return null;
    }

    private static final int FIRST = 11;

    public OT first() {
        return null;
    }

    public OT firstEval() {
        return null;
    }

    private static final int LAST = 12;

    public OT last() {
        return null;
    }

    public OT lastEval() {
        return null;
    }

    private static final int INCLUDING = 13;

    public OclSequence<OT,ET> including(OT o) {
        return null;
    }

    public OclSequence<OT,ET> includingEval(OT o) {
        return null;
    }

    private static final int EXCLUDING = 14;

    public OclSequence<OT,ET> excluding(OT o) {
        return null;
    }

    public OclSequence<OT,ET> excludingEval(OT o) {
        return null;
    }

    private static final int AS_BAG = 15;

    public OclBag<OT,ET> asBag() {
        return null;
    }

    public OclBag<OT,ET> asBagEval() {
        return null;
    }

    private static final int AS_SET = 16;

    public OclSet<OT,ET> asSet() {
        return null;
    }

    public OclSet<OT,ET> asSetEval() {
        return null;
    }

    private static final int AS_SEQUENCE = 17;

    public OclSequence<OT,ET> asSequence() {
        return null;
    }

    public OclSequence<OT,ET> asSequenceEval() {
        return null;
    }

    private static final int SELECT = 18;

    public OclSequence<OT,ET> select(OT iterator, OclBoolean body) {
        return null;
    }

    public OclSequence<OT,ET> selectEval(OT iterator, OclBoolean body) {
        return null;
    }

    private static final int REJECT = 19;

    public OclSequence<OT,ET> reject(OT iterator, OclBoolean body) {
        return null;
    }

    public OclSequence<OT,ET> rejectEval(OT iterator, OclBoolean body) {
        return null;
    }

    private static final int SORTED_BY = 20;

    public OclSequence<OT,ET> sortedBy(OT iterator, OclInteger body) {
        return null;
    }

    public OclSequence<OT,ET> sortedByEval(OT iterator, OclInteger body) {
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
