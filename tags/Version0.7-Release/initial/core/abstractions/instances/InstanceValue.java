package core.abstractions.instances;


public interface InstanceValue extends core.abstractions.expressions.ValueSpecification
{

    public core.abstractions.instances.InstanceSpecification getInstance();

    public void setInstance(core.abstractions.instances.InstanceSpecification value);

}

