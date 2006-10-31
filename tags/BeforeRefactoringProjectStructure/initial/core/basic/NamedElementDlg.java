package core.basic;


public class NamedElementDlg extends hub.sam.mof.reflection.ObjectImpl implements NamedElement
{
    public NamedElementDlg(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public NamedElementDlg(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected NamedElement self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (NamedElement)self;
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

