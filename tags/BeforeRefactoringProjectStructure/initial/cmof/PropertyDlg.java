package cmof;


public class PropertyDlg extends hub.sam.mof.reflection.ObjectImpl implements Property
{
    public PropertyDlg(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public PropertyDlg(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected Property self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (Property)self;
    }

    public boolean isRedefinitionContextValid(core.abstractions.redefinitions.RedefinableElement redefinable)  {
        java.lang.Object value = invokeOperation("isRedefinitionContextValid_redefinable", new java.lang.Object[] { redefinable });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        java.lang.Object value = get("ownedComment");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Comment>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.UmlClass getUmlClass() {
        java.lang.Object value = get("umlClass");
        if (value == null) {
           return null;
        } else {
            return (cmof.UmlClass)value;
        }
    }

    public void setUmlClass(cmof.UmlClass value) {
        set("umlClass", value);
    }

    public void setUmlClass(core.basic.UmlClass value) {
        set("umlClass", value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> subsettingContext()  {
        java.lang.Object value = invokeOperation("subsettingContext", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Classifier>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.Association getOwningAssociation() {
        java.lang.Object value = get("owningAssociation");
        if (value == null) {
           return null;
        } else {
            return (cmof.Association)value;
        }
    }

    public void setOwningAssociation(cmof.Association value) {
        set("owningAssociation", value);
    }

    public String getQualifiedName() {
        java.lang.Object value = get("qualifiedName");
        if (value == null) {
           return null;
        } else {
            return (String)value;
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

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getRedefinitionContext() {
        java.lang.Object value = get("redefinitionContext");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Classifier>((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean includesMultiplicity(core.abstractions.multiplicities.MultiplicityElement M)  {
        java.lang.Object value = invokeOperation("includesMultiplicity_M", new java.lang.Object[] { M });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public String getDetails() {
        java.lang.Object value = get("details");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public void setDetails(String value) {
        set("details", value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Property> getRedefinedProperty() {
        java.lang.Object value = get("redefinedProperty");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Property>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.Property getOpposite() {
        java.lang.Object value = get("opposite");
        if (value == null) {
           return null;
        } else {
            return (cmof.Property)value;
        }
    }

    public void setOpposite(cmof.Property value) {
        set("opposite", value);
    }

    public void setOpposite(core.basic.Property value) {
        set("opposite", value);
    }

    public cmof.Type getType() {
        java.lang.Object value = get("type");
        if (value == null) {
           return null;
        } else {
            return (cmof.Type)value;
        }
    }

    public void setType(cmof.Type value) {
        set("type", value);
    }

    public void setType(core.abstractions.typedelements.Type value) {
        set("type", value);
    }

    public boolean isOrdered() {
        java.lang.Object value = get("isOrdered");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsOrdered(boolean value) {
        set("isOrdered", value);
    }

    public int getLower() {
        java.lang.Object value = get("lower");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Integer)value;
        }
    }

    public void setLower(int value) {
        set("lower", value);
    }

    public boolean isReadOnly() {
        java.lang.Object value = get("isReadOnly");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsReadOnly(boolean value) {
        set("isReadOnly", value);
    }

    public String qualifiedNameOperation()  {
        java.lang.Object value = invokeOperation("qualifiedName", new java.lang.Object[] {  });
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

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.Association getAssociation() {
        java.lang.Object value = get("association");
        if (value == null) {
           return null;
        } else {
            return (cmof.Association)value;
        }
    }

    public void setAssociation(cmof.Association value) {
        set("association", value);
    }

    public boolean isComposite() {
        java.lang.Object value = get("isComposite");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsComposite(boolean value) {
        set("isComposite", value);
    }

    public boolean isUnique() {
        java.lang.Object value = get("isUnique");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsUnique(boolean value) {
        set("isUnique", value);
    }

    public boolean isDerivedUnion() {
        java.lang.Object value = get("isDerivedUnion");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsDerivedUnion(boolean value) {
        set("isDerivedUnion", value);
    }

    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns)  {
        java.lang.Object value = invokeOperation("isDistinguishableFrom_n_ns", new java.lang.Object[] { n, ns });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public boolean isDerived() {
        java.lang.Object value = get("isDerived");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsDerived(boolean value) {
        set("isDerived", value);
    }

    public boolean includesCardinality(int C)  {
        java.lang.Object value = invokeOperation("includesCardinality_C", new java.lang.Object[] { C });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public long upperBound()  {
        java.lang.Object value = invokeOperation("upperBound", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Long)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getFeaturingClassifier() {
        java.lang.Object value = get("featuringClassifier");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Classifier>((cmof.common.ReflectiveCollection)value);
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

    public cmof.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (cmof.Element)value;
        }
    }

    public boolean isMultivalued()  {
        java.lang.Object value = invokeOperation("isMultivalued", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
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

    public cmof.common.ReflectiveCollection<? extends cmof.RedefinableElement> getRedefinedElement() {
        java.lang.Object value = get("redefinedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.RedefinableElement>((cmof.common.ReflectiveCollection)value);
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

    public boolean isConsistentWith(cmof.RedefinableElement redefinee)  {
        java.lang.Object value = invokeOperation("isConsistentWith_redefinee", new java.lang.Object[] { redefinee });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee)  {
        java.lang.Object value = invokeOperation("isConsistentWith_redefinee", new java.lang.Object[] { redefinee });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Property> getSubsettedProperty() {
        java.lang.Object value = get("subsettedProperty");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Property>((cmof.common.ReflectiveCollection)value);
        }
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

    public cmof.DataType getDatatype() {
        java.lang.Object value = get("datatype");
        if (value == null) {
           return null;
        } else {
            return (cmof.DataType)value;
        }
    }

    public void setDatatype(cmof.DataType value) {
        set("datatype", value);
    }

    public long getUpper() {
        java.lang.Object value = get("upper");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Long)value;
        }
    }

    public void setUpper(long value) {
        set("upper", value);
    }

    public String getDefault() {
        java.lang.Object value = get("default");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public void setDefault(String value) {
        set("default", value);
    }

    public cmof.Namespace getNamespace() {
        java.lang.Object value = get("namespace");
        if (value == null) {
           return null;
        } else {
            return (cmof.Namespace)value;
        }
    }

    public int lowerBound()  {
        java.lang.Object value = invokeOperation("lowerBound", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Integer)value;
        }
    }

    public cmof.Property oppositeOperation()  {
        java.lang.Object value = invokeOperation("opposite", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return (cmof.Property)value;
        }
    }

}

