package cmofimpl.instancemodel;

import java.util.*;
import cmof.*;
import hub.sam.mof.instancemodel.*;

public class MofClassInstance extends ClassInstance<UmlClass,Property,java.lang.Object> implements Cloneable {

    protected MofClassInstance(UmlClass classifier, MofInstanceModel model) {
        this(MofClassifierSemantics.createClassClassifierForUmlClass(classifier), model);
    }
	
    private MofClassInstance(MofClassSemantics instanceClassifier, MofInstanceModel model) {
        super(new Integer(ids++).toString(), (UmlClass)instanceClassifier.getClassifier(), model);//TODO
        id = ids;
        instanceClass = instanceClassifier;
        if (instanceClassifier != null) {
            initialize();
        }
    }
    
    private MofClassSemantics instanceClass;
    private Map<Property, MofStructureSlot> slotForProperty = new HashMap<Property, MofStructureSlot>();       
    private boolean valid = true;
    private final int id;
    private Collection<MofClassInstance> components = new HashSet<MofClassInstance>(); 
    
    private static int ids = 0;
            
    /**
     *  Be aware: This does only work property when no non primitive or enum value association end is set.
     */
    public MofClassInstance clone() {
        if (components.size() > 0) {
            throw new RuntimeException("Clone of instances with components not implemented yet.");
        }
        try {
            MofClassInstance copy = (MofClassInstance)super.clone();
            Map<Property, MofStructureSlot> slotForProperty = new HashMap<Property, MofStructureSlot>(this.slotForProperty);
            for (Property key: this.slotForProperty.keySet()) {
                slotForProperty.put(key, (MofStructureSlot)this.slotForProperty.get(key).clone(copy));
            }
            copy.slotForProperty = slotForProperty;
            copy.components = new HashSet<MofClassInstance>();
            return copy;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("assert");
        }
    }
            
    /**
     * Creates all slots for the instance.
     */
    protected void initialize() {                                    
        for (Property property: instanceClass.getFinalProperties()) {
            MofStructureSlot newSlot = new MofStructureSlot(this, property);
            slotForProperty.put(property, newSlot);
            slotForProperty.put(property, newSlot);
        }  
    }
    
    public UmlClass getClassifier() {
        checkValid();
        return super.getClassifier();
    }
    
    /** Returns all slots of this instance. A Slot contains the value of a signle detinct feature of the defining metaclass.*/
    public Collection<MofStructureSlot> getSlot() {
        checkValid();
        return new HashSet<MofStructureSlot>(slotForProperty.values());        
    }

    /** Returns the slot that carries the value for the feature that is the final redefining feature for the given feature 
     * and the metaclass of this instance. */
    public MofStructureSlot get(Property definingFeature) {
        checkValid();
        return slotForProperty.get(instanceClass.getFinalProperty(definingFeature));
    }
    
    /** Returns the values for the feature that is the final redefining feature for the given feature and the metaclass of
     * this instance.
     */
    public MofValueSpecificationList getValuesOfFeature(Property definingFeature) {
        MofStructureSlot theSlot = get(definingFeature);
        if (theSlot == null) {
            throw new IllegalArgumentException();
        } else {
            return (MofValueSpecificationList)theSlot.getValuesAsList();
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
        for (MofClassInstance component: components) {
            component.delete();
        }
        MofValueSpecificationList.deleteInstance(this);
        valid = false;       
        slotForProperty = null;
    }   
        
    public MofClassSemantics getInstanceClassifier() {
        return instanceClass;        
    } 
    
    public Collection<MofClassInstance> getComponents() {
        return components;
    }
}
