package hub.sam.mof.java;

import cmof.exception.ModelException;

public class ProxyModelException extends ModelException {
    public ProxyModelException(String msg) {
        super(msg);
    }

    public ProxyModelException(String msg, Exception t) {
        super(msg, t);
    }
}
