package cmof;


public class ConstraintDlg extends hub.sam.mof.reflection.ObjectImpl implements Constraint
{
    public ConstraintDlg(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public ConstraintDlg(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected Constraint self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (Constraint)self;
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

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        java.lang.Object value = get("ownedComment");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Comment>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.Namespace getContext() {
        java.lang.Object value = get("context");
        if (value == null) {
           return null;
        } else {
            return (cmof.Namespace)value;
        }
    }

    public String qualifiedNameOperation()  {
        java.lang.Object value = invokeOperation("qualifiedName", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public String getQualifiedName() {
        java.lang.Object value = get("qualifiedName");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.namespaces.Namespace> allNamespaces()  {
        java.lang.Object value = invokeOperation("allNamespaces", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<core.abstractions.namespaces.Namespace>((cmof.common.ReflectiveSequence)value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public String separator()  {
        java.lang.Object value = invokeOperation("separator", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return (String)value;
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

    public cmof.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (cmof.Element)value;
        }
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Element> getConstrainedElement() {
        java.lang.Object value = get("constrainedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<cmof.Element>((cmof.common.ReflectiveSequence)value);
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

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements()  {
        java.lang.Object value = invokeOperation("allOwnedElements", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns)  {
        java.lang.Object value = invokeOperation("isDistinguishableFrom_n_ns", new java.lang.Object[] { n, ns });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

}

