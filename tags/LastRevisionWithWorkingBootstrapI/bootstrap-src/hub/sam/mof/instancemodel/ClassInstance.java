package hub.sam.mof.instancemodel;

import java.util.*;
import cmof.exception.CompositeViolation;

public class ClassInstance<C,P,DataValue> implements Cloneable {
    private final Map<P, StructureSlot<C,P,DataValue>> slots = new HashMap<P, StructureSlot<C,P,DataValue>>();
    private final String id;
    private C classifier;
    private ClassInstance<C,P,DataValue> composite = null;
    private final InstanceModel<C,P,DataValue> model;
    
    protected ClassInstance(String id, C classifier, InstanceModel<C,P,DataValue> model) {
        this.id = id;
        this.classifier = classifier;
        this.model = model;
    }
    
    public void addValue(P feature, ValueSpecificationImpl<C,P,DataValue> value) {
        if (feature == null) {
            throw new NullPointerException();
        }
        StructureSlot<C,P,DataValue> slot = slots.get(feature);
        if (slot == null) {
            slot = new StructureSlot<C,P,DataValue>(feature);
            slots.put(feature, slot);
        }
        slot.addValue(value);
    } 
    
    public C getClassifier() {
        return classifier;
    }
    
    public void setClassifier(C classifier) {
        if (this.classifier == null) {
            this.classifier = classifier;
        } else {
            throw new IllegalAccessError("this property is final");
        }
    }
    
    public Collection<StructureSlot<C,P,DataValue>> getSlots() {
        return slots.values();
    }

    public StructureSlot<C,P,DataValue> get(P property) {
        return slots.get(property);
    }
    
    public String getId() {
        return id;
    }

    public ClassInstance<C, P, DataValue> getComposite() {
        return composite;
    }

    public final void setComposite(ClassInstance<C, P, DataValue> composite) {
        if (getComposite() != null && composite != null && composite != getComposite()) {
            throw new CompositeViolation(composite);
        }
        this.composite = composite;
        if (this.composite != null) {
            model.removeOutermostComposite(this);
        } else {
            model.addOutermostComposite(this);
        }
    }
    
    public ClassInstance<C,P,DataValue> clone() throws CloneNotSupportedException {
        return (ClassInstance)super.clone();
    }
    
    public void delete() {
        
    }
}
