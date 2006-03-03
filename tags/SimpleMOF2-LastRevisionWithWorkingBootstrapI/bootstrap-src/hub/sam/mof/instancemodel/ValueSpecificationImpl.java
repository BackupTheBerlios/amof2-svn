package hub.sam.mof.instancemodel;

public class ValueSpecificationImpl<C,P,DataValue> implements ValueSpecification<C,P,DataValue> {
    public InstanceValue<C,P,DataValue> asInstanceValue() {
        return null;
    }

    public UnspecifiedValue<C,P,DataValue> asUnspecifiedValue() {
        return null;
    }
    
    public PrimitiveDataValue<C,P,DataValue> asDataValue() {
        return null;
    }
}
