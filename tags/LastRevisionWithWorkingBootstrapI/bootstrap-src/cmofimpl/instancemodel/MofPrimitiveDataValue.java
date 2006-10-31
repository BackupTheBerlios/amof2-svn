package cmofimpl.instancemodel;

import cmof.*;
import hub.sam.mof.instancemodel.ValueSpecification;

public class MofPrimitiveDataValue extends hub.sam.mof.instancemodel.PrimitiveDataValue<UmlClass,Property,java.lang.Object> implements ValueSpecification<UmlClass,Property,java.lang.Object> {
          
    private final java.lang.Object value;

    protected MofPrimitiveDataValue(java.lang.Object value) {
        super(value);
        this.value = value;
    }  
          
    public DataType getClassifier() {
        return null;
    }
    
    public String getValueRepresentation() {
        return getValue().toString();
    }
    
    public java.lang.Object getValue() {
        return value;
    }
               
    public int hashCode() {
        return value.hashCode();
    }
    public boolean equals(Object theOther) {
        if (theOther instanceof MofPrimitiveDataValue) {
            return getValue().equals(((MofPrimitiveDataValue)theOther).value);
        } else {
            return false;
        }
    }  
}
