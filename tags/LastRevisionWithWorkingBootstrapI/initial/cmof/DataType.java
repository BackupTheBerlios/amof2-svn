package cmof;


public interface DataType extends core.basic.DataType, cmof.Classifier
{

    public cmof.common.ReflectiveSequence<? extends cmof.Property> getOwnedAttribute();

    public cmof.common.ReflectiveSequence<? extends cmof.Operation> getOwnedOperation();

}

