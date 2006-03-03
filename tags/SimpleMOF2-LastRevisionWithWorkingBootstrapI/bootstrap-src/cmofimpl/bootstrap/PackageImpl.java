package cmofimpl.bootstrap;

import hub.sam.mof.instancemodel.*;
import cmofimpl.reflection.ExtentImpl;

public class PackageImpl extends cmof.PackageImpl {

    public PackageImpl(ClassInstance instance, ExtentImpl extent) {
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

    public cmof.common.ReflectiveCollection<? extends cmof.Type> getOwnedMember() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Type>)get("ownedMemberPackage");
    }
    
    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        return getOwnedMember();
    }
    public cmof.common.ReflectiveSequence<? extends cmof.Type> getOwnedType() {
        return (cmof.common.ReflectiveSequence<? extends cmof.Type>)get("ownedType");
    }
    
    public cmof.common.ReflectiveSequence<? extends cmof.Package>getNestedPackage() {
        return (cmof.common.ReflectiveSequence<? extends cmof.Package>)get("nestedPackage");
    }
    
    public cmof.Element getOwner() {
        return (cmof.Element)get("owner");
    }
}
