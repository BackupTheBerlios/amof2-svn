package core.basic;


public class PackageDlg extends hub.sam.mof.reflection.ObjectImpl implements Package
{
    public PackageDlg(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public PackageDlg(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected Package self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (Package)self;
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

    public core.basic.Package getNestingPackage() {
        java.lang.Object value = get("nestingPackage");
        if (value == null) {
           return null;
        } else {
            return (core.basic.Package)value;
        }
    }

    public void setNestingPackage(core.basic.Package value) {
        set("nestingPackage", value);
    }

    public cmof.common.ReflectiveCollection<? extends core.basic.Package> getNestedPackage() {
        java.lang.Object value = get("nestedPackage");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.basic.Package>((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.basic.Type> getOwnedType() {
        java.lang.Object value = get("ownedType");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.basic.Type>((cmof.common.ReflectiveCollection)value);
        }
    }

}

