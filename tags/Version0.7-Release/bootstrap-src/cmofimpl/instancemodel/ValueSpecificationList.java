package cmofimpl.instancemodel;

import cmofimpl.util.*;
import java.util.Iterator;
import java.util.ListIterator;
import core.abstractions.expressions.*;
import cmof.Property;
import cmof.common.*;
import cmof.exception.MultiplicityViolation;
import core.abstractions.instances.*;

/** A wrapper arround lists of ValueSpecifications. It ensures: 
 *  correct multiplicity (including lower, upper, unique and ordering),
 *  the type, whether its readOnly, derived. It handles the subset structure,
 *  link consistency, checks composition.<p/>
 *      
 *  It uses functionality, inherited from ListImpl, to store values. There are two 
 *  different kinds of updating methods (line add, set, remove, etc.). The normal update methods
 *  ensure that all depending slots and their values are updated too. All update methods,
 *  ending with <i>Plain</i>, do NOT update other values. But they are used by the normal
 *  update methods. The update methods for mulitple or all elements (like addAll, removeAll, etc.)
 *  delegate all updating to the normal update methods for single elements.
 */
public class ValueSpecificationList extends ListImpl<ValueSpecification> implements ReflectiveSequence<ValueSpecification>, Cloneable {
    
    private void checkReadOnly() {
        if (property.isReadOnly()) {
            throw new IllegalArgumentException("property is readonly");
        }
    } 
	
	private void checkUpperMultiplicity() {
		long upper = property.getUpper();
		if (upper > 0 && size() > upper) {
			throw new MultiplicityViolation(slot);
		}
	}
	
	private void checkLowerMultiplicity() {
		int lower = property.getLower();
		if (lower > 0 && size() < lower) {
			throw new MultiplicityViolation(slot);
		}
	}
    
    // TBD checks for unique;
    // TBD derived, types, ordering

    private final ReflectiveCollection<? extends Property> supersettedPropertys;
    private final ReflectiveCollection<? extends Property> subsettedPropertys;
    private final cmof.Property property;
    private boolean cannotPreserveOrder;
    private final ElementInstanceImpl owner;
	private final Slot slot;
    
    private final static java.util.Map<InstanceSpecification, ReflectiveCollection<ValueSpecificationList>> instanceOccurences =
            new java.util.HashMap<InstanceSpecification, ReflectiveCollection<ValueSpecificationList>>();
    
	/**
	 * Is only used by {@link StructureSlotImpl}. Creates a new empty list of value
	 * specifications for a concrete slot and for a concrete element instance. 
	 * @param owner The {@link ElementInstance} that owns the slot for this values.
	 * @param slot The {@link Slot} for this values.
	 */
    protected ValueSpecificationList(ElementInstance owner, Slot slot) {        
        this.property = (Property)slot.getDefiningFeature();
		this.slot = slot;
        this.cannotPreserveOrder = false;
        this.owner = (ElementInstanceImpl)owner;       
        this.supersettedPropertys = this.owner.getInstanceClassifier().getAllSupersettedPropertys(property);
        this.subsettedPropertys = this.owner.getInstanceClassifier().getAllSubsettedPropertys(property);
    }
   
	/**
	 * Is used for cloning or sublists. 
	 * @see #clone(ElementInstanceImpl, Slot)
	 * @see #subList(int, int)
	 * @param parent the list to clone
	 * @param owner the owner ({@link ElementInstance}) of the clone
	 * @param the slot that the clone will the values for
	 * @param values the allready cloned values
	 */
    private ValueSpecificationList(ValueSpecificationList parent, ElementInstanceImpl owner, Slot slot, java.util.List<ValueSpecification> values) {
        this.values = values;
        this.owner = owner;
		this.slot = slot;
        this.property = parent.property;       
        this.cannotPreserveOrder = parent.cannotPreserveOrder;
        this.supersettedPropertys = parent.supersettedPropertys;
        this.subsettedPropertys = parent.subsettedPropertys;
    }
            
