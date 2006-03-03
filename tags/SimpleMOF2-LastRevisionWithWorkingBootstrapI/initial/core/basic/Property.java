package core.basic;


public interface Property extends core.basic.TypedElement, core.abstractions.multiplicities.MultiplicityElement
{

    public boolean isReadOnly();

    public void setIsReadOnly(boolean value);

    public String getDefault();

    public void setDefault(String value);

    public boolean isComposite();

    public void setIsComposite(boolean value);

    public boolean isDerived();

    public void setIsDerived(boolean value);

    public core.basic.UmlClass getUmlClass();

    public void setUmlClass(core.basic.UmlClass value);

    public core.basic.Property getOpposite();

    public void setOpposite(core.basic.Property value);

}

