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

    private static final int OCL_EQUALS = 1;

    public OclBoolean oclEquals(OclAny object2) {
        return new OclBoolean(OCL_EQUALS, null, this, new OclAny[] {object2});
    }

    public OclBoolean oclEqualsEval(OclAny object2) {
        return new OclBoolean(this.oclValue().equals(object2.oclValue()));
    }

    private static final int NOT_EQUALS = 2;

    public OclBoolean notEquals(OclAny object2) {
        return new OclBoolean(NOT_EQUALS, null, self, new OclAny[] {object2});
    }

    public OclBoolean notEqualsEval(OclAny object2) {
        return new OclBoolean(!this.value.equals(object2.value));
    }

    public OclBoolean oclIsUndefined() {
        return null;
    }

    public <E> OclAny<E> oclAsType(OclType<E> type) {
        return null;
    }

    public OclBoolean oclIsTypeOf(OclType<? extends T> type) {
        return null;
    }

    public OclBoolean oclIsKindOf(OclType<? extends T> type) {
        return null;
    }

    public T oclValue() {
        if (value != null) {
            return (T)value;
        } else {
            value = self.eval(symbolCode, name, children).oclValue();
            return (T)value;
        }
    }

    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case OCL_EQUALS:
                return oclEqualsEval(children[0]);
            case NOT_EQUALS:
                return notEqualsEval(children[0]);
            default:
                throw new RuntimeException("This part of the OCL library is not implemented");
        }
    }
}
