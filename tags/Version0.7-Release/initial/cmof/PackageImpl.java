package cmof;


public class PackageImpl extends cmofimpl.reflection.ObjectImpl implements Package
{
    public PackageImpl(cmofimpl.instancemodel.ClassInstanceImpl instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public PackageImpl(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
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

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getImportedMember() {
        java.lang.Object value = get("importedMember");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.PackageableElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Type> getOwnedType() {
        java.lang.Object value = get("ownedType");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Type>((cmof.common.ReflectiveCollection)value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.PackageMerge> getPackageMerge() {
        java.lang.Object value = get("packageMerge");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.PackageMerge>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Package> getNestedPackage() {
        java.lang.Object value = get("nestedPackage");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Package>((cmof.common.ReflectiveCollection)value);
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

    public cmof.Namespace getNamespace() {
        java.lang.Object value = get("namespace");
        if (value == null) {
           return null;
        } else {
            return (cmof.Namespace)value;
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

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getMember() {
        java.lang.Object value = get("member");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.NamedElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getOwnedRule() {
        java.lang.Object value = get("ownedRule");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.Constraint>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageImport> getPackageImport() {
        java.lang.Object value = get("packageImport");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.PackageImport>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getOwnedMember() {
        java.lang.Object value = get("ownedMember");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.PackageableElement>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.Package getNestingPackage() {
        java.lang.Object value = get("nestingPackage");
        if (value == null) {
           return null;
        } else {
            return (cmof.Package)value;
        }
    }

    public void setNestingPackage(cmof.Package value) {
        set("nestingPackage", value);
    }

    public void setNestingPackage(core.basic.Package value) {
        set("nestingPackage", value);
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

    public cmof.common.ReflectiveCollection<? extends cmof.ElementImport> getElementImport() {
        java.lang.Object value = get("elementImport");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<cmof.ElementImport>((cmof.common.ReflectiveCollection)value);
        }
    }

}

