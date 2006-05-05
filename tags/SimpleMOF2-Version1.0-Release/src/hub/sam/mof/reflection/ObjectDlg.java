package hub.sam.mof.reflection;

import cmof.Operation;
import cmof.Property;
import cmof.UmlClass;
import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;
import cmof.exception.IllegalArgumentException;
import cmof.reflection.Argument;
import cmof.reflection.Extent;
import cmof.reflection.Object;
import cmof.reflection.ObjectEventHandler;

public class ObjectDlg implements Object {

    private Object reflectionself = null;

    protected void setSelf(Object self) {
        reflectionself = self;
    }

    protected Object getSelf() {
        return reflectionself;
    }

    public UmlClass getMetaClass() {
        return reflectionself.getMetaClass();
    }

    public Object container() {
        return reflectionself.container();
    }

    public java.lang.Object get(Property property) throws IllegalArgumentException {
        return reflectionself.get(property);
    }

    public void set(Property property, java.lang.Object value) throws ClassCastException, IllegalArgumentException {
        reflectionself.set(property, value);
    }

    public boolean isSet(Property property) throws IllegalArgumentException {
        return reflectionself.isSet(property);
    }

    public void unset(Property property) throws IllegalArgumentException {
        reflectionself.unset(property);
    }

    public void delete() {
        reflectionself.delete();
    }

    public java.lang.Object invokeOperation(Operation op, ReflectiveSequence<Argument> arguments) {
        return reflectionself.invokeOperation(op, arguments);
    }

    public boolean isInstanceOfType(UmlClass type, boolean includeSubTypes) {
        return reflectionself.isInstanceOfType(type, includeSubTypes);
    }

    public Object getOutermostContainer() {
        return reflectionself.getOutermostContainer();
    }

    public ReflectiveCollection<? extends Object> getComponents() {
        return reflectionself.getComponents();
    }

    public Extent getExtent() {
        return reflectionself.getExtent();
    }

    public java.lang.Object get(String property) {
        return reflectionself.get(property);
    }

    public void set(String property, java.lang.Object value) {
        reflectionself.set(property, value);
    }

    public java.lang.Object invokeOperation(String opName, java.lang.Object[] args) {
        return reflectionself.invokeOperation(opName, args);
    }

    public void addObjectEventHandler(ObjectEventHandler handler) {
        reflectionself.addObjectEventHandler(handler);
    }

    @Override
    public String toString() {
        return reflectionself.toString();
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (other instanceof ObjectDlg) {
            return reflectionself.equals(((ObjectDlg)other).reflectionself);
        } else if (other instanceof ObjectImpl) {
            return reflectionself.equals(other);
        } else {
            return false;
        }
    }
}
