package hub.sam.mof.bootstrap;

import java.util.*;
import cmof.*;
import cmofimpl.reflection.*;
import cmofimpl.util.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.instancemodel.ValueSpecification;

public class ClassInstanceWrapper extends ClassInstance<UmlClass,Property,java.lang.Object> {

    private final ClassInstance<ClassInstance,ClassInstance,Object> instance;
    private final BootstrapModel model;
    private final BootstrapExtent extent;
    
    protected ClassInstanceWrapper(ClassInstance instance, BootstrapModel model, BootstrapExtent extent) {
        super(null, null, null);
        this.instance = instance;
        this.model = model;
        this.extent = extent;
    }
    
    protected ClassInstance<ClassInstance,ClassInstance,Object> getInstance() {
        return instance;
    }
    
    public UmlClass getClassifier() {
        return (UmlClass)extent.getObjectForInstance(model.getWrapper(instance.getClassifier()));        
    }
            
    public StructureSlot<UmlClass,Property,java.lang.Object> get(Property property) {   
        return new StructureSlotWrapper(instance.get(((ClassInstanceWrapper)((ObjectImpl)property).getClassInstance()).instance));
    }
        
    public ClassInstance<UmlClass,Property,java.lang.Object> getComposite() {
        return model.getWrapper(instance.getComposite()); 
    }

    class StructureSlotWrapper extends StructureSlot<UmlClass,Property,Object> {
        private final StructureSlot<ClassInstance,ClassInstance,Object> slot;
        protected StructureSlotWrapper(StructureSlot<ClassInstance,ClassInstance,Object> slot) {
            super(null);
            this.slot = slot;
        }        
        public ValueSpecificationList<UmlClass,Property,Object> getValuesAsList() {
            if (slot == null) {
                return new ValueList(new Vector<ValueSpecification<ClassInstance,ClassInstance,Object>>(), null);
            }
            return new ValueList(slot.getValues(), new Wrapper<ValueSpecification<UmlClass,Property,Object>,ValueSpecification<ClassInstance,ClassInstance,Object>>() {
                public ValueSpecification<UmlClass, Property, Object> getE(ValueSpecification<ClassInstance, ClassInstance, Object> forO) {
                    if (forO.asDataValue() != null) {
                        return model.getMofModel().createPrimitiveValue(forO.asDataValue().getValue());
                    } else {
                        return model.getMofModel().createInstanceValue(model.getWrapper(forO.asInstanceValue().getInstance()));
                    }
                }

                public ValueSpecification<ClassInstance, ClassInstance, Object> getO(ValueSpecification<UmlClass, Property, Object> forE) {                    
                    if (forE.asDataValue() != null) {
                        return (ValueSpecification)model.createPrimitiveValue(forE.asDataValue().getValue());
                    } else {
                        return (ValueSpecification)model.createInstanceValue((ClassInstance)((ClassInstanceWrapper)forE.asInstanceValue().getInstance()).instance);
                    }
                }                
            });  
        }
        
        class ValueList extends ReadOnlyListWrapper<ValueSpecification<UmlClass,Property,Object>,ValueSpecification<ClassInstance,ClassInstance,Object>> implements ValueSpecificationList<UmlClass,Property,Object> {
            public ValueList(List<? extends ValueSpecification<ClassInstance, ClassInstance, Object>> wrapped, Wrapper<ValueSpecification<UmlClass, Property, Object>, ValueSpecification<ClassInstance, ClassInstance, Object>> wrapper) {
                super(wrapped, wrapper);
            }           
        }
    }
}
