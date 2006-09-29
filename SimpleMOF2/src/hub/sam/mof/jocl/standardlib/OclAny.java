package hub.sam.mof.jocl.standardlib;

public class OclAny<T> {

    protected final int symbolCode;
    protected final String name;
    protected final OclAny self;
    protected final OclAny[] children;

    protected Object value;

    protected OclAny(Object value) {
        super();
        this.value = value;
        symbolCode = 0;
        self = null;
        children = null;
        name = null;
    }

    public OclAny(int symbolCode, String name, OclAny self, OclAny[] children) {
        super();
        this.symbolCode = symbolCode;
        this.self = self;
        this.children = children;
        this.name = name;
    }

    private static final int OCL_EQUALS = 1 + (100 * 2);

    public OclBoolean oclEquals(OclAny object2) {
        return new OclBoolean(OCL_EQUALS, null, this, new OclAny[] {object2});
    }

    public OclBoolean oclEqualsEval(OclAny object2) {
        return new OclBoolean(this.oclValue().equals(object2.oclValue()));
    }

    private static final int NOT_EQUALS = 2 + (100 * 2);

    public OclBoolean notEquals(OclAny object2) {
        return new OclBoolean(NOT_EQUALS, null, self, new OclAny[] {object2});
    }

    public OclBoolean notEqualsEval(OclAny object2) {
        return new OclBoolean(!this.value.equals(object2.value));
    }

    private static final int OCL_IS_UNDEFINED = 3 + (100 * 2);

    public OclBoolean oclIsUndefined() {
        return new OclBoolean(OCL_IS_UNDEFINED, null, self, new OclAny[] {});
    }

    public OclBoolean oclIsUndefinedEval() {
        return null;
    }

    private static final int OCL_AS_TYPE = 4 + (100 * 2);

    public <E> OclAny<E> oclAsType(OclType<E> type) {
        return new OclAny<E>(OCL_AS_TYPE, null, self, new OclAny[] {type});
    }

    public <E> OclAny<E> oclAsTypeEval(OclType<E> type) {
        return null;
    }

    private static final int OCL_IS_TYPE_OF = 5 + (100 * 2);

    public OclBoolean oclIsTypeOf(OclType type) {
        return new OclBoolean(OCL_IS_TYPE_OF, null, self, new OclAny[] {type});
    }

    public OclBoolean oclIsTypeOfEval(OclType<? extends T> type) {
        return null;
    }

    private static final int OCL_IS_KIND_OF = 6 + (100 * 2);

    public OclBoolean oclIsKindOf(OclType<? extends T> type) {
        return new OclBoolean(OCL_IS_KIND_OF, null, self, new OclAny[] {type});
    }

    public OclBoolean oclIsKindOfEval(OclType<? extends T> type) {
        return null;
    }

    public T oclValue() {
        if (value != null) {
            return (T)value;
        } else {
            return (T)self.eval(symbolCode, name, children).oclValue();
        }
    }

    protected void setOclValue(T value) {
        this.value = value;
    }

    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case OCL_EQUALS:
                return oclEqualsEval(children[0]);
            case NOT_EQUALS:
                return notEqualsEval(children[0]);
            case OCL_IS_UNDEFINED:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case OCL_AS_TYPE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case OCL_IS_TYPE_OF:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case OCL_IS_KIND_OF:
                throw new RuntimeException("This part of the OCL library is not implemented");
            default:
                throw new RuntimeException("This part of the OCL library is not implemented");
        }
    }
}
