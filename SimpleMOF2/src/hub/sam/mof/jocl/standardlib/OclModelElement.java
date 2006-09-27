package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;

public class OclModelElement<T> extends OclAny<T> {

    private final String name;

    protected OclModelElement(Object value) {
        super(value);
        name = null;
    }

    protected OclModelElement(int symbolCode, String name,
                              OclAny self, OclAny[] children) {
        super(symbolCode, self, children);
        this.name = name;
    }

    private static final int GET = 1;

    protected OclAny get(String property) {
        return new OclModelElement(GET, property, this, new OclAny[] {});
    }

    private OclAny getEval() {
        return getReturnForResult(((cmof.reflection.Object)self.getValue()).get(name));
    }

    public static final int QUALIFIED_GET = 2;

    protected OclAny get(OclAny qualifier, String property) {
        return new OclModelElement(QUALIFIED_GET, property, this, new OclAny[] {qualifier});
    }

    protected OclAny qualifiedGetEval() {
        return getReturnForResult(((cmof.reflection.Object)self.getValue()).get(
                name, children[1].getValue()));
    }

    private static final int INVOKE = 3;

    protected OclAny invoke(String operation, OclAny[] args) {
        return new OclModelElement(INVOKE, operation, this, args);
    }

    private OclAny invokeEval() {
        Object[] args = new Object[children.length];
        for (int i = 0; i < children.length; i++) {
            args[i] = children[i].getValue();
        }
        return getReturnForResult(((cmof.reflection.Object)self.getValue()).invokeOperation(name, args));
    }

    private OclAny getReturnForResult(java.lang.Object result) {
        if (result instanceof cmof.reflection.Object) {
            return new OclModelElement(result);
        } else if (result instanceof ReflectiveSequence) {
            return new OclSequence((ReflectiveSequence)result);
        } else if (result instanceof ReflectiveCollection) {
            return new OclSet((ReflectiveCollection)result);
        } else if (result instanceof String) {
            return new OclString((String)result);
        } else if (result instanceof Boolean) {
            return new OclBoolean((Boolean)result);
        } else if (result instanceof Integer) {
            return new OclInteger((Integer)result);
        } else if (result instanceof Long) {
            return new OclInteger((Long)result);
        } else if (result.getClass().isEnum()) {
            throw new RuntimeException("not implemented");
        } else {
            throw new RuntimeException("unsupported type");
        }
    }

    @Override
    protected OclAny eval(int symbolCode, OclAny[] children) {
        switch(symbolCode) {
            case GET:
                return getEval();
            case QUALIFIED_GET:
                return qualifiedGetEval();
            case INVOKE:
                return invokeEval();
            default:
                throw new RuntimeException("This part of the OCL library is not implemented");
        }
    }
}
