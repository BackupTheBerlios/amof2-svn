package hub.sam.mof.bootstrap;

import java.util.*;
import cmofimpl.javamapping.*;
import hub.sam.mof.instancemodel.*;

public class BootstrapSemantics extends CommonClassifierSemantics<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,String> 
        implements ClassifierSemantics<ClassInstance<ClassInstance,ClassInstance,Object>,String> {
    
    private final BootstrapModel model;
    private final JavaMapping mapping = new JavaMapping();
        
    public BootstrapSemantics(ClassInstance<ClassInstance,ClassInstance,Object> classifier, BootstrapModel model) {        
        super(classifier);                
        this.model = model;
        initialize();
    }
        
    public String getName(ClassInstance<ClassInstance, ClassInstance, Object> forProperty) {
        StructureSlot<ClassInstance,ClassInstance,Object> slot = forProperty.get(model.getPropertyNames().get("cmof.NamedElement.name"));
        if (slot == null) {
            throw new NullPointerException();            
        }
        return slot.getValues().get(0).asDataValue().getValue().toString();
    }

    public boolean isCollectionProperty(ClassInstance<ClassInstance, ClassInstance, Object> forProperty) {
        StructureSlot<ClassInstance,ClassInstance,Object> slot = forProperty.get(model.getPropertyNames().get("core.abstractions.multiplicities.MultiplicityElement.upper"));
        if (slot == null) {
            return false;
        } else {
            return new Integer(slot.getValues().get(0).asDataValue().getValue().toString()).intValue() != 1;
        }        
    }

    public String getJavaGetMethodNameForProperty(ClassInstance<ClassInstance, ClassInstance, Object> forProperty) {
        String name = (String)model.get(forProperty, "name");
        ClassInstance type = (ClassInstance)model.get(forProperty, "type");
        if (model.get((ClassInstance)type.getClassifier(), "name").equals(cmof.PrimitiveType.class.getSimpleName())) {            
            if (model.get(type,"name").equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                return mapping.getJavaGetMethodNameForProperty(name, true);        
            }                             
        }
        return mapping.getJavaGetMethodNameForProperty(name, false);
    }
    
    private Collection<ClassInstance<ClassInstance,ClassInstance,Object>> get(ClassInstance<ClassInstance,ClassInstance,Object> instance, String property) {
        StructureSlot<ClassInstance,ClassInstance,Object> slot = instance.get(model.getPropertyNames().get(property));
        if (slot == null) {
            return new Vector<ClassInstance<ClassInstance,ClassInstance,Object>>(0);
        }
        List<ValueSpecificationImpl<ClassInstance,ClassInstance,Object>> values = slot.getValues();
        Collection<ClassInstance<ClassInstance,ClassInstance,Object>> result = new Vector<ClassInstance<ClassInstance,ClassInstance,Object>>(values.size());
        for (ValueSpecificationImpl<ClassInstance,ClassInstance,Object> value: values) {
            result.add(value.asInstanceValue().getInstance());
        }
        return result;    
    }
    
    protected Collection<? extends ClassInstance<ClassInstance, ClassInstance, Object>> ownedProperties(ClassInstance<ClassInstance, ClassInstance, Object> c) {
        return get(c, "cmof.Class.ownedAttribute");    
    }

    protected Collection<? extends ClassInstance<ClassInstance, ClassInstance, Object>> redefinedProperties(ClassInstance<ClassInstance, ClassInstance, Object> p) {
        return get(p, "cmof.Property.redefinedProperty");
    }
    
    protected Collection<? extends ClassInstance<ClassInstance, ClassInstance, Object>> subsettedProperties(ClassInstance<ClassInstance, ClassInstance, Object> p) {
        return get(p, "cmof.Property.subsettedProperty");
    }
    
    protected Collection<? extends ClassInstance<ClassInstance, ClassInstance, Object>> superClasses(ClassInstance<ClassInstance, ClassInstance, Object> c) {        
        return get(c, "cmof.Class.superClass");
    }
}
