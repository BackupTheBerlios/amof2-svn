package cmof;


public class PackageMergeImpl extends hub.sam.mof.reflection.ObjectImpl implements PackageMerge
{
    public PackageMergeImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public PackageMergeImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected PackageMerge self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (PackageMerge)self;
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getSource() {
        java.lang.Object value = get("source");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getTarget() {
        java.lang.Object value = get("target");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
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

    public boolean mustBeOwned()  {
        java.lang.Object value = invokeOperation("mustBeOwned", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public cmof.Package getMergedPackage() {
        java.lang.Object value = get("mergedPackage");
        if (value == null) {
           return null;
        } else {
            return (cmof.Package)value;
        }
    }

    public void setMergedPackage(cmof.Package value) {
        set("mergedPackage", value);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements()  {
        java.lang.Object value = invokeOperation("allOwnedElements", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.Package getMergingPackage() {
        java.lang.Object value = get("mergingPackage");
        if (value == null) {
           return null;
        } else {
            return (cmof.Package)value;
        }
    }

    public void setMergingPackage(cmof.Package value) {
        set("mergingPackage", value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

}

