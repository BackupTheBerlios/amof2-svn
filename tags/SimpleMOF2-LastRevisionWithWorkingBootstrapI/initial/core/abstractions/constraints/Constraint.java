package core.abstractions.constraints;


public interface Constraint extends core.abstractions.namespaces.NamedElement
{

    public core.abstractions.namespaces.Namespace getContext();

    public core.abstractions.constraints.Namespace getNamespace();

    public void setNamespace(core.abstractions.constraints.Namespace value);

    public void setNamespace(core.abstractions.namespaces.Namespace value);

    public core.abstractions.expressions.ValueSpecification getSpecification();

    public void setSpecification(core.abstractions.expressions.ValueSpecification value);

    public cmof.common.ReflectiveSequence<? extends core.abstractions.ownerships.Element> getConstrainedElement();

}

