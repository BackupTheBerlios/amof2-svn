package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;

public class OclCollection<T> extends OclAny<ReflectiveCollection<T>> {

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

    public OclBoolean includes(OclAny<T> object) {
        return null;
    }

    public OclBoolean includesEval(OclAny<T> object) {
        return null;
    }

    private static final int EXCLUDES = 3 + (100 * 1);

    public OclBoolean excludes(OclAny<T> object) {
        return null;
    }

    public OclBoolean excludesEval(OclAny<T> object) {
        return null;
    }

    private static final int COUNT = 4 + (100 * 1);

    public OclInteger count(OclAny<T> object) {
        return null;
    }

    public OclInteger countEval(OclAny<T> object) {
        return null;
    }

    private static final int INCLUDES_ALL = 5 + (100 * 1);

    public OclBoolean includesAll(OclCollection<T> c2) {
        return null;
    }

    public OclBoolean includesAllEval(OclCollection<T> c2) {
        return null;
    }

    private static final int EXCLUDES_ALL = 6 + (100 * 1);

    public OclBoolean excludesAll(OclCollection<T> c2) {
        return null;
    }

    public OclBoolean excludesAllEval(OclCollection<T> c2) {
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

    public OclBoolean exists(OclAny<T> iterator, OclBoolean body) {
        return new OclBoolean(EXISTS, null, this, new OclAny[] {iterator, body});
    }

    public OclBoolean existsEval(OclAny<T> iterator, OclBoolean body) {
        for(T element: oclValue()) {
            iterator.setOclValue(element);
            if (body.oclValue()) {
                return new OclBoolean(true);
            }
        }
        return new OclBoolean(false);
    }

    private static final int FOR_ALL = 10 + (100 * 1);

    public OclBoolean forAll(OclAny<T> iterator, OclBoolean body) {
        return new OclBoolean(FOR_ALL, null, this, new OclAny[] {iterator, body});
    }

    public OclBoolean forAllEval(OclAny<T> iterator, OclBoolean body) {
        for(T element: oclValue()) {
            iterator.setOclValue(element);
            if (!body.oclValue()) {
                return new OclBoolean(false);
            }
        }
        return new OclBoolean(true);
    }

    private static final int IS_UNIQUE = 11 + (100 * 1);

    public OclBoolean isUnique(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclBoolean isUniqueEval(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    private static final int ANY = 12 + (100 * 1);

    public OclAny<T> any(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclAny<T> anyEval(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    private static final int ONE = 13 + (100 * 1);

    public OclBoolean one(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    public OclBoolean oneEval(OclAny<T> iterator, OclBoolean body) {
        return null;
    }

    private static final int COLLECT = 14 + (100 * 1);

    public <E> OclCollection<E> collect(OclAny<T> iterator, OclAny<E> body) {
        return null;
    }

    public <E> OclCollection<E> collectEval(OclAny<T> iterator, OclAny<E> body) {
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
                return existsEval(children[0], (OclBoolean)children[1]);
            case FOR_ALL:
                return forAllEval(children[0], (OclBoolean)children[1]);
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
