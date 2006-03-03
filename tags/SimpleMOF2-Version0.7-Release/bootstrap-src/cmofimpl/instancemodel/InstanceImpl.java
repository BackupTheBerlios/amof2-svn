package cmofimpl.instancemodel;

import core.abstractions.instances.*;
import core.abstractions.ownerships.*;
import core.abstractions.namespaces.*;
import core.abstractions.expressions.*;
import cmof.Classifier;
import cmof.common.ReflectiveCollection;


/**
 * An instance specification is a model element that represents an instance in a modeled system.
 */
public abstract class InstanceImpl implements Instance, Cloneable {
			
	private core.abstractions.ownerships.Element owner;
    private String name;
        	
	public InstanceImpl(String name, Element owner) {
        this.name = name;
		this.owner = owner;
	}
    
    /**
     * The Classifier or Classifiers of the represented instance. If multiple are specified the instance is classified
     * by all of them.
     * 
     * Multiplicity 1..*
     * Redefines {@link core.abstractions.instances.InstanceSpecification#getClassifier()}
     */
    public abstract ReflectiveCollection<? extends Classifier> getClassifier();

    /**
     * A slot giving the value or values of a structural feature of the
     * instance. An instance speci-fication can have one slot per structural
     * feature of its classifiers, including inherited fea-tures. It is not
     * necessary to model a slot for each structural feature, in which case the
     * instance specification is a partial description. Subsets
     * Element::ownedElement.
     * 
     * Multiplicity *
     * From {@link core.abstractions.instances.InstanceSpecification#getSlot()}
     * Subsets {@link core.abstractions.ownerships.Element#getOwnedElement()}
     */
    public abstract ReflectiveCollection<? extends Slot> getSlot();

    /**
     * A specification of how to compute, derive, or construct the instance.
     * Subsets Element::ownedElement.
     * 
     * Multiplicity *
     * From {@link core.abstractions.instances.InstanceSpecification#getSpecification()}
     * Subsets {@link core.abstractions.ownerships.Element#getOwnedElement()}
     */
    public ValueSpecification getSpecification() {
        return new InstanceValueImpl(this);
    }

    public void setSpecification(ValueSpecification value) {
        throw new RuntimeException("assert");
    }

    /**
     * A collection of NamedElements owned by the Namespace. Subsets
     * Element::ownedElement and Namespace::member. This is a derived union.
     * 
     * Derived Union
     * Mulitiplicity *
     * From {@link core.abstractions.namespaces.Namespace}
     * Subsets {@link core.abstractions.ownerships.Element#getOwnedElements()}
     * Subsets {@link core.abstractions.namespaces.Namespace#getMembers()}
     */
    //public Collection<? extends NamedElement> getOwnedMembers() {
	//	
    //    return new cmofimpl.util.CollectionImpl<NamedElement>();
    //}

    /**
     * A collection of NamedElements identifiable within the Namespace, either
     * by being owned or by being introduced by importing or inheritance. This
     * is a derived union.
     * 
     * Derived Union 
     * Mulitiplicity * 
     * From {@link core.abstractions.namespaces.Namespace}
     */
    //public Collection<? extends NamedElement> getMembers() {
	//	return new cmofimpl.util.CollectionImpl<NamedElement>();
    //}

    /**
     * The name of the NamedElement.
     * 
     * Multiplicity 0..1
     * From {@link core.abstractions.namespaces.NamedElement}
     */
    public String getName() {
        return name;
    }

    public void setName(String value) {
		this.name = value;
    }

    /**
     * A name which allows the NamedElement to be identified within a hierarchy
     * of nested Namespaces. It is constructed from the names of the containing
     * namespaces starting at the root of the hierarchy and ending with the name
     * of the NamedElement itself. This is a derived attribute.
     * 
     * Derived
     * Multiplicity 0..1
     * From {@link core.abstractions.namespaces.NamedElement}
     */
    public String getQualifiedName() {
		Namespace ns = getNamespace();
		if (ns != null) {
			return ns.getQualifiedName() + "." + getName(); 
		} else {
			return getName();
		}
    }

    /**
     * Specifies the namespace that owns the NamedElement. Subsets
     * Element::owner. This is a derived union.
     * 
     * Derived Union
     * Mulitplicity 0..1
     * From {@link core.abstractions.namespaces.NamedElement}
     * Subsets {@link core.abstractions.ownerships.Element#getOwner()}
     */
    public Namespace getNamespace() {
        if (owner == null) {
            return null;
        }
		if (owner instanceof Namespace) {
			return (Namespace)owner;
		} else {
			return null;
		}
    }

    /**
     * The Elements owned by this element. This is a derived union.
     * 
     * Derived Union
     * Mulitplicity *
     * From {@link core.abstractions.ownerships.Element}
     */
    public ReflectiveCollection<core.abstractions.ownerships.Element> getOwnedElement() {
        return null;
    }

    /**
     * The Element that owns this element. This is a derived union.
     * 
     * Derived Union
     * Mulitplicity 0..1
     * From {@link core.abstractions.ownerships.Element}
     */
    public core.abstractions.ownerships.Element getOwner() {
        return owner;
    }

    // unused
    public Namespace allNamespaces() {
        return null;
    }

    public boolean isDistinguishableFrom(NamedElement n, Namespace ns) {
        return false;
    }

    public String separator() {
        return null;
    }

    public String qualifiedNameOperation() {
        return null;
    }

    public Element allOwnedElements() {
        return null;
    }

    public boolean mustBeOwned() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public boolean isComputable() {
        return false;
    }

    public int integerValue() {
        return 0;
    }

    public boolean booleanValue() {
        return false;
    }

    public String stringValue() {
        return null;
    }

    public long unlimitedValue() {
        return 0;
    }
}
