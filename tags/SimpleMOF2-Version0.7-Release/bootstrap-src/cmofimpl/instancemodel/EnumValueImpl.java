package cmofimpl.instancemodel;

public class EnumValueImpl extends DataValueImpl {
    
    public EnumValueImpl(Enum value) {
        super(value);        
    }
    
    public Enum getEnum() {
        return (Enum)getValue();
    }
}