    /**
     * Clones this list for a new slot and a new instance.
     * @param newOwner
     * @param newSlot
     * @return
     */
    public Object clone(ElementInstanceImpl newOwner, Slot newSlot) {
        return new ValueSpecificationList(this, newOwner, newSlot, new java.util.Vector<ValueSpecification>(this.values));
    }
    
    final class MyIterator implements ListIterator<ValueSpecification> {
        private ListIterator<ValueSpecification> base;
        
        public MyIterator(ListIterator<ValueSpecification> base) {
            this.base = base;
        }
        
        public boolean hasNext() {
            return base.hasNext();
        }

        public ValueSpecification next() {
            return base.next();
        }

        public void remove() {
            checkReadOnly();
            base.remove();
        }

        public boolean hasPrevious() {
            return base.hasPrevious();
        }

        public ValueSpecification previous() {
            return base.previous();
        }

        public int nextIndex() {
            return base.nextIndex();
        }

        public int previousIndex() {
            return base.previousIndex();
        }

        public void set(ValueSpecification o) {
            throw new RuntimeException("Not Implemented.");                   
        }

        public void add(ValueSpecification o) {            
            throw new RuntimeException("Not Implemented.");
        }        
    }

    public Iterator<ValueSpecification> iterator() {
        return new MyIterator(values.listIterator());
    }

    public boolean add(Object o) {
        ValueSpecification value = (ValueSpecification)o;
        ReflectiveCollection<Modification> modifications = new SetImpl<Modification>();
        valuesToAlter(value, true, false, modifications);
        modifications.remove(new Modification(this, value));        
        for (Modification modification: modifications) {
            modification.valuesToAlter.addPlain(modification.value);            
        }
        return addPlain(value);
    }        

    public boolean remove(Object o) {
        ValueSpecification value = (ValueSpecification)o;
        ReflectiveCollection<Modification> modifications = new SetImpl<Modification>();
        valuesToAlter(value, false, false, modifications);
        modifications.remove(new Modification(this, value));        
        for (Modification modification: modifications) {
            modification.valuesToAlter.removePlain(modification.value);            
        }
        return removePlain(value);
    }
    
    public void set(int index, Object o) {
        ValueSpecification value = (ValueSpecification)o;
        
        ReflectiveCollection<Modification> removes = new SetImpl<Modification>();
        valuesToAlter(get(index), false, true, removes);        
        removes.remove(new Modification(this, get(index)));
        for (Modification remove: removes) {
            remove.valuesToAlter.removePlain(remove.value);
        }
        
        ReflectiveCollection<Modification> adds = new SetImpl<Modification>(); 
        valuesToAlter(value, true, true, adds);        
        adds.remove(new Modification(this, value));        
        for (Modification add: adds) {
            add.valuesToAlter.addPlain(add.value);
        }
        
        setPlain(index, value);
    }
    
    public void add(int index, Object o) {
        ValueSpecification value = (ValueSpecification)o;
        ReflectiveCollection<Modification> modifications = new SetImpl<Modification>();
        valuesToAlter(value, true, true, modifications);
        modifications.remove(new Modification(this, value));        
        for (Modification modification: modifications) {
            modification.valuesToAlter.addPlain(modification.value);            
        }                
        addPlain(index, value);
    }
    
    public void remove(int index) {                
        ReflectiveCollection<Modification> modifications = new SetImpl<Modification>();
        valuesToAlter(get(index), false, true, modifications);
        modifications.remove(new Modification(this, get(index)));        
        for (Modification modification: modifications) {
            modification.valuesToAlter.removePlain(modification.value);            
        }   
        remove(index);
    }
    
