package core.abstractions.expressions;


public class ExpressionDlg extends cmofimpl.reflection.ObjectImpl implements Expression
{
    public ExpressionDlg(cmofimpl.instancemodel.ClassInstanceImpl instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public ExpressionDlg(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public String getSymbol() {
        java.lang.Object value = get("symbol");
        if (value == null) {
           return null;
        } else {
            return (String)value;
        }
    }

    public void setSymbol(String value) {
        set("symbol", value);
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.expressions.ValueSpecification> getOperand() {
        java.lang.Object value = get("operand");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperListImpl<core.abstractions.expressions.ValueSpecification>((cmof.common.ReflectiveSequence)value);
        }
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

