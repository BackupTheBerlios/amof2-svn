package core.abstractions.umlsuper;


public interface Classifier extends core.abstractions.classifiers.Classifier
{

    public boolean isAbstract();

    public void setIsAbstract(boolean value);

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getInheritedMember();

    public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> getGeneral();

}

