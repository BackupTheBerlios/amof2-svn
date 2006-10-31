package core.abstractions.instances;


public class SlotImpl extends cmofimpl.reflection.ObjectImpl implements Slot
{
    public SlotImpl(cmofimpl.instancemodel.ClassInstanceImpl instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public SlotImpl(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
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
            return new cmofimpl.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
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

    public cmof.common.ReflectiveSequence<? extends core.abstractions.expressions.ValueSpecification> getValue() {
        java.lang.Object value = get("value");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperListImpl<core.abstractions.expressions.ValueSpecification>((cmof.common.ReflectiveSequence)value);
        }
    }

}

