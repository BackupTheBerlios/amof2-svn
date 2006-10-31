package cmofimpl.bootstrap;

import cmofimpl.instancemodel.ClassInstanceImpl;
import cmofimpl.reflection.ExtentImpl;

public class PrimitiveTypeImpl extends cmof.PrimitiveTypeImpl {

    public PrimitiveTypeImpl(ClassInstanceImpl instance, ExtentImpl extent) {
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
    public cmof.Element getOwner() {
        return (cmof.Element)get("owner");
    }
}
