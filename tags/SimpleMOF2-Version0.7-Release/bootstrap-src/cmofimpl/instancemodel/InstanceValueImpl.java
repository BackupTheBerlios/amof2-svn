package cmofimpl.instancemodel;

import cmof.common.ReflectiveCollection;
import core.abstractions.instances.*;
import core.abstractions.ownerships.*;

public class InstanceValueImpl implements InstanceValue {

    private final InstanceSpecification theInstance;
    
    public InstanceValueImpl(InstanceSpecification theInstance) {
        this.theInstance = theInstance;
    }
    
    public InstanceSpecification getInstance() {
        return theInstance;
    }

    public void setInstance(InstanceSpecification value) {        
        throw new RuntimeException("Assert");
    }

    public ReflectiveCollection<Element> getOwnedElement() {        
        throw new RuntimeException("Assert");
    }

    public Element getOwner() {
        throw new RuntimeException("Assert");
    }
    public int hashCode() {
        return theInstance.hashCode();
    }
    public boolean equals(Object theOther) {
        if (theOther instanceof InstanceValue) {
            return theInstance.equals(((InstanceValue)theOther).getInstance());
        } else {
            return false;
        }
    }
    public String toString() {
        return theInstance.toString();
    }

    // unused
    public Element allOwnedElements() {
        return null;
    }

    public boolean mustBeOwned() {
        return false;
    }

    public boolean isComputable() {
        return false;
    }

    public int integerValue() {
        return 0;
    }

    public boolean booleanValue() {
        return false;
    }

    public String stringValue() {
        return null;
    }

    public long unlimitedValue() {
        return 0;
    }

    public boolean isNull() {
        return false;
    }
}
