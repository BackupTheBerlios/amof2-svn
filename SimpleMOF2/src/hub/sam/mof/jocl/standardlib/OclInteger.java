package hub.sam.mof.jocl.standardlib;

public class OclInteger extends OclAny<Integer> {

    public OclInteger(int value) {
        super(value);
    }

    public OclInteger(Integer value) {
        super(value);
    }

    public OclInteger(Long value) {
        super(value.intValue());
    }

    public OclInteger(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    public OclInteger neg() {
        return null;
    }

    public OclInteger abs() {
        return null;
    }

    public OclInteger sub(OclInteger i) {
        return null;
    }

    public OclInteger add(OclInteger i) {
        return null;
    }

    public OclInteger mul(OclInteger i) {
        return null;
    }

    public OclInteger div(OclInteger i) {
        return null;
    }

    public OclInteger mod(OclInteger i) {
        return null;
    }

    public OclInteger max(OclInteger i) {
        return null;
    }

    public OclInteger min(OclInteger i) {
        return null;
    }

}
