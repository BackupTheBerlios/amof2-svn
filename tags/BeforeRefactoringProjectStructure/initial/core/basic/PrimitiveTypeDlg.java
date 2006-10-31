package core.basic;


public class PrimitiveTypeDlg extends hub.sam.mof.reflection.ObjectImpl implements PrimitiveType
{
    public PrimitiveTypeDlg(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public PrimitiveTypeDlg(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected PrimitiveType self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (PrimitiveType)self;
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

}

