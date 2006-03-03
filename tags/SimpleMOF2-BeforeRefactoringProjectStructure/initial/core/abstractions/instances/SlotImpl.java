package core.abstractions.instances;


public class SlotImpl extends hub.sam.mof.reflection.ObjectImpl implements Slot
{
    public SlotImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public SlotImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected Slot self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (Slot)self;
    }

    public core.abstractions.structuralfeatures.StructuralFeature getDefiningFeature() {
        java.lang.Object value = get("definingFeature");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.structuralfeatures.StructuralFeature)value;
        }
    }

    public void setDefiningFeature(core.abstractions.structuralfeatures.StructuralFeature value) {
        set("definingFeature", value);
    }

    public core.abstractions.instances.InstanceSpecification getOwningInstance() {
        java.lang.Object value = get("owningInstance");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.instances.InstanceSpecification)value;
        }
    }

    public void setOwningInstance(core.abstractions.instances.InstanceSpecification value) {
        set("owningInstance", value);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.expressions.ValueSpecification> getValue() {
        java.lang.Object value = get("value");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<core.abstractions.expressions.ValueSpecification>((cmof.common.ReflectiveSequence)value);
        }
    }

    public boolean mustBeOwned()  {
        java.lang.Object value = invokeOperation("mustBeOwned", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public core.abstractions.ownerships.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.ownerships.Element)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements()  {
        java.lang.Object value = invokeOperation("allOwnedElements", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

}

