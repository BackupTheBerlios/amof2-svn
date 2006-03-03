package cmofimpl.bootstrap;

import cmof.common.ReflectiveCollection;
import cmofimpl.instancemodel.ClassInstanceImpl;
import cmofimpl.reflection.ExtentImpl;

public class PropertyImpl extends cmof.PropertyImpl {

    public PropertyImpl(ClassInstanceImpl instance, ExtentImpl extent) {
        super(instance, extent);
    }

    public String getName() {
        return (String)get("name");
    }
    
    public String getQualifiedName() {
        return getName();
    }
    
    public void setName(String value) {
        set("name", value);
    }
    
    public long getUpper() {
        return (Long)get("upper");
    }
    
    public int getLower() {
        return (Integer)get("lower");
    }
    
    public boolean isUnique() {
        return (Boolean)get("isUnique");
    }
    
    public boolean isOrdered() {
        return (Boolean)get("isOrdered");
    }
    
    public boolean isReadOnly() {
        return (Boolean)get("isReadOnly");
    }
    
    public boolean isDerived() {
        return (Boolean)get("isDerived");
    }
    
    public boolean isDerivedUnion() {
        return (Boolean)get("isDerivedUnion");
    }
    
    public ReflectiveCollection<cmof.Property> getSubsettedProperty() {
        return (ReflectiveCollection<cmof.Property>)get("subsettedProperty");
    }
                                
    public ReflectiveCollection<cmof.Property> getRedefinedProperty() {
        return (ReflectiveCollection<cmof.Property>)get("redefinedProperty");
    }
    
    public ReflectiveCollection<cmof.Property> getRedefinedElement() {
        return (ReflectiveCollection<cmof.Property>)get("redefinedProperty");
    }
    
    public cmof.Property getOpposite() {
        return (cmof.Property)get("opposite");
    }
    
    public cmof.Association getOwningAssociation() {
        cmof.Element owner = getOwner();
        if (owner instanceof cmof.Association) {
            return (cmof.Association)owner;
        } else {
            return null;
        }
    }
    
    public cmof.Type getType() {
        return (cmof.Type)get("type");
    }

    public String getDefault() {
        return (String)get("default");
    }
        
    public cmof.Element getOwner() {
        return (cmof.Element)get("owner");
    }
}
