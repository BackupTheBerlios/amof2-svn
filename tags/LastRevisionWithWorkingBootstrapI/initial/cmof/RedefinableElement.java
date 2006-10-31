package cmof;


public interface RedefinableElement extends core.abstractions.redefinitions.RedefinableElement, cmof.NamedElement
{

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getRedefinitionContext();

    public cmof.common.ReflectiveCollection<? extends cmof.RedefinableElement> getRedefinedElement();

}

