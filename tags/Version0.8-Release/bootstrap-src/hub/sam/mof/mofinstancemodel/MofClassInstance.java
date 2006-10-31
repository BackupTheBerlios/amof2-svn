/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
*/

package hub.sam.mof.mofinstancemodel;

import java.util.*;
import cmof.*;
import hub.sam.mof.instancemodel.*;

public class MofClassInstance extends ClassInstance<UmlClass,Property,java.lang.Object> {

    protected MofClassInstance(UmlClass classifier, MofInstanceModel model) {
        this(MofClassifierSemantics.createClassClassifierForUmlClass(classifier), model);
        this.model = model;
    }
	
    private MofClassInstance(MofClassSemantics instanceClassifier, MofInstanceModel model) {
        super(new Integer(ids++).toString(), (UmlClass)instanceClassifier.getClassifier(), model);//TODO
        this.model = model;
        id = ids;
        instanceClass = instanceClassifier;
        if (instanceClassifier != null) {
            initialize();
        }
    }
    
    private MofInstanceModel model;
    private MofClassSemantics instanceClass;
    private Map<Property, MofStructureSlot> slotForProperty = new HashMap<Property, MofStructureSlot>();       
    private boolean valid = true;
    private final int id;
    private Collection<MofClassInstance> components = new HashSet<MofClassInstance>(); 
    
    private static int ids = 0;
                          
    /**
     * Creates all slots for the instance and sets default values.
     */
    protected void initialize() {                                    
        for (Property property: instanceClass.getFinalProperties()) {
            MofStructureSlot newSlot = new MofStructureSlot(this, property);
            slotForProperty.put(property, newSlot);
        }  
        for (core.abstractions.elements.Element member: getClassifier().getMember()) {
            if (member instanceof Property) {
                Property property = (Property)member;
                Type type = property.getType();
                String defaultValue = collectDefaultValue(property);
                if (type instanceof cmof.DataType && defaultValue != null) {
                    addValue(property, model.createPrimitiveValue(hub.sam.mof.reflection.FactoryImpl.staticCreateFromString((DataType)type, defaultValue)));
                }
            }
        }
    }     
    
    private String collectDefaultValue(Property property) {
        String result = property.getDefault();
        if (result != null) {
            return result;
        } else {
            for (Property redefinedProperty: property.getRedefinedProperty()) {
                result = collectDefaultValue(redefinedProperty);
                if (result != null) {
                    return result;
                }
            }
            return null;
        }        
    }
    
    public UmlClass getClassifier() {
        checkValid();
        return super.getClassifier();
    }
    
    /** Returns all slots of this instance. A Slot contains the value of a signle detinct feature of the defining metaclass.*/
    public Collection<MofStructureSlot> getSlot() {
        checkValid();
        return new HashSet<MofStructureSlot>(slotForProperty.values());        
    }

    /** Returns the slot that carries the value for the feature that is the final redefining feature for the given feature 
     * and the metaclass of this instance. */
    public MofStructureSlot get(Property definingFeature) {
        checkValid();
        return slotForProperty.get(instanceClass.getFinalProperty(definingFeature));
    }
    
    /** Returns the values for the feature that is the final redefining feature for the given feature and the metaclass of
     * this instance.
     */
    public MofValueSpecificationList getValuesOfFeature(Property definingFeature) {
        MofStructureSlot theSlot = get(definingFeature);
        if (theSlot == null) {
            throw new IllegalArgumentException();
        } else {
            return (MofValueSpecificationList)theSlot.getValuesAsList();
        }
    }
    
    public void addValue(Property feature, ValueSpecificationImpl<UmlClass,Property,java.lang.Object> value) {        
        if (feature.getUpper() == 1) {
            MofValueSpecificationList values = getValuesOfFeature(feature);
            if (values.size() == 0) {
                values.add(value);
            } else {
                values.set(0, value);
            }
        } else {           
            getValuesOfFeature(feature).add(value);
        }
    }
    
    private void checkValid() {
        if (!valid) {
            throw new RuntimeException("access of an invalid instance");
        }
    }
    
    /**
     * Deletes itself, all components, removes all references to itself or to
     * all components.
     */
    public void delete() {
        for (MofClassInstance component: components) {
            component.delete();
        }
        MofValueSpecificationList.deleteInstance(this);
        valid = false;       
        slotForProperty = null;
        super.delete();
    }   
        
    public MofClassSemantics getInstanceClassifier() {
        return instanceClass;        
    } 
    
    public Collection<MofClassInstance> getComponents() {
        return components;
    }        
    
    public Collection<StructureSlot<UmlClass,Property,java.lang.Object>> getSlots() {
        return (Collection)slotForProperty.values();
    }
}
