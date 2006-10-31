package hub.sam.mof.instancemodel;

public class PrimitiveDataValue<C,P,DataValue> extends ValueSpecificationImpl<C,P,DataValue> {

    private final DataValue value;
    public PrimitiveDataValue(DataValue value) {
        this.value = value;
    }
    
    public String getValueRepresentation() {
        return value.toString();
    }
    
    public DataValue getValue() {
        return value;
    }
    
    public PrimitiveDataValue<C,P,DataValue> asDataValue() {
        return this;
    }
}
