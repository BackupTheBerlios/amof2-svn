package hub.sam.mof.instancemodel;

public interface ValueSpecification<C,P,DataValue> {
    public InstanceValue<C,P,DataValue> asInstanceValue();

    public UnspecifiedValue<C,P,DataValue> asUnspecifiedValue();
    
    public PrimitiveDataValue<C,P,DataValue> asDataValue();
}
