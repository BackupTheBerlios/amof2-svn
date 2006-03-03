package core.abstractions.changeabilities;


public interface StructuralFeature extends core.abstractions.structuralfeatures.StructuralFeature
{

    public boolean isReadOnly();

    public void setIsReadOnly(boolean value);

}

