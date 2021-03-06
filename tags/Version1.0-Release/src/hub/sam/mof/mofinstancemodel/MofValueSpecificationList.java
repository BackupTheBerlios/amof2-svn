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

import cmof.Property;
import cmof.UmlClass;
import cmof.common.ReflectiveCollection;
import cmof.exception.MultiplicityViolation;
import hub.sam.mof.instancemodel.InstanceValue;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.ValueSpecificationList;
import hub.sam.mof.util.ListImpl;
import hub.sam.mof.util.SetImpl;
import hub.sam.util.Identity;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

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

	@Override
	protected List<ValueSpecification<UmlClass,Property,java.lang.Object>> createList() {
		return new Vector<ValueSpecification<UmlClass,Property,java.lang.Object>>();
	}

    private static boolean performingSet = false;

    private void checkReadOnly() {
        if (property.isReadOnly()) {
            throw new cmof.exception.IllegalArgumentException("A readonly property can not be changed: " + property.getQualifiedName());
        }
    }

    private void checkDerived() {
    	if (property.isDerived() || property.isDerivedUnion()) {
    		System.out.println("WARNING: " + "A derived property can not be changed: " + property.getQualifiedName());
        	//throw new cmof.exception.IllegalArgumentException("A derived property can not be changed: " + property.getQualifiedName());
        }
    }

	private void checkUpperMultiplicity() {
		long upper = property.getUpper();
		if (upper > 0 && size() > upper) {
			throw new MultiplicityViolation(slot);
		}
	}

	public static boolean checkLower = true;

	private void checkLowerMultiplicity() {
		int lower = property.getLower();
		if (lower > 0 && size() < lower && !performingSet && checkLower) {
			throw new MultiplicityViolation(slot);
		}
	}

    // TODO checks for unique;
    // TODO derived, types, ordering

    private Collection<Property> subsettedPropertys;
    private cmof.Property property;
    protected MofClassInstance owner;
	protected MofStructureSlot slot;
    private Nodes nodes;
    private static final java.util.Map<MofClassInstance, ReflectiveCollection<MofValueSpecificationList>> instanceOccurences;

    static {
        instanceOccurences = new HashMap<MofClassInstance, ReflectiveCollection<MofValueSpecificationList>>();
    }

    /**
     * Is only used by {@link MofStructureSlot}. Creates a new empty list of value
     * specifications for a concrete slot and for a concrete element instance.
     * @param owner The {@link MofClassInstance} that owns the slot for this values.
     * @param slot The {@link MofStructureSlot} for this values.
     */
    protected MofValueSpecificationList(MofClassInstance owner, MofStructureSlot slot) {
        super();
        this.property = slot.getDefiningFeature();
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
	 * @param slot that the sublist will the values for
	 * @param values the allready cloned values
	 */
    private MofValueSpecificationList(MofValueSpecificationList parent, MofClassInstance owner, MofStructureSlot slot, java.util.List<ValueSpecification<UmlClass,Property,java.lang.Object>> values, Nodes nodes) {
        super();
        this.values = values;
        this.owner = owner;
        this.slot = slot;
        this.property = parent.property;
        this.subsettedPropertys = parent.subsettedPropertys;
        this.nodes = nodes;
    }

    public Property getProperty() {
    	return property;
    }

    public Identity getIdentity() {
    	return slot;
    }

    final class MyIterator implements ListIterator<ValueSpecification<UmlClass,Property,java.lang.Object>> {
        private ListIterator<ValueSpecification<UmlClass,Property,java.lang.Object>> base;

        MyIterator(ListIterator<ValueSpecification<UmlClass,Property,java.lang.Object>> base) {
            super();
            this.base = base;
        }

        public boolean hasNext() {
            return base.hasNext();
        }

        public ValueSpecification<UmlClass,Property,java.lang.Object> next() {
            return base.next();
        }

        @SuppressWarnings("synthetic-access")
		public void remove() {
            checkReadOnly();
            checkDerived();
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

    @Override
	public Iterator<ValueSpecification<UmlClass,Property,java.lang.Object>> iterator() {
        return new MyIterator(values.listIterator());
    }

    @SuppressWarnings({"unchecked","synthetic-access"})
	@Override
	public boolean add(Object o) {
        checkReadOnly();
        checkDerived();
    	ValueSpecification<UmlClass,Property,java.lang.Object> value = (ValueSpecification<UmlClass,Property,java.lang.Object>)o;
        return new UpdateGraphCreation().add(this, value).primaryAdd();
    }

    @SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
        checkReadOnly();
        checkDerived();
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

    @SuppressWarnings("unchecked")
	@Override
	public synchronized void set(int index, Object o) {
        checkReadOnly();
        checkDerived();
        performingSet = true;
        if (o == null || !o.equals(values.get(index))) {
        	remove(index);
        	if (o != null) {
        		add(index, o);
        	}
        }
        performingSet = false;
    }

    @SuppressWarnings({"unchecked","synthetic-access"})
	@Override
	public void add(int index, Object o) {
        checkReadOnly();
        checkDerived();
        ValueSpecification<UmlClass,Property,java.lang.Object> value = (ValueSpecification<UmlClass,Property,java.lang.Object>)o;
        new UpdateGraphCreation().add(this, value).primaryAdd(index);
    }

    @Override
	public void remove(int index) {
        checkReadOnly();
        checkDerived();
        for (UpdateGraphNode node: new Vector<UpdateGraphNode>(nodes.get(index))) {
            node.primaryRemove();
        }
    }

	/** Adds a value to the list, but does this without updating the values of
	 * any depending slots.
     */
    @SuppressWarnings("unchecked")
	public boolean addPlain(ValueSpecification<UmlClass,Property,java.lang.Object> value) {
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

	@SuppressWarnings("unchecked")
	public boolean removePlain(ValueSpecification<UmlClass,Property,java.lang.Object> value) {
        int index = values.indexOf(value);
        if (index != -1) {
        	removePlain(index);
        	return true;
        } else {
        	return false;
        }
    }

    @SuppressWarnings("unchecked")
	public void setPlain(int index, ValueSpecification<UmlClass,Property,java.lang.Object> newValue) {
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

    @SuppressWarnings("unchecked")
	public void addPlain(int index, ValueSpecification<UmlClass,Property,java.lang.Object> value) {
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

    @SuppressWarnings("unchecked")
	public void removePlain(int index) {
        ValueSpecification<UmlClass,Property,java.lang.Object> oldValue = values.get(index);
        if (oldValue == null) {
            return;
        }
        values.remove(index);
        nodes.removeAll(index); // NEU
        if (!values.contains(oldValue)) {
            occurencesRemove(oldValue);
            if (oldValue instanceof InstanceValue) {
            	if (property.isComposite()) {
            		MofClassInstance oldValueAsInstance = (MofClassInstance)((InstanceValue)oldValue).getInstance();
            		oldValueAsInstance.setComposite(null);
            	}
                //owner.getComponents().remove(oldValueAsInstance);
            }
        }
		checkLowerMultiplicity();
    }

    @Override
	public MofValueSpecificationList subList(int fromIndex, int toIndex) {
        return new MofValueSpecificationList(this, owner, slot, values.subList(fromIndex, toIndex), nodes);
    }

    class Nodes {
        private final Vector<Collection<UpdateGraphNode>> nodes = new Vector<Collection<UpdateGraphNode>>();

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

        @SuppressWarnings("boxing")
        void addNode(int index, UpdateGraphNode node) {
        	node.setPosition(index);
            get(index).add(node);
        }

        void removeNode(int index, UpdateGraphNode node) {
            get(index).remove(node);
            node.setPosition(-1);
        }

        boolean isLastNode(int index, UpdateGraphNode node) {
            return get(index).size() == 1;
        }

        void remove(UpdateGraphNode node) {
            int index = node.getPosition();
            if (index != -1) {
                removeNode(index, node);
            }
        }

        void removeAll(int index) {
        	// remove all nodes for index
        	Collection<UpdateGraphNode> toDelete = new Vector<UpdateGraphNode>(get(index).size());
        	for (UpdateGraphNode node: get(index)) {
        		toDelete.add(node);
        	}
        	for (UpdateGraphNode node: toDelete) {
        		removeNode(index, node);
        	}
        	// remove the now empty nodeset for index
            nodes.remove(index);
            // recalculate positions
            loop: for (int i = index; i < nodes.size(); i++) {
            	Collection<UpdateGraphNode> wrongIdextedNodes = nodes.get(i);
            	if (wrongIdextedNodes == null) {
            		break loop;
            	}
            	for (UpdateGraphNode node: wrongIdextedNodes) {
            		node.setPosition(node.getPosition() - 1);
            	}
            }
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

        private void clear() {
            nodes.clear();
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
        int index = node.getPosition();
        if (index != -1) {
            boolean lastNode = nodes.isLastNode(index, node);
            if (lastNode) {
                removePlain(index);
            } else {
            	nodes.removeNode(index, node);
            }
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

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
	private void occurencesRemove(ValueSpecification<UmlClass,Property,java.lang.Object> value) {
        if (value instanceof InstanceValue) {
            MofClassInstance instance = (MofClassInstance)((InstanceValue)value).getInstance();
            ReflectiveCollection<MofValueSpecificationList> occurences = instanceOccurences.get(instance);
            if (occurences != null) {
                if (!contains(values)) {// TODO ???
                    occurences.remove(this);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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

        @SuppressWarnings({"synthetic-access","unchecked"})
		private UpdateGraphNode add(MofValueSpecificationList forList, ValueSpecification<UmlClass,Property,java.lang.Object> value) {
            if (updatedLists.contains(forList)) {
                return null;
            }
            updatedLists.add(forList);
            UpdateGraphNode node = new UpdateGraphNode(value, forList);
            for (Property dependedProperty: forList.subsettedPropertys) {
                if (dependedProperty.getOwner() instanceof cmof.Association) {
                    node.addAjacentReasoning(add(MofLink.findStructureSlotForEnd(
                            dependedProperty, new InstanceValue(forList.owner))
                            .getValuesAsList(), value));
                } else {
                    node.addAjacentReasoning(add(forList.owner.getValuesOfFeature(dependedProperty), value));
                }
            }
            Property oppositeProperty = forList.property.getOpposite();
            if (oppositeProperty != null) {
                Property redefiningProperty;
                if (oppositeProperty.getOwner() instanceof cmof.Association) {
                    redefiningProperty = oppositeProperty;
                } else {
                    redefiningProperty = ((MofClassInstance)((InstanceValue)value).getInstance()).getInstanceClassifier().getFinalProperty(oppositeProperty);
                }
                node.addAjacentReasoning(add(MofLink.findStructureSlotForEnd(
                        redefiningProperty, (InstanceValue)value).getValuesAsList(), new InstanceValue(forList.owner)));
            }
            return node;
        }
    }

    @Override
	@SuppressWarnings("synthetic-access")
	public void clear() {
        for(ValueSpecification<UmlClass,Property,java.lang.Object> value: new Vector<ValueSpecification<UmlClass,Property,java.lang.Object>>(values)) {
        	remove(value);
        }
    }

    public void myFinalize() {
    	subsettedPropertys.clear();
        property = null;
        owner = null;
        slot = null;
        nodes.clear();
    }

    @SuppressWarnings("unused")
	private void checkListConsitency() {
    	for (int i = 0; i < values.size(); i++) {
    		if (values.get(i) != null) {
    			if (nodes.get(i).size() == 0) {
    				System.out.println("cosistency error");//throw new AssertionException();
    			}
    		}
    	}
    }
}
