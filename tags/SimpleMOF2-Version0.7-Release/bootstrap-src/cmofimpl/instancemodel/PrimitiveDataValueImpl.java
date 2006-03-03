package cmofimpl.instancemodel;

import cmof.PrimitiveType;

public class PrimitiveDataValueImpl extends DataValueImpl implements PrimitiveDataValue {
    
    public enum PrimitiveKind { INT, UNLIMITEDNUMBER, STRING, BOOLEAN }          
    private final PrimitiveKind kind;
    
	public PrimitiveDataValueImpl(Integer integer) {
		super(integer);        
        kind = PrimitiveKind.INT;        
	}
	
    public PrimitiveDataValueImpl(Long unlimitedNumber) {
        super(unlimitedNumber);        
        kind = PrimitiveKind.UNLIMITEDNUMBER;        
    }
    
    public PrimitiveDataValueImpl(String string) {
        super(string);        
        kind = PrimitiveKind.STRING;
    }
    
    public PrimitiveDataValueImpl(Boolean bool) {
        super(bool);        
        kind = PrimitiveKind.BOOLEAN;        
    }
    
    public cmof.common.ReflectiveSequence<? extends PrimitiveType> getClassifier() {
        return new cmofimpl.util.ListImpl<PrimitiveType>();
    }
    
    public String getValueRepresentation() {
        return getValue().toString();
    } 
}
