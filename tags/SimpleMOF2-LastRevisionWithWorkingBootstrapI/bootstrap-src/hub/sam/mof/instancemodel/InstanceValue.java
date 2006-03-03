package hub.sam.mof.instancemodel;

public class InstanceValue<C,P,DataValue> extends ValueSpecificationImpl<C,P,DataValue> {
    
    private final ClassInstance<C,P,DataValue> instance;
    
    public int hashCode() {
        if (instance != null) {
            return instance.hashCode();
        } else {
            return super.hashCode();
        }
    }
    
    public boolean equals(Object theOther) {
        if (theOther instanceof InstanceValue) {
            if (instance != null) {
                return instance.equals(((InstanceValue)theOther).getInstance());
            } else {
                return super.equals(theOther);
            }
        } else {
            return false;
        }
    }
    
    public InstanceValue(ClassInstance<C,P,DataValue> instance) {         
        this.instance = instance;
    }
    
    public ClassInstance<C,P,DataValue> getInstance() {
        return instance;
    } 
    
    public InstanceValue<C,P,DataValue> asInstanceValue() {
        return this;
    }

    public String toString() {
        return instance.toString();
    }
}
