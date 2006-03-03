package core.abstractions.instances;


public interface Slot extends core.abstractions.ownerships.Element
{

    public core.abstractions.instances.InstanceSpecification getOwningInstance();

    public void setOwningInstance(core.abstractions.instances.InstanceSpecification value);

    public cmof.common.ReflectiveSequence<? extends core.abstractions.expressions.ValueSpecification> getValue();

    public core.abstractions.structuralfeatures.StructuralFeature getDefiningFeature();

    public void setDefiningFeature(core.abstractions.structuralfeatures.StructuralFeature value);

}

