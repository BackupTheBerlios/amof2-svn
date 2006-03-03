package cmofimpl.instancemodel;

import core.abstractions.instances.*;
import core.abstractions.ownerships.Element;
import core.abstractions.structuralfeatures.StructuralFeature;

public abstract class SlotImpl implements Slot {
	
	private Instance owner;
	private StructuralFeature feature;
	
    public SlotImpl(Instance owner, StructuralFeature feature) {
        this.owner = owner;
        this.feature = feature;
    }
       
    public void setDefiningFeature(StructuralFeature value) {
        throw new IllegalArgumentException("setting a constant is not allowed");
    }
    
    public void setOwningInstance(InstanceSpecification value) {
        throw new IllegalArgumentException("setting a constant is not allowed");    
    }

    public StructuralFeature getDefiningFeature() {
        return feature;
    }   
    
    public Instance getOwningInstance() {
        return owner;
    }
    
    public core.abstractions.ownerships.Element getOwner() {
        return owner;
    }   

    // unused
    public Element allOwnedElements() {
        return null;
    }

    public boolean mustBeOwned() {
        return false;
    }
}
