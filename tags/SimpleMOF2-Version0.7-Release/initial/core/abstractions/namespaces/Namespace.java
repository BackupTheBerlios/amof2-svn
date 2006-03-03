package core.abstractions.namespaces;


public interface Namespace extends core.abstractions.namespaces.NamedElement
{

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getOwnedMember();

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getMember();

}

