package hub.sam.mof.jocl.standardlib;

public class OclString extends OclAny<String> {

    protected OclString(String value) {
        super(value);
    }

    public OclInteger size() {
        return null;
    }

    public OclString concat(OclString s) {
        return null;
    }

    public OclString substring(OclInteger lower, OclInteger upper) {
        return null;
    }

    public OclInteger toInteger() {
        return null;
    }
}
