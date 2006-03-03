package hub.sam.mof.bootstrap;

import java.util.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.xmi.*;

public class InstantiatedXmiSemantics extends CommonClassifierSemantics<ClassInstance<XmiClassifier,String,String>, ClassInstance<XmiClassifier,String,String>,String> {
    
    private final InstanceModel<XmiClassifier,String,String> model;
    
    protected InstantiatedXmiSemantics(ClassInstance<XmiClassifier,String,String> classifier, InstanceModel<XmiClassifier,String,String> model) {
        super(classifier);
        this.model = model;
        initialize();        
    }
    
    public String getName(ClassInstance<XmiClassifier, String, String> forProperty) {
        return InstanciatedXmiModel.get(forProperty,"name");
    }

    public boolean isCollectionProperty(ClassInstance<XmiClassifier,String,String> forProperty) {
        return false;
    }
    
    private Collection<ClassInstance<XmiClassifier,String,String>> get(ClassInstance<XmiClassifier,String,String> instance, String property) {
        StructureSlot<XmiClassifier,String,String> slot = instance.get(property);
        if (slot == null) {
            return new Vector<ClassInstance<XmiClassifier,String,String>>(0);
        }
        List<ValueSpecificationImpl<XmiClassifier,String,String>> values = instance.get(property).getValues();        
        Collection<ClassInstance<XmiClassifier,String,String>> result = new Vector<ClassInstance<XmiClassifier,String,String>>(values.size());
        for (ValueSpecification<XmiClassifier,String,String> value: values) {
            if (value.asInstanceValue() == null) {
                String[] valueIds = value.asUnspecifiedValue().getUnspecifiedData().toString().split(" ");
                for (String valueId: valueIds) {
                    result.add(model.getInstance(valueId.trim()));
                }
            } else {
                result.add(value.asInstanceValue().getInstance());
            }
        }
        return result;
    }
    
    protected Collection<? extends ClassInstance<XmiClassifier, String, String>> ownedProperties(ClassInstance<XmiClassifier, String, String> c) {
        return get(c,"ownedAttribute");
    }

    protected Collection<? extends ClassInstance<XmiClassifier, String, String>> redefinedProperties(ClassInstance<XmiClassifier, String, String> p) {        
        return get(p,"redefinedProperty");
    }

    protected Collection<? extends ClassInstance<XmiClassifier, String, String>> subsettedProperties(ClassInstance<XmiClassifier, String, String> p) {
        return new Vector<ClassInstance<XmiClassifier,String,String>>(0);
    }
    
    protected Collection<? extends ClassInstance<XmiClassifier, String, String>> superClasses(ClassInstance<XmiClassifier, String, String> c) {
        return get(c,"superClass");
    }  
}
