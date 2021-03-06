package hub.sam.mof.bootstrap;

import java.util.*;

import cmof.*;
import cmofimpl.reflection.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.instancemodel.ValueSpecification;

public class BootstrapModel extends InstanceModel<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> {

    private BootstrapExtent extent = null;
    private InstanceModel<UmlClass,Property,Object> mofModel = new InstanceModel<UmlClass,Property,Object>();    
    private Map<ClassInstance,ClassInstanceWrapper> wrapper = new HashMap<ClassInstance,ClassInstanceWrapper>();
    private Map<ClassInstance,ClassifierSemanticsWrapper> semanticsWrapper = new HashMap<ClassInstance,ClassifierSemanticsWrapper>();
    private Map<String, ClassInstance<ClassInstance,ClassInstance,Object>> propertyNames = null;
    
    public ClassInstance<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> createAInstance(String id, ClassInstance<ClassInstance,ClassInstance,Object> classifier) {
        return new BootstrapInstance(id, classifier, this);
    }
    
    public ClassInstanceWrapper getWrapper(ClassInstance instance) {
        if (instance == null) {
            return null;
        }
        ClassInstanceWrapper result = wrapper.get(instance);
        if (result == null) {
            result = new ClassInstanceWrapper(instance, this, extent);
            wrapper.put(instance,result);
        }
        return result;
    }
    
    protected void setExtent(BootstrapExtent extent) {
        this.extent = extent;
    }
    
    private Map<ClassInstance, BootstrapSemantics> semantics = new HashMap<ClassInstance,BootstrapSemantics>();
    protected BootstrapSemantics createBootstrapSemantics(ClassInstance<ClassInstance,ClassInstance,Object> classifier) {
        BootstrapSemantics result = semantics.get(classifier);
        if (result == null) {
            result = new BootstrapSemantics(classifier, this);
            semantics.put(classifier, result);
        }
        return result;
    }
    
    protected void setPropertyNames(Map<String, ClassInstance<ClassInstance,ClassInstance,Object>> propertyNames) {
        this.propertyNames = propertyNames;
    }
    
    protected Map<String, ClassInstance<ClassInstance,ClassInstance,Object>> getPropertyNames() {
        return propertyNames;
    }
    
    public ClassifierSemanticsWrapper getSemanticsWrapper(ClassInstance classifier) {        
        ClassifierSemanticsWrapper result = semanticsWrapper.get(classifier);
        if (result == null) {
            result = new ClassifierSemanticsWrapper(createBootstrapSemantics(classifier), this);
            semanticsWrapper.put(classifier, result);            
        }
        return result;
    }         
    
    protected InstanceModel<UmlClass,Property,Object> getMofModel() {
        return mofModel;
    }
    
    protected ClassInstance<ClassInstance,ClassInstance,Object> getBootstrapElement(cmof.Element forObject) {
        return ((ClassInstanceWrapper)((ObjectImpl)forObject).getClassInstance()).getInstance();
    }
    
    protected ObjectImpl getObjectImpl(ClassInstance<ClassInstance,ClassInstance,Object> forInstance) {
        return extent.getObjectForInstance(getWrapper(forInstance));
    }

    protected Object get(ClassInstance<ClassInstance,ClassInstance,Object> instance, String property) {
        Object result = null;
        try {
            BootstrapSemantics semantics = createBootstrapSemantics(instance.getClassifier());
            ValueSpecificationImpl<ClassInstance,ClassInstance,Object> value =
                    instance.get(semantics.getProperty(property)).getValues().get(0);
            if (value.asDataValue() == null) {
                return value.asInstanceValue().getInstance();
            } else {
                return value.asDataValue().getValue();
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    protected List<ValueSpecificationImpl<ClassInstance,ClassInstance,Object>> getList(ClassInstance<ClassInstance,ClassInstance,Object> instance, String property) {
        Object result = null;
        try {
            BootstrapSemantics semantics = createBootstrapSemantics(instance.getClassifier());
            return instance.get(semantics.getProperty(property)).getValues();           
        } catch (Exception e) {
            return new Vector<ValueSpecificationImpl<ClassInstance,ClassInstance,Object>>();
        }
    }
    
    protected void setDependendProperties() {
        for (ClassInstance<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> instance: getInstances()) {
            ((BootstrapInstance)instance).insertDefaults();
            ((BootstrapInstance)instance).insertOppositeValues();
        }
        for (ClassInstance<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> instance: getInstances()) {
            ((BootstrapInstance)instance).insertSupersetValues();        
        }
    }
}
