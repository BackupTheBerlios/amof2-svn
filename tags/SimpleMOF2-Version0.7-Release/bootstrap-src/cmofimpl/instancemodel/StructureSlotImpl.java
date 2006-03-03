package cmofimpl.instancemodel;

import cmof.common.ReflectiveCollection;
import core.abstractions.structuralfeatures.StructuralFeature;

public class StructureSlotImpl extends SlotImpl implements StructureSlot, Cloneable {

	private final ElementInstance owner;       
    private ValueSpecificationList values;    
    
    public StructureSlotImpl(ElementInstance owner, StructuralFeature feature, 
            ValueSpecificationList values) {
        super(owner, feature);
        this.owner = owner;        
        this.values = values;    
    }
	
	public StructureSlotImpl(ElementInstance owner, StructuralFeature feature) {
		super(owner, feature);
		this.owner = owner;
		this.values = new ValueSpecificationList(owner, this);
	}
    
    public ValueSpecificationList getValue() {
        return values;
    }
    
    public ReflectiveCollection<core.abstractions.ownerships.Element> getOwnedElement() {
        return null;
    }
	
    public ElementInstance getOwningInstance() {
        return owner;
    }
    
    public Object clone(ElementInstanceImpl newOwner) {
        try {
            StructureSlotImpl copy = (StructureSlotImpl)super.clone();
            copy.values = (ValueSpecificationList)this.values.clone(newOwner, copy);
            return copy;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("assert");
        }
    }
}
