package cmofimpl.instancemodel;

import cmof.*;
import hub.sam.mof.instancemodel.*;

public class MofStructureSlot extends StructureSlot<UmlClass,Property,java.lang.Object> implements Cloneable {

	private final MofClassInstance owner;       
    private MofValueSpecificationList values;
    private Property feature;
    	
	protected MofStructureSlot(MofClassInstance owner, Property feature) {
        super(feature);
		this.owner = owner;
        this.feature = feature;
		this.values = new MofValueSpecificationList(owner, this);
	}
    
    public MofValueSpecificationList getValuesAsList() {
        return values;
    }
    
    public MofClassInstance getOwningInstance() {
        return owner;
    }
    
    public Object clone(MofClassInstance newOwner) {
        try {
            MofStructureSlot copy = (MofStructureSlot)super.clone();
            copy.values = (MofValueSpecificationList)this.values.clone(newOwner, copy);
            return copy;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("assert");
        }
    }
    
    public Property getDefiningFeature() {
        return feature;
    }           
}
