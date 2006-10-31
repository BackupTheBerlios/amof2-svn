package cmof;


public interface UmlClass extends cmof.Classifier, core.basic.UmlClass
{

    public boolean isAbstract();

    public void setIsAbstract(boolean value);

    public cmof.common.ReflectiveSequence<? extends cmof.Property> getOwnedAttribute();

    public cmof.common.ReflectiveSequence<? extends cmof.Operation> getOwnedOperation();

    public cmof.common.ReflectiveCollection<? extends cmof.UmlClass> getSuperClass();

}

