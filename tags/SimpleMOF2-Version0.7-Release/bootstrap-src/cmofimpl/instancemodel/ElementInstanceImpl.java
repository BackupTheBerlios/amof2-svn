package cmofimpl.instancemodel;

import java.util.HashMap;
import java.util.Map;

import cmof.Property;
import cmof.Classifier;
import cmof.common.*;
import cmof.exception.CompositeViolation;
import cmofimpl.util.*;
import core.abstractions.namespaces.NamedElement;
import core.abstractions.namespaces.Namespace;
import core.abstractions.ownerships.Element;
import core.abstractions.structuralfeatures.StructuralFeature;

public abstract class ElementInstanceImpl  extends InstanceImpl implements ElementInstance, Cloneable {
	
    private InstanceClassifierImpl instanceClass;
    private Map<Property, StructureSlotImpl> slotForProperty = new HashMap<Property, StructureSlotImpl>();       
    private boolean valid = true;
    private final int id;
    private ReflectiveCollection<ElementInstanceImpl> components = new SetImpl<ElementInstanceImpl>();
    private ElementInstanceImpl composite = null;    
    
    private static int ids = 0;
    
    protected ElementInstanceImpl(cmofimpl.reflection.ExtentImpl extent, InstanceClassifierImpl instanceClassifier) {
        super("instance " + ids++, extent);
        id = ids;
        instanceClass = instanceClassifier;
        if (instanceClassifier != null) {
            initialize();
        }
    }
    
    /**
     *  Be aware: This does only work property when no non primitive or enum value association end is set.
     */
    public ElementInstanceImpl clone() {
        if (components.size() > 0) {
            throw new RuntimeException("Clone of instances with components not implemented yet.");
        }
        try {
            ElementInstanceImpl copy = (ElementInstanceImpl)super.clone();
            Map<Property, StructureSlotImpl> slotForProperty = new HashMap<Property, StructureSlotImpl>(this.slotForProperty);
            for (Property key: this.slotForProperty.keySet()) {
                slotForProperty.put(key, (StructureSlotImpl)this.slotForProperty.get(key).clone(copy));
            }
            copy.slotForProperty = slotForProperty;
            copy.components = new SetImpl<ElementInstanceImpl>();
            return copy;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("assert");
        }
    }
            
	/**
	 * Creates all slots for the instance.
	 */
    protected void initialize() {                                    
        for (Property property: instanceClass.getFinalProperty()) {
            StructureSlotImpl newSlot = new StructureSlotImpl(this, property);
            slotForProperty.put(property, newSlot);
            slotForProperty.put(property, newSlot);
        }  
    }
    
    /** Returns the defining metaclass.*/
    private ReflectiveSequence<? extends Classifier> classifierAsList = null;
    public ReflectiveSequence<? extends Classifier> getClassifier() {
        checkValid();
        if (classifierAsList == null) {
            classifierAsList = new ListImpl<Classifier>();
            classifierAsList.add(instanceClass.getClassifier());
        }        
        return classifierAsList;
    }
    
    /** Returns all slots of this instance. A Slot contains the value of a signle detinct feature of the defining metaclass.*/
    public ReflectiveCollection<StructureSlot> getSlot() {
        checkValid();
        return new SetImpl<StructureSlot>(slotForProperty.values());        
    }

    public ReflectiveCollection<? extends NamedElement> getOwnedMember() {
        checkValid();
        return null;
    }

    public ReflectiveCollection<? extends NamedElement> getMember() {
        checkValid();
        return null;
    }

    /** Returns the slot that carries the value for the feature that is the final redefining feature for the given feature 
     * and the metaclass of this instance. */
    public StructureSlot getSlot(StructuralFeature definingFeature) {
        checkValid();
        return slotForProperty.get(instanceClass.resolveFinalProperty((Property)definingFeature));
    }
    
    /** Returns the values for the feature that is the final redefining feature for the given feature and the metaclass of
     * this instance.
     */
    public ValueSpecificationList getValuesOfFeature(StructuralFeature definingFeature) {
        StructureSlot theSlot = getSlot(definingFeature);
        if (theSlot == null) {
            throw new IllegalArgumentException();
        } else {
            return (ValueSpecificationList)theSlot.getValue();
        }
    }
    
    private void checkValid() {
        if (!valid) {
            throw new RuntimeException("access of an invalid instance");
        }
    }
    
	/**
	 * Deletes itself, all components, removes all references to itself or to
	 * all components.
	 */
    public void delete() {
        for (ElementInstanceImpl component: components) {
            component.delete();
        }
        ValueSpecificationList.deleteInstance(this);
        valid = false;       
        slotForProperty = null;
    }   
        
    public InstanceClassifierImpl getInstanceClassifier() {
        return instanceClass;        
    } 
    
    public ReflectiveCollection<ElementInstanceImpl> getComponents() {
        return components;
    }
    
    public ElementInstanceImpl getComposite() {
        return composite;
    }
    
    protected void setComposite(ElementInstanceImpl composite) {
        if (this.composite != null && composite != null && composite != this.composite) {
            throw new CompositeViolation(composite);
        }
        this.composite = composite;
    }
   
}
