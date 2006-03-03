package core.basic;


public class PackageImpl extends cmofimpl.reflection.ObjectImpl implements Package
{
    public PackageImpl(cmofimpl.instancemodel.ClassInstanceImpl instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public PackageImpl(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public cmof.common.ReflectiveCollection<? extends core.basic.Package> getNestedPackage() {
        java.lang.Object value = get("nestedPackage");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<core.basic.Package>((cmof.common.ReflectiveCollection)value);
        }
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

    public cmof.common.ReflectiveCollection<? extends core.basic.Type> getOwnedType() {
        java.lang.Object value = get("ownedType");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<core.basic.Type>((cmof.common.ReflectiveCollection)value);
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

}

