package cmofimpl.instancemodel;

import cmof.common.*;
import cmofimpl.util.*;
import core.abstractions.instances.*;
import core.abstractions.expressions.ValueSpecification;
import core.abstractions.ownerships.Element;
import core.abstractions.structuralfeatures.StructuralFeature;

public class LinkSlotImpl extends SlotImpl implements LinkSlot {

	private final Link owner;
    private final ReflectiveSequence<ValueSpecification> values = new ListImpl<ValueSpecification>();
	
	public LinkSlotImpl(Link owner, StructuralFeature feature, InstanceValue value) {        
		super(owner, feature);
		this.owner = owner;		
        values.add(value);
	}
	
    public ReflectiveSequence<? extends ValueSpecification> getValue() {
        return values;
    }
	
    public Link getOwningInstance() {
        return owner;
    }

    public ReflectiveCollection<? extends Element> getOwnedElement() {
        return values;
    }
}
