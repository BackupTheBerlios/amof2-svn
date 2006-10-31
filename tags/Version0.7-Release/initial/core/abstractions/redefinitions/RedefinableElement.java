package core.abstractions.redefinitions;


public interface RedefinableElement extends core.abstractions.namespaces.NamedElement
{

    public cmof.common.ReflectiveCollection<? extends core.abstractions.redefinitions.RedefinableElement> getRedefinedElement();

    public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> getRedefinitionContext();

}

