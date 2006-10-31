package cmofimpl.bootstrap;

import hub.sam.mof.instancemodel.*;
import cmofimpl.reflection.ExtentImpl;

public class AssociationImpl extends cmof.AssociationImpl {

    public AssociationImpl(ClassInstance instance, ExtentImpl extent) {
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
