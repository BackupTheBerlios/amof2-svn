package hub.sam.mof.java;

import cmof.Operation;
import cmof.Property;
import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.reflection.ObjectImpl;

public class ProxyClassImplementations extends ProxyImplementations {

    public ProxyClassImplementations(ProxyModelContext context) {
        super(context);
    }

    @Override
    public Object invokeImplementationFor(Operation operation, cmof.reflection.Object object, Object[] args,
            ClassifierSemantics<Property, Operation, String> semantics) {
        Class javaClass = ((ProxyClassClass)((ObjectImpl)object).getClassInstance()).getTheClass();
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
            return context.mantleToRefObject(javaClass.getMethod(operation.getName(), argTypes).invoke(null, args));
        } catch (Exception e) {
            throw new ProxyModelException("Exception during operation invokation", e);
        }
    }
}
