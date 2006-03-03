package core.basic;


public class UmlClassImpl extends hub.sam.mof.reflection.ObjectImpl implements UmlClass
{
    public UmlClassImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public UmlClassImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected UmlClass self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (UmlClass)self;
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

    public boolean isAbstract() {
        java.lang.Object value = get("isAbstract");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsAbstract(boolean value) {
        set("isAbstract", value);
    }

    public cmof.common.ReflectiveCollection<? extends core.basic.UmlClass> getSuperClass() {
        java.lang.Object value = get("superClass");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl<core.basic.UmlClass>((cmof.common.ReflectiveCollection)value);
        }
    }

    public core.basic.Package getPackage() {
        java.lang.Object value = get("package");
        if (value == null) {
           return null;
        } else {
            return (core.basic.Package)value;
        }
    }

    public void setPackage(core.basic.Package value) {
        set("package", value);
    }

    public cmof.common.ReflectiveSequence<? extends core.basic.Operation> getOwnedOperation() {
        java.lang.Object value = get("ownedOperation");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<core.basic.Operation>((cmof.common.ReflectiveSequence)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends core.basic.Property> getOwnedAttribute() {
        java.lang.Object value = get("ownedAttribute");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl<core.basic.Property>((cmof.common.ReflectiveSequence)value);
        }
    }

}

