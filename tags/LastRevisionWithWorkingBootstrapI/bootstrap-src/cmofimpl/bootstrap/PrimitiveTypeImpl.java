package cmofimpl.bootstrap;

import hub.sam.mof.instancemodel.*;
import cmofimpl.reflection.ExtentImpl;

public class PrimitiveTypeImpl extends cmof.PrimitiveTypeImpl {

    public PrimitiveTypeImpl(ClassInstance instance, ExtentImpl extent) {
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
