package cmof;


public class ElementImportImpl extends hub.sam.mof.reflection.ObjectImpl implements ElementImport
{
    public ElementImportImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public ElementImportImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected ElementImport self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (ElementImport)self;
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getSource() {
        java.lang.Object value = get("source");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.PackageableElement getImportedElement() {
        java.lang.Object value = get("importedElement");
        if (value == null) {
           return null;
        } else {
            return (cmof.PackageableElement)value;
        }
    }

    public void setImportedElement(cmof.PackageableElement value) {
        set("importedElement", value);
    }

    public cmof.Namespace getImportingNamespace() {
        java.lang.Object value = get("importingNamespace");
        if (value == null) {
           return null;
        } else {
            return (cmof.Namespace)value;
        }
    }

    public void setImportingNamespace(cmof.Namespace value) {
        set("importingNamespace", value);
    }

    public String getName()  {
        java.lang.Object value = invokeOperation("getName", new java.lang.Object[] {  });
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
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Comment>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getRelatedElement() {
        java.lang.Object value = get("relatedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
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

    public cmof.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (cmof.Element)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getTarget() {
        java.lang.Object value = get("target");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
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

    public String getAlias() {
        java.lang.Object value = get("alias");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public void setAlias(String value) {
        set("alias", value);
    }

}

