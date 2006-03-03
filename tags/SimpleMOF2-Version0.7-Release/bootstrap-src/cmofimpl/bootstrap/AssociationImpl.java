package cmofimpl.bootstrap;

import cmofimpl.instancemodel.ClassInstanceImpl;
import cmofimpl.reflection.ExtentImpl;

public class AssociationImpl extends cmof.AssociationImpl {

    public AssociationImpl(ClassInstanceImpl instance, ExtentImpl extent) {
        super(instance, extent);
    }

    public String getName() {
        return (String)get("name");
    }
    
    public String getQualifiedName() {
        return getName();
    }
    
    public cmof.Package getPackage() {
        return (cmof.Package)getOwner();
    }
    
    public cmof.Element getOwner() {
        return (cmof.Element)get("owner");
    }
}
