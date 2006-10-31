package core.abstractions.constraints;


public class ConstraintImpl extends cmofimpl.reflection.ObjectImpl implements Constraint
{
    public ConstraintImpl(hub.sam.mof.instancemodel.ClassInstance instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public ConstraintImpl(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public String getQualifiedName() {
        java.lang.Object value = get("qualifiedName");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public core.abstractions.constraints.Namespace getNamespace() {
        java.lang.Object value = get("namespace");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.constraints.Namespace)value;
        }
    }

    public void setNamespace(core.abstractions.constraints.Namespace value) {
        set("namespace", value);
    }

    public void setNamespace(core.abstractions.namespaces.Namespace value) {
        set("namespace", value);
    }

    public core.abstractions.expressions.ValueSpecification getSpecification() {
        java.lang.Object value = get("specification");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.expressions.ValueSpecification)value;
        }
    }

    public void setSpecification(core.abstractions.expressions.ValueSpecification value) {
        set("specification", value);
    }

    public core.abstractions.namespaces.Namespace getContext() {
        java.lang.Object value = get("context");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.namespaces.Namespace)value;
        }
    }

    public String getName() {
        java.lang.Object value = get("name");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public void setName(String value) {
        set("name", value);
    }

    public core.abstractions.ownerships.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.ownerships.Element)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.ownerships.Element> getConstrainedElement() {
        java.lang.Object value = get("constrainedElement");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperListImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveSequence)value);
        }
    }

}

