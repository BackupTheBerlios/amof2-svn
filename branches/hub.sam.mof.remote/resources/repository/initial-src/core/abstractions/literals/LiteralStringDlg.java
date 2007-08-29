package core.abstractions.literals;


public class LiteralStringDlg extends hub.sam.mof.reflection.ObjectDlg implements LiteralString
{
    protected LiteralString self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (LiteralString)self;
    }

    public java.lang.String getValue() {
        return self.getValue();
    }

    public void setValue(java.lang.String value) {
        self.setValue(value);
    }

    public boolean isComputable()  {
        return self.isComputable();
    }

    public java.lang.String stringValue()  {
        return self.stringValue();
    }

    public int integerValue()  {
        return self.integerValue();
    }

    public boolean booleanValue()  {
        return self.booleanValue();
    }

    public long unlimitedValue()  {
        return self.unlimitedValue();
    }

    public boolean isNull()  {
        return self.isNull();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.getOwnedElement();
    }

    public core.abstractions.ownerships.Element getOwner() {
        return (core.abstractions.ownerships.Element)(java.lang.Object)self.getOwner();
    }

    public void setOwner(core.abstractions.ownerships.Element value) {
        self.setOwner(value);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.allOwnedElements();
    }

    public boolean mustBeOwned()  {
        return self.mustBeOwned();
    }

}

