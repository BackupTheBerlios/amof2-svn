package core.abstractions.instances;


public interface InstanceSpecification extends core.abstractions.namespaces.NamedElement
{

    public cmof.common.ReflectiveCollection<? extends core.abstractions.instances.Slot> getSlot();

    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Classifier> getClassifier();

    public core.abstractions.expressions.ValueSpecification getSpecification();

    public void setSpecification(core.abstractions.expressions.ValueSpecification value);

}

