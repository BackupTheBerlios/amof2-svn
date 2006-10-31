package core.basic;


public class EnumerationDlg extends cmofimpl.reflection.ObjectImpl implements Enumeration
{
    public EnumerationDlg(cmofimpl.instancemodel.ClassInstanceImpl instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public EnumerationDlg(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
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

    public cmof.common.ReflectiveSequence<? extends core.basic.EnumerationLiteral> getOwnedLiteral() {
        java.lang.Object value = get("ownedLiteral");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperListImpl<core.basic.EnumerationLiteral>((cmof.common.ReflectiveSequence)value);
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

