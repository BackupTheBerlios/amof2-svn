package core.abstractions.elements;


public class ElementDlg extends hub.sam.mof.reflection.ObjectImpl implements Element
{
    public ElementDlg(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public ElementDlg(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }
    protected Element self = null;
    protected void setSelf(hub.sam.mof.reflection.ObjectImpl self) {
        this.self = (Element)self;
    }

}

