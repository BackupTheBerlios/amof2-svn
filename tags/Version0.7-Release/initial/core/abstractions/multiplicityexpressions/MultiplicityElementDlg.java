package core.abstractions.multiplicityexpressions;


public class MultiplicityElementDlg extends cmofimpl.reflection.ObjectImpl implements MultiplicityElement
{
    public MultiplicityElementDlg(cmofimpl.instancemodel.ClassInstanceImpl instance, cmofimpl.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public MultiplicityElementDlg(cmofimpl.reflection.Identifier id, cmofimpl.reflection.Identifier metaId, String implementationClassName, String[] delegateClassNames) {
        super(id, metaId, implementationClassName, delegateClassNames);
    }

    public boolean isUnique() {
        java.lang.Object value = get("isUnique");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsUnique(boolean value) {
        set("isUnique", value);
    }

    public boolean isOrdered() {
        java.lang.Object value = get("isOrdered");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Boolean)value;
        }
    }

    public void setIsOrdered(boolean value) {
        set("isOrdered", value);
    }

    public int getLower() {
        java.lang.Object value = get("lower");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Integer)value;
        }
    }

    public void setLower(int value) {
        set("lower", value);
    }

    public core.abstractions.expressions.ValueSpecification getLowerValue() {
        java.lang.Object value = get("lowerValue");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.expressions.ValueSpecification)value;
        }
    }

    public void setLowerValue(core.abstractions.expressions.ValueSpecification value) {
        set("lowerValue", value);
    }

    public long getUpper() {
        java.lang.Object value = get("upper");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (Long)value;
        }
    }

    public void setUpper(long value) {
        set("upper", value);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new cmofimpl.util.TypeWrapperSetImpl<core.abstractions.ownerships.Element>((cmof.common.ReflectiveCollection)value);
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

    public core.abstractions.expressions.ValueSpecification getUpperValue() {
        java.lang.Object value = get("upperValue");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.expressions.ValueSpecification)value;
        }
    }

    public void setUpperValue(core.abstractions.expressions.ValueSpecification value) {
        set("upperValue", value);
    }

}

