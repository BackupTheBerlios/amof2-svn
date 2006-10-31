package core.abstractions.namespaces;


public interface NamedElement extends core.abstractions.ownerships.Element
{

    public String getName();

    public void setName(String value);

    public String getQualifiedName();

    public core.abstractions.namespaces.Namespace getNamespace();

}

