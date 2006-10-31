package cmofimpl.bootstrap;

import cmofimpl.instancemodel.ClassInstanceImpl;
import cmofimpl.reflection.ExtentImpl;

public class EnumerationImpl extends cmof.EnumerationImpl {

    public EnumerationImpl(ClassInstanceImpl instance, ExtentImpl extent) {
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
