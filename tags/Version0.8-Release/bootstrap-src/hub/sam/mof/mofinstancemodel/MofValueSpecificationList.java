/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
*/

package hub.sam.mof.mofinstancemodel;

import java.util.*;

import cmof.*;
import cmof.common.*;
import cmof.exception.*;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.util.*;

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
public class MofValueSpecificationList extends ListImpl<ValueSpecification<UmlClass,Property,java.lang.Object>> implements ValueSpecificationList<UmlClass,Property,java.lang.Object> {
    
    private static boolean performingSet = false;
    
    private void checkReadOnly() {
        if (property.isReadOnly()) {
            throw new cmof.exception.IllegalArgumentException("property is readonly");
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
		if (lower > 0 && size() < lower && !performingSet) {
			throw new MultiplicityViolation(slot);
		}
	}
    
    // TBD checks for unique;
    // TBD derived, types, ordering

    private final Collection<Property> subsettedPropertys;    
    private final cmof.Property property;
    private final MofClassInstance owner;
	private final MofStructureSlot slot;
    private final Nodes nodes;
    private final boolean isReadOnly;
    
    private final static java.util.Map<MofClassInstance, ReflectiveCollection<MofValueSpecificationList>> instanceOccurences =
            new java.util.HashMap<MofClassInstance, ReflectiveCollection<MofValueSpecificationList>>();
    
	/**
	 * Is only used by {@link MofStructureSlot}. Creates a new empty list of value
	 * specifications for a concrete slot and for a concrete element instance. 
	 * @param owner The {@link MofClassInstance} that owns the slot for this values.
	 * @param slot The {@link MofStructureSlot} for this values.
	 */
    protected MofValueSpecificationList(MofClassInstance owner, MofStructureSlot slot) {        
        this.property = (Property)slot.getDefiningFeature();
        this.isReadOnly = property.isReadOnly();
		this.slot = slot;        
        this.owner = owner;              
        this.subsettedPropertys = this.owner.getInstanceClassifier().getSubsettedProperties(property); 
        this.nodes = new Nodes();
    }
   
	/**
	 * Is used for sublists. 
	 * @see #subList(int, int)
	 * @param parent the list to sublist
	 * @param owner the owner ({@link MofClassInstance}) of the sublist
	 * @param the slot that the sublist will the values for
	 * @param values the allready cloned values
	 */
    private MofValueSpecificationList(MofValueSpecificationList parent, MofClassInstance owner, MofStructureSlot slot, java.util.List<ValueSpecification<UmlClass,Property,java.lang.Object>> values, Nodes nodes, boolean isReadOnly) {
        this.values = values;
        this.owner = owner;
		this.slot = slot;
        this.property = parent.property;                       
        this.subsettedPropertys = parent.subsettedPropertys;
        this.nodes = nodes;
        this.isReadOnly = isReadOnly;
    }
            
    final class MyIterator implements ListIterator<ValueSpecification<UmlClass,Property,java.lang.Object>> {
        private ListIterator<ValueSpecification<UmlClass,Property,java.lang.Object>> base;
        
        public MyIterator(ListIterator<ValueSpecification<UmlClass,Property,java.lang.Object>> base) {
            this.base = base;
        }
        
        public boolean hasNext() {
            return base.hasNext();
        }

        public ValueSpecification<UmlClass,Property,java.lang.Object> next() {
            return base.next();
        }

        public void remove() {
            checkReadOnly();
            base.remove();
        }

        public boolean hasPrevious() {
            return base.hasPrevious();
        }

        public ValueSpecification<UmlClass,Property,java.lang.Object> previous() {
            return base.previous();
        }

        public int nextIndex() {
            return base.nextIndex();
        }
        
        public int previousIndex() {
            return base.previousIndex();
        }
        
        public void set(ValueSpecification<UmlClass,Property,java.lang.Object> o) {
            throw new RuntimeException("Not Implemented.");                   
        }        
        public void add(ValueSpecification<UmlClass,Property,java.lang.Object> o) {            
            throw new RuntimeException("Not Implemented.");
        }        
    }

    public Iterator<ValueSpecification<UmlClass,Property,java.lang.Object>> iterator() {
        return new MyIterator(values.listIterator());
    }

    public boolean add(Object o) {        
        ValueSpecification<UmlClass,Property,java.lang.Object> value = (ValueSpecification<UmlClass,Property,java.lang.Object>)o;
        return new UpdateGraphCreation().add(this, value).primaryAdd();               
    }        

    public boolean remove(Object o) {
        ValueSpecification<UmlClass,Property,java.lang.Object> value = (ValueSpecification<UmlClass,Property,java.lang.Object>)o;
        int index = values.indexOf(value);
        boolean removed = false;
        while(index>=0) {                 
            for (UpdateGraphNode node: new Vector<UpdateGraphNode>(nodes.get(index))) {
                node.primaryRemove();
            }
            removed = true;
            index = values.indexOf(value);
        }
        return removed;
    }
    
    public synchronized void set(int index, Object o) {
        ValueSpecification<UmlClass,Property,java.lang.Object> value = (ValueSpecification<UmlClass,Property,java.lang.Object>)o;
        performingSet = true;
        remove(index);
        add(index, o);
        performingSet = false;
    }
    
    public void add(int index, Object o) {
        ValueSpecification<UmlClass,Property,java.lang.Object> value = (ValueSpecification<UmlClass,Property,java.lang.Object>)o;
        new UpdateGraphCreation().add(this, value).primaryAdd(index);        
    }
    
    public void remove(int index) {                                            
        for (UpdateGraphNode node: new Vector<UpdateGraphNode>(nodes.get(index))) {
            node.primaryRemove();
        }        
    }
    
	/** Adds a value to the list, but does this without updating the values of 
	 * any depending slots.
     */
    public boolean addPlain(ValueSpecification<UmlClass,Property,java.lang.Object> value) {
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
                MofClassInstance valueAsInstance = (MofClassInstance)((InstanceValue)value).getInstance();
                valueAsInstance.setComposite(owner);
                owner.getComponents().add(valueAsInstance);
            }
        }
		checkUpperMultiplicity();
        return returnValue;
    }

    public boolean removePlain(ValueSpecification<UmlClass,Property,java.lang.Object> value) {
        checkReadOnly();        
        boolean returnValue = values.remove(value);
        if (returnValue) {
            occurencesRemove(value);
        }
        if (property.isComposite() && returnValue) {
            if (value instanceof InstanceValue) {
                MofClassInstance valueAsInstance = (MofClassInstance)((InstanceValue)value).getInstance();
                valueAsInstance.setComposite(null);
                owner.getComponents().remove(valueAsInstance);
            }
        }
		//TODO checkLowerMultiplicity();        
        return returnValue;
    }
           
    public void setPlain(int index, ValueSpecification<UmlClass,Property,java.lang.Object> newValue) {
        checkReadOnly();        
        ValueSpecification<UmlClass,Property,java.lang.Object> oldValue = values.get(index);        
        values.set(index, newValue);
        if (property.isComposite()) {
            if (oldValue instanceof InstanceValue) {
                MofClassInstance oldValueAsInstance = (MofClassInstance)((InstanceValue)oldValue).getInstance();
                oldValueAsInstance.setComposite(null);
                owner.getComponents().remove(oldValueAsInstance);
            }
            if (newValue instanceof InstanceValue) {
                MofClassInstance newValueAsInstance = (MofClassInstance)((InstanceValue)newValue).getInstance();
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

    public void addPlain(int index, ValueSpecification<UmlClass,Property,java.lang.Object> value) {
        checkReadOnly();
        if (performingSet && index < values.size()) {
            values.set(index, value);
        } else {
            values.add(index, value);
        }
        occurencesAdd(value);
        if (property.isComposite()) {
            if (value instanceof InstanceValue) {
                MofClassInstance valueAsInstance = (MofClassInstance)((InstanceValue)value).getInstance();
                valueAsInstance.setComposite(owner);
                owner.getComponents().add(valueAsInstance);
            }
        }
		checkUpperMultiplicity();
    }

    public void removePlain(int index) {
        checkReadOnly();
        ValueSpecification<UmlClass,Property,java.lang.Object> oldValue = values.get(index);
        if (oldValue == null) {
            return;
        }
        values.remove(index);
        if (!values.contains(oldValue)) {
            occurencesRemove(oldValue);
            if (oldValue instanceof InstanceValue) {
                MofClassInstance oldValueAsInstance = (MofClassInstance)((InstanceValue)oldValue).getInstance();
                oldValueAsInstance.setComposite(null);
                owner.getComponents().remove(oldValueAsInstance);
            }
        }
		checkLowerMultiplicity();
    }

    public MofValueSpecificationList subList(int fromIndex, int toIndex) {
        return new MofValueSpecificationList(this, owner, slot, values.subList(fromIndex, toIndex), nodes, true);
    }
    
    class Nodes {
        private final Vector<Collection<UpdateGraphNode>> nodes = new Vector<Collection<UpdateGraphNode>>();
        private final Map<UpdateGraphNode, Integer> positions = new HashMap<UpdateGraphNode, Integer>();
        
        Collection<UpdateGraphNode> get(int index) {
            if (index > (nodes.size() - 1)) {
                nodes.setSize((index+10)*2);
            }
            Collection<UpdateGraphNode> result = nodes.get(index);
            if (result == null) {
                result = new HashSet<UpdateGraphNode>();
                nodes.set(index, result);
            }
            return result;
        }
        
        void addNode(int index, UpdateGraphNode node) {                       
            positions.put(node, index);
            get(index).add(node);
        }
    
        void removeNode(int index, UpdateGraphNode node) {                       
            get(index).remove(node);
            positions.remove(node);            
        }
    
        int findNode(UpdateGraphNode node) {
            Integer result = positions.get(node);
            if (result == null) {
                return -1;
            } else {
                return result;
            }
        }
    
        boolean isLastNode(int index, UpdateGraphNode node) {            
            return get(index).size() == 1;            
        }
        
        void remove(UpdateGraphNode node) {
            int index = findNode(node);
            if (index != -1) {
                removeNode(index, node);
            }
        }
        
        void removeAll(int index) {                        
            get(index).clear();
        }
        
        Nodes copy() {
            Nodes result = new Nodes();
            for (Collection<UpdateGraphNode> node: nodes) {
                if (node == null) {                    
                    result.nodes.add(new HashSet<UpdateGraphNode>());
                } else {
                    result.nodes.add(new HashSet<UpdateGraphNode>(node));
                }
            }
            return result;
        }
    }
    
    public boolean primaryAdd(UpdateGraphNode node) {
        boolean added = addPlain(node.getValue());
        int index = values.lastIndexOf(node.getValue());
        nodes.addNode(index, node);
        return added;
    }
    
    public void primaryAdd(int index, UpdateGraphNode node) {
        addPlain(index, node.getValue());
        nodes.addNode(index, node);
    }
    
    public void primaryRemove(UpdateGraphNode node) {
        int index = nodes.findNode(node);
        if (index != -1) {         
            boolean lastNode = nodes.isLastNode(index, node);
            if (lastNode) {
                removePlain(index);
            }     
            nodes.removeNode(index, node);
        }
    }
    
    public void primaryRemove(int index, UpdateGraphNode node) {
        removePlain(index);
        nodes.removeNode(index, node);
    }
    
    public void secondaryAdd(UpdateGraphNode node) {
        primaryAdd(node);        
    }
    
    public void secondaryRemove(UpdateGraphNode node) {
        primaryRemove(node);
    }
    
    private void occurencesAdd(ValueSpecification<UmlClass,Property,java.lang.Object> value) {
        if (value instanceof InstanceValue) {
            MofClassInstance instance = (MofClassInstance)((InstanceValue)value).getInstance();
            ReflectiveCollection<MofValueSpecificationList> occurences = instanceOccurences.get(instance);
            if (occurences == null) {
                occurences = new SetImpl<MofValueSpecificationList>();
                instanceOccurences.put(instance, occurences);
            }
            occurences.add(this);
        }        
    }
    
    private void occurencesRemove(ValueSpecification<UmlClass,Property,java.lang.Object> value) {
        if (value instanceof InstanceValue) {
            MofClassInstance instance = (MofClassInstance)((InstanceValue)value).getInstance();
            ReflectiveCollection<MofValueSpecificationList> occurences = instanceOccurences.get(instance);
            if (occurences != null) {
                if (!contains(values)) {
                    occurences.remove(this);
                }
            }            
        }
    }
    
    private void forceRemove(ValueSpecification<UmlClass,Property,java.lang.Object> value) {
        boolean removed = true;
        boolean removedOne = false;
        while(removed) {
            int index = values.lastIndexOf(value);
            removed = values.remove(value);
            if (removed) {
                removedOne = true;
                nodes.removeAll(index);                
            }
        } 

        if (removedOne) {
            occurencesRemove(value);
        }
        if (property.isComposite() && removedOne) {
            if (value instanceof InstanceValue) {
                MofClassInstance valueAsInstance = (MofClassInstance)((InstanceValue)value).getInstance();
                valueAsInstance.setComposite(null);
                owner.getComponents().remove(valueAsInstance);
            }
        }        
    }
    
    /**
     * Is a helper methods to remove all references to an instance. This is used to remove all values that represent
     * an instance that is going to be deleted.
     * @param instance the instance that is going to be removed
     */
    protected static void deleteInstance(MofClassInstance instance) {
        // make a copy because the set behind the iterator will change
        ReflectiveCollection<MofValueSpecificationList> origOccurences = instanceOccurences.get(instance);
        if (origOccurences != null) {
            ReflectiveCollection<MofValueSpecificationList> occurences = new SetImpl<MofValueSpecificationList>(instanceOccurences.get(instance));
            for (MofValueSpecificationList list: occurences) {
                list.forceRemove(new InstanceValue(instance));
            }
            if (origOccurences.size() != 0) {
                throw new RuntimeException("assert");
            }
            instanceOccurences.remove(instance);
        }
    }
        
    class UpdateGraphCreation {        
        private Collection<MofValueSpecificationList> updatedLists = new HashSet<MofValueSpecificationList>();                   
        
        private UpdateGraphNode add(MofValueSpecificationList forList, ValueSpecification<UmlClass,Property,java.lang.Object> value) {
            if (updatedLists.contains(forList)) {
                return null;
            }
            updatedLists.add(forList);            
            UpdateGraphNode node = new UpdateGraphNode(value, forList);
            for (Property dependedProperty: forList.subsettedPropertys) {
                if (dependedProperty.getOwner() instanceof cmof.Association) {
                    node.addAjacentReasoning(add(((MofValueSpecificationList) MofLink.findStructureSlotForEnd(
                            dependedProperty, new InstanceValue(forList.owner))
                            .getValuesAsList()), value));
                } else {
                    node.addAjacentReasoning(add(forList.owner.getValuesOfFeature(dependedProperty), value));                    
                }   
            }
            Property oppositeProperty = forList.property.getOpposite();
            if (oppositeProperty != null) { 
                Property redefiningProperty = null;
                if (oppositeProperty.getOwner() instanceof cmof.Association) {
                    redefiningProperty = oppositeProperty;
                } else {                    
                    redefiningProperty = (Property)((MofClassInstance)((InstanceValue)value).getInstance()).getInstanceClassifier().getFinalProperty(oppositeProperty);                    
                }                         
                node.addAjacentReasoning(add(((MofValueSpecificationList)MofLink.findStructureSlotForEnd(
                        redefiningProperty, (InstanceValue)value).getValuesAsList()), new InstanceValue(forList.owner)));                                               
            }                           
            return node;
        }
    }
}