	/** Adds a value to the list, but does this without updating the values of 
	 * any depending slots.
     */
    public boolean addPlain(ValueSpecification value) {
        checkReadOnly();
        boolean returnValue;
        if (property.isUnique()) {
            if (!values.contains(value)) {
                returnValue = values.add(value);            
            } else {
                returnValue = false;
            }
        } else {
            returnValue = values.add(value);
        }
        if (returnValue) {
            occurencesAdd(value);
        }
        if (property.isComposite() && returnValue) {
            if (value instanceof InstanceValue) {
                ElementInstanceImpl valueAsInstance = (ElementInstanceImpl)((InstanceValue)value).getInstance();
                valueAsInstance.setComposite(owner);
                owner.getComponents().add(valueAsInstance);
            }
        }
		checkUpperMultiplicity();
        return returnValue;
    }

    public boolean removePlain(ValueSpecification value) {
        checkReadOnly();
        boolean returnValue = values.remove(value);
        if (returnValue) {
            occurencesRemove(value);
        }
        if (property.isComposite() && returnValue) {
            if (value instanceof InstanceValue) {
                ElementInstanceImpl valueAsInstance = (ElementInstanceImpl)((InstanceValue)value).getInstance();
                valueAsInstance.setComposite(null);
                owner.getComponents().remove(valueAsInstance);
            }
        }
		//TBD checkLowerMultiplicity();
        return returnValue;
    }

    public void setPlain(int index, ValueSpecification newValue) {
        checkReadOnly();        
        ValueSpecification oldValue = values.get(index);        
        values.set(index, newValue);
        if (property.isComposite()) {
            if (oldValue instanceof InstanceValue) {
                ElementInstanceImpl oldValueAsInstance = (ElementInstanceImpl)((InstanceValue)oldValue).getInstance();
                oldValueAsInstance.setComposite(null);
                owner.getComponents().remove(oldValueAsInstance);
            }
            if (newValue instanceof InstanceValue) {
                ElementInstanceImpl newValueAsInstance = (ElementInstanceImpl)((InstanceValue)newValue).getInstance();
                newValueAsInstance.setComposite(owner);
                owner.getComponents().add(newValueAsInstance);
            }
        }
        if (!values.contains(oldValue)) {
            occurencesRemove(oldValue);
        }
        occurencesAdd(newValue);
		checkLowerMultiplicity();
		checkUpperMultiplicity();
    }

    public void addPlain(int index, ValueSpecification value) {
        checkReadOnly();
        values.add(index, value);
        occurencesAdd(value);
        if (property.isComposite()) {
            if (value instanceof InstanceValue) {
                ElementInstanceImpl valueAsInstance = (ElementInstanceImpl)((InstanceValue)value).getInstance();
                valueAsInstance.setComposite(owner);
                owner.getComponents().add(valueAsInstance);
            }
        }
		checkUpperMultiplicity();
    }

    public void removePlain(int index) {
        checkReadOnly();
        ValueSpecification oldValue = values.get(index);
        if (oldValue == null) {
            return;
        }
        values.remove(index);
        if (!values.contains(oldValue)) {
            occurencesRemove(oldValue);
            if (oldValue instanceof InstanceValue) {
                ElementInstanceImpl oldValueAsInstance = (ElementInstanceImpl)((InstanceValue)oldValue).getInstance();
                oldValueAsInstance.setComposite(null);
                owner.getComponents().remove(oldValueAsInstance);
            }
        }
		checkLowerMultiplicity();
    }

    public ValueSpecificationList subList(int fromIndex, int toIndex) {
        return new ValueSpecificationList(this, owner, slot, values.subList(fromIndex, toIndex));
    }
    
    private void occurencesAdd(ValueSpecification value) {
        if (value instanceof InstanceValue) {
            InstanceSpecification instance = ((InstanceValue)value).getInstance();
            ReflectiveCollection<ValueSpecificationList> occurences = instanceOccurences.get(instance);
            if (occurences == null) {
                occurences = new SetImpl<ValueSpecificationList>();
                instanceOccurences.put(instance, occurences);
            }
            occurences.add(this);
        }        
    }
    
