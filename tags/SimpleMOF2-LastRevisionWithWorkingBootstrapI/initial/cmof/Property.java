package cmof;


public interface Property extends cmof.StructuralFeature, core.basic.Property
{

    public boolean isReadOnly();

    public void setIsReadOnly(boolean value);

    public boolean isDerivedUnion();

    public void setIsDerivedUnion(boolean value);

    public cmof.UmlClass getUmlClass();

    public void setUmlClass(cmof.UmlClass value);

    public void setUmlClass(core.basic.UmlClass value);

    public cmof.Association getOwningAssociation();

    public void setOwningAssociation(cmof.Association value);

    public cmof.common.ReflectiveCollection<? extends cmof.Property> getRedefinedProperty();

    public cmof.common.ReflectiveCollection<? extends cmof.Property> getSubsettedProperty();

    public cmof.Property getOpposite();

    public void setOpposite(cmof.Property value);

    public void setOpposite(core.basic.Property value);

    public cmof.DataType getDatatype();

    public void setDatatype(cmof.DataType value);

    public cmof.Association getAssociation();

    public void setAssociation(cmof.Association value);

}

