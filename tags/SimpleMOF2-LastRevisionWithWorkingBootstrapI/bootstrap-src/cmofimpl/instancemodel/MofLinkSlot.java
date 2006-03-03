package cmofimpl.instancemodel;

import java.util.*;
import cmof.*;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.*;

public class MofLinkSlot {

	private final MofLink owner;
    private final Property property;
    private final List<ValueSpecification<UmlClass,Property,java.lang.Object>> values = new Vector<ValueSpecification<UmlClass,Property,java.lang.Object>>();
	
	public MofLinkSlot(MofLink owner, Property feature, InstanceValue<UmlClass,Property,java.lang.Object> value) {
        this.property = feature;
		this.owner = owner;		
        values.add((ValueSpecification<UmlClass,Property,java.lang.Object>)value);
	}
	
    public List<? extends ValueSpecification<UmlClass,Property,java.lang.Object>> getValue() {
        return values;
    }
	
    public MofLink getOwningInstance() {
        return owner;
    }
    
    public Property getDefiningFeature() {
        return property;
    }
}