    private void occurencesRemove(ValueSpecification value) {
        if (value instanceof InstanceValue) {
            InstanceSpecification instance = ((InstanceValue)value).getInstance();
            ReflectiveCollection<ValueSpecificationList> occurences = instanceOccurences.get(instance);
            if (occurences != null) {
                if (!contains(values)) {
                    occurences.remove(this);
                }
            }            
        }
    }
    
    /**
     * Is a helper methods to remove all references to an instance. This is used to remove all values that represent
     * an instance that is going to be deleted.
     * @param instance the instance that is going to be removed
     */
    protected static void deleteInstance(InstanceSpecification instance) {
        // make a copy because the set behind the iterator will change
        ReflectiveCollection<ValueSpecificationList> origOccurences = instanceOccurences.get(instance);
        if (origOccurences != null) {
            ReflectiveCollection<ValueSpecificationList> occurences = new SetImpl<ValueSpecificationList>(instanceOccurences.get(instance));
            for (ValueSpecificationList list: occurences) {
                while(list.removePlain(new InstanceValueImpl(instance)));
            }
            if (origOccurences.size() != 0) {
                throw new RuntimeException("assert");
            }
            instanceOccurences.remove(instance);
        }
    }
    
    /**
     * An instance of this class represents a modification to a ValueSpecificationList.     
     */
    class Modification {
        ValueSpecificationList valuesToAlter = null;
        ValueSpecification value;
        public Modification(ValueSpecificationList valuesToAlter, ValueSpecification value) {
            this.valuesToAlter = valuesToAlter;
            this.value = value;
        }
        public int hashCode() {
            return valuesToAlter.hashCode();
        }
        public boolean equals(Object other) {           
            if (other instanceof Modification) {                
                return ((Modification)other).valuesToAlter.equals(this.valuesToAlter);    
            } else {
                return false;
            }
            
        }
    }
            
    /**
     * Collects all modifications that have to be done in order to update 
     * this value list. An update might be to remove a value or to add a value.
     * @param value the value to add or remove
     * @param forAdd specifices whether the update is an add or remove
     * @param indexed specifices whether the update is made at a specific index
     * @param valuesToAlter the list that all modifications are put in
     * @see Modification
     */
    private void valuesToAlter(ValueSpecification value, boolean forAdd, boolean indexed, ReflectiveCollection<Modification> valuesToAlter) {
        if (valuesToAlter.contains(new Modification(this, value))) {
            return;
        }
        valuesToAlter.add(new Modification(this, value));
        
        ReflectiveCollection<? extends Property> dependedPropertys = (forAdd ? subsettedPropertys : supersettedPropertys); 
        for (Property dependedProperty: dependedPropertys) {          
            if (indexed && cannotPreserveOrder) {
                throw new IllegalArgumentException(
                        "No indexed operation allowed when a superset is indexed.");
            }
            if (dependedProperty.getOwner() instanceof cmof.Association) {
                ((ValueSpecificationList) LinkImpl.findStructureSlotForEnd(
                        dependedProperty, new InstanceValueImpl(owner))
                        .getValue()).valuesToAlter(value, forAdd, indexed,
                        valuesToAlter);
            } else {
                owner.getValuesOfFeature(dependedProperty).valuesToAlter(value,
                        forAdd, indexed, valuesToAlter);
            }  
        }
        
        Property oppositeProperty = property.getOpposite();
        if (oppositeProperty != null) {
            Property redefiningProperty = null;
            if (oppositeProperty.getOwner() instanceof cmof.Association) {
                redefiningProperty = oppositeProperty;
            } else {
                redefiningProperty = (Property)((ClassInstanceImpl)((InstanceValue)value).getInstance()).getInstanceClassifier().resolveFinalProperty(oppositeProperty);                
            }                         
            ((ValueSpecificationList)LinkImpl.findStructureSlotForEnd(redefiningProperty, (InstanceValue)value).getValue()).
                    valuesToAlter(new InstanceValueImpl(owner), forAdd, false, valuesToAlter);                       
        }                
    }           
}
