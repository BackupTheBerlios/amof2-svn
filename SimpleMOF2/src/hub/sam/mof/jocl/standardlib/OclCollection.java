package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;

public class OclCollection<OT,ET> extends OclAny<ReflectiveCollection<OT>,ReflectiveCollection<ET>> {

    public OclCollection(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    protected OclCollection(Object value) {
        super(value);
    }

    private static final int SIZE = 1 + (100 * 1);

    public OclInteger size() {
        return null;
    }

    public OclInteger sizeEval() {
        return null;
    }

    private static final int INCLUDES = 2 + (100 * 1);

    public OclBoolean includes(OT object) {
        return null;
    }

    public OclBoolean includesEval(OT object) {
        return null;
    }

    private static final int EXCLUDES = 3 + (100 * 1);

    public OclBoolean excludes(OT object) {
        return null;
    }

    public OclBoolean excludesEval(OT object) {
        return null;
    }

    private static final int COUNT = 4 + (100 * 1);

    public OclInteger count(OT object) {
        return null;
    }

    public OclInteger countEval(OT object) {
        return null;
    }

    private static final int INCLUDES_ALL = 5 + (100 * 1);

    public OclBoolean includesAll(OclCollection<OT,ET> c2) {
        return null;
    }

    public OclBoolean includesAllEval(OclCollection<OT,ET> c2) {
        return null;
    }

    private static final int EXCLUDES_ALL = 6 + (100 * 1);

    public OclBoolean excludesAll(OclCollection<OT,ET> c2) {
        return null;
    }

    public OclBoolean excludesAllEval(OclCollection<OT,ET> c2) {
        return null;
    }

    private static final int IS_EMPTY = 7 + (100 * 1);

    public OclBoolean isEmpty() {
        return null;
    }

    public OclBoolean isEmptyEval() {
        return null;
    }

    private static final int NOT_EMPTY = 8 + (100 * 1);

    public OclBoolean notEmpty() {
        return null;
    }

    public OclBoolean notEmptyEval() {
        return null;
    }

    private static final int EXISTS = 9 + (100 * 1);

    public OclBoolean exists(OT iterator, OclBoolean body) {
        return new OclBoolean(EXISTS, null, this, new OclAny[] {(OclAny)iterator, body});
    }

    public OclBoolean existsEval(OT iterator, OclBoolean body) {
        for(ET element: oclValue()) {
            ((OclAny)iterator).setOclValue(element);
            if (body.oclValue()) {
                return new OclBoolean(true);
            }
        }
        return new OclBoolean(false);
    }

    private static final int FOR_ALL = 10 + (100 * 1);

    public OclBoolean forAll(OT iterator, OclBoolean body) {
        return new OclBoolean(FOR_ALL, null, this, new OclAny[] {(OclAny)iterator, body});
    }

    public OclBoolean forAllEval(OT iterator, OclBoolean body) {
        for(ET element: oclValue()) {
            ((OclAny)iterator).setOclValue(element);
            if (!body.oclValue()) {
                return new OclBoolean(false);
            }
        }
        return new OclBoolean(true);
    }

    private static final int IS_UNIQUE = 11 + (100 * 1);

    public OclBoolean isUnique(OT iterator, OclBoolean body) {
        return null;
    }

    public OclBoolean isUniqueEval(OT iterator, OclBoolean body) {
        return null;
    }

    private static final int ANY = 12 + (100 * 1);

    public OT any(OT iterator, OclBoolean body) {
        return null;
    }

    public OT anyEval(OT iterator, OclBoolean body) {
        return null;
    }

    private static final int ONE = 13 + (100 * 1);

    public OclBoolean one(OT iterator, OclBoolean body) {
        return null;
    }

    public OclBoolean oneEval(OT iterator, OclBoolean body) {
        return null;
    }

    private static final int COLLECT = 14 + (100 * 1);

    public <OT1,ET1> OclCollection<OT1,ET1> collect(OT iterator, OclAny<OT1,ET1> body) {
        return null;
    }

    public <OT1,ET1> OclCollection<OT1,ET1> collectEval(OT iterator, OclAny<OT1,ET1> body) {
        return null;
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case SIZE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INCLUDES:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case EXCLUDES:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case COUNT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INCLUDES_ALL:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case EXCLUDES_ALL:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case IS_EMPTY:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case NOT_EMPTY:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case EXISTS:
                return existsEval((OT)children[0], (OclBoolean)children[1]);
            case FOR_ALL:
                return forAllEval((OT)children[0], (OclBoolean)children[1]);
            case IS_UNIQUE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case ANY:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case ONE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case COLLECT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            default:
                return super.eval(symbolCode, name, children);
        }
    }
}
