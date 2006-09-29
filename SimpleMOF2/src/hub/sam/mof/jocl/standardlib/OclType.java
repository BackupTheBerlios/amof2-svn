package hub.sam.mof.jocl.standardlib;

public class OclType<T> extends OclAny<T> {

    public OclType(Object value) {
        super(value);
    }

    public OclType(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    private static final int allInstance = 1;

    public OclSet<T> allInstance() {
        return null;
    }

    public OclSet<T> allInstanceEval() {
        return null;
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case allInstance:
                throw new RuntimeException("This part of the OCL library is not implemented");
            default:
                return super.eval(symbolCode, name, children);
        }
    }
}
