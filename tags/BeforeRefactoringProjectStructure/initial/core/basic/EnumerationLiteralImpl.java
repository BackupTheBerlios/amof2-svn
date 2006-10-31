package core.basic;


public class EnumerationLiteralImpl extends hub.sam.mof.reflection.ObjectImpl implements EnumerationLiteral
{
    public EnumerationLiteralImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public EnumerationLiteralImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected EnumerationLiteral self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (EnumerationLiteral)self;
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

    public core.basic.Enumeration getEnumeration() {
        java.lang.Object value = get("enumeration");
        if (value == null) {
           return null;
        } else {
            return (core.basic.Enumeration)value;
        }
    }

    public void setEnumeration(core.basic.Enumeration value) {
        set("enumeration", value);
    }

}

