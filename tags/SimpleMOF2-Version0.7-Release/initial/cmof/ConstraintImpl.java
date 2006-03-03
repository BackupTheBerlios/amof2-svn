package cmof;


public class ConstraintImpl extends cmofimpl.reflection.ObjectImpl implements Constraint
{
    public ConstraintImpl(cmofimpl.instancemodel.ClassInstanceImpl instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public ConstraintImpl(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public core.abstractions.visibilities.VisibilityKind getVisibility() {
        java.lang.Object value = get("visibility");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.visibilities.VisibilityKind)value;
        }
    }

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value) {
        set("visibility", value);
    }

    public String getQualifiedName() {
        java.lang.Object value = get("qualifiedName");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        java.lang.Object value = get("ownedComment");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Comment>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.Namespace getNamespace() {
        java.lang.Object value = get("namespace");
        if (value == null) {
           return null;
        } else {
            return (cmof.Namespace)value;
        }
    }

    public void setNamespace(cmof.Namespace value) {
        set("namespace", value);
    }

    public void setNamespace(core.abstractions.namespaces.Namespace value) {
        set("namespace", value);
    }

    public void setNamespace(core.abstractions.constraints.Namespace value) {
        set("namespace", value);
    }

    public cmof.Namespace getContext() {
        java.lang.Object value = get("context");
        if (value == null) {
           return null;
        } else {
            return (cmof.Namespace)value;
        }
    }

    public cmof.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (cmof.Element)value;
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

    public cmof.ValueSpecification getSpecification() {
        java.lang.Object value = get("specification");
        if (value == null) {
           return null;
        } else {
            return (cmof.ValueSpecification)value;
        }
    }

    public void setSpecification(cmof.ValueSpecification value) {
        set("specification", value);
    }

    public void setSpecification(core.abstractions.expressions.ValueSpecification value) {
        set("specification", value);
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Element> getConstrainedElement() {
        java.lang.Object value = get("constrainedElement");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperListImpl<cmof.Element>((cmof.common.ReflectiveSequence)value);
        }
    }

}

