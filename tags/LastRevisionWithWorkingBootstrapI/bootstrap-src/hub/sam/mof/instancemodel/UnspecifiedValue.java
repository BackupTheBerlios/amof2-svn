package hub.sam.mof.instancemodel;

public class UnspecifiedValue<C,P,DataValue> extends ValueSpecificationImpl<C,P,DataValue> {

    private final Object value;
    protected UnspecifiedValue(Object value) {
        this.value = value;
    }
        
    public Object getUnspecifiedData() {
        return value;
    }

    public UnspecifiedValue<C,P,DataValue> asUnspecifiedValue() {
        return this;
    }        
}
