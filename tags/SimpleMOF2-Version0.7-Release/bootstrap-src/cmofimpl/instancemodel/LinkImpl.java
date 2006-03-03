package cmofimpl.instancemodel;

import cmofimpl.util.*;
import java.util.*;
import core.abstractions.namespaces.NamedElement;
import core.abstractions.structuralfeatures.StructuralFeature;
import core.abstractions.instances.*;
import cmof.*;
import cmof.common.ReflectiveCollection;

/**
 * Links are not stored in the model explicitly. All links are derived from properties
 * instanciated through slots in the according element instances. Any association end 
 * that is not navigable, and thus not owned by the association, but the classifier, is
 * realised by an unvisible slot in the appropriate element instance.
 * @see #findStructureSlotForEnd(Property, InstanceValue)
 * @see #fakeSlotsForAssociation
 */
public class LinkImpl extends InstanceImpl implements Link {

	private final ReflectiveCollection<Classifier> classifiers = new SetImpl<Classifier>();
	private final Association association;
	private final ReflectiveCollection<LinkSlot> slots = new SetImpl<LinkSlot>();
    private final LinkSlot slotOne, slotTwo;
	
    /** For all owned association ends (properties) a preuso structure slot is created
     * and bound to the instance value. Thus a navigable end is faked, and all those
     * owned association ends can be handled like ends that are owned by classes.
     * 
     * To bound those fakeSlots to instances a map from instances to slots per association
     * is used.
     */
    public static final Map<Association, Map<InstanceValue, StructureSlot>> fakeSlotsForAssociation;    
    
    static {                
        fakeSlotsForAssociation = new HashMap<Association, Map<InstanceValue, StructureSlot>>();
    }
    
    public static void createLink(Association association, InstanceValue one,
            InstanceValue two) {
        StructureSlot slotOne = findStructureSlotForEnd(association.getMemberEnd().get(1), one);
        StructureSlot slotTwo = findStructureSlotForEnd(association.getMemberEnd().get(0), two);
        if ((slotOne == null) || (slotTwo == null)) {
            throw new IllegalArgumentException("instances are not compatible with association");
        }
        ((ValueSpecificationList)slotOne.getValue()).addPlain(two);
        ((ValueSpecificationList)slotTwo.getValue()).addPlain(one);
    }    
    
    public static Link getLink(Association association, InstanceValue one, InstanceValue two) {
        StructureSlot slotOne = findStructureSlotForEnd(association.getMemberEnd().get(1), one);
        StructureSlot slotTwo = findStructureSlotForEnd(association.getMemberEnd().get(0), two);
        if ((slotOne == null) || (slotTwo == null)) {
            throw new IllegalArgumentException("instances are not compatible with association");
        }
        if (slotOne.getValue().contains(two) && slotTwo.getValue().contains(one)) {
            return new LinkImpl(association,one, two); 
        } else {
            return null;
        }
    }
    
    public static void removeLink(Association association, InstanceValue one, InstanceValue two) {
        if (getLink(association, one, two) != null) {
            ((ValueSpecificationList)findStructureSlotForEnd(association.getMemberEnd().get(1), one).getValue()).removePlain(two);
            ((ValueSpecificationList)findStructureSlotForEnd(association.getMemberEnd().get(0), two).getValue()).removePlain(one);
        }
    }
                
	/**
	 * Locates the slot that represents the given association end.
	 * @param property the association end
	 * @param value the instance should contate the according slot 
	 * @throws NullPointerException if there is no such slot.
	 */
    protected static StructureSlot findStructureSlotForEnd(Property property, InstanceValue
            value) {
        StructureSlot result = null;
        if (property.getOwner() instanceof Association) {
            Map<InstanceValue, StructureSlot> fakeSlots =
                fakeSlotsForAssociation.get(property.getOwningAssociation());
            if (fakeSlots == null) {
                fakeSlots = new HashMap<InstanceValue, StructureSlot>();
                fakeSlotsForAssociation.put(property.getOwningAssociation(), fakeSlots);
            }
            StructureSlot slot = fakeSlots.get(value);
            if (slot == null) {
                slot = new StructureSlotImpl((ElementInstance)value.getInstance(), property);
                fakeSlots.put(value, slot);
            }
            result = slot;
        } else {
            result = ((ClassInstance)value.getInstance()).getSlot(property);
        }        
        if (result == null) {
            throw new NullPointerException();
        }
        return result;
    }
    
    private static int id = 0;
	private LinkImpl(Association association, InstanceValue one, InstanceValue two) {
		super(association.getName() + " instance " + id++ ,null);
		this.association = association;
		classifiers.add(getAssociation());
        slotOne = new LinkSlotImpl(this, association.getMemberEnd().get(0), one);
        slotTwo = new LinkSlotImpl(this, association.getMemberEnd().get(1), two);
	}
	
	public ReflectiveCollection<? extends Classifier> getClassifier() {
		return classifiers;
	}
	
    public Association getAssociation() {
        return this.association;
    }

    public ReflectiveCollection<LinkSlot> getSlot() {
        return slots;
    }

    public ReflectiveCollection<? extends NamedElement> getOwnedMember() {
        return null;
    }

    public ReflectiveCollection<? extends NamedElement> getMember() {
        return null;
    }

    public boolean equals(Object other) {
        if (other instanceof LinkImpl) {
            LinkImpl otherLink = (LinkImpl)other;
            boolean equals = true;
            equals = equals && otherLink.slotTwo.getValue().get(0).equals(this.slotTwo.getValue().get(0));
            equals = equals && otherLink.slotOne.getValue().get(0).equals(this.slotOne.getValue().get(0));
            equals = equals && otherLink.association.equals(this.association);
            return equals;
        } else {
            return false;
        }
    }

    public LinkSlot getSlot(StructuralFeature definingFeature) {
        return (slotOne.getDefiningFeature() == definingFeature) ? slotOne : slotTwo; 
    }
}
