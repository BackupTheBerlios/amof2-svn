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

package hub.sam.mof.instancemodel;

import java.util.*;
import cmof.exception.CompositeViolation;

public class ClassInstance<C,P,DataValue> {
    private final Map<P, StructureSlot<C,P,DataValue>> slots = new HashMap<P, StructureSlot<C,P,DataValue>>();
    private final String id;
    private C classifier;
    private ClassInstance<C,P,DataValue> composite = null;
    private Collection<ClassInstance<C,P,DataValue>> components = new HashSet<ClassInstance<C,P,DataValue>>();
    private InstanceModel<C,P,DataValue> model;
    
    protected ClassInstance(String id, C classifier, InstanceModel<C,P,DataValue> model) {
        this.id = id;
        this.classifier = classifier;
        this.model = model;
    }
    
    public void addValue(P feature, ValueSpecificationImpl<C,P,DataValue> value) {
        if (feature == null) {
            throw new NullPointerException();
        }
        StructureSlot<C,P,DataValue> slot = slots.get(feature);
        if (slot == null) {
            slot = new StructureSlot<C,P,DataValue>(feature);
            slots.put(feature, slot);
        }
        slot.addValue(value);
    } 
    
    public C getClassifier() {
        return classifier;
    }
    
    public void setClassifier(C classifier) {
        if (this.classifier == null) {
            this.classifier = classifier;
        } else {
            throw new IllegalAccessError("this property is final");
        }
    }
    
    public Collection<StructureSlot<C,P,DataValue>> getSlots() {
        return slots.values();
    }

    public StructureSlot<C,P,DataValue> get(P property) {
        return slots.get(property);
    }
    
    public String getId() {
        return id;
    }

    public ClassInstance<C, P, DataValue> getComposite() {
        return composite;
    }

    public final void setComposite(ClassInstance<C, P, DataValue> composite) {
        if (getComposite() != null && composite != null && composite != getComposite()) {
            throw new CompositeViolation(composite);
        }
        if (this.composite != null) {
            this.composite.components.remove(this);
        }
        this.composite = composite;
        if (this.composite != null) {            
            this.composite.components.add(this);
        }
        if (this.composite != null) {
            model.removeOutermostComposite(this);
        } else {
            model.addOutermostComposite(this);
        }                
    }
    
    public final void changeComposite(ClassInstance<C, P, DataValue> newComposite) {
        if (this.composite == null && newComposite != null) {
            model.removeOutermostComposite(this);
        } else if (newComposite == null) {
            model.addOutermostComposite(this);
        }
        if (this.composite != null) {
            this.composite.components.remove(this);
        }
        this.composite = newComposite;
        if (this.composite != null) {
            this.composite.components.add(this);
        }        
    }
    
    public Collection<ClassInstance<C,P,DataValue>> getComponentsAsCollection() {
        return Collections.unmodifiableCollection(components);
    }
    
    public void delete() {
        model.deleteInstance(this);
    }
    
    protected void finalize() {
        for (StructureSlot slot: slots.values()) {
            slot.finalize();
        }
        slots.clear();
        components.clear();
        model = null;
    }
}
