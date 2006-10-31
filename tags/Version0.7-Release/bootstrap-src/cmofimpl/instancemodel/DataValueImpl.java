package cmofimpl.instancemodel;

import cmof.DataType;
import cmof.common.ReflectiveSequence;
import cmof.common.ReflectiveCollection;
import cmofimpl.reflection.ExtentImpl;
import core.abstractions.structuralfeatures.StructuralFeature;

/*
 * Support for datavalues other then primitive or enum values is not implemented yet. 
 */
public abstract class DataValueImpl extends ElementInstanceImpl implements DataValue {
    private final java.lang.Object value;
    private final boolean isPrimitive;
    
    public DataValueImpl(ExtentImpl extent, DataType classifier) {        
        super(extent, InstanceClassifierImpl.createDataValueTypeForDataType(classifier));
        this.value = null;
        isPrimitive = false;
    }
    
    public DataValueImpl(java.lang.Object value) {
        super(null, null);
        this.value = value;
        isPrimitive = true;
    }
    
    protected void initialize() {
        if (!isPrimitive) {
            super.initialize();
        }
    }
    
    public ReflectiveSequence<? extends DataType> getClassifier() {
        return (ReflectiveSequence<? extends DataType>)super.getClassifier();
    }
    
    public InstanceClassImpl getInstanceClassifier() {
        return (InstanceClassImpl)super.getInstanceClassifier();        
    } 
    
    public ClassInstanceImpl clone() {
        return (ClassInstanceImpl)super.clone();
    }
           
    public java.lang.Object getValue() {
        return value;
    }
	
	public ReflectiveCollection<StructureSlot> getSlot() {
        if (isPrimitive) {
            throw new IllegalArgumentException("not allowed for primitive values");
        } else {
            return super.getSlot();
        }
	}
        
    public int hashCode() {
        return value.hashCode();
    }
    public boolean equals(Object theOther) {
        if (theOther instanceof DataValueImpl) {
            return getValue().equals(((DataValueImpl)theOther).value);
        } else {
            return false;
        }
    }

    public StructureSlot getSlot(StructuralFeature definingFeature) {
        if (isPrimitive) {
            return null;
        } else {
            return super.getSlot(definingFeature);
        }
    }
}
