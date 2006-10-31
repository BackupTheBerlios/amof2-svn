package core.basic;


public interface UmlClass extends core.basic.Type
{

    public boolean isAbstract();

    public void setIsAbstract(boolean value);

    public cmof.common.ReflectiveSequence<? extends core.basic.Property> getOwnedAttribute();

    public cmof.common.ReflectiveSequence<? extends core.basic.Operation> getOwnedOperation();

    public cmof.common.ReflectiveCollection<? extends core.basic.UmlClass> getSuperClass();

}

