package hub.sam.mof.bootstrap;

import java.util.*;
import hub.sam.mof.instancemodel.*;

public class BootstrapInstance extends ClassInstance<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> {

    private final BootstrapModel model;
    
    protected BootstrapInstance(String id, ClassInstance<ClassInstance, ClassInstance, Object> classifier, BootstrapModel model) {
        super(id, classifier, model);
        this.model = model;
    }
        
    private ClassInstance<ClassInstance,ClassInstance,Object> getType(ClassInstance<ClassInstance,ClassInstance,Object> property) {
        BootstrapSemantics semantics = model.createBootstrapSemantics(property.getClassifier());        
        try {            
            return property.get(semantics.getProperty("type")).getValues().get(0).asInstanceValue().getInstance();
        } catch (NullPointerException e) {
            return null;
        }  
    }
    
    private boolean isDataProperty(ClassInstance<ClassInstance,ClassInstance,Object> property) {
        BootstrapSemantics semantics = model.createBootstrapSemantics(property.getClassifier());
        String typeClassifierName = null;
        try {
            typeClassifierName = (String) 
            model.get(getType(property).getClassifier(),"name");
        } catch (NullPointerException e) {
            return false;
        } 
        return typeClassifierName.equals(cmof.PrimitiveType.class.getSimpleName()) || typeClassifierName.equals(cmof.Enumeration.class.getSimpleName());
    }
    
    private String getDefault(ClassInstance<ClassInstance,ClassInstance,Object> property) {
        return (String)model.get(property, "default");
    }
          
    protected void insertDefaults() {
        BootstrapSemantics semantics = model.createBootstrapSemantics(getClassifier());
        for (ClassInstance<ClassInstance,ClassInstance,Object> property: semantics.getFinalProperties()) {
            if (isDataProperty(property)) {
                if (get(property) == null) {
                    String stringRepresentation = getDefault(property);
                    Object defaultValue = null;
                    if (stringRepresentation != null) {
                        String name = (String)model.get(getType(property), "name");
                        if (name.equals(core.primitivetypes.String.class.getSimpleName())) {
                            defaultValue = stringRepresentation;
                        } else if (name.equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                            defaultValue = new Boolean(stringRepresentation);
                        } else if (name.equals(core.primitivetypes.Integer.class.getSimpleName())) {
                            defaultValue = new Integer(stringRepresentation);
                        } else if (name.equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                            if (stringRepresentation.equals("*")) {
                                defaultValue =  new Long(-1);
                            } else {
                                defaultValue = new Long(stringRepresentation);
                            }
                        } else {
                            throw new RuntimeException("assert");
                        }
                    
                        addValue(property, model.createPrimitiveValue(defaultValue));
                    }
                }
            }
        }
    }
            
    private Map<ClassInstance, Collection<ClassInstance>> updatedOpposites = new HashMap<ClassInstance, Collection<ClassInstance>>();
    protected void insertOppositeValues() {      
        for (StructureSlot<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> slot: new Vector<StructureSlot<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object>>(getSlots())) {
            ClassInstance<ClassInstance,ClassInstance,Object> forProperty = slot.getProperty();
            ClassInstance<ClassInstance,ClassInstance,Object> opposite = (ClassInstance<ClassInstance,ClassInstance,Object>)model.get(forProperty,"opposite");
            if (opposite != null && model.get(opposite.getComposite().getClassifier(),"name").equals("Class")) {
                for (ValueSpecificationImpl value: get(forProperty).getValues()) {
                    if (value.asInstanceValue() != null) {
                        BootstrapInstance oppositeValue = (BootstrapInstance)value.asInstanceValue().getInstance();
                        Collection<ClassInstance> alreadyUpdatedValues = oppositeValue.updatedOpposites.get(opposite);
                        if (alreadyUpdatedValues == null || !alreadyUpdatedValues.contains(this)) {                            
                            BootstrapSemantics oppositeSemantics = model.createBootstrapSemantics(oppositeValue.getClassifier());
                            ClassInstance finalOpposite = oppositeSemantics.getFinalProperty(opposite);
                            if (finalOpposite != null) {
                                oppositeValue.addValue(oppositeSemantics.getFinalProperty(opposite), model.createInstanceValue(this));
                            } else {
                                oppositeValue.addValue(opposite, model.createInstanceValue(this));
                            }
                            Collection<ClassInstance> updatedValues = this.updatedOpposites.get(forProperty);
                            if (updatedValues == null) {
                                updatedValues = new Vector<ClassInstance>();
                                this.updatedOpposites.put(forProperty, updatedValues);
                            }
                            updatedValues.add(oppositeValue);
                        } 
                    } 
                }        
            }
        }      
    }
    
    private void collectSupersets(ClassInstance<ClassInstance,ClassInstance,Object> forProperty,Collection<ClassInstance<ClassInstance,ClassInstance,Object>> supersets ) {
        BootstrapSemantics semantics = model.createBootstrapSemantics(this.getClassifier());        
        supersets.addAll(semantics.getSubsettedProperties(forProperty));
        for(ClassInstance<ClassInstance,ClassInstance,Object> superset: semantics.getSubsettedProperties(forProperty)) {
            for (ValueSpecificationImpl<ClassInstance,ClassInstance,Object> redefinedOfSuperset: model.getList(superset, "redefinedProperty")) {               
                collectSupersets(redefinedOfSuperset.asInstanceValue().getInstance(), supersets);                           
            }
        }
    }
       
    private void insertSupersetValues(ClassInstance<ClassInstance,ClassInstance,Object> forProperty) {
        BootstrapSemantics semantics = model.createBootstrapSemantics(this.getClassifier());
        // collect all supersets, all means superset of supersets and supersets of redefines
        Collection<ClassInstance<ClassInstance,ClassInstance,Object>> supersets = new HashSet<ClassInstance<ClassInstance,ClassInstance,Object>>();        
        collectSupersets(forProperty,supersets);
        // collapse them to final properties
        Collection<ClassInstance<ClassInstance,ClassInstance,Object>> finalSupersets = new HashSet<ClassInstance<ClassInstance,ClassInstance,Object>>();
        for (ClassInstance<ClassInstance,ClassInstance,Object> superset: supersets) {
            if (model.get(superset.getComposite().getClassifier(),"name").equals("Class")) {
                finalSupersets.add(semantics.getFinalProperty(superset));
            }
        }
        // add the values to them        
        for (ClassInstance<ClassInstance,ClassInstance,Object> finalSupersetProperty: finalSupersets) {                                        
            for (ValueSpecificationImpl value: get(forProperty).getValues()) {                
                addValue(finalSupersetProperty, value);
            }            
        }
    }
    
    protected void insertSupersetValues() {
        for (StructureSlot<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> slot: new Vector<StructureSlot<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object>>(getSlots())) {
            insertSupersetValues(slot.getProperty());
        }
    }
          
    public Collection<StructureSlot<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object>> getSlots() {  
        return super.getSlots();
    }

    public StructureSlot<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> get(ClassInstance<ClassInstance,ClassInstance,Object> property) {
        return super.get(property);
    }
    
    public String toString() {
        Object name = model.get((ClassInstance)this, "name");
        if (name != null) {
            return name.toString();
        } else {
            return super.toString();
        }
    }
}
