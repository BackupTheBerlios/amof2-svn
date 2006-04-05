package hub.sam.mof.java;

import cmof.Operation;
import cmof.Property;
import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.reflection.Implementations;
import hub.sam.mof.reflection.ObjectDlg;
import hub.sam.mof.reflection.ObjectImpl;

import java.util.Collections;
import java.util.List;

public class ProxyImplementations implements Implementations  {

    protected final ProxyModelContext context;

    public ProxyImplementations(ProxyModelContext context) {
        super();
        this.context = context;
    }

    public boolean hasImplementationFor(Property property, ClassifierSemantics<Property, Operation, String> semantics) {
        return false;
    }

    public boolean hasImplementationFor(
            Operation operatoin, ClassifierSemantics<Property, Operation, String> semantics) {
        return true;
    }

    @SuppressWarnings({"unchecked"})
    public List<ObjectDlg> getDelegates() {
        return Collections.EMPTY_LIST;
    }

    public Object invokeImplementationFor(
            Property property, cmof.reflection.Object object,
            ClassifierSemantics<Property, Operation, String> semantics) {
        throw new RuntimeException("assert");
    }

    public Object invokeImplementationFor(
            Operation operation, cmof.reflection.Object object, Object[] args,
            ClassifierSemantics<Property, Operation, String> semantics) {
        Object javaObject = ((ProxyClassInstance)((ObjectImpl)object).getClassInstance()).getTheObject();
        Class[] argTypes = new Class[args.length];
        Object[] dismantledArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                argTypes[i] = java.lang.Object.class;
                dismantledArgs[i] = null;
            } else {
                argTypes[i] = args[i].getClass();
                dismantledArgs[i] = context.disMantleFromRefObject(args[i]);

            }
        }
        try {
            return context.mantleToRefObject(javaObject.getClass().getMethod(operation.getName(),
                    argTypes).invoke(javaObject, args));
        } catch (Exception e) {
            throw new ProxyModelException("Exception during operation invokation", e);
        }
    }
}
