package cmofimpl.instancemodel;

public interface PrimitiveDataValue extends DataValue {
    
    public String getValueRepresentation();
    
    public cmof.common.ReflectiveSequence<? extends cmof.PrimitiveType> getClassifier();

}
