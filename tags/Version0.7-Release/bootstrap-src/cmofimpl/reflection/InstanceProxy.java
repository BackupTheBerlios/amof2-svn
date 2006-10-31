package cmofimpl.reflection;

import java.util.Map;
import core.abstractions.instances.Slot;
import core.abstractions.structuralfeatures.StructuralFeature;
import cmof.*;
import cmofimpl.instancemodel.*;

public class InstanceProxy implements Cloneable {

    private ClassInstanceImpl instance;
    private Map<String, Property> featureForName;
    
    protected InstanceProxy(ClassInstanceImpl instance, ExtentImpl extent) {
        this.instance = instance;
        initialize();
    }
    
    private void initialize() {
        featureForName = new java.util.HashMap<String, Property>();
        if (instance != null) {
            for (Slot slot: instance.getSlot()) {
                StructuralFeature feature = slot.getDefiningFeature();        
                if (feature.getName() == null) {
                    throw new RuntimeException("model error");
                }
                if (feature instanceof cmof.Property) {
                    featureForName.put(feature.getName(), (Property)feature);
                } else {
                    throw new RuntimeException("assert");
                }
            }        
        }
    }
    
    protected UmlClass getMetaClass() {
        return instance.getClassifier().get(0);
    }
    
    protected ClassInstanceImpl getInstance() {
        return instance;
    }
    
    protected InstanceClassImpl getClassClass() {
        return instance.getInstanceClassifier();
    }
    
    protected java.util.Collection<String> getPropertyNames() {
        return featureForName.keySet();
    }
    
    protected Property getPropertyForName(String name) {
        return featureForName.get(name);
    }
    
    protected ValueSpecificationList getProperty(Property property) {
        return instance.getValuesOfFeature(property);  
    }
      
    protected Feature getFinalFeature(Feature forFeature) {
        if (forFeature instanceof Property) {
            return instance.getInstanceClassifier().resolveFinalProperty((Property)forFeature);
        } else {
            return forFeature;
        }
    }
    
    public int hashCode() {
        return instance.hashCode();
    }
    public boolean equals(java.lang.Object element) {
        if (element instanceof InstanceProxy) {
            return instance.equals(((InstanceProxy)element).instance);    
        } else {
            return false;
        }        
    }
    
    public InstanceProxy clone() throws CloneNotSupportedException {
        InstanceProxy copy = (InstanceProxy) super.clone();
        copy.instance = instance.clone();
        return copy;
    }
}
