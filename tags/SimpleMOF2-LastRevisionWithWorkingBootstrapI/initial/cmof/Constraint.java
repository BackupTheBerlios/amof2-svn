package cmof;


public interface Constraint extends core.abstractions.constraints.Constraint, cmof.PackageableElement
{

    public cmof.Namespace getContext();

    public cmof.Namespace getNamespace();

    public void setNamespace(cmof.Namespace value);

    public void setNamespace(core.abstractions.namespaces.Namespace value);

    public void setNamespace(core.abstractions.constraints.Namespace value);

    public cmof.common.ReflectiveSequence<? extends cmof.Element> getConstrainedElement();

    public cmof.ValueSpecification getSpecification();

    public void setSpecification(cmof.ValueSpecification value);

    public void setSpecification(core.abstractions.expressions.ValueSpecification value);

}

