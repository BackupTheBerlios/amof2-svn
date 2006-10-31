package core.basic;


public class EnumerationLiteralImpl extends cmofimpl.reflection.ObjectImpl implements EnumerationLiteral
{
    public EnumerationLiteralImpl(cmofimpl.instancemodel.ClassInstanceImpl instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public EnumerationLiteralImpl(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
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

