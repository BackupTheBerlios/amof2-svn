package cmofimpl.instancemodel;

import java.util.Collection;
import cmof.*;
import hub.sam.mof.instancemodel.*;

public class MofInstanceModel extends InstanceModel<UmlClass,Property,java.lang.Object> {

    public MofClassInstance createAInstance(String id, UmlClass classifier) {
        return new MofClassInstance(classifier, this);        
    }
    
    public Collection<ReferenceValue<UmlClass,Property,java.lang.Object>> createReferences(String idString) {
        throw new RuntimeException("assert");
    }
    
    public UnspecifiedValue<UmlClass,Property,java.lang.Object> createUnspecifiedValue(Object unspecifiedData) {
        throw new RuntimeException("assert");
    }
        
    public InstanceValue<UmlClass,Property,java.lang.Object> createInstanceValue(ClassInstance<UmlClass,Property,java.lang.Object> instance) {
        return super.createInstanceValue(instance);        
    }
    
    public MofPrimitiveDataValue createPrimitiveValue(java.lang.Object primitiveData) {
        return new MofPrimitiveDataValue(primitiveData);        
    }      
}
