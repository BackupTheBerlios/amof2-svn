package hub.sam.mof.jocl.standardlib;

public class OclBoolean extends OclAny<Boolean> {

    public OclBoolean(Boolean value) {
        super(value);
    }

    public OclBoolean(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    public OclBoolean or(OclBoolean b) {
        return null;
    }

    public OclBoolean and(OclBoolean b) {
        return null;
    }

    public OclBoolean xor(OclBoolean b) {
        return null;
    }

    public OclBoolean implies(OclBoolean b) {
        return null;
    }

    public OclBoolean not() {
        return null;
    }
}
