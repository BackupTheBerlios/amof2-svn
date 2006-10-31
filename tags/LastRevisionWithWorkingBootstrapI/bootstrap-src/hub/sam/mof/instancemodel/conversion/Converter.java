package hub.sam.mof.instancemodel.conversion;

import hub.sam.mof.instancemodel.*;
import hub.sam.util.*;
import java.util.*;

public class Converter <Co,Po,DataValueo,Ci,Pi,T,D,DataValuei> {
    
    private final Conversion<Co,Po,DataValueo,Ci,Pi,T,D,DataValuei> conversion;
    private InstanceModel<Ci,Pi,DataValuei> targetModel;
    private InstanceModel<Co,Po,DataValueo> sourceModel;    
    private AbstractFluxBox<String, ClassInstance<Ci,Pi,DataValuei>, Ci> fluxBox = 
        new AbstractFluxBox<String, ClassInstance<Ci,Pi,DataValuei>, Ci>() {
            protected ClassInstance<Ci,Pi,DataValuei> createValue(String key, Ci params) {
                return targetModel.createInstance(key, params);
            }
    };
    
    public Converter(Conversion<Co,Po,DataValueo,Ci,Pi,T,D,DataValuei> conversion) {
        this.conversion = conversion;
    }
    
    public void convert(InstanceModel<Co,Po, DataValueo> model, InstanceModel<Ci,Pi,DataValuei> targetModel) 
            throws MetaModelException {        
        this.targetModel = targetModel;
        this.sourceModel = model;
        
        for(ClassInstance<Co,Po,DataValueo> instance: model.getOutermostComposites()) {
            convertInstance(instance);            
        }        
    }
    
    private Map<ClassInstance<Co,Po,DataValueo>, String> ids = new HashMap<ClassInstance<Co,Po,DataValueo>,String>();
    private static int unique = 1;
    public String getId(ClassInstance<Co,Po,DataValueo> instance) {
        String id = instance.getId();
        if (id == null) {            
            id = ids.get(instance);
            if (id == null) {
                id = new Integer((hashCode() % (Integer.MAX_VALUE / 2)) + unique++).toString();
                ids.put(instance, id);
            }
        }
        return id;
    }
    
    public AbstractFluxBox<String, ClassInstance<Ci,Pi,DataValuei>, Ci> getFluxBox() {
        return fluxBox;
    }        
    
    private Collection<ClassInstance<Co,Po,DataValueo>> alreadyConverted = new HashSet<ClassInstance<Co,Po,DataValueo>>();
    
    public ClassInstance<Ci,Pi,DataValuei> convertInstance(ClassInstance<Co,Po,DataValueo> instance) 
            throws MetaModelException {        
        Ci classifieri = conversion.getClassifier(instance.getClassifier()); 
        ClassInstance<Ci,Pi,DataValuei> targetInstance = getFluxBox().getObject(getId(instance), classifieri);
        if (targetInstance.getClassifier() == null) {
            targetInstance.setClassifier(classifieri);
        }
        
        if (alreadyConverted.contains(instance)) {
            return targetInstance;
        } else {
            alreadyConverted.add(instance);
        }
        
        for(StructureSlot<Co,Po,DataValueo> slot: instance.getSlots()) {
            for(ValueSpecificationImpl<Co,Po,DataValueo> value: slot.getValues()) {
               convertValue(value, slot, instance, targetInstance);                                
            }
        }

        if (instance.getComposite() != null) {
            targetInstance.setComposite(convertInstance(instance.getComposite()));
        }        
        return targetInstance;
    }
    
    private void convertValue(ValueSpecificationImpl<Co,Po,DataValueo> value, StructureSlot<Co,Po,DataValueo> slot, 
            ClassInstance<Co,Po,DataValueo> instance, ClassInstance<Ci,Pi,DataValuei> targetInstance) 
            throws MetaModelException {
        ValueSpecificationImpl<Ci,Pi,DataValuei> targetValue = null;        
        if (value.asInstanceValue() != null) {            
            targetValue = targetModel.createInstanceValue(convertInstance(value.asInstanceValue().getInstance()));                        
        } else if (value.asDataValue() != null) { 
            T type = conversion.getPropertyType(conversion.getProperty(
                    slot.getProperty(),conversion.getClassifier(instance.getClassifier())));
            D dataType = conversion.asDataType(type);
            if (dataType == null) {
                throw new MetaModelException("Type \"" + type + "\" is expected to be a data type, but it is not.");
            }
            targetValue = targetModel.createPrimitiveValue(
                    conversion.createFromString(dataType, value.asDataValue().getValue()));
        } else if (value.asUnspecifiedValue() != null) {
            T type = conversion.getPropertyType(conversion.getProperty(
                    slot.getProperty(),conversion.getClassifier(instance.getClassifier())));
            D dataType = conversion.asDataType(type);
            if (dataType == null) {
                // it must be a reference
                for (ValueSpecificationImpl<Co,Po,DataValueo> aValue: 
                        sourceModel.createReferences((String)value.asUnspecifiedValue().getUnspecifiedData())) {
                    value = aValue; 
                    convertValue(value, slot, instance, targetInstance);
                }
                return;
            } else {
                // it is a DataValue
                value = sourceModel.createPrimitiveValue((DataValueo)value.asUnspecifiedValue().getUnspecifiedData());
                convertValue(value, slot, instance, targetInstance);
                return;
            }                
        } else {
            throw new RuntimeException("unreachable");
        }                    
        targetInstance.addValue(conversion.getProperty(slot.getProperty(), targetInstance.getClassifier()), targetValue);        
    }
    

}
