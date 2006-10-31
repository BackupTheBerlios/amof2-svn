package cmofimpl.bootstrap;

import hub.sam.mof.instancemodel.*;
import cmofimpl.reflection.ExtentImpl;

public class EnumerationImpl extends cmof.EnumerationImpl {

    public EnumerationImpl(ClassInstance instance, ExtentImpl extent) {
        super(instance, extent);
    }

    public String getName() {
        return (String)get("name");
    }
    
    public String getQualifiedName() {
        return getName();
    }
    
    public cmof.common.ReflectiveSequence<? extends cmof.EnumerationLiteral>getOwnedLiteral() {
        return (cmof.common.ReflectiveSequence<? extends cmof.EnumerationLiteral>)get("ownedLiteral");
    }
    public cmof.Element getOwner() {
        return (cmof.Element)get("owner");
    }
}
