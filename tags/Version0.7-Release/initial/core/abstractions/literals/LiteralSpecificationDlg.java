package core.abstractions.literals;


public class LiteralSpecificationDlg extends cmofimpl.reflection.ObjectImpl implements LiteralSpecification
{
    public LiteralSpecificationDlg(cmofimpl.instancemodel.ClassInstanceImpl instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public LiteralSpecificationDlg(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public core.abstractions.ownerships.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.ownerships.Element)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
        }
    }

}

