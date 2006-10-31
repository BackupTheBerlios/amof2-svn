package core.abstractions.constraints;


public interface Namespace extends core.abstractions.namespaces.Namespace
{

    public cmof.common.ReflectiveCollection<? extends core.abstractions.constraints.Constraint> getOwnedRule();

}

