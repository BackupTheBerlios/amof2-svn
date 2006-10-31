package hub.sam.mof.instancemodel;

import java.util.*;

public class StructureSlot<C,P,DataValue> {
    private List<ValueSpecificationImpl<C,P,DataValue>> values = new Vector<ValueSpecificationImpl<C,P,DataValue>>();
    private final P property;
    
    protected StructureSlot(P feature) {
        this.property = feature;
    }
    
    public void addValue(ValueSpecificationImpl<C,P,DataValue> value) {
        values.add(value);
    }
    
    public List<ValueSpecificationImpl<C,P,DataValue>> getValues() {
        return values;
    }
    
    public ValueSpecificationList<C,P,DataValue> getValuesAsList() {
        return null;
    }
    
    public P getProperty() {
        return this.property;
    }
}
