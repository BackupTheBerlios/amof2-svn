package hub.sam.mof.java;

import cmof.Property;
import cmof.UmlClass;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.ValueSpecificationImpl;
import hub.sam.mof.mofinstancemodel.MofClassInstance;
import hub.sam.mof.mofinstancemodel.MofStructureSlot;

import java.util.Collection;
import java.util.Collections;

public class ProxyClassInstance extends MofClassInstance {

    private java.lang.Object theObject = null;
    private final ProxyModelContext context;

    protected ProxyClassInstance(UmlClass classifier, ProxyModelContext context) {
        super(classifier, context.getModel());
        this.context = context;
    }

    @Override
    protected MofStructureSlot createSlot(Property property) {
        return new ProxyStructureSlot(this, property);
    }

    @Override
    public void addValue(Property feature, ValueSpecificationImpl<UmlClass, Property, Object> value) {
        super.addValue(feature, value);
    }

    @Override
    public void delete() {
        theObject = null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<MofClassInstance> getComponents() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public ClassInstance<UmlClass, Property, Object> getComposite() {
        return null;
    }

    @Override
    public boolean isValid() {
        return theObject != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (theObject == null) {
            return super.equals(obj);
        } else {
            if (obj instanceof ProxyClassInstance) {
                return theObject.equals(((ProxyClassInstance)obj).theObject);
            } else {
                return theObject.equals(obj);
            }
        }
    }

    @Override
    public int hashCode() {
        if (theObject == null) {
            return super.hashCode();
        } else {
            return theObject.hashCode();
        }
    }

    @Override
    public String toString() {
        return theObject.toString();
    }

    Object getTheObject() {
        return theObject;
    }

    void setTheObject(Object theObject) {
        this.theObject = theObject;
    }

    ProxyModelContext getContext() {
        return context;
    }
}
